package com.twygo.usacucar.Regra;

import com.twygo.usacucar.Entidades.FilaProcessamento;
import com.twygo.usacucar.Enums.NovoStatusEnvio;
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

    public void finalizaAcao(FilaProcessamento registro, String body, String requestBody, NovoStatusEnvio novoStatusEnvio) {

        repository.atualizaStatus(registro.getSq_fila(), novoStatusEnvio.getCode());
        repository.atualizaJsonRetorno(registro.getSq_fila(), body);
        repository.atualizaJsonEnvio(registro.getSq_fila(),requestBody);

        if (registro.getId_usuario() != null) {repository.atualizaIdUsuario(registro.getSq_fila(), registro.getId_usuario());}
        if( registro.getId_usuario() != null) {repository.atualizaIdInscricao(registro.getSq_fila(), registro.getId_inscricao());}


    }

}