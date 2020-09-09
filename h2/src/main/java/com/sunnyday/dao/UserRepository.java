package com.sunnyday.dao;

import com.sunnyday.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author TMW
 * @date 2020/9/9 16:28
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    @Modifying
    @Query(value = "delete from USER where id = ?1")
    void deleteById(int id);

}
