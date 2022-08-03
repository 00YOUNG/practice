package com.young.test1.response;

import com.github.pagehelper.PageInfo;
import com.young.test1.domain.vo.SelfPageInfo;
import org.springframework.jmx.export.naming.SelfNaming;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 读书破万卷，下笔如有神 *
 * 代码反行之，算法记于心 *
 * 项目名: test
 * author: 0YOUNG
 * data:2022/7/18
 */

@RequestMapping
public  class  Response {
    private Integer code;
    private String msg;
    private Object data;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }



    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(Object data) {
        this.data = data;
    }



    public static Response correct(){
        Response response = new Response();
        response.setCode(200);
        response.setMsg("成功");
        response.setData(null);
        return response;
    }
    public static Response correct(Object data){
        Response response = new Response();
        response.setCode(200);
        response.setMsg("成功");
        response.setData(data);

        return  response;

    }


    public static Response error(Object data){
        Response response = new Response();
        response.setCode(500);
        response.setMsg("失败");
        response.setData(data);
        return response;
    }

//    public  static Response error(Integer code,String msg){
//        Response response = new Response();
//        response.setCode(code);
//        response.setMsg(msg);
//        return  response;
//    }

    public  static Response error(String msg){
        Response response = new Response();
        response.setCode(500);
        response.setMsg(msg);
        return  response;
    }

}
