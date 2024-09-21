package com.fang.screw.upm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fang.screw.domain.po.UserPO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @FileName UserMapper
 * @Description
 * @Author yaoHui
 * @date 2024-09-20
 **/
@Mapper
public interface UserMapper extends BaseMapper<UserPO> {
}
