package com.fang.screw.chat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fang.screw.domain.po.MessagePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @FileName MessageMapper
 * @Description
 * @Author yaoHui
 * @date 2024-10-09
 **/
@Mapper
public interface MessageMapper extends BaseMapper<MessagePO> {
}
