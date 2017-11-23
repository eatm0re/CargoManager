package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entities.User;

public interface UserDAO extends DAO<User> {

    User selectByLogin(String login);
}
