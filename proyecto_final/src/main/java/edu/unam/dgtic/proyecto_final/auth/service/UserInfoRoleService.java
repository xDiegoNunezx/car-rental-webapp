package edu.unam.dgtic.proyecto_final.auth.service;

import edu.unam.dgtic.proyecto_final.auth.dto.UserInfoRoleDTO;
import edu.unam.dgtic.proyecto_final.auth.exception.UserInfoRoleNotFoundException;
import edu.unam.dgtic.proyecto_final.auth.model.UserInfoRole;

import java.util.List;

public interface UserInfoRoleService {
    List<UserInfoRoleDTO> findAll();
    List<UserInfoRoleDTO> findAllOrderByUsrRoleName();
    UserInfoRoleDTO findById(Long id) throws UserInfoRoleNotFoundException;
    UserInfoRoleDTO save(UserInfoRoleDTO role);
    UserInfoRoleDTO convertEntityToDTO(UserInfoRole userInfo);
    UserInfoRole convertDTOtoEntity(UserInfoRoleDTO userInfo);
}
