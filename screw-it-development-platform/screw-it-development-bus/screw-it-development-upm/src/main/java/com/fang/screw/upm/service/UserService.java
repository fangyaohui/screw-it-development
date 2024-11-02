package com.fang.screw.upm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.dto.UserDTO;
import com.fang.screw.domain.po.UserPO;
import com.fang.screw.domain.vo.FamilyVO;
import com.fang.screw.domain.vo.UserVO;

import java.util.List;

/**
 * @FileName UserService
 * @Description
 * @Author yaoHui
 * @date 2024-07-17
 **/
public interface UserService extends IService<UserPO> {

    /***
     * @Description 根据用户ID查询该用户对应的具体用户信息
     * @param userId
     * @return {@link R< UserDTO> }
     * @Author yaoHui
     * @Date 2024/9/23
     */
    R<UserDTO> getUserDTOById(String userId);

    /***
     * @Description 根据keyword查询用户名、电子邮件、电话等相关的用户列表
     * @param keyword
     * @return {@link R< List< UserDTO>> }
     * @Author yaoHui
     * @Date 2024/10/9
     */
    R<List<UserVO>> getUserByUserNameOrEmail(String keyword);

    /***
    * @Description 测试ApplicationsContextAware
    * @return {@link R< String> }
    * @Author yaoHui
    * @Date 2024/10/13
    */
    R<String> testApplicationContextAware();

    /***
     * @Description 更新用户的相关信息
     * @param userVO
     * @return {@link R< String> }
     * @Author yaoHui
     * @Date 2024/10/23
     */
    R<UserVO> updateUserInfo(UserVO userVO);

    /***
     * @Description 查询当前用户的Family相关信息
     * @return {@link R< FamilyVO> }
     * @Author yaoHui
     * @Date 2024/11/2
     */
    R<FamilyVO> getUserFamily();

    /***
     * @Description 保存用户的相关Family信息——新建
     * @param familyVO
     * @return {@link R< String> }
     * @Author yaoHui
     * @Date 2024/11/2
     */
    R<String> saveAndUpdateFamily(FamilyVO familyVO);

}
