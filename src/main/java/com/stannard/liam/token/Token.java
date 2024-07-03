package com.stannard.liam.token;


import com.stannard.liam.user.User;
import jakarta.persistence.*;

@Entity
public class Token {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    public Integer id;

    public String token;

    public boolean expired;

    public boolean revoked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public User user;

    public Token()
    {

    }

    public Token(String token, User user)
    {
        this.token = token;
        this.expired = false;
        this.revoked = false;
        this.user = user;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public boolean isExpired()
    {
        return expired;
    }

    public void setExpired(boolean expired)
    {
        this.expired = expired;
    }

    public boolean isRevoked()
    {
        return revoked;
    }

    public void setRevoked(boolean revoked)
    {
        this.revoked = revoked;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
