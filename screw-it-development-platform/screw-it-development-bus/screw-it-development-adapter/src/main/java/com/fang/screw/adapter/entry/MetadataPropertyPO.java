package com.fang.screw.adapter.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @FileName MetadataPropertyPO
 * @Description
 * @Author yaoHui
 * @date 2024-10-21
 **/
@Data
@TableName("metadata_property")
public class MetadataPropertyPO {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id; // 主数据属性ID

    private Long version; // 乐观锁标识

    private Long tenantId; // 租户ID

    private Integer metaPropType; // 主数据属性类型

    private String metaPropCode; // 主数据属性编码

    private String metaPropName; // 主数据属性名称

    private String metaPropAlias; // 主数据属性别名

    private Integer metaPropDataType; // 主数据属性数据类型

    private Integer metaPropLength; // 主数据属性数据长度

    private Integer metaPropDecimals; // 主数据属性数据小数位

    private Boolean isBizKeyProp; // 是否业务关键属性

    private String description; // 描述

    private Integer delFlag; // 删除标志

    private String createBy; // 创建人

    private LocalDateTime createTime; // 创建时间

    private String updateBy; // 更新人

    private LocalDateTime updateTime; // 更新时间
}
