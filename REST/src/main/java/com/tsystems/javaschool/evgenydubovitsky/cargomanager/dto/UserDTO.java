package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.User;

public class UserDTO extends DTO<User> {

    private String login;
    private String password;
    private User.Post post;

    /**
     * Empty
     */
    public UserDTO() {
    }

    /**
     * Identifier
     */
    public UserDTO(String login) {
        this.login = login;
    }

    /**
     * Creator and Changer
     */
    public UserDTO(String login, String password, User.Post post) {
        this.login = login;
        this.password = password;
        this.post = post;
    }

    /**
     * Full
     */
    public UserDTO(long id, String login, String password, User.Post post) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.post = post;
    }

    /**
     * Converter
     */
    public UserDTO(User entity) {
        this(
                entity.getId(),
                entity.getLogin(),
                entity.getPassword(),
                entity.getPost()
        );
    }

    @Override
    public void fill(User entity) {
        // no fields to fill
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User.Post getPost() {
        return post;
    }

    public void setPost(User.Post post) {
        this.post = post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        if (!super.equals(o)) return false;

        UserDTO userDTO = (UserDTO) o;

        if (login != null ? !login.equals(userDTO.login) : userDTO.login != null) return false;
        if (password != null ? !password.equals(userDTO.password) : userDTO.password != null) return false;
        return post == userDTO.post;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (post != null ? post.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", post=" + post +
                '}';
    }
}
