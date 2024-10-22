package com.fang.screw.adapter.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @FileName DataAdapterActionsPO
 * @Description
 * @Author yaoHui
 * @date 2024-10-21
 **/
@Data
@TableName(value = "data_adapter_actions")
public class DataAdapterActionsPO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String actionName;

    private Long thirdPartyDataSourceId;

    private String sourceTableName;

    private String sourceColumns;

    private String filterConditions;

    private String transformationLogic;

    private Long localStorageConfigurationId;

    private String targetTableName;

    private String targetColumns;

    private String operationType;

    private Integer syncFrequencyMinutes;

    private LocalDateTime lastRunTime;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer delFlag;


}
