package com.twygo.usacucar.Regra;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.twygo.usacucar.Entidades.FilaProcessamento;
import com.twygo.usacucar.Enums.NovoStatusEnvioEnum;
import com.twygo.usacucar.Repositorios.FilaProcessamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilaProcessamentoRegra {

    private FilaProcessamentoRepository repository;

    @Autowired
    public FilaProcessamentoRegra(FilaProcessamentoRepository repository) {
        this.repository = repository;
    }

    public List<FilaProcessamento> buscarRegistrosOrdenadosTpAcao() {
        List<FilaProcessamento> registros = repository.findAll();
        return registros;
    }

    public void finalizaAcao(FilaProcessamento registro, String body, String requestBody, NovoStatusEnvioEnum novoStatusEnvioEnum) {

        repository.atualizaStatus(registro.getSq_fila(), novoStatusEnvioEnum.getCode());
        repository.atualizaJsonRetorno(registro.getSq_fila(), body);
        repository.atualizaJsonEnvio(registro.getSq_fila(),requestBody);


        JsonObject jsonResponse = JsonParser.parseString(body).getAsJsonObject();

        JsonObject student = jsonResponse.getAsJsonObject("student");
        if(!student.isEmpty()){String studentId = String.valueOf(student.get("id").getAsLong());}


        JsonObject event = jsonResponse.getAsJsonObject("event");
        if(!event.isEmpty()){String cursoId = String.valueOf(event.get("id").getAsLong());}



        switch (registro.getTp_acao()) {
            case "1":
                repository.atualizaIdUsuario(registro.getSq_fila(), studentId);
                break;
            case "6":
                repository.atualizaIdInscricao(registro.getSq_fila(), cursoId);
                break;
            case "7":
                repository.atualizaStatus(registro.getSq_fila(), registro.getSq_fila());
                break;
        }





    }

}