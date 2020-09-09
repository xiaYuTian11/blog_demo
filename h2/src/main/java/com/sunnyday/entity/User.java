package com.sunnyday.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author TMW
 * @date 2020/9/9 16:23
 */
@Entity(name = "USER")
@Data
public class User {
    @Id
    @Column(name = "USE_ID")
    private int id;
    @Column(name = "USE_NAME")
    private String name;
    @Column(name = "USE_SEX")
    private String sex;
    @Column(name = "USE_AGE")
    private int age;
}
