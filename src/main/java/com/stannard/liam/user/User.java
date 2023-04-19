package com.stannard.liam.user;

import jakarta.persistence.*;
@Entity
@Table(name = "user")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    private String name;
    private String role;

    User() {}

    User(String name, String role)
    {
        this.name = name;
        this.role = role;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
}
