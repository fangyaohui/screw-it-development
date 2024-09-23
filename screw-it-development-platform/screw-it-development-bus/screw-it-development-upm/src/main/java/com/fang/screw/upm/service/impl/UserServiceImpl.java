package com.fang.screw.upm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.dto.UserDTO;
import com.fang.screw.domain.po.UserPO;
import com.fang.screw.upm.mapper.UserMapper;
import com.fang.screw.upm.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


/**
 * @FileName UserServiceImpl
 * @Description
 * @Author yaoHui
 * @date 2024-09-21
 **/
@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPO> implements UserService{



    /***
     * @Description 根据用户ID查询该用户对应的具体用户信息
     * @param userId
     * @return {@link R< UserDTO> }
     * @Author yaoHui
     * @Date 2024/9/23
     */
    @Override
    public R<UserDTO> getUserDTOById(String userId) {

        UserPO userPO = getById(userId);

        if(ObjectUtils.isEmpty(userPO)){
            return R.success();
        }

        UserDTO userDTO = userPO.transformDTO();
        return R.success(userDTO);
    }
}
