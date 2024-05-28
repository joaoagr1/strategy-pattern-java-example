package com.twygo.usacucar.Regra;

import com.twygo.usacucar.Entidades.Acesso;
import com.twygo.usacucar.Regra.TokenRegra;
import com.twygo.usacucar.Repositorios.AcessoRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("desenv")
@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

    @Mock
    private AcessoRepository acessoRepositoryMock;

    @InjectMocks
    private TokenRegra tokenRegra;

    @Mock
    private HttpURLConnection connection;

    @Mock
    private Acesso mockAcesso;

    @BeforeEach
    void requisicaoSimulada() throws IOException {
        mockAcesso = new Acesso();
        mockAcesso.setId(1L);
        mockAcesso.setUsernameTwygo("teste");
        mockAcesso.setPasswordTwygo("senha");
        mockAcesso.setUrl("http://teste.com.br");
        when(acessoRepositoryMock.findAll()).thenReturn(Collections.singletonList(mockAcesso));
    }

    @Test
    @DisplayName("verificando se está pegando o acesso")
    void findAcessos() {
        List<Acesso> acesso = acessoRepositoryMock.findAll();
        assertThat(acesso).isNotEmpty();
    }

    @Test
    @DisplayName("verificando se acesso = null")
    void findAcessosNull() {
        when(acessoRepositoryMock.findAll()).thenReturn(Collections.emptyList());
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> tokenRegra.refreshAccessToken());
        String erro = exception.getMessage();
        assertEquals("No Acesso details found in the database", erro);
    }

    @Test
    @DisplayName("verificando se está gerando token com sucesso")
    void geraTokenComSucesso() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(connection.getOutputStream()).thenReturn(new DataOutputStream(outputStream));
        when(connection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        ByteArrayInputStream body = new ByteArrayInputStream("{\"access_token\": \"1234\",\"token_type\": \"Bearer\"}".getBytes());
        when(connection.getInputStream()).thenReturn(body);
        TokenRegra spyTokenService = spy(tokenRegra);
        doReturn(connection).when(spyTokenService).createConnection(anyString());
        tokenRegra = spyTokenService;
        String token = tokenRegra.getAccessToken();
        assertThat(token).isNotEmpty();
        assertThat(token).isEqualTo("1234");
        // Verifica se o token foi atualizado
        verify(spyTokenService).refreshAccessToken();
    }

    @Test
    @DisplayName("verificando quando não vem o token")
    void geraTokenErro() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        when(connection.getOutputStream()).thenReturn(new DataOutputStream(outputStream));
        when(connection.getResponseCode()).thenReturn(HttpURLConnection.HTTP_OK);
        // Simula resposta vazia
        ByteArrayInputStream body = new ByteArrayInputStream("".getBytes());
        when(connection.getInputStream()).thenReturn(body);
        TokenRegra spyTokenService = spy(tokenRegra);
        doReturn(connection).when(spyTokenService).createConnection(anyString());
        tokenRegra = spyTokenService;
        // Verifica se uma exceção é lançada quando não há token na resposta
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, tokenRegra::getAccessToken);
        String erro = exception.getMessage();
        assertEquals("Erro em criar o token", erro);
        // Verifica se o token não foi atualizado
        verify(spyTokenService, atLeastOnce()).refreshAccessToken();
    }
}
