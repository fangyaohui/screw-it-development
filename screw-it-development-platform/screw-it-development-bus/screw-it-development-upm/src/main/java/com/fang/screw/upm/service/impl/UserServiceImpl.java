package com.fang.screw.upm.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fang.screw.communal.utils.R;
import com.fang.screw.communal.utils.StringUtil;
import com.fang.screw.domain.dto.UserDTO;
import com.fang.screw.domain.po.UserPO;
import com.fang.screw.domain.vo.UserVO;
import com.fang.screw.upm.mapper.UserMapper;
import com.fang.screw.upm.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;


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

    /***
     * @Description 根据keyword查询用户名、电子邮件、电话等相关的用户列表
     * @param keyword
     * @return {@link R< List< UserDTO>> }
     * @Author yaoHui
     * @Date 2024/10/9
     */
    @Override
    public R<List<UserVO>> getUserByUserNameOrEmail(String keyword) {

        if(StringUtils.isBlank(keyword)){
            return R.failed("请输入需要查询的用户名或者邮箱等");
        }
        List<UserPO> userPOList = baseMapper.selectList(Wrappers.<UserPO>lambdaQuery()
                .eq(UserPO::getUsername,keyword)
                .or()
                .eq(UserPO::getEmail,keyword)
                .or()
                .eq(UserPO::getPhoneNumber,keyword));

        if(ObjectUtils.isEmpty(userPOList)){
            return R.failed("查无此人");
        }

        List<UserVO> userVOList = userPOList.stream().map(e -> {
            UserVO userVO = new UserVO();
            userVO.setId(e.getId());
            userVO.setUsername(e.getUsername());
            userVO.setAvatar(e.getAvatar());
            return userVO;
        }).collect(Collectors.toList());

        return R.ok(userVOList);
    }


}
