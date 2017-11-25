package com.tsystems.javaschool.evgenydubovitsky.cargomanager.service;

import com.tsystems.javaschool.evgenydubovitsky.cargomanager.dto.UserDTO;
import com.tsystems.javaschool.evgenydubovitsky.cargomanager.exception.BusinessException;

public interface UserService extends Service<UserDTO> {

    UserDTO findByLogin(String login) throws BusinessException;

    long register(UserDTO userDTO) throws BusinessException;

    void change(UserDTO userDTO) throws BusinessException;
}
