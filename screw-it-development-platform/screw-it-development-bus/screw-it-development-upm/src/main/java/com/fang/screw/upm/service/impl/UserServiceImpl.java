package com.fang.screw.upm.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fang.screw.communal.holder.CurrentUserHolder;
import com.fang.screw.communal.utils.JWTUtils;
import com.fang.screw.communal.utils.R;
import com.fang.screw.communal.utils.RedisUtils;
import com.fang.screw.communal.utils.StringUtil;
import com.fang.screw.domain.dto.UserDTO;
import com.fang.screw.domain.enums.CodeMsg;
import com.fang.screw.domain.po.FamilyPO;
import com.fang.screw.domain.po.UserPO;
import com.fang.screw.domain.vo.FamilyVO;
import com.fang.screw.domain.vo.UserVO;
import com.fang.screw.upm.mapper.FamilyMapper;
import com.fang.screw.upm.mapper.UserMapper;
import com.fang.screw.upm.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.fang.screw.communal.constant.DynamicParameter.NOT_DEL_FLAG;
import static com.fang.screw.communal.constant.RedisDynamicParameter.REDIS_USER_LOGIN_STATUS;
import static com.fang.screw.communal.constant.RedisDynamicParameter.REDIS_USER_LOGIN_TOKEN;


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

    private RabbitTemplate rabbitTemplate;

    private FamilyMapper familyMapper;

    private RedisUtils redisUtils;

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

    /***
     * @Description 测试ApplicationsContextAware
     * @return {@link R< String> }
     * @Author yaoHui
     * @Date 2024/10/13
     */
    @Override
    public R<String> testApplicationContextAware() {
        return R.ok("testApplicationContextAware is running");
    }

    /***
     * @Description 更新用户的相关信息
     * @param userVO
     * @return {@link R< String> }
     * @Author yaoHui
     * @Date 2024/10/23
     */
    @Override
    public R<UserVO> updateUserInfo(UserVO userVO) {

        if(ObjectUtils.isEmpty(userVO)){
            return R.failed("需要更新的数据为空");
        }

        UserPO user = CurrentUserHolder.getUser();
        if(!Objects.equals(user.getId(), userVO.getId())){
            return R.failed("账户状态异常，请重新登录");
        }

        user = userVO.transformPO();

        baseMapper.update(user,Wrappers.<UserPO>lambdaUpdate()
                .eq(UserPO::getId,user.getId()));

        user = baseMapper.selectById(user.getId());

        rabbitTemplate.convertAndSend("updateUserInfoExchange","updateUserInfoKey",user.transformVO());
        return R.ok(user.transformVO());
    }

    /***
     * @Description 查询当前用户的Family相关信息
     * @return {@link R< FamilyVO> }
     * @Author yaoHui
     * @Date 2024/11/2
     */
    @Override
    public R<FamilyVO> getUserFamily() {

        UserPO userPO = CurrentUserHolder.getUser();
        if(ObjectUtils.isEmpty(userPO)){
            return R.failed(CodeMsg.LOGIN_EXPIRED);
        }

        // 查询MySQL中对应的Family信息
        FamilyPO familyPO = familyMapper.selectOne(Wrappers.<FamilyPO>lambdaQuery()
                .eq(FamilyPO::getLeftId,userPO.getId())
                .eq(FamilyPO::getDelFlag,NOT_DEL_FLAG));
        if(ObjectUtils.isEmpty(familyPO)){
            return R.ok(null);
        }

//        // 尝试在Redis中找到对应用户的头像和昵称
//        String leftToken = (String) redisUtils.get(REDIS_USER_LOGIN_STATUS + familyPO.getLeftId());
//        String rightToken = (String) redisUtils.get(REDIS_USER_LOGIN_STATUS + familyPO.getRightId());
//        UserPO leftUserPO = null;
//        UserPO rightUserPO = null;
//        if(!ObjectUtils.isEmpty(leftToken)){
//            leftUserPO = (UserPO) redisUtils.get(REDIS_USER_LOGIN_TOKEN + JWTUtils.getUUID(leftToken));
//        }
//        if(!ObjectUtils.isEmpty(rightToken)){
//            rightUserPO = (UserPO) redisUtils.get(REDIS_USER_LOGIN_TOKEN + JWTUtils.getUUID(rightToken));
//        }
//
//        // 如果在Redis中未找到对应用户信息，则到MySQL中查询
//        if(ObjectUtils.isEmpty(leftUserPO)){
//            leftUserPO = getById(familyPO.getLeftId());
//        }
//        if(ObjectUtils.isEmpty(rightUserPO)){
//            rightUserPO = getById(familyPO.getRightId());
//        }
//
//        // 将用户头像和昵称添加至VO中
//        FamilyVO familyVO = familyPO.transformVO();
//        if(!ObjectUtils.isEmpty(leftUserPO)){
//            familyVO.setLeftCover(leftUserPO.getAvatar());
//            familyVO.setLeftName(leftUserPO.getUsername());
//        }
//        if(!ObjectUtils.isEmpty(rightUserPO)){
//            familyVO.setRightCover(rightUserPO.getAvatar());
//            familyVO.setRightName(rightUserPO.getUsername());
//        }

        return R.ok(familyPO.transformVO());
    }

    /***
     * @Description 保存用户的相关Family信息——新建
     * @param familyVO
     * @return {@link R< String> }
     * @Author yaoHui
     * @Date 2024/11/2
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> saveAndUpdateFamily(FamilyVO familyVO) {
        if(!baseMapper.exists(Wrappers.<UserPO>lambdaQuery().eq(UserPO::getId,familyVO.getRightId()))){
            return R.failed(CodeMsg.PARAMETER_ERROR);
        }

        UserPO userPO = CurrentUserHolder.getUser();
        if(ObjectUtils.isEmpty(userPO)){
            return R.failed(CodeMsg.LOGIN_EXPIRED);
        }
        familyVO.setLeftId(userPO.getId());

        try{
            // 更新Family信息
            if(familyMapper.exists(Wrappers.<FamilyPO>lambdaQuery()
                    .eq(FamilyPO::getLeftId,familyVO.getLeftId()))){
                familyMapper.update(familyVO.transformPO(),Wrappers.<FamilyPO>lambdaUpdate()
                        .eq(FamilyPO::getLeftId,familyVO.getLeftId()));
                FamilyPO familyPO = exchangeFamilyInfo(familyVO.transformPO());
                familyMapper.update(familyPO,Wrappers.<FamilyPO>lambdaUpdate()
                        .eq(FamilyPO::getLeftId,familyPO.getLeftId()));
                return R.ok();
            }

            // 保存自己Family
            familyMapper.insert(familyVO.transformPO());

            // 保存伴侣的Family
            FamilyPO familyPO = exchangeFamilyInfo(familyVO.transformPO());
            familyMapper.insert(familyPO);
        }catch(Exception e){
            log.error(e.getMessage());
            return R.failed(CodeMsg.FAIL);
        }

        return R.ok();
    }

    /***
    * @Description 交换伴侣名称、ID、头像
    * @param familyPO
    * @return {@link FamilyPO }
    * @Author yaoHui
    * @Date 2024/11/2
    */
    private FamilyPO exchangeFamilyInfo(FamilyPO familyPO){
        Integer id = familyPO.getRightId();
        familyPO.setRightId(familyPO.getLeftId());
        familyPO.setLeftId(id);
        String temp = familyPO.getRightCover();
        familyPO.setRightCover(familyPO.getLeftCover());
        familyPO.setLeftCover(temp);
        temp = familyPO.getRightName();
        familyPO.setRightName(familyPO.getLeftName());
        familyPO.setLeftName(temp);
        return familyPO;
    }
}
