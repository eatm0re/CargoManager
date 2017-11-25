package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.UserDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.User;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.*;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.service.UserService;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.util.Loggable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl extends AbstractService<User, UserDTO> implements UserService {

    public UserServiceImpl() {
        super(User.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
    @Loggable
    public UserDTO findByLogin(String login) throws BusinessException {
        if (!isSimpleName(login)) {
            throw new WrongParameterException("Wrong user login");
        }
        User user = dao.getUserDAO().selectByLogin(login);
        if (user == null) {
            throw new EntityNotFoundException("User " + login + " not found");
        }
        return new UserDTO(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Loggable
    public long register(UserDTO userDTO) throws BusinessException {

        // create user
        User user = new User();

        // login
        String login = userDTO.getLogin();
        if (login == null || login.length() == 0) {
            throw new MissedParameterException("User login must be specified");
        }
        if (!isSimpleName(login)) {
            throw new WrongParameterException("Wrong user login");
        }
        if (dao.getUserDAO().selectByLogin(login) != null) {
            throw new EntityExistsException("User " + login + " is already exists");
        }
        user.setLogin(login);

        // password
        String password = userDTO.getPassword();
        if (password == null || password.length() == 0) {
            throw new MissedParameterException("User password must be specified");
        }
        if (!isLongName(password)) {
            throw new WrongParameterException("Wrong user password");
        }
        user.setPassword(password);

        // post
        User.Post post = userDTO.getPost();
        if (post == null) {
            throw new MissedParameterException("User post must be specified");
        }
        user.setPost(post);

        // insert
        dao.getUserDAO().insert(user);
        return user.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Loggable
    public void change(UserDTO userDTO) throws BusinessException {

        // find user by login
        String login = userDTO.getLogin();
        if (login == null || login.length() == 0) {
            throw new MissedParameterException("User login must be specified");
        }
        if (!isSimpleName(login)) {
            throw new WrongParameterException("Wrong user login");
        }
        User user = dao.getUserDAO().selectByLogin(login);
        if (user == null) {
            throw new EntityNotFoundException("User " + login + " not found");
        }

        // password
        String password = userDTO.getPassword();
        if (password != null && password.length() > 0) {
            if (!isLongName(password)) {
                throw new WrongParameterException("Wrong user password");
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
