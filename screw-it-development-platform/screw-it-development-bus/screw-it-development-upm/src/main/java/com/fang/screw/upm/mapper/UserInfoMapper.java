package com.fang.screw.upm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.fang.screw.domain.po.UserInfoPO;

/**
 * @FileName UserInfoMapper
 * @Description
 * @Author yaoHui
 * @date 2024-07-17
 **/
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfoPO> {
}