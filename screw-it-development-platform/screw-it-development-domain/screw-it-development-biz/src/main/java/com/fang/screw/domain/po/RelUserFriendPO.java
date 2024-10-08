package com.fang.screw.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @FileName RelUserFriendPO
 * @Description
 * @Author yaoHui
 * @date 2024-10-08
 **/
@Data
@TableName(value = "rel_user_friend")
public class RelUserFriendPO {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userA;

    private Integer userB;

    private Integer delFlag;

    private String avatar;


}
