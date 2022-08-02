package com.young.test1.controller;

import com.young.test1.domain.dto.PermissionTreeDto;

import com.young.test1.mapper.PermissionTreeMapper;
import com.young.test1.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 读书破万卷，下笔如有神 *
 * 代码反行之，算法记于心 *
 * 项目名: test
 * author: 0YOUNG
 * data:2022/8/1
 */
@RestController
@RequestMapping()
public class PermissionTreeController {

    @Resource
    PermissionTreeMapper permissionTreeMapper;

    @GetMapping("getPermissionTree1")
    public Response getAfterSaleServiceTree3() {

        // 从数据库中获取全部数据
        List<PermissionTreeDto> permissionTreeDtos = permissionTreeMapper.getAllPermission();
        // 用来保存树形结构
        List<PermissionTreeDto> permissionTree = new ArrayList<PermissionTreeDto>();

        for (PermissionTreeDto permissionTreeDto : permissionTreeDtos) {
            // 添加根节点
            if (permissionTreeDto.getParentId() == 0) {
                permissionTree.add(permissionTreeDto);
            }
            for (PermissionTreeDto permissionTreeDto1 : permissionTreeDtos) {
                //子节点的父如果等于根节点
                if (permissionTreeDto.getId().intValue() == permissionTreeDto1.getParentId().intValue()) {
                    permissionTreeDto.getChildren().add(permissionTreeDto1);
                }
            }
        }
        return Response.correct(permissionTree);
    }


    @GetMapping("getPermissionTree")
    public Response getPermissionTree() {
        List<PermissionTreeDto> allPermissionList = permissionTreeMapper.getAllPermission();
        //实体转换
        //生成权限树
        List<PermissionTreeDto> permissionTree = PermissionTreeController.setPermissionTree(allPermissionList);
        return Response.correct(permissionTree);
    }

    public static List<PermissionTreeDto> setPermissionTree(List<PermissionTreeDto> allPermissionList) {
        //根节点
        List<PermissionTreeDto> rootList = new ArrayList();
        //子节点
        List<PermissionTreeDto> childList = new ArrayList();
        //遍历所有节点，将所有根节点放入rootList集合中
        for (PermissionTreeDto permission : allPermissionList) {
            if (permission.getParentId() == 0) {
                rootList.add(permission);
            }
        }
        //遍历根节点设置子节点
        for (PermissionTreeDto permission : rootList) {
            childList = getChildren(permission.getId(), allPermissionList);
            permission.setChildren(childList);
        }
        return rootList;

    }

    /**
     * 递归获取树的子节点
     */

    private static List<PermissionTreeDto> getChildren(Integer id, List<PermissionTreeDto> allList) {
        //存放子节点
        ArrayList<PermissionTreeDto> childList = new ArrayList();
        //遍历所有节点,如果该节点的parentId等于传过来的id,则该节点为子节点
        for (PermissionTreeDto permission : allList) {
            if (permission.getParentId() != null && permission.getParentId().equals(id)) {
                childList.add(permission);
            }
        }
        //递归设置子节点的子节点
        for (PermissionTreeDto permission : childList) {
            permission.setChildren(getChildren(permission.getId(), allList));
        }
        //如果没有子节点则返回空集合
        if (childList.size() == 0) {
            return new ArrayList<>();
        }
        return childList;
    }


}
