package com.fang.screw.communal.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @FileName BusinessException
 * @Description
 * @Author yaoHui
 * @date 2024-07-27
 **/
public class BusinessException extends Exception{

    private String desc;

    public BusinessException(String message){

        super(message);
        this.desc = message;
    }

    public BusinessException(Integer code,String message){
        super(message);
        this.desc = message;
    }

    public BusinessException(){
        super();
    }

    public R<String> getDesc(){
        return R.failed(desc);
    }


}