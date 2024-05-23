package com.twygo.usacucar.Entidades;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "vw_fila_processamento")
@Entity(name = "vw_fila_processamento")
@EqualsAndHashCode(of = "sq_fila")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FilaProcessamento {

    @Id
    @Column(name = "sq_fila")
    private String sq_fila;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "email")
    private String email;

    @Column(name = "nome")
    private String first_name;

    @Column(name = "sobrenome")
    private String last_name;

    @Column(name = "tp_acao")
    private String tp_acao;

    @Column(name = "id_curso")
    private String id_curso;

    @Column(name="id_usuario")
    private String id_usuario;

    @Column(name="id_inscricao_curso")
    private String id_inscricao;

    @Column(name="ds_filial")
    private String ds_filial;

    @Column(name="ds_cargo")
    private String ds_cargo;

    @Column(name = "ds_area_processo")
    private String ds_area;

}
