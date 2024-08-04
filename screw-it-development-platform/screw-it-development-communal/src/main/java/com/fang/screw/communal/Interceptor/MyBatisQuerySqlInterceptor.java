package com.fang.screw.communal.Interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Connection;

import static com.fang.screw.communal.constant.DynamicParameter.NOT_DEL_FLAG;

/**
 * @FileName MyBatisQuerySqlInterceptor
 * @Description 自动为所有查询添加del_flag = 0 这个条件
 * @Author yaoHui
 * @date 2024-07-28
 **/
@Slf4j
@Component
@Intercepts({
    @Signature(type = StatementHandler.class,
            method = "prepare",
            args = {Connection.class, Integer.class}),
})
public class MyBatisQuerySqlInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();

        BoundSql boundSql = statementHandler.getBoundSql();

        String sql = boundSql.getSql();

        if(!StringUtils.contains(sql,"INSERT")){
            if(StringUtils.contains(sql,"WHERE") && !StringUtils.contains(sql,"del_flag =")){
                sql = sql.substring(0,sql.length() - 1);
                sql = sql + " AND del_flag = " + NOT_DEL_FLAG;

                sql = sql + ")";
            }else if(!StringUtils.contains(sql,"del_flag =")){
                sql = sql + " WHERE (";
                sql = sql + "del_flag = " + NOT_DEL_FLAG;
                sql = sql + ")";
            }
        }



        Field field = boundSql.getClass().getDeclaredField("sql");
        field.setAccessible(true);
        field.set(boundSql,sql);

        log.info(boundSql.getSql().toString());
        return invocation.proceed();
    }
}
