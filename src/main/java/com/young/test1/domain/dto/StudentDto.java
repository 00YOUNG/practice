package com.young.test1.domain.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 读书破万卷，下笔如有神 *
 * 代码反行之，算法记于心 *
 * 项目名: test
 * author: 0YOUNG
 * data:2022/7/20
 */

public class StudentDto implements Serializable {

    private Integer id;

    private String userName;

    private String password;

    private String realName;

    private Integer sex;
    private Integer age;
    private List<StudentHobbyDto> studentHobbyDtos;




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }



    public List<StudentHobbyDto> getStudentHobbyDtos() {
        return studentHobbyDtos;
    }

    public void setStudentHobbyDtos(List<StudentHobbyDto> studentHobbyDtos) {
        this.studentHobbyDtos = studentHobbyDtos;
    }
}
