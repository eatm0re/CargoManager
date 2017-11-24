package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.UserDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.User;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@Service
public class UserServiceImpl extends AbstractService<User, UserDTO> implements UserService {

    public UserServiceImpl() {
        super(User.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
    public UserDTO findByLogin(String login) {
        if (!isSimpleName(login)) {
            throw new IllegalArgumentException("Wrong user login");
        }
        User user = dao.getUserDAO().selectByLogin(login);
        if (user == null) {
            throw new EntityNotFoundException("User " + login + " not found");
        }
        return new UserDTO(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long register(UserDTO userDTO) {

        // create user
        User user = new User();

        // login
        String login = userDTO.getLogin();
        if (login == null || login.length() == 0) {
            throw new IllegalArgumentException("User login must be specified");
        }
        if (!isSimpleName(login)) {
            throw new IllegalArgumentException("Wrong user login");
        }
        if (dao.getUserDAO().selectByLogin(login) != null) {
            throw new EntityExistsException("User " + login + " is already exists");
        }
        user.setLogin(login);

        // password
        String password = userDTO.getPassword();
        if (password == null || password.length() == 0) {
            throw new IllegalArgumentException("User password must be specified");
        }
        if (!isLongName(password)) {
            throw new IllegalArgumentException("Wrong user password");
        }
        user.setPassword(password);

        // post
        User.Post post = userDTO.getPost();
        if (post == null) {
            throw new IllegalArgumentException("User post must be specified");
        }
        user.setPost(post);

        // insert
        dao.getUserDAO().insert(user);
        return user.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void change(UserDTO userDTO) {

        // find user by login
        String login = userDTO.getLogin();
        if (login == null || login.length() == 0) {
            throw new IllegalArgumentException("User login must be specified");
        }
        if (!isSimpleName(login)) {
            throw new IllegalArgumentException("Wrong user login");
        }
        User user = dao.getUserDAO().selectByLogin(login);
        if (user == null) {
            throw new EntityNotFoundException("User " + login + " not found");
        }

        // password
        String password = userDTO.getPassword();
        if (password != null && password.length() > 0) {
            if (!isLongName(password)) {
                throw new IllegalArgumentException("Wrong user password");
            }
            user.setPassword(password);
        }

        // post
        User.Post post = userDTO.getPost();
        if (post != null) {
            user.setPost(post);
        }
    }
}
