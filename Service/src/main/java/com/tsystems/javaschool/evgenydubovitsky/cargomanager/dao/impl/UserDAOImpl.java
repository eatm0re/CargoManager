package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.UserDAO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl extends AbstractDAO<User> implements UserDAO {

    public UserDAOImpl() {
        super(User.class);
    }

    @Override
    public User selectByLogin(String login) {
        List<User> list = selectByParam("login", login);
        return list.size() == 0 ? null : list.get(0);
    }
}
