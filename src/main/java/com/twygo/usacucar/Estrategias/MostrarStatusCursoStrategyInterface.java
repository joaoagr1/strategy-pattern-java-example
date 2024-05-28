package com.twygo.usacucar.Estrategias;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twygo.usacucar.Entidades.FilaProcessamento;
import com.twygo.usacucar.Enums.AcaoHttpEnum;
import com.twygo.usacucar.Enums.NovoStatusEnvioEnum;
import com.twygo.usacucar.Regra.FilaProcessamentoRegra;
import com.twygo.usacucar.Regra.TokenRegra;
import com.twygo.usacucar.Repositorios.FilaProcessamentoRepository;
import com.twygo.usacucar.Interfaces.StrategyInterface;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

@Service
@Data
@Slf4j
public class MostrarStatusCursoStrategyInterface implements StrategyInterface {

    @Value("${baseUrl}")
    private String baseUrl;

    @Autowired
    private FilaProcessamentoRegra filaProcessamentoRegra;

    @Autowired
    private TokenRegra tokenRegra;

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private FilaProcessamentoRepository filaProcessamentoRepository;

    @Override
    public void executarAcao(FilaProcessamento registro) throws HttpException, IOException, ParseException, InterruptedException {
        mostrarStatusConclusaoCurso(registro);
    }

    private HttpResponse<String> sendRequestWithAuthorization(String accessToken, FilaProcessamento registro) throws IOException, HttpException, InterruptedException {
        String url = baseUrl + "students_events/" + registro.getId_inscricao();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + accessToken)
                .GET()
                .build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Override
    public void mostrarStatusConclusaoCurso(FilaProcessamento registro) throws InterruptedException, ParseException, HttpException {
      try {
        String accessToken = tokenRegra.getAccessToken();
        HttpResponse<String> response = sendRequestWithAuthorization(accessToken, registro);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonResponse = objectMapper.readValue(response.body(), new TypeReference<>() {});
        Map<String, Object> event = (Map<String, Object>) jsonResponse.get("event");

        String aprovado = (String) event.get("approved_at");
        Double progress_score = (Double) event.get("progress_score");
        String last_access_at = (String) event.get("last_access_at");
        String sq_fila = String.valueOf(registro.getSq_fila());

        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        isoFormat.setTimeZone(TimeZone.getTimeZone("America/Brasilia"));
        Date aprovadoDate = aprovado != null ? isoFormat.parse(aprovado) : null;
        Date last_access_atDate = last_access_at != null ? isoFormat.parse(last_access_at) : null;

        filaProcessamentoRepository.atualizaStatus(last_access_atDate, progress_score, aprovadoDate, sq_fila);

        AcaoHttpEnum action = AcaoHttpEnum.fromStatusCode(response.statusCode());

          filaProcessamentoRegra.finalizaAcao(registro, response.body(), registro.toString(), action.getStatus());
      } catch (Exception e) {
          filaProcessamentoRegra.finalizaAcao(registro, e.getMessage(), String.valueOf(e.getCause()), NovoStatusEnvioEnum.ERRO);
              }
    }



    @Override
    public void criarUsuario(FilaProcessamento registro) {
        throw new UnsupportedOperationException("Não implementado");
    }

    @Override
    public void inscreverUsuarioCurso(FilaProcessamento registro) {
        throw new UnsupportedOperationException("Não implementado");
    }
}
