package com.fang.screw.blog.feign;

import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @FileName UserFeign
 * @Description
 * @Author yaoHui
 * @date 2024-09-22
 **/
@FeignClient(name = "screw-it-development-upm")
public interface UserFeign {

    @GetMapping("/user/getUserDTOById/{userId}")
    R<UserDTO> getUserDTOById(@PathVariable Integer userId);
}
