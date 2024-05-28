package com.twygo.usacucar.Entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Table(name = "rhparame")
@Entity(name = "rhparame")
@EqualsAndHashCode(of = "PASSWORD_TWYGO")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Acesso  {

    @Id
    @Column(insertable=false, updatable=false)
    private Long PASSWORD_TWYGO;

    @Column(name = "password_twygo")
    private String passwordTwygo;

    @Column(name = "username_twygo")
    private String usernameTwygo;

    @Column(name = "URL_TWYGO")
    private String url;

}
