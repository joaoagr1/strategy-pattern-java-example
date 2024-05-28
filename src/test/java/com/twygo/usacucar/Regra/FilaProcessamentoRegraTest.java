package com.twygo.usacucar.Regra;

import com.twygo.usacucar.Enums.NovoStatusEnvioEnum;
import com.twygo.usacucar.Entidades.FilaProcessamento;
import com.twygo.usacucar.Regra.FilaProcessamentoRegra;
import com.twygo.usacucar.Repositorios.FilaProcessamentoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FilaProcessamentoRegraTest {

    @Mock
    private FilaProcessamentoRepository repository;

    @InjectMocks
    private FilaProcessamentoRegra filaProcessamentoRegra;

    @Test
    @DisplayName("Verificando busca de registros ordenados")
    void testBuscarRegistrosOrdenadosTpAcao() {
        // Mock de dados de registros
        FilaProcessamento registro1 = new FilaProcessamento();
        registro1.setSq_fila("1");
        FilaProcessamento registro2 = new FilaProcessamento();
        registro2.setSq_fila("2");
        List<FilaProcessamento> registrosMock = Arrays.asList(registro1, registro2);

        // Configurando comportamento do mock do repositório
        when(repository.findAll()).thenReturn(registrosMock);

        // Chamando o método a ser testado
        List<FilaProcessamento> registrosRetornados = filaProcessamentoRegra.buscarRegistrosOrdenadosTpAcao();

        // Verificando se a lista retornada não é nula e contém os registros esperados
        assertThat(registrosRetornados).isNotNull();
        assertThat(registrosRetornados).containsExactly(registro1, registro2);
    }



    @Test
    @DisplayName("Teste de finalização de ação com registro não nulo")
    void testFinalizaAcaoRegistroNaoNulo() {
        // Criando um registro de exemplo
        FilaProcessamento registro = new FilaProcessamento();
        registro.setSq_fila("1");

        // Chamando o método a ser testado
        filaProcessamentoRegra.finalizaAcao(registro, "body", "requestBody", NovoStatusEnvioEnum.FINALIZADO);

        // Verifica se os métodos de atualização foram chamados corretamente com os parâmetros esperados
        verify(repository).atualizaStatus("1", NovoStatusEnvioEnum.FINALIZADO.getCode());
        verify(repository).atualizaJsonRetorno("1", "body");
        verify(repository).atualizaJsonEnvio("1", "requestBody");
        verify(repository, never()).atualizaIdUsuario(anyString(), anyString());
        verify(repository, never()).atualizaIdInscricao(anyString(), anyString());
    }
}
