package com.examplesoft.ecommercemonolite.security;

import com.examplesoft.ecommercemonolite.domain.user.entity.User;
import com.examplesoft.ecommercemonolite.domain.user.repo.UserRepository;
import com.examplesoft.ecommercemonolite.util.BaseException;
import com.examplesoft.ecommercemonolite.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repository;

    @Override
    @Cacheable(value = "userCache",key = "#username")
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("CACHE KULLANILMADI! Veritabanına erişim başlıyor...");
        User user = repository.findByUsername(username).orElseThrow(() -> new BaseException(MessageUtil.ENTITY_NOT_FOUND, User.class.getSimpleName(),username));
        return new CustomUserDetails(user);
    }
}
