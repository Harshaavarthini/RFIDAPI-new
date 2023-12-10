package org.ups.rfidtrack.controller;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.ups.rfidtrack.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.ups.rfidtrack.service.UserService;



@RestController
@RequestMapping("usr")
public class UserController {


    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("save")
    public String saveUser(@RequestBody User user){
        return userService.saveUser(new User(
                user.getUserEmail(),
                passwordEncoder.encode(user.getUserPassword()),
                user.getRole()
        ));
    }


}
