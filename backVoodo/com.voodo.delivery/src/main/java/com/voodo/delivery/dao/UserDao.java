package com.voodo.delivery.dao;

import com.voodo.delivery.entidad.User;
import com.voodo.delivery.wrapper.UserWrapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserDao  extends JpaRepository<User,Integer> {

    User findByEmailId(@Param("email") String email);

    List<UserWrapper> getAllUser();
    List<String> getAllAdmin();


    @Transactional
    @Modifying
    Integer updateEstado(@Param("estado") String status,@Param("id") Integer id);

    User findByEmail(String email);
}