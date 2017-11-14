package com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users", schema = "cargomanager")
public class User {
    private long id;
    private String login;
    private String password;
    private Serializable post;

    @Id
    @Column(name = "idUser", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "userLogin", nullable = false, length = 45)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "userPassword", nullable = false, length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "userPost", nullable = false)
    public Serializable getPost() {
        return post;
    }

    public void setPost(Serializable post) {
        this.post = post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        return post != null ? post.equals(user.post) : user.post == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (post != null ? post.hashCode() : 0);
        return result;
    }
}
