package com.allianz.utils.auth.repo;

import com.allianz.utils.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
    public User findUserByUserName(String userName);
}
