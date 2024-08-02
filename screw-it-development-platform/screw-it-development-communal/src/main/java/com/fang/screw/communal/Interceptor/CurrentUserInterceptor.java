package com.fang.screw.communal.Interceptor;

import com.fang.screw.communal.holder.CurrentUserHolder;
import com.fang.screw.communal.utils.JWTUtils;
import com.fang.screw.communal.utils.RedisUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import po.BlogUserPO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.fang.screw.communal.constant.RedisDynamicParameter.REDIS_USER_LOGIN_TOKEN;

/**
 * @FileName CurrentUserInterceptor
 * @Description
 * @Author yaoHui
 * @date 2024-08-02
 **/
@Component
@AllArgsConstructor
public class CurrentUserInterceptor implements HandlerInterceptor {

    private RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String uuid = JWTUtils.getUUID(token);
            CurrentUserHolder.setUser((BlogUserPO) redisUtils.get(REDIS_USER_LOGIN_TOKEN+uuid));
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        CurrentUserHolder.clear();
    }
}
