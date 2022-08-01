package com.young.test1.mapper;

import com.young.test1.domain.dto.StudentHobbyDto;
import com.young.test1.domain.po.StudentHobby;
import com.young.test1.domain.vo.StudentHobbyVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 读书破万卷，下笔如有神 *
 * 代码反行之，算法记于心 *
 * 项目名: test
 * author: 0YOUNG
 * data:2022/7/20
 */
@Repository
public interface StudentHobbyMapper {
    void insert(StudentHobby studentHobby);
    void insertList(@Param("stuHobDtos") List<StudentHobbyDto> studentHobbyDtos,@Param("id")Integer id);

    void deleteById(@Param("id") Integer id);

    List<StudentHobbyVo> getList(@Param("id") Integer id);

}
