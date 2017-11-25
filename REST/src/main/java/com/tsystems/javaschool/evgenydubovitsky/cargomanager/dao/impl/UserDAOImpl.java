package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.UserDAO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.User;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.util.Loggable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserDAOImpl extends AbstractDAO<User> implements UserDAO {

    public UserDAOImpl() {
        super(User.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS, readOnly = true)
    @Loggable
    public User selectByLogin(String login) {
        List<User> list = selectByParam("login", login);
        return list.isEmpty() ? null : list.get(0);
    }
}
