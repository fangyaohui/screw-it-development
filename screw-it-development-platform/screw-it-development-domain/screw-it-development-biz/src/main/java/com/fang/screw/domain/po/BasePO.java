package com.fang.screw.domain.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @FileName BasePO
 * @Description 基础PO
 * @Author yaoHui
 * @date 2024-07-27
 **/
@Data
public class BasePO {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    @TableField(fill = FieldFill.INSERT,value = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE,value = "update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private Integer status;

    private Integer delFlag;

    private String description;
}
