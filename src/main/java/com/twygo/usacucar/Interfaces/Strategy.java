package com.twygo.usacucar.Interfaces;


import com.twygo.usacucar.Entidades.FilaProcessamento;
import org.apache.hc.core5.http.HttpException;

import java.io.IOException;
import java.text.ParseException;

public interface Strategy {

    /**
     * Executa a ação específica da estratégia.
     *
     * @param registro O registro que contém os dados para a ação.
     */
    void executarAcao(FilaProcessamento registro) throws HttpException, IOException, ParseException, InterruptedException;

    /**
     * Cria um novo usuário com base nos dados do registro.
     *
     * @param registro O registro que contém os dados do usuário.
     * @return O usuário criado.
     */
    void criarUsuario(FilaProcessamento registro);

    /**
     * Inscreve um usuário em um curso com base nos dados do registro.
     *
     * @param registro O registro que contém os dados da inscrição.
     */
    void inscreverUsuarioCurso(FilaProcessamento registro);

    /**
     * Mostra o status de conclusão de um curso para um usuário específico.
     *
     * @param registro O registro que contém os dados do usuário e do curso.
     * @return O status de conclusão do curso.
     */
    void mostrarStatusConclusaoCurso(FilaProcessamento registro) throws HttpException, InterruptedException, ParseException;
}
