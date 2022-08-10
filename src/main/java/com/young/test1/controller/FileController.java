package com.young.test1.controller;


import cn.hutool.log.Log;
import com.young.test1.exception.CustomerException;
import com.young.test1.response.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;


import java.net.URLEncoder;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

/**
 * 读书破万卷，下笔如有神 *
 * 代码反行之，算法记于心 *
 * 项目名: test
 * author: 0YOUNG
 * data:2022/8/3
 */
@RestController
@RequestMapping("file")
@Slf4j
public class FileController {

    //定义路径变量
//  private static final String DIR = "C:\Users\EDY\Desktop\file\";不使用，写死在代码，写在yml文件。
    //注解引入
    @Value("${file.path}")
    private String basePath;

    //1.1   定义一个接收POST请求请求参数为file的处理方法
    @PostMapping("/upload")//
    @ResponseBody
    public Response upload(MultipartFile file) {// 前端传的参数名是file 可以通过前端控制台查看,注意
        //2. 获取上传的文件名字
        String originalFilename = file.getOriginalFilename();
        //3. 根据文件名获取后缀 food.jpg
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")); //重最后一个点拿  .jpg
        String prefix = originalFilename.substring(0,originalFilename.lastIndexOf("."));
        //4. 生成唯一的文件名  UUID+后缀, UUID.jpg
        String fileName = UUID.randomUUID().toString() + suffix;

        //5. 首先判断这个目录存在不存在，如果不存在，则创建该目录
        File dir = new File(basePath);
        if (file.isEmpty()) {
            Response.error("未选择文件");
        }
        if (!dir.exists()) {
            dir.mkdirs();//不存创建，使用dir.mkdirs()而不使用dir.mkdir()，因为dir.mkdirs()可以同时创建多级目录
        }
        //6. 把文件存储到指定目录中
        try {
            File newFile = new File(basePath, originalFilename);
            for (int i = 1; newFile.exists() && i < Integer.MAX_VALUE; i++) {
                newFile = new File(basePath, prefix+ '(' + i + ')' + suffix);
                originalFilename=prefix + '(' + i + ')' + suffix;
            }
            file.transferTo(newFile);
        } catch (IOException e) {
            log.error("upload error!", e);
        }
        //7. 返回文件名字
        return Response.correct( originalFilename+ "--上传成功");

    }

    //    1 定义一个接收GET请求参数为url参数name的处理方法
    @GetMapping("/download")
    public Response download(String path, HttpServletResponse response) throws CustomerException {
        try {
            //如果传的是目录的话  String  path
            // path是指想要下载的文件的路径
            File file = new File(path);
            log.info(file.getPath());
            // 获取文件名
            String filename = file.getName();
            // 获取文件后缀名
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
            log.info("文件后缀名：" + ext);
//            2 从服务器的指定目录读取文件流
            FileInputStream fileInputStream = new FileInputStream(new File(path));
//            3 根据response获取输出流
            ServletOutputStream outputStream = response.getOutputStream();
//            4 设置response的content-type为image/jpeg
            response.reset();
            response.setContentType("application/octet-stream");
            String fileName = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);

//            5 把输入流复制给输出流
            int len = 0;
            byte[] bytes = new byte[1024];
//            fileInputStream.read(bytes))的返回值如果不等于-1，会继续读取，等于-1则说明文件读取结束
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
//            6 关闭输入流与输出流
            fileInputStream.close();
            outputStream.close(); // 当输出流close的时候，则会完成响应，把响应的文件发送给前端
        } catch (Exception e) {
            log.error("file download error", e);
            throw new CustomerException("文件下载失败");
        }
        return Response.correct(response);
    }


}
