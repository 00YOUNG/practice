package com.young.test1.service;

import com.github.pagehelper.PageInfo;
import com.young.test1.domain.dto.QueryStudentDto;
import com.young.test1.domain.dto.StudentDto;
import com.young.test1.domain.vo.SelfPageInfo;
import com.young.test1.domain.vo.StudentVo;
import com.young.test1.exception.CustomerException;
import org.checkerframework.checker.units.qual.C;

/**
 * 读书破万卷，下笔如有神 *
 * 代码反行之，算法记于心 *
 * 项目名: test
 * author: 0YOUNG
 * data:2022/7/20
 */

public interface StudentService {
    void add(StudentDto studentDto) throws CustomerException;

    void update(StudentDto studentDto) throws CustomerException;


    Object getDetail(Integer id);

    SelfPageInfo getList(QueryStudentDto queryStudentDto);

    void delete(Integer id);

    void getMenuTree();
}
