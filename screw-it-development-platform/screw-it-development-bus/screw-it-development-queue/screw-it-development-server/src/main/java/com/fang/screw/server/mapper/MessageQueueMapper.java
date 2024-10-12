package com.fang.screw.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fang.screw.domain.po.MessageQueuePO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @FileName MessageQueueMapper
 * @Description
 * @Author yaoHui
 * @date 2024-10-12
 **/
@Mapper
public interface MessageQueueMapper extends BaseMapper<MessageQueuePO> {
}
