package com.twygo.usacucar.Repositorios;

import com.twygo.usacucar.Entidades.FilaProcessamento;
import com.twygo.usacucar.Enums.NovoStatusEnvioEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("desenv")
@ExtendWith(MockitoExtension.class)
class FilaProcessamentoRepositoryTest {

    @Mock
    private FilaProcessamentoRepository repositoryMock;

    @InjectMocks
    private FilaProcessamento filaProcessamentoMock;

    @Mock
    private NovoStatusEnvioEnum novoStatusEnvioEnum;

    @BeforeEach
    void processamento(){
        filaProcessamentoMock.setSq_fila("0");
        filaProcessamentoMock.setId_usuario("12");
        filaProcessamentoMock.setId_inscricao("34");
        novoStatusEnvioEnum = NovoStatusEnvioEnum.FINALIZADO;

    }

    @Test
    void atualizaJsonEnvio() {


        when(repositoryMock.atualizaJsonEnvio(filaProcessamentoMock.getSq_fila(),"{\"nome\":\"teste\"}")).thenReturn(1);

        int modificado = repositoryMock.atualizaJsonEnvio(filaProcessamentoMock.getSq_fila(),"{\"nome\":\"teste\"}");
        assertThat(modificado).isNotZero();

    }

    @Test
    void atualizaJsonRetorno() {

        when(repositoryMock.atualizaJsonRetorno(filaProcessamentoMock.getSq_fila(),"{\"usuario\":\"criado com sucesso\"}")).thenReturn(1);

        int modificado = repositoryMock.atualizaJsonRetorno(filaProcessamentoMock.getSq_fila(),"{\"usuario\":\"criado com sucesso\"}");

        assertThat(modificado).isNotZero();
    }

    @Test
    void atualizaStatus() {

        when(repositoryMock.atualizaStatus(filaProcessamentoMock.getSq_fila(),novoStatusEnvioEnum.getCode())).thenReturn(1);

        int modificado = repositoryMock.atualizaStatus(filaProcessamentoMock.getSq_fila(),novoStatusEnvioEnum.getCode());

        assertThat(modificado).isNotZero();
    }

    @Test
    void atualizaIdUsuario() {

        when(repositoryMock.atualizaIdUsuario(filaProcessamentoMock.getId_usuario(),filaProcessamentoMock.getId_usuario())).thenReturn(1);

        int modificado = repositoryMock.atualizaIdUsuario(filaProcessamentoMock.getId_usuario(),filaProcessamentoMock.getId_usuario());

        assertThat(filaProcessamentoMock.getId_usuario()).isNotEmpty();
        assertThat(modificado).isNotZero();

    }

    @Test
    void atualizaIdInscricao() {

        when(repositoryMock.atualizaIdInscricao(filaProcessamentoMock.getId_inscricao(),filaProcessamentoMock.getSq_fila())).thenReturn(1);

        int modificado = repositoryMock.atualizaIdInscricao(filaProcessamentoMock.getId_inscricao(),filaProcessamentoMock.getSq_fila());

        assertThat(filaProcessamentoMock.getId_inscricao()).isNotEmpty();
        assertThat(modificado).isNotZero();
    }

    @Test
    void testAtualizaStatus() {

        Date acesso = new Date();
        Date aprovacao = new Date();

        when(repositoryMock.atualizaStatus(acesso,10.0,aprovacao,filaProcessamentoMock.getSq_fila())).thenReturn(1);

        int modificado = repositoryMock.atualizaStatus(acesso,10.0,aprovacao,filaProcessamentoMock.getSq_fila());

        assertThat(filaProcessamentoMock.getSq_fila()).isNotEmpty();
        assertThat(modificado).isNotZero();

    }
}