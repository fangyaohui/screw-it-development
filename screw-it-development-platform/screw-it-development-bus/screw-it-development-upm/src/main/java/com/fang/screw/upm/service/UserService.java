package com.fang.screw.upm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.dto.UserDTO;
import com.fang.screw.domain.po.BlogUserPO;
import com.fang.screw.domain.po.UserInfoPO;
import com.fang.screw.domain.po.UserPO;
import com.fang.screw.domain.vo.UserVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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

}
