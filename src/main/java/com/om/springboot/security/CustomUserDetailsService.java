package com.om.springboot.security;

import com.om.springboot.exception.ResourceNotFoundException;
import com.om.springboot.mappers.user.UserMapper;
import com.om.springboot.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {
        // Let people login with either username or email
        User user = userMapper.getUserByEmail(usernameOrEmail);
        if(user ==null){
            throw new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail);
        }
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userMapper.getUserById(id);
        if(user ==null){
            throw new ResourceNotFoundException("User", "id", id);
        }

        return UserPrincipal.create(user);
    }
}
