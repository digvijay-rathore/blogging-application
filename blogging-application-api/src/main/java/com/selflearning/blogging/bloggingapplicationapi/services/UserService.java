package com.selflearning.blogging.bloggingapplicationapi.services;

import com.selflearning.blogging.bloggingapplicationapi.payloads.UserDto;

import java.util.List;

public interface UserService
{
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto, Integer userId);
    UserDto getUserById(Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userId);
}
