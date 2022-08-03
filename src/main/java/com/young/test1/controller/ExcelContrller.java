package com.young.test1.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;
import com.young.test1.domain.UploadData;
import com.young.test1.domain.UploadDataListener;
import com.young.test1.domain.po.Student;
import com.young.test1.mapper.UploadMapper;
import com.young.test1.response.Response;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

import java.util.List;

/**
 * 读书破万卷，下笔如有神 *
 * 代码反行之，算法记于心 *
 * 项目名: test
 * author: 0YOUNG
 * data:2022/8/3
 */
@RestController
@RequestMapping("excel")
public class ExcelContrller {

    @Resource
    private UploadMapper uploadMapper;
    /**
     * 文件下载（失败了会返回一个有部分数据的Excel）
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link Student}
     * <p>
     * 2. 设置返回的 参数
     * <p>
     * 3. 直接写，这里注意，finish的时候会自动关闭OutputStream,当然你外面再关闭流问题不大
     */
    @GetMapping("download")
    public Response download(HttpServletResponse response) throws IOException {
        // 请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
        String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        EasyExcel.write(response.getOutputStream(), Student.class).sheet("student1").doWrite(data());
        return Response.correct();
    }


    /**
     * 文件上传
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link UploadData}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link UploadDataListener}
     * <p>
     * 3. 直接读即可
     */
    @PostMapping("upload")
    @ResponseBody
    public String upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), UploadData.class, new UploadDataListener(uploadMapper)).sheet().doRead();
        return "success";
    }

    private List<Student> data() {
        List<Student> list = ListUtils.newArrayList();
        for (int i = 0; i < 11; i++) {
            Student data = new Student();
            data.setId(i);
            data.setUserName("userName"+i);
            data.setPassword("password"+i);
            data.setRealName("realName"+i);
            data.setSex(i % 2 == 0 ? 1 : 0);
            data.setAge(18+i);
            list.add(data);
        }
        return list;
    }
}
