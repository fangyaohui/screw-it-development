package com.fang.screw.communal.service;

import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.po.BlogPermissionPO;
import com.fang.screw.domain.po.BlogUserPO;
import com.fang.screw.domain.vo.BlogUserVO;

import java.util.List;

/**
 * @FileName UserDubboService
 * @Description
 * @Author yaoHui
 * @date 2024-07-24
 **/
public interface UserDubboService {

    List<BlogUserPO> getAllUserInfoList();

    /**
     * @Description 根据用户ID查询用户的具体信息
     * @param userId
     * @return {@link R< BlogUserVO> }
     * @Author yaoHui
     * @Date 2024/8/5
     */
    R<BlogUserVO> getUserInfoByUserId(Long userId);

    /**
    * @Description 根据用户ID查询该用户的所有角色列表
    * @param userId
    * @return {@link R< List< Long>> }
    * @Author yaoHui
    * @Date 2024/8/5
    */
    R<List<Long>> getUserRoleIdListByUSerId(Long userId);

    /**
    * @Description 根据用户ID查询其具有的所有权限List
    * @param userId
    * @return {@link R< List< BlogPermissionPO>> }
    * @Author yaoHui
    * @Date 2024/8/5
    */
    List<BlogPermissionPO> getBlogPermissionListByUserId(Long userId);

}