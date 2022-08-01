package com.young.test1.guava;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * 读书破万卷，下笔如有神 *
 * 代码反行之，算法记于心 *
 * 项目名: test
 * author: 0YOUNG
 * data:2022/7/19
 */

public class GuavaCache {


    private static final long CACHE_TIME_180 = 30;
    public static Cache<String,String> GLOBAL_CACHE_PWD_FAIL_COUNT = null ;
    public static Cache<String,String> GLOBAL_CACHE_CODE_INFO = null ;
    public static Cache<String,String> GLOBAL_CACHE_CODE_FAIL_COUNT = null ;
    public static Cache<String,String> GLOBAL_CACHE_ACCOUNT_BANNED = null ;
    public static Cache<String,String> GLOBAL_CACHE_ACCOUNT_BANNED_INFO = null ;

    static{
        GLOBAL_CACHE_PWD_FAIL_COUNT = getPwdFailCount();
        GLOBAL_CACHE_CODE_INFO  = getCodeInfo();
        GLOBAL_CACHE_CODE_FAIL_COUNT = getCodeFailCount();
        GLOBAL_CACHE_ACCOUNT_BANNED = getAccountBanned();
        GLOBAL_CACHE_ACCOUNT_BANNED_INFO = getAccountBannedInfo();
    }

    private static Cache<String,String>  getPwdFailCount (){
        Cache<String,String> cache = CacheBuilder.newBuilder()
                //.maximumSize(2) //
                .expireAfterWrite(120, TimeUnit.SECONDS)
                .build();
        return cache;
    }
    private static Cache<String,String>  getCodeInfo (){
        Cache<String,String> cache = CacheBuilder.newBuilder()
                //.maximumSize(2)
                //验证码120s有效
                .expireAfterWrite(120, TimeUnit.SECONDS)
                .build();
        return cache;
    }
    private static Cache<String,String>  getCodeFailCount (){
        Cache<String,String> cache = CacheBuilder.newBuilder()
                //.maximumSize(2) //
                .expireAfterWrite(120, TimeUnit.SECONDS)
                .build();
        return cache;
    }

    private static Cache<String,String>  getAccountBanned (){
        Cache<String,String> cache = CacheBuilder.newBuilder()
                //.maximumSize(2)
                //封号30S
                .expireAfterWrite(30, TimeUnit.SECONDS)
                .build();
        return cache;
    }
    private static Cache<String,String>  getAccountBannedInfo (){
        Cache<String,String> cache = CacheBuilder.newBuilder()
                //.maximumSize(2)
                //封号30S
                .expireAfterWrite(180, TimeUnit.SECONDS)
                .build();
        return cache;
    }
}
