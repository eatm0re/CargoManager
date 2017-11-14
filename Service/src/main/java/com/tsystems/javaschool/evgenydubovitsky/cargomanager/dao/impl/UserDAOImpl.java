package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.impl;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao.UserDAO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl extends AbstractDAO<User> implements UserDAO {

    public UserDAOImpl() {
        super(User.class);
    }
}
