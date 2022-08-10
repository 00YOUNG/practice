package com.young.test1.controller;

import com.young.test1.Captcha;
import com.young.test1.domain.Info;
import com.young.test1.guava.GuavaCache;
import com.young.test1.response.Response;
import org.springframework.http.HttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 项目名: test
 * author: 0YOUNG
 * data:2022/7/18
 * @author EDY
 */
@RestController
@RequestMapping("")
public class loginTest {


    @RequestMapping("/login")
    public Response login(@RequestBody Info info) {

        String username = info.getUsername();
        String password = info.getPassword();
        String code = info.getCode();

        String AccountInfo = GuavaCache.GLOBAL_CACHE_ACCOUNT_BANNED.getIfPresent(username);
        String CodeFailCount = GuavaCache.GLOBAL_CACHE_CODE_FAIL_COUNT.getIfPresent(username);
        String AccountBannedInfo = GuavaCache.GLOBAL_CACHE_ACCOUNT_BANNED_INFO.getIfPresent(username);

        //判断用户是否已经封过号   -->用户是没有被封过号的
        if (!StringUtils.hasLength(AccountBannedInfo)) {
            //判断是否是封号状态
            if (StringUtils.hasLength(AccountInfo)) {
                return Response.error("账号属于封号状态，请30S之后在试");
            }
            //判断验证码错误是否超过3次
            if (StringUtils.hasLength(CodeFailCount) && Integer.parseInt(CodeFailCount) >= 3) {
                GuavaCache.GLOBAL_CACHE_ACCOUNT_BANNED.put(username, "1");    //是否已经封号
                GuavaCache.GLOBAL_CACHE_ACCOUNT_BANNED_INFO.put(username, "1");   //是否封过号
                return Response.error("验证码输入错误三次，已封号，30S再试");
            } else {
                //判断是否有验证码
                if (StringUtils.hasLength(code)) {
                    //判断验证码是否错误
                    String CodeInfo = GuavaCache.GLOBAL_CACHE_CODE_INFO.getIfPresent(username);
                    if (!code.equals(CodeInfo)) {
                        if (!StringUtils.hasLength(CodeFailCount)) {
                            CodeFailCount = "1";
                        } else {
                            CodeFailCount = (Integer.valueOf(CodeFailCount) + 1) + "";
                        }
                        GuavaCache.GLOBAL_CACHE_CODE_FAIL_COUNT.put(username, CodeFailCount);
                        return Response.error("验证码输入错误");
                    }

                    //判断用户名
                    if (!"zhangsan".equals(username)) {
                        return Response.error("用户名错误");
                    }
                    //验证输入密码是否超过三次
                    String PwdFailCount = GuavaCache.GLOBAL_CACHE_PWD_FAIL_COUNT.getIfPresent(username);
                    if (StringUtils.hasLength(PwdFailCount) && Integer.parseInt(PwdFailCount) >= 3) {
                        return Response.error("密码输入超过三次，请输入验证码");
                    }
                    //判断密码是否错误，错误就+1（第一次为0）
                    if (!"123".equals(password)) {
                        if (!StringUtils.hasLength(PwdFailCount)) {
                            PwdFailCount = "1";
                        } else {
                            PwdFailCount = (Integer.parseInt(PwdFailCount) + 1) + "";
                        }
                        GuavaCache.GLOBAL_CACHE_PWD_FAIL_COUNT.put(username, PwdFailCount);
                        return Response.error("密码不正确！");
                    }
                } else {
                    //判断用户名
                    if (!"zhangsan".equals(username)) {
                        return Response.error("用户名错误");
                    }
                    //验证输入密码是否超过三次
                    String PwdFailCount = GuavaCache.GLOBAL_CACHE_PWD_FAIL_COUNT.getIfPresent(username);
                    if (StringUtils.hasLength(PwdFailCount) && Integer.parseInt(PwdFailCount) >= 3) {
                        return Response.error("密码输入超过三次，请输入验证码");
                    }
                    //判断密码是否错误，错误就+1（第一次为0）
                    if (!"123".equals(password)) {
                        if (!StringUtils.hasLength(PwdFailCount)) {
                            PwdFailCount = "1";
                        } else {
                            PwdFailCount = (Integer.parseInt(PwdFailCount) + 1) + "";
                        }
                        GuavaCache.GLOBAL_CACHE_PWD_FAIL_COUNT.put(username, PwdFailCount);

                        return Response.error("密码不正确！");

                    }
                }


                return Response.correct();
            }
        }
        //判断用户是否已经封过号 --> 用户已经是封过号了的
        else {
            //判断是否是封号状态
            if (StringUtils.hasLength(AccountInfo)) {
                return Response.error("账号属于封号状态，请30S之后在试");
            }
            if (StringUtils.hasLength(CodeFailCount) && Integer.parseInt(CodeFailCount) >= 3) {
                GuavaCache.GLOBAL_CACHE_ACCOUNT_BANNED.put(username, "1");    //是否已经封号
                GuavaCache.GLOBAL_CACHE_ACCOUNT_BANNED_INFO.put(username, "1");   //是否封过号
                return Response.error("验证码输入错误三次，已封号，30S再试");
            } else {
                if (!StringUtils.hasLength(code)) {
                    return Response.error("该账户已被封过号，请输入验证码");
                }
                //判断验证码是否错误
                String CodeInfo = GuavaCache.GLOBAL_CACHE_CODE_INFO.getIfPresent(username);
                if (!code.equals(CodeInfo)) {
                    if (!StringUtils.hasLength(CodeFailCount)) {
                        CodeFailCount = "1";
                    } else {
                        CodeFailCount = (Integer.valueOf(CodeFailCount) + 1) + "";
                    }
                    GuavaCache.GLOBAL_CACHE_CODE_FAIL_COUNT.put(username, CodeFailCount);
                    return Response.error("验证码输入错误");
                }
                //判断用户名
                if (!"zhangsan".equals(username)) {
                    return Response.error("用户名错误");
                }
                String PwdFailCount = GuavaCache.GLOBAL_CACHE_PWD_FAIL_COUNT.getIfPresent(username);
                //验证输入密码是否超过三次
                if (StringUtils.hasLength(PwdFailCount) && Integer.parseInt(PwdFailCount) >= 3) {
                    return Response.error("密码输入超过三次，请输入验证码");
                }
                //判断密码是否错误，错误就+1（第一次为0）
                if (!"123".equals(password)) {
                    if (!StringUtils.hasLength(PwdFailCount)) {
                        PwdFailCount = "1";
                    } else {
                        PwdFailCount = (Integer.parseInt(PwdFailCount) + 1) + "";
                    }
                    GuavaCache.GLOBAL_CACHE_PWD_FAIL_COUNT.put(username, PwdFailCount);
                    return Response.error("密码不正确！");
                }
                return Response.correct();
            }


        }

    }


    @GetMapping("/getCode")
    public void getCode (String username,HttpServletRequest request, HttpServletResponse response){



        Captcha captcha = new Captcha();
        String code =captcha.getRandcode(request,response);

        GuavaCache.GLOBAL_CACHE_CODE_INFO.put(username, code);
    }
    


}





