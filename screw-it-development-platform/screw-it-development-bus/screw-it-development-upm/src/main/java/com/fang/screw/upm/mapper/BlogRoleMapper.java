package com.fang.screw.upm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import po.BlogRolePO;

import java.util.List;

/**
 * @FileName BlogRoleMapper
 * @Description
 * @Author yaoHui
 * @date 2024-08-05
 **/
@Mapper
public interface BlogRoleMapper extends BaseMapper<BlogRolePO> {

    /**
    * @Description 根据用户ID查询其所有有效的角色列表
    * @param userId
    * @return {@link List< Long> }
    * @Author yaoHui
    * @Date 2024/8/5
    */
    List<Long> getRoleListByUserId(Long userId);

}