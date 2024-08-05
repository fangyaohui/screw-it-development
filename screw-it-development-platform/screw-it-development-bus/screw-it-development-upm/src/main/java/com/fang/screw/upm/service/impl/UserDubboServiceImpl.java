package com.fang.screw.upm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fang.screw.communal.service.UserDubboService;
import com.fang.screw.communal.utils.R;
import com.fang.screw.upm.mapper.BlogUserMapper;
import com.fang.screw.upm.mapper.UserInfoMapper;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import po.BlogUserPO;
import po.UserInfoPO;
import vo.BlogUserVO;

import java.util.List;

/**
 * @FileName UserServiceImpl
 * @Description
 * @Author yaoHui
 * @date 2024-07-17
 **/
@Service
public class UserDubboServiceImpl extends ServiceImpl<BlogUserMapper, BlogUserPO> implements UserDubboService {



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


}