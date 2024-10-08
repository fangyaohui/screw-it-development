package com.fang.screw.communal.component;

import com.fang.screw.communal.annotation.CurrentUser;
import com.fang.screw.communal.holder.CurrentUserHolder;
import com.fang.screw.communal.utils.JWTUtils;
import com.fang.screw.communal.utils.RedisUtils;
import com.fang.screw.domain.po.UserPO;
import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import com.fang.screw.domain.po.BlogUserPO;

import javax.servlet.http.HttpServletRequest;

import static com.fang.screw.communal.constant.RedisDynamicParameter.REDIS_USER_LOGIN_TOKEN;

/**
 * @FileName CurrentUserArgumentResolver
 * @Description
 * @Author yaoHui
 * @date 2024-08-02
 **/
@Component
@AllArgsConstructor
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    private RedisUtils redisUtils;

    /***
    * @Description 拦截器 判断是否有使用这个CurrentUser注解
    * @param parameter
    * @return {@link boolean }
    * @Author yaoHui
    * @Date 2024/8/2
    */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            String uuid = JWTUtils.getUUID(token);
            CurrentUserHolder.setUser((UserPO) redisUtils.get(REDIS_USER_LOGIN_TOKEN+uuid));
            return (UserPO) redisUtils.get(REDIS_USER_LOGIN_TOKEN+uuid);
        }
        return null;
    }
}
