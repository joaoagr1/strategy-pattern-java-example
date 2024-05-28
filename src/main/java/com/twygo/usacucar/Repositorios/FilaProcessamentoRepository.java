package com.twygo.usacucar.Repositorios;

import com.twygo.usacucar.Entidades.FilaProcessamento;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Date;




@Repository
public interface FilaProcessamentoRepository extends JpaRepository<FilaProcessamento,String> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE fila_processamento SET JSON_ENVIO = :json WHERE SQ_FILA = :sqFila", nativeQuery = true)
    int atualizaJsonEnvio(@Param("sqFila") String sqFila, @Param("json") String json);

    @Transactional
    @Modifying
    @Query(value = "UPDATE fila_processamento SET JSON_RETORNO = :json WHERE SQ_FILA = :sqFila", nativeQuery = true)
    int atualizaJsonRetorno(@Param("sqFila") String sqFila, @Param("json") String json);


    @Modifying
    @Transactional
    @Query(value ="UPDATE fila_processamento SET STATUS_ENVIO = :novoStatus WHERE SQ_FILA = :sqFila", nativeQuery = true)
    int atualizaStatus(@Param("sqFila") String sqFila, @Param("novoStatus") String novoStatus);

    @Transactional
    @Modifying
    @Query(value = "UPDATE fila_processamento SET id_usuario = :id_usuario, status_envio = 'F' WHERE sq_fila = :sq_fila AND tp_acao = 1 ", nativeQuery = true)
    int atualizaIdUsuario(@Param("id_usuario") String id_usuario,
                           @Param("sq_fila") String sq_fila);

    @Transactional
    @Modifying
    @Query(value = "UPDATE fila_processamento SET id_inscricao = :id_inscricao WHERE  sq_fila = :sq_fila", nativeQuery = true)
    int atualizaIdInscricao(@Param("id_inscricao") String id_inscricao,
                             @Param("sq_fila") String sq_fila);


    @Transactional
    @Modifying
    @Query(value = "UPDATE fila_processamento SET LAST_ACCESS_AT = :ultimoAcesso, PROGRESS_SCORE = :progresso, APPROVED_AT = :dataAprovacao, STATUS_ENVIO = 'F' WHERE sq_fila = :sq_fila", nativeQuery = true)
    int atualizaStatus(@Param("ultimoAcesso") Date ultimoAcesso,
                        @Param("progresso") Double progresso,
                        @Param("dataAprovacao") Date dataAprovacao,
                        @Param("sq_fila") String sqFila);



}
