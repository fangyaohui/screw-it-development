package com.fang.screw.chat.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @FileName TestController
 * @Description
 * @Author yaoHui
 * @date 2024-10-08
 **/
@RestController
@RequestMapping("/test")
@AllArgsConstructor
@Slf4j
public class TestController {

    @RequestMapping("/getTest")
    public String getTest(){
        return "fang";
    }
}
