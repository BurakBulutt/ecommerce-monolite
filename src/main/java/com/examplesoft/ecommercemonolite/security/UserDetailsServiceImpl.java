package com.examplesoft.ecommercemonolite.security;

import com.examplesoft.ecommercemonolite.domain.user.entity.User;
import com.examplesoft.ecommercemonolite.domain.user.repo.UserRepository;
import com.examplesoft.ecommercemonolite.util.BaseException;
import com.examplesoft.ecommercemonolite.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username).orElseThrow(() -> new BaseException(MessageUtil.ENTITY_NOT_FOUND, User.class.getSimpleName(),username));
        return new CustomUserDetails(user);
    }
}
