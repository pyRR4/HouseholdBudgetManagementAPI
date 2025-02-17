package com.example.transactions.mapper;

import com.example.transactions.dto.UserDTO;
import com.example.transactions.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<User, UserDTO>{}