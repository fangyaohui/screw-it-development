package com.fang.screw.adapter.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @FileName MetadataTypePO
 * @Description
 * @Author yaoHui
 * @date 2024-10-21
 **/
@Data
@TableName("metadata_type")
public class MetadataTypePO {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id; // 主数据类型ID

    private Long version; // 乐观锁标识

    private Long tenantId; // 租户ID

    private String metaClass; // 主数据大类

    private String metaCategory; // 主数据类别

    private String metaTypeCode; // 主数据类型编码

    private String metaTypeName; // 主数据类型名称

    private String metaTypeAlias; // 主数据类型别名

    private String description; // 描述

    private Integer delFlag; // 删除标志

    private String createBy; // 创建人

    private LocalDateTime createTime; // 创建时间

    private String updateBy; // 更新人

    private LocalDateTime updateTime; // 更新时间

}
