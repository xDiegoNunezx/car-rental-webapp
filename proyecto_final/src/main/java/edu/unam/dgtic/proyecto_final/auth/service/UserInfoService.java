package edu.unam.dgtic.proyecto_final.auth.service;

import edu.unam.dgtic.proyecto_final.auth.dto.UserInfoDTO;
import edu.unam.dgtic.proyecto_final.auth.exception.UserInfoNotFoundException;

import java.util.List;

public interface UserInfoService {
    List<UserInfoDTO> findAll();
    UserInfoDTO findById(Long id) throws UserInfoNotFoundException;
    UserInfoDTO save(UserInfoDTO userAdmin) throws UserInfoNotFoundException;
    UserInfoDTO findByUseEmail(String email) throws UserInfoNotFoundException;
}
