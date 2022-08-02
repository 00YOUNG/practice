package com.young.test1.domain.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 读书破万卷，下笔如有神 *
 * 代码反行之，算法记于心 *
 * 项目名: test
 * author: 0YOUNG
 * data:2022/8/1
 */

public class PermissionTreeDto {
    private Integer  id;
    private String name;
    private Integer parentId;
    private Integer seq;
    private List<PermissionTreeDto> children =new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public List<PermissionTreeDto> getChildren() {
        return children;
    }

    public void setChildren(List<PermissionTreeDto> children) {
        this.children = children;
    }
}
