package com.fang.screw.communal.aop;

import com.alibaba.nacos.shaded.com.google.gson.Gson;
import com.fang.screw.communal.annotation.CacheRemove;
import com.fang.screw.communal.annotation.Cacheable;
import com.fang.screw.communal.utils.R;
import com.fang.screw.communal.utils.RedisUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static com.fang.screw.communal.constant.RedisDynamicParameter.REDIS_BASE_KEY;

/**
 * @FileName CacheAspect
 * @Description
 * @Author yaoHui
 * @date 2024-10-23
 **/
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class CacheAspect {

    private RedisUtils redisUtils;

    private TransactionTemplate transactionTemplate;

    @Pointcut("@annotation(com.fang.screw.communal.annotation.CacheRemove)")
    public void cacheRemovePointCut(){

    }

    // 设置切入点，被注解cacheable标记的方法为切入点
    @Pointcut("@annotation(com.fang.screw.communal.annotation.Cacheable)")
    public void cacheablePointCut(){

    }

    @Around("cacheRemovePointCut()")
    public Object doCacheRemovePointCut(ProceedingJoinPoint joinPoint) throws Throwable {

        final Object[] res = {null};

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
                    CacheRemove cacheRemove = method.getAnnotation(CacheRemove.class);

                    String keyExpression = cacheRemove.key();
                    Object[] args = joinPoint.getArgs();
                    String cacheKey = generateCacheKey(keyExpression, args);
                    cacheKey = cacheKey.substring(0,cacheKey.length()-1);

                    String key = getRedisKey(cacheRemove.value(),cacheKey) + "*";

                    List<String> keys = new ArrayList<>(redisUtils.getKeysByPattern(key));

                    res[0] =  joinPoint.proceed();

                    redisUtils.del(keys);

                    log.info("CacheRemoveKey running delete key is :"+keys.toString());
                } catch (Exception e ) {
                    // 发生异常时，手动回滚事务
                    status.setRollbackOnly();
                    log.error("发生异常，事务已回滚：" + e.getMessage());
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
        return res[0];
    }

    /***
    * @Description 判断该方法绑定的Redis中是否存在结果，如果不存在则把结果保存到Redis中
    * @param joinPoint
    * @return {@link Object }
    * @Author yaoHui
    * @Date 2024/10/23
    */
    @Around("cacheablePointCut()")
    public Object doCacheablePointCut(ProceedingJoinPoint joinPoint) throws Throwable {

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Cacheable cacheable = method.getAnnotation(Cacheable.class);
        String keyExpression  = cacheable.key();

        Object[] args = joinPoint.getArgs();
        // 解析 key 表达式
        String cacheKey = generateCacheKey(keyExpression, args);
        cacheKey = cacheKey.substring(0,cacheKey.length()-1);

        String key = getRedisKey(cacheable.value(),cacheKey);

        // 为每一个设置一个随机过期时间 避免缓存雪崩
        long expireTime = new Random().nextInt(100) * 2000L;

        R result = (R) redisUtils.get(key);
        if(ObjectUtils.isEmpty(result)){
            result = (R) joinPoint.proceed();
            redisUtils.set(key,result,cacheable.expireTime()*1000L + expireTime);
        }

        return result;
    }

    private String getRedisKey(String value,String key){
        return REDIS_BASE_KEY + value + ":" + key;
    }

    private String generateCacheKey(String keyExpression, Object[] args) {
        // 解析 key 表达式
        StringBuilder cacheKeyBuilder = new StringBuilder();
        String[] keyParts = keyExpression.split(":");
        int index = 0;
        for (String part : keyParts) {
            if (part.startsWith("#")) {
                // 取出参数的索引，支持多层级属性访问
                String parameterExpression = part.substring(1);
                Object paramValue = null;
                if(!parameterExpression.contains(".")){
                    paramValue = args[index++];
                }else{
                    String[] properties = parameterExpression.split("\\.");
                    paramValue = getParamValue(args, properties);
                }
                cacheKeyBuilder.append(paramValue).append(":");
            } else {
                cacheKeyBuilder.append(part).append(":");
            }
        }

        return cacheKeyBuilder.toString();
    }

    private Object getParamValue(Object[] args, String[] properties) {
        int paramIndex = Integer.parseInt(properties[0]);
        Object param = args[paramIndex];

        for (int i = 1; i < properties.length; i++) {
            String property = properties[i];
            try {
                param = param.getClass().getMethod("get" + capitalize(property)).invoke(param);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        return param;
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
