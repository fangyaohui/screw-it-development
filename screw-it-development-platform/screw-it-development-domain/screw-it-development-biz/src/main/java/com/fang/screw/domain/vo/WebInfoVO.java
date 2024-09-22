package com.fang.screw.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 网站信息表
 * </p>
 *
 * @author sara
 * @since 2021-09-14
 */
@Data

public class WebInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 网站名称
     */
    private String webName;

    /**
     * 网站信息
     */
    private String webTitle;

    /**
     * 公告
     */
    private String notices;

    /**
     * 页脚
     */
    private String footer;

    /**
     * 背景
     */
    private String backgroundImage;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 随机头像
     */
    private String randomAvatar;

    /**
     * 随机名称
     */
    private String randomName;

    /**
     * 随机封面
     */
    private String randomCover;

    /**
     * 看板娘消息
     */
    private String waifuJson;

    /**
     * 是否启用[0:否，1:是]
     */
    private Boolean status;

    private String historyAllCount;

    private String historyDayCount;

    private String defaultStoreType;

    private Integer delFlag;
}
