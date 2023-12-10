package org.ups.rfidtrack.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ups.rfidtrack.entity.User;


@Repository
public interface UserRepo extends JpaRepository<User,String>{
}
