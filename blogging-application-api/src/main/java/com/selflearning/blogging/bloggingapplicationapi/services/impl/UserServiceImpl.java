package com.selflearning.blogging.bloggingapplicationapi.services.impl;

import com.selflearning.blogging.bloggingapplicationapi.entities.User;
import com.selflearning.blogging.bloggingapplicationapi.exceptions.ResourceNotFoundException;
import com.selflearning.blogging.bloggingapplicationapi.payloads.UserDto;
import com.selflearning.blogging.bloggingapplicationapi.repositories.UserRepository;
import com.selflearning.blogging.bloggingapplicationapi.services.UserService;
import lombok.NoArgsConstructor;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService
{
    /*@Autowired
    private SessionFactory sessionFactory;

    public UserDto getName(){
        return sessionFactory.getCurrentSession().get(UserDto.class,1);
        return sessionFactory.getCurrentSession().createQuery("");
    } -- By Ramesh*/

    //Where is the implementation class for userRepository? : At the startup IOC container scans
    //through all the repository interfaces and creates an implementation class for them at
    //the runtime. [Class : jdk.proxy2.$Proxy104] See the unit test case.
    UserRepository userRepository;
    ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper)
    {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto)
    {
        User user = this.dtoToUser(userDto);
        User savedUser = userRepository.save(user);

        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId)
    {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        User updatedUser = this.userRepository.save(user);

        return this.userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId)
    {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers()
    {
        List<User> users = this.userRepository.findAll();

        return users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer userId)
    {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        this.userRepository.delete(user);
    }

    private User dtoToUser(UserDto userDto)
    {
        User user = this.modelMapper.map(userDto, User.class);

//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());

        return user;
    }

    private UserDto userToDto(User user)
    {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);

//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());

        return userDto;
    }
}
