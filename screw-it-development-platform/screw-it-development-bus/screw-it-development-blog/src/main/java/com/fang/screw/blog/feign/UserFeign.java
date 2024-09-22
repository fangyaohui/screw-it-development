package com.fang.screw.blog.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @FileName UserFeign
 * @Description
 * @Author yaoHui
 * @date 2024-09-22
 **/
@FeignClient(name = "screw-it-development-upm")
public interface UserFeign {

}
