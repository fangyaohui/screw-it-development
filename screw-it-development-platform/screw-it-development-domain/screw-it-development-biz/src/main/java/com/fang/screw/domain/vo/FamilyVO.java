package com.fang.screw.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fang.screw.domain.po.FamilyPO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * @FileName FamilyVO
 * @Description
 * @Author yaoHui
 * @date 2024-11-02
 **/
@Data
public class FamilyVO {

    /**
     * id
     */
    private Integer id;

    /**
     * 左边用户ID
     */
    private Integer leftId;

    /**
     * 左边用户头像
     */
    private String leftCover;

    /**
     * 男生昵称
     */
    private String leftName;

    /**
     * 右边用户ID
     */
    private Integer rightId;

    /**
     * 右边头像
     */
    private String rightCover;

    /**
     * 女生昵称
     */
    private String rightName;

    /**
     * 背景封面
     */
    private String bgCover;

    /**
     * 计时
     */
    private String timing;

    /**
     * 倒计时标题
     */
    private String countdownTitle;

    /**
     * 是否启用[0:否，1:是]
     */
    private Boolean status;

    /**
     * 倒计时时间
     */
    private String countdownTime;

    /**
     * 额外信息
     */
    private String familyInfo;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 最终修改时间
     */
    private LocalDateTime updateTime;

    private Integer delFlag;

    public FamilyPO transformPO(){
        FamilyPO familyPO = new FamilyPO();
        BeanUtils.copyProperties(this,familyPO);
        return familyPO;
    }


}
