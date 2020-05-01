package com.allianz.utils.auth.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class User {
    @Id
    private Integer id;
    private String userName;
    private String role;
}
