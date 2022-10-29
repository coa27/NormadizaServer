package com.coa.model;

import javax.persistence.*;

@Entity
@Table
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long id;

    @Column(length = 20)
    private String roleNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleNombre() {
        return roleNombre;
    }

    public void setRoleNombre(String roleNombre) {
        this.roleNombre = roleNombre;
    }
}
