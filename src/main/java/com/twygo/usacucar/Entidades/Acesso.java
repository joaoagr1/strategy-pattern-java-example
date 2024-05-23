package com.twygo.usacucar.Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Table(name = "rhparame")
@Entity(name = "rhparame")
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Acesso  {

    @Id
    private Long id;

    @Column(name = "password_twygo")
    private String passwordTwygo;

    @Column(name = "username_twygo")
    private String usernameTwygo;

    @Column(name = "URL_TWYGO")
    private String url;

}
