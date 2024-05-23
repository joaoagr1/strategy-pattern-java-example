package com.twygo.usacucar.Estrategias;

import com.google.gson.Gson;
import com.twygo.usacucar.Entidades.FilaProcessamento;
import com.twygo.usacucar.Enums.AcaoHttp;
import com.twygo.usacucar.Enums.NovoStatusEnvio;
import com.twygo.usacucar.Regra.FilaProcessamentoRegra;
import com.twygo.usacucar.Interfaces.Strategy;
import com.twygo.usacucar.Regra.TokenRegra;
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
import java.util.HashMap;
import java.util.Map;

@Service
@Data
@Slf4j
public class InscreverUsuarioCursoStrategy implements Strategy {

    @Value("${baseUrl}")
    private String baseUrl;

    @Autowired
    private FilaProcessamentoRegra filaProcessamentoRegra;

    @Autowired
    private TokenRegra tokenRegra;

    @Autowired
    private HttpClient httpClient;


    @Override
    public void executarAcao(FilaProcessamento registro) {
        inscreverUsuarioCurso(registro);
    }

    private String createInscricaoRequestBody(FilaProcessamento registro) {
        Map<String, String> requestBodyMap = new HashMap<>();
        requestBodyMap.put("event_id", registro.getId_curso());
        requestBodyMap.put("student_id", registro.getId_usuario());

        return new Gson().toJson(requestBodyMap);
    }

    private HttpResponse<String> sendRequestWithAuthorization(String accessToken, String requestBody) throws IOException, HttpException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/students_events"))
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    @Override
    public void inscreverUsuarioCurso(FilaProcessamento registro) {
        try {
            String accessToken = tokenRegra.getAccessToken();
            String requestBody = createInscricaoRequestBody(registro);
            HttpResponse<String> response = sendRequestWithAuthorization(accessToken, requestBody);

            AcaoHttp action = AcaoHttp.fromStatusCode(response.statusCode());
            filaProcessamentoRegra.finalizaAcao(registro, response.body(), requestBody, action.getStatus());

        } catch (IOException | HttpException | InterruptedException e) {
            filaProcessamentoRegra.finalizaAcao(registro, e.getMessage(), String.valueOf(e.getCause()), NovoStatusEnvio.ERRO);
        }
    }

    @Override
    public void criarUsuario(FilaProcessamento registro) {
        throw new UnsupportedOperationException("Não implementado");
    }

    @Override
    public void mostrarStatusConclusaoCurso(FilaProcessamento registro) {
        throw new UnsupportedOperationException("Não implementado");
    }
}
