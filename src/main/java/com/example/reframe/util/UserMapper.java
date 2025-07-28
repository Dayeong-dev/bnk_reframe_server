package com.example.reframe.util;

import com.example.reframe.dto.UserDTO;
import com.example.reframe.entity.User;

public class UserMapper {
	public User toEntity(UserDTO userDTO) {
		User userEntity = new User();
		
		userEntity.setUsername(userDTO.getUsername());
		userEntity.setPassword(userDTO.getPassword());
		userEntity.setName(userDTO.getName());
		userEntity.setEmail(userDTO.getEmail());
		userEntity.setPhone(userDTO.getPhone());
		userEntity.setUsertype(userDTO.getUsertype());
		userEntity.setRole(userDTO.getRole());
		
		return userEntity;
	}
	
	public UserDTO toDTO(User userEntity) {
		UserDTO userDTO = new UserDTO();
		
		userDTO.setUsername(userEntity.getUsername());
		userDTO.setPassword(userEntity.getPassword());
		userDTO.setName(userEntity.getName());
		userDTO.setEmail(userEntity.getEmail());
		userDTO.setPhone(userEntity.getPhone());
		userDTO.setUsertype(userEntity.getUsertype());
		userDTO.setRole(userEntity.getRole());
		
		return userDTO;
	}
}
