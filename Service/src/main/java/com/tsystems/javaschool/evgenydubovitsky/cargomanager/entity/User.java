package com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity;

import javax.persistence.*;

@Entity
@Table(name = "users", schema = "cargomanager")
public class User extends AbstractEntity {

    public enum Post {DRIVER, STUFF}

    private String login;
    private String password;
    private Post post;

    public User() {
    }

    public User(long id, String login, String password, Post post) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.post = post;
    }

    @Id
    @Column(name = "idUser", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Enumerated(EnumType.STRING)
    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", post=" + post +
                '}';
    }
}
