package com.fang.screw.adapter.entry;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fang.screw.adapter.enums.ApiMethodEnum;
import com.fang.screw.adapter.enums.DataSourceTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @FileName DataSourcesPO
 * @Description
 * @Author yaoHui
 * @date 2024-10-21
 **/
@Data
@TableName(value = "data_sources")
public class DataSourcesPO  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private DataSourceTypeEnum type;

    private String host;

    private Integer sourceType;

    private Integer port;

    private String username;

    private String password;

    private String databaseName;

    private String connectionOptions;

    private String apiUrl;

    private ApiMethodEnum apiMethod;

    private String apiHeaders;

    private String apiToken;

    private Integer redisDbIndex;

    private String ossBucket;

    private String ossAccessKey;

    private String ossSecretKey;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer delFlag;

}
