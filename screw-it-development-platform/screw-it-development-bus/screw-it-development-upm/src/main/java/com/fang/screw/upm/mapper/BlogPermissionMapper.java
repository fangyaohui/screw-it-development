package com.fang.screw.upm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.fang.screw.domain.po.BlogPermissionPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @FileName BlogPermissionMapper
 * @Description 权限Mapper
 * @Author yaoHui
 * @date 2024-08-05
 **/
@Mapper
public interface BlogPermissionMapper extends BaseMapper<BlogPermissionPO> {

    /**
    * @Description 根据用户ID查询其绑定的权限List
    * @param userId
    * @return {@link List< BlogPermissionPO> }
    * @Author yaoHui
    * @Date 2024/8/5
    */
    List<BlogPermissionPO> getBlogPermissionListByUserId(@Param("userId") Long userId);

}