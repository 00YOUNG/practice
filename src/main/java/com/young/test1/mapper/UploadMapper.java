package com.young.test1.mapper;

import com.young.test1.domain.UploadData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 读书破万卷，下笔如有神 *
 * 代码反行之，算法记于心 *
 * 项目名: test
 * author: 0YOUNG
 * data:2022/8/3
 */

public interface UploadMapper {
    void save(@Param("UploadDataList") List<UploadData> cachedDataList);
}
