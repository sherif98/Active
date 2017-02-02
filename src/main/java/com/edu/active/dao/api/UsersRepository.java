package com.edu.active.dao.api;

import com.edu.active.dao.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long> {

}
