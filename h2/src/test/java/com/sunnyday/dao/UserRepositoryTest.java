package com.sunnyday.dao;

import com.sunnyday.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author TMW
 * @date 2020/9/9 16:31
 */
@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository repository;


    @Test
    public void insertTest(){
        User user = new User();
        user.setId(1);
        user.setName("李四");
        user.setSex("0");
        user.setAge(20);
        final User save = repository.save(user);
        System.out.println(save);
    }


    @Test
    public void findAllTest(){
        final List<User> userList = repository.findAll();
        userList.forEach(System.out::println);
    }



    @Test
    public void updateTest(){
        final Optional<User> byId = repository.findById(1);
        final User user = byId.get();
        user.setName("李四11");
        repository.save(user);
    }

    @Test
    public void delTest(){
        repository.deleteById(0);
    }


}