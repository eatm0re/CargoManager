package com.tsystems.javaschool.evgenydubovitsky.cargomanager.dao;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.entity.User;

public interface UserDAO extends DAO<User> {

    User selectByLogin(String login);
}
