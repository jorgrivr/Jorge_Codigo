package com.voodo.delivery.servicioImpl;
import com.google.common.base.Strings;

import com.voodo.delivery.JWT.CostumerUserDetailsService;
import com.voodo.delivery.JWT.JwtFilter;
import com.voodo.delivery.JWT.JwtUtil;
import com.voodo.delivery.constantes.Constantes;
import com.voodo.delivery.dao.UserDao;
import com.voodo.delivery.entidad.User;
import com.voodo.delivery.servicio.UserServicio;
import com.voodo.delivery.utils.EmailUtils;
import com.voodo.delivery.utils.VoodoUtils;
import com.voodo.delivery.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserServicioImpl implements UserServicio {
    @Autowired
    UserDao userDao;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    CostumerUserDetailsService costumerUserDetailsService;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    JwtFilter jwtFilter;
    @Autowired
    EmailUtils emailUtils;
    @Override
    public ResponseEntity<String> registrar(Map<String, String> requestMap) {
        log.info("Dentro registrar {}",requestMap);
        try{
            if(validateRegistrarMap(requestMap)){
                User user=userDao.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)){
                    userDao.save(getUserFromMap(requestMap));
                    return VoodoUtils.getResponseEntity("Registro exitoso",HttpStatus.OK);

                }else {
                    return VoodoUtils.getResponseEntity("Email ya existe",HttpStatus.BAD_REQUEST);
                }

            }else {
                return VoodoUtils.getResponseEntity(Constantes.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return VoodoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }



    private boolean validateRegistrarMap(Map<String,String> requestMap){
        if (requestMap.containsKey("nombre") && requestMap.containsKey("numero_contacto")
                && requestMap.containsKey("email") && requestMap.containsKey("password")) {
            return true;
        }else {
            return false;
        }
    }

    private User getUserFromMap(Map<String,String> requestMap){
        User user=new User();
        user.setNombre(requestMap.get("nombre"));
        user.setNumero_contacto(requestMap.get("numero_contacto"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setEstado("false");
        user.setRole("user");
        return user;
    }

    @Override
    public ResponseEntity<String> iniciarSesion(Map<String, String> requestMap) {
        log.info("Dentro iniciarSesion");
        try {
            Authentication auth= authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"),requestMap.get("password"))
            );
            if(auth.isAuthenticated()){
                if(costumerUserDetailsService.getUserDetail().getEstado().equalsIgnoreCase("true")){
                    return new ResponseEntity<String>("{\"token\":\""+
                            jwtUtil.generateToken(costumerUserDetailsService.getUserDetail().getEmail(),
                                    costumerUserDetailsService.getUserDetail().getRole())+ "\"}",
                            HttpStatus.OK);
                }else {
                    return new ResponseEntity<String>("{\"message\":\""+"Esperando aprobacion del admin"+"\"}",HttpStatus.BAD_REQUEST);
                }

            }

        }catch (Exception ex){
            log.error("{}",ex);
        }
        return new ResponseEntity<String>("{\"message\":\""+"Error de credenciales"+"\"}",HttpStatus.BAD_REQUEST);

    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {
            if(jwtFilter.isAdmin()) {
                return new ResponseEntity<>(userDao.getAllUser(), HttpStatus.OK);


            }else {
                return new ResponseEntity<>(new ArrayList<>(),HttpStatus.UNAUTHORIZED);
            }


        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try{
            if(jwtFilter.isAdmin()){
                Optional<User> optional= userDao.findById(Integer.parseInt(requestMap.get("id")));
                if(!optional.isEmpty()){
                    userDao.updateEstado(requestMap.get("estado"),Integer.parseInt(requestMap.get("id")));
                    sendMailToAllAdmin(requestMap.get("estado"),optional.get().getEmail(),userDao.getAllAdmin());
                    return VoodoUtils.getResponseEntity("Estado usuario actualizado correctamente",HttpStatus.OK);

                }else {
                    return VoodoUtils.getResponseEntity("User id no existe",HttpStatus.OK);
                }

            }else{
                return VoodoUtils.getResponseEntity(Constantes.UNAUTHORIZED_ACCESS,HttpStatus.UNAUTHORIZED);

            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return VoodoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }



    private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {
        allAdmin.remove(jwtFilter.getCurrentUser());
        if(status!=null && status.equalsIgnoreCase("true")){
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(),"Cuenta aprobada","USER:-" + user+"\n es aprobada por\nADMIN:-"+jwtFilter.getCurrentUser(),allAdmin);

        }else {
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(),"Cuenta desactivada","USER:-" + user+"\n es desactivada por \nADMIN:-"+jwtFilter.getCurrentUser(),allAdmin);

        }
    }
    @Override
    public ResponseEntity<String> checkToken() {
        return VoodoUtils.getResponseEntity("true",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> cambiarPassword(Map<String, String> requestMap) {
        try {
            User userObj=userDao.findByEmail(jwtFilter.getCurrentUser());
            if(!userObj.equals(null)){
                if(userObj.getPassword().equals(requestMap.get("viejaPassword"))){
                    userObj.setPassword(requestMap.get("nuevaPassword"));
                    userDao.save(userObj);
                    return VoodoUtils.getResponseEntity("Password actualizada exitosamente",HttpStatus.OK);

                }
                return VoodoUtils.getResponseEntity("Contraseña vieja incorrecta",HttpStatus.BAD_REQUEST);


            }else {
                return VoodoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return VoodoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> olvidarPassword(Map<String, String> requestMap) {
        try {
            User user=userDao.findByEmail(requestMap.get("email"));
            if(!Objects.isNull(user)&& !Strings.isNullOrEmpty(user.getEmail())){
                emailUtils.forgotMail(user.getEmail(),"Credenciales por Voodo Delivery",user.getPassword());
                return VoodoUtils.getResponseEntity("Revisa el mail para las credenciales",HttpStatus.OK);

            }

        }catch (Exception ex){

        }
        return VoodoUtils.getResponseEntity(Constantes.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}