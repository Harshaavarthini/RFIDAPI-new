package org.ups.rfidtrack.service;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.ups.rfidtrack.entity.User;
import org.ups.rfidtrack.repo.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;


    @Autowired
    PasswordEncoder passwordEncoder;


    public String saveUser(User user){
        userRepo.save(new User(
                user.getUserEmail(),
                passwordEncoder.encode(user.getUserPassword()),
                user.getRole()
                ));
        return "saved  successfully";
    }

    public boolean checkUser(User user){
        return userRepo.existsById(user.getUserEmail());
    }


}
