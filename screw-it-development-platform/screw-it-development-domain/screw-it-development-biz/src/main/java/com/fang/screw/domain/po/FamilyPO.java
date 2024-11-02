package com.fang.screw.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fang.screw.domain.vo.FamilyVO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * @FileName FamilyPO
 * @Description
 * @Author yaoHui
 * @date 2024-11-02
 **/
@Data
@TableName(value = "family")
public class FamilyPO {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 左边用户ID
     */
    @TableField("left_id")
    private Integer leftId;

    /**
     * 左边用户头像
     */
    @TableField("left_cover")
    private String leftCover;

    /**
     * 男生昵称
     */
    @TableField("left_name")
    private String leftName;

    /**
     * 右边用户ID
     */
    @TableField("right_id")
    private Integer rightId;

    /**
     * 右边头像
     */
    @TableField("right_cover")
    private String rightCover;

    /**
     * 女生昵称
     */
    @TableField("right_name")
    private String rightName;

    /**
     * 背景封面
     */
    @TableField("bg_cover")
    private String bgCover;

    /**
     * 计时
     */
    @TableField("timing")
    private String timing;

    /**
     * 倒计时标题
     */
    @TableField("countdown_title")
    private String countdownTitle;

    /**
     * 是否启用[0:否，1:是]
     */
    @TableField("status")
    private Boolean status;

    /**
     * 倒计时时间
     */
    @TableField("countdown_time")
    private String countdownTime;

    /**
     * 额外信息
     */
    @TableField("family_info")
    private String familyInfo;

    /**
     * 点赞数
     */
    @TableField("like_count")
    private Integer likeCount;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 最终修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    private Integer delFlag;

    public FamilyVO transformVO(){
        FamilyVO familyVO = new FamilyVO();
        BeanUtils.copyProperties(this,familyVO);
        return familyVO;
    }

}
