package com.examplesoft.ecommercemonolite.domain.useraddress.repo;

import com.examplesoft.ecommercemonolite.domain.useraddress.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAddressRepository extends JpaRepository<UserAddress, String> {
    List<UserAddress> findAllByUserId(String userId);
}
