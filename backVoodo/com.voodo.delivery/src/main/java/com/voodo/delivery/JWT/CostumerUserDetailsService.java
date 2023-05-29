package com.voodo.delivery.JWT;
import com.voodo.delivery.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;
@Slf4j
@Service
public class CostumerUserDetailsService implements UserDetailsService {
    @Autowired
    UserDao userDao;

    private com.voodo.delivery.entidad.User userDetail;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Dentro de {} ",username);
        userDetail=userDao.findByEmailId(username);
        if(!Objects.isNull(username)){
            return new User(userDetail.getEmail(),userDetail.getPassword(),new ArrayList<>());
        }else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }

    public com.voodo.delivery.entidad.User getUserDetail(){

        return userDetail;
    }
}