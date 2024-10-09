package com.fang.screw.communal.aop;

import com.fang.screw.communal.holder.CurrentUserHolder;
import com.fang.screw.communal.utils.R;
import com.fang.screw.domain.po.UserPO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @FileName SecurityAspect
 * @Description 权限校验 限流 防刷 AOP切面
 * @Author yaoHui
 * @date 2024-10-09
 **/
@Aspect
@Slf4j
@Component
public class SecurityAspect {

    // 用于记录每个IP的请求时间戳，用于限流
    private Map<String, List<Long>> requestTimestamps = new ConcurrentHashMap<>();

    // 用于封禁IP的列表
    private Set<String> bannedIps = ConcurrentHashMap.newKeySet();

    // 限流阈值
    private static final int MAX_REQUESTS = 10;  // 最多允许的请求次数
    private static final long TIME_WINDOW_MS = 10 * 1000; // 时间窗口，10秒

    // 切入点 指定被注解CheckPermission标记的就是连接点
    @Pointcut("@annotation(com.fang.screw.communal.annotation.CheckPermission)")
    public void checkPermission(){

    }

    /***
    * @Description 对连接点进行业务扩展 进行鉴权 限流 防刷等操作
    * @param proceedingJoinPoint
    * @return {@link Object }
    * @Author yaoHui
    * @Date 2024/10/9
    */
    @Around("checkPermission()")
    public Object doPermissionCheck(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String ipAddress = request.getRemoteAddr();
        UserPO userPO = CurrentUserHolder.getUser();

        // 检查IP是否被封禁
        if (bannedIps.contains(ipAddress)) {
            return R.failed("该IP已被封禁，请稍后再试！");
        }

        // 非超级管理员不可以访问该接口
        if (userPO.getUserType() != 0){
            return R.failed("无访问权限！");
        }

        // 限流机制
        if (isRequestTooFrequent(ipAddress)) {
            bannedIps.add(ipAddress);  // 封禁该IP
            log.warn("IP {} 被封禁，因短时间内过多请求", ipAddress);
            return R.failed("请求过于频繁，请稍后再试！");
        }

        return proceedingJoinPoint.proceed();
    }


    /***
    * @Description 判断请求是否过于频繁
    * @param ipAddress
    * @return {@link boolean }
    * @Author yaoHui
    * @Date 2024/10/9
    */
    private boolean isRequestTooFrequent(String ipAddress) {
        long currentTime = System.currentTimeMillis();
        requestTimestamps.putIfAbsent(ipAddress, new ArrayList<>());
        List<Long> timestamps = requestTimestamps.get(ipAddress);

        timestamps.add(currentTime);

        // 清理超过时间窗口的请求
        timestamps.removeIf(timestamp -> currentTime - timestamp > TIME_WINDOW_MS);

        return timestamps.size() > MAX_REQUESTS;
    }




}
