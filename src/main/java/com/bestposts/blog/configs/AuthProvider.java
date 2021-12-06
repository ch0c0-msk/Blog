package com.bestposts.blog.configs;

import com.bestposts.blog.models.CustomUserDetailsService;
import com.bestposts.blog.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthProvider implements AuthenticationProvider
{
    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        String email = authentication.getName();
        String password = (String) authentication.getCredentials();
        Users user = (Users) userService.loadUserByUsername(email);

        if(user != null && (user.getUsername().equals(email)))
        {
            if(false)
            {
                throw new BadCredentialsException("Неправильный пароль");
            }

            return new UsernamePasswordAuthenticationToken(user, password);
        }
        else
            throw new BadCredentialsException("Неправильный email");
    }

    public boolean supports(Class<?> arg)
    {
        return true;
    }
}
