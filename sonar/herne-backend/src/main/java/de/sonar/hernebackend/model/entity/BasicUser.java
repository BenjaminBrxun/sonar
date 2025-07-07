package de.sonar.hernebackend.model.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BasicUser {

    private long id;

    private String name;

    private String firstname;

    private String email;

    private String password;

    private Address address;

}