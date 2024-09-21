package com.fang.screw.communal.holder;

import com.fang.screw.domain.po.UserPO;
import org.springframework.stereotype.Component;
import com.fang.screw.domain.po.BlogUserPO;

/**
 * @FileName CurrentUserHolder
 * @Description 全局类 保存当前用户信息
 * @Author yaoHui
 * @date 2024-08-02
 **/
@Component
public class CurrentUserHolder {

    private static final ThreadLocal<UserPO> userIdHolder = new ThreadLocal<>();

    public static void setUser(UserPO user) {
        userIdHolder.set(user);
    }

    public static UserPO getUser() {
        return userIdHolder.get();
    }

    public static void clear() {
        userIdHolder.remove();
    }

}
