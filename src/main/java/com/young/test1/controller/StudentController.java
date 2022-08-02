package com.young.test1.controller;

import com.young.test1.domain.dto.QueryStudentDto;
import com.young.test1.domain.dto.StudentDto;
import com.young.test1.exception.CustomerException;
import com.young.test1.response.Response;
import com.young.test1.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 读书破万卷，下笔如有神 *
 * 代码反行之，算法记于心 *
 * 项目名: test
 * author: 0YOUNG
 * data:2022/7/20
 */
@RestController
@RequestMapping("student")
public class StudentController {
    @Autowired
    StudentService studentService;
    /**
     * 校验参数
     * @param studentDto
     * @throws Exception
     */
    private void getResponse(StudentDto studentDto) throws Exception{
        if(!StringUtils.hasLength(studentDto.getUserName())){
            throw new CustomerException("用户名不能为空");
        }

        if(!StringUtils.hasLength(studentDto.getRealName())){
            throw new CustomerException("真是姓名不能为空");
        }
        if(studentDto.getAge()==null){
            throw new CustomerException("年龄不能为空");
        }
        if(studentDto.getSex()==null){
            throw new CustomerException("性别不能为空");
        }

    }
    /**
     * 添加用户
     * @param studentDto
     * @return
     * @throws Exception
     */
    @PostMapping("add")
    public Response add(@RequestBody StudentDto  studentDto) throws Exception{
        getResponse(studentDto);
        if(!StringUtils.hasLength(studentDto.getPassword())){
            throw new CustomerException("密码不能为空");
        }
        studentService.add(studentDto);

        return Response.correct();
    }
    /**
     * 更新用户
     */
    @PutMapping("update")
    public Response update(@RequestBody StudentDto studentDto) throws Exception{
        getResponse(studentDto);
        studentService.update(studentDto);
        return Response.correct();
    }

    @GetMapping("getDetail")
    public  Response getDetail(Integer id){

        return  Response.correct(studentService.getDetail(id));
    }

    @GetMapping("getList")
    public  Response getList(QueryStudentDto queryStudentDto){

        return Response.correct(studentService.getList(queryStudentDto));
    }
    @DeleteMapping("delete/{id}")
    public  Response delete(@PathVariable("id") Integer id){
        studentService.delete(id);
        return Response.correct();
    }

    @GetMapping("getMenuTree")
    public  Response getMenuTree(){
        studentService.getMenuTree();
        return Response.correct();
    }


}
