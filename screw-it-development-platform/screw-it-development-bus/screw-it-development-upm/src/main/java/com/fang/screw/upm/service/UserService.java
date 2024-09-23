package com.fang.screw.upm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.dto.UserDTO;
import com.fang.screw.domain.po.BlogUserPO;
import com.fang.screw.domain.po.UserInfoPO;
import com.fang.screw.domain.po.UserPO;
import org.springframework.web.bind.annotation.PathVariable;

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
}
