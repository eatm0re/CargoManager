package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.UserDTO;

public interface UserService extends Service<UserDTO> {

    UserDTO findByLogin(String login);

    long register(UserDTO userDTO);

    void change(UserDTO userDTO);
}
