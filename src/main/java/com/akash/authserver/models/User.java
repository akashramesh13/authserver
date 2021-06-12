package com.akash.authserver.models;

import com.akash.authserver.idgenerator.RandomIdGenerator;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@Component
public class User {
    @Id
    @GeneratedValue(generator = RandomIdGenerator.generatorName)
    @GenericGenerator(name = RandomIdGenerator.generatorName, strategy = "com.akash.authserver.idgenerator.RandomIdGenerator")
    public String userId;
    @Column(unique = true)
    public String username;
    public String password;
    public String role;
}
