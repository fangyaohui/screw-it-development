package com.fang.screw.adapter.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @FileName MetaMappingPO
 * @Description
 * @Author yaoHui
 * @date 2024-10-21
 **/
@Data
@TableName("meta_mapping")
public class MetaMappingPO {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id; // 主数据数据映射配置ID

    private Long version; // 乐观锁标识

    private Long metaTypePropId; // 主数据类型属性配置ID

    private String metaTypeCode; // 主数据类型编码

    private String metaTypeName; // 主数据类型名称

    private String metaPropCode; // 主数据属性编码

    private String metaPropName; // 主数据属性名称

    private String mappingPropName; // 映射属性名称

    private Integer mappingPropDataType; // 映射属性数据类型

    private Integer mappingPropLength; // 映射属性数据长度

    private Integer mappingPropDecimals; // 映射属性数据小数位

    private String description; // 描述

    private String delFlag; // 删除标志

    private String createBy; // 创建人

    private LocalDateTime createTime; // 创建时间

    private String updateBy; // 更新人

    private LocalDateTime updateTime; // 更新时间

}
