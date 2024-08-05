package com.fang.screw.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @FileName BlogPermissionPO
 * @Description
 * @Author yaoHui
 * @date 2024-08-05
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "blog_permission")
public class BlogPermissionPO extends BasePO{

    // 权限类型(1-按钮,2-表单,3-数据,4-其它)
    private Integer permissionType;

    // 权限编码
    private String permissionCode;

    // 权限名称
    private String permissionName;

    // 动作ID(按钮ID)
    private Integer actionId;

    // 权限范围(1-普通博客，2-普通视频、3-VIP博客、4-VIP视频、5-付费博客、6-付费视频、7-所有资源)
    private Integer permissionScope;

    // 权限类别(1-Read,2-Write,3-Read/Write,4-Other)
    private Integer permissionCategory;

    // 自定义Form唯一ID
    private Integer menuFormId;

    // 创建人
    private String createBy;

    // 修改人
    private String updateBy;


}