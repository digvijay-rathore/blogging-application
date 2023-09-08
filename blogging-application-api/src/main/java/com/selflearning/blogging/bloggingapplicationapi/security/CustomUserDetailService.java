package com.selflearning.blogging.bloggingapplicationapi.security;

import com.selflearning.blogging.bloggingapplicationapi.entities.User;
import com.selflearning.blogging.bloggingapplicationapi.exceptions.ResourceNotFoundException;
import com.selflearning.blogging.bloggingapplicationapi.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService
{
    UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Email", username));

        return user;
    }
}
