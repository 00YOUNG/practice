package com.young.test1.mapper;

import com.young.test1.domain.po.Student;
import com.young.test1.domain.vo.StudentVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 读书破万卷，下笔如有神 *
 * 代码反行之，算法记于心 *
 * 项目名: test
 * author: 0YOUNG
 * data:2022/7/20
 * @author EDY
 */
@Repository
public interface StudentMapper {


    Student queryStudentByUserName(@Param("name") String userName);


    void insert(Student student);

    Student queryStudentById(@Param("id") Integer id);

    StudentVo getStudentById(@Param("id") Integer id);

    void update(Student student);

    List getList(@Param("id") Integer id, @Param("userName") String userName, @Param("password") String password);

    void delete(@Param("id") Integer id);
}
