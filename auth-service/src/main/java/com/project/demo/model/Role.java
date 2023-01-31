package com.project.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.demo.model.enumerations.ERoles;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Data
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERoles name;

    @Override
    @JsonIgnore
    public String getAuthority() {
        return this.name.name();
    }
}
