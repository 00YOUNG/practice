package com.young.test1.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.young.test1.domain.dto.QueryStudentDto;
import com.young.test1.domain.dto.StudentDto;
import com.young.test1.domain.dto.StudentHobbyDto;
import com.young.test1.domain.po.Student;
import com.young.test1.domain.po.StudentHobby;
import com.young.test1.domain.vo.SelfPageInfo;
import com.young.test1.domain.vo.StudentHobbyVo;
import com.young.test1.domain.vo.StudentVo;
import com.young.test1.exception.CustomerException;
import com.young.test1.mapper.StudentHobbyMapper;
import com.young.test1.mapper.StudentMapper;
import com.young.test1.service.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 读书破万卷，下笔如有神 *
 * 代码反行之，算法记于心 *
 * 项目名: test
 * author: 0YOUNG
 * data:2022/7/20
 */
@Service
public class StudentServiceImpl  implements StudentService {

    @Autowired
    StudentMapper studentMapper;
    @Autowired
    StudentHobbyMapper studentHobbyMapper;
    @Resource
    RedisTemplate<Integer, Object> redisTemplate;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(StudentDto studentDto) throws CustomerException {
        //根据用户名查询学生
        Student tempStudent = studentMapper.queryStudentByUserName(studentDto.getUserName());
        if (tempStudent != null) {
            throw new CustomerException(studentDto.getUserName() + "已经存在了，请变更");
        }

        //将用户存入数据库
        Student student = new Student();
        BeanUtils.copyProperties(studentDto, student);
        studentMapper.insert(student);
        List<StudentHobbyDto> studentHobbyDtos = studentDto.getStudentHobbyDtos();
        //单个保存
        for (int i = 0; i < studentHobbyDtos.size(); i++) {
            StudentHobbyDto studentHobbyDto = studentHobbyDtos.get(i);
            StudentHobby studentHobby = new StudentHobby();
            BeanUtils.copyProperties(studentHobbyDto, studentHobby);
            studentHobby.setStudentId(student.getId());
            studentHobbyMapper.insert(studentHobby);
        }
        studentDto.setStudentHobbyDtos(studentHobbyDtos);
        //将用户存入缓存
        redisTemplate.opsForValue().set(studentDto.getId(),studentDto,5,TimeUnit.MINUTES);
  //      redisTemplate.opsForValue().getAndExpire(studentDto.getId(),);

    }
    @Override
    public void update(StudentDto studentDto) throws CustomerException{
        if (studentDto.getId()==null){
            throw new CustomerException("该ID为空");
        }
        if (studentMapper.queryStudentById(studentDto.getId())==null) {
            throw new CustomerException("id为空，不存在改用户");
        }
        Student student = new Student();
        BeanUtils.copyProperties(studentDto,student);
        studentMapper.update(student);
        studentHobbyMapper.deleteById(student.getId());
        List<StudentHobbyDto> studentHobbyDtos =studentDto.getStudentHobbyDtos();
        studentHobbyMapper.insertList(studentHobbyDtos,student.getId());
        //缓存
        redisTemplate.opsForValue().set(studentDto.getId(),studentDto);
    }

    @Override
    public Object getDetail(Integer id){

        if (redisTemplate.hasKey(id)){
            return redisTemplate.opsForValue().get(id);
        }else {
            StudentVo studentVo = studentMapper.getStudentById(id);
            List<StudentHobbyVo> studentHobbyVos =studentHobbyMapper.getList(id);
            studentVo.setStudentHobbyVos(studentHobbyVos);
            return studentVo;
        }
    }
    @Override
    public SelfPageInfo getList(QueryStudentDto queryStudentDto){
        PageHelper.startPage(queryStudentDto.getPageNumber(),queryStudentDto.getPageSize());
        PageInfo pageInfo = new PageInfo(
                studentMapper.getList(queryStudentDto.getId(),queryStudentDto.getUserName(),queryStudentDto.getPassword()));

        if (redisTemplate.hasKey(queryStudentDto.getId())){
            SelfPageInfo selfPageInfo = new SelfPageInfo(Long.valueOf(pageInfo.getTotal()).intValue(), pageInfo.getPageNum(), pageInfo.getPageSize()
                    ,pageInfo.getPages(), redisTemplate.opsForValue().get(queryStudentDto.getId()));
            return selfPageInfo ;
        }else {
            SelfPageInfo selfPageInfo = new SelfPageInfo(Long.valueOf(pageInfo.getTotal()).intValue(), pageInfo.getPageNum(), pageInfo.getPageSize()
                    ,pageInfo.getPages(),pageInfo.getList());
            return  selfPageInfo;
        }
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Integer id){
        studentMapper.delete(id);
        studentHobbyMapper.deleteById(id);
        redisTemplate.delete(id);
    }
}
