package com.fang.screw.upm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fang.screw.communal.service.UserDubboService;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.po.BlogPermissionPO;
import com.fang.screw.upm.mapper.BlogPermissionMapper;
import com.fang.screw.upm.mapper.BlogUserMapper;

import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.fang.screw.domain.po.BlogUserPO;
import com.fang.screw.domain.vo.BlogUserVO;

import java.util.List;

/**
 * @FileName UserServiceImpl
 * @Description
 * @Author yaoHui
 * @date 2024-07-17
 **/
@DubboService
@AllArgsConstructor
public class UserDubboServiceImpl extends ServiceImpl<BlogUserMapper, BlogUserPO> implements UserDubboService {

    private BlogPermissionMapper blogPermissionMapper;

    @Override
    public List<BlogUserPO> getAllUserInfoList() {
        return baseMapper.selectList(null);
    }

    /**
     * @Description 根据用户ID查询用户的具体信息
     * @param userId
     * @return {@link R< BlogUserVO> }
     * @Author yaoHui
     * @Date 2024/8/5
     */
    @Override
    public R<BlogUserVO> getUserInfoByUserId(Long userId) {
        BlogUserPO blogUserPO = baseMapper.selectById(userId);
        if(ObjectUtils.isEmpty(blogUserPO)){
            return R.failed("查无此人");
        }

        return R.ok(blogUserPO.transformToVO());
    }

    /**
     * @Description 根据用户ID查询该用户的所有角色列表
     * @param userId
     * @return {@link R< List< Long>> }
     * @Author yaoHui
     * @Date 2024/8/5
     */
    @Override
    public R<List<Long>> getUserRoleIdListByUSerId(Long userId) {

        return null;
    }

    /**
     * @Description 根据用户ID查询其具有的所有权限List
     * @param userId
     * @return {@link R< List< BlogPermissionPO>> }
     * @Author yaoHui
     * @Date 2024/8/5
     */
    @Override
    public List<BlogPermissionPO> getBlogPermissionListByUserId(Long userId) {
        return blogPermissionMapper.getBlogPermissionListByUserId(userId);
//        return null;
    }


}