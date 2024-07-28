package com.fang.screw.communal.Interceptor;

import com.alibaba.cloud.commons.lang.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @FileName MybatisSqlInterceptor
 * @Description Mybatis-Plus 自定义SQL拦截处理
 * @Author yaoHui
 * @date 2024-07-24
 **/
@Slf4j
@Component
@Intercepts({
        @Signature(type = StatementHandler.class,
                method = "query",
                args = {Statement.class, ResultHandler.class}),
        @Signature(
                type = StatementHandler.class,
                method = "update",
                args = {Statement.class}
        ),
        @Signature(
        type = StatementHandler.class,
        method = "batch",
        args = {Statement.class})
})
@Order(2)
public class MybatisSqlLogInterceptor implements Interceptor {

    /**
     * 定义一个包含需要添加单引号括起来的参数类型集合。
     */
    private static final Set<String> NEED_BRACKETS =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList("String", "Date", "Time", "LocalDate", "LocalTime",
                    "LocalDateTime", "BigDecimal", "Timestamp")));

    /**
     * MyBatis的配置对象。
     */
    private Configuration configuration = null;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        long startTime = System.currentTimeMillis();
        // 初始化行数为 1
        int lines = 1;
        // 默认状态为 "失败"
        String status = "failed";
        try {
            // 执行原始方法，并获取返回结果
            Object proceed = invocation.proceed();
            // 如果返回结果为集合，则统计行数
            if (proceed instanceof Collection<?>) {
                lines = ((List<?>) proceed).size();
            }
            // 执行成功，将状态设置为 "成功"
            status = "success";
            return proceed;
        } finally {
            // 计算 SQL 执行耗时
            long sqlCost = System.currentTimeMillis() - startTime;
            // 获取 SQL 语句
            String sql = this.getSql(target);
            // 打印日志
            log.info("\u001B[38;5;220mSQL 执行结果：{}. SQL 信息：{}\u001B[0m", status, sql);
            log.info("\u001B[38;5;220m耗时：{} mm. Total：{}\u001B[0m", sqlCost, lines);
        }
    }

    /**
    * @Description 获取即将会执行的SQL语句 并把参数插入到SQL语句中
    * @param target
    * @return {@link String }
    * @Author yaoHui
    * @Date 2024/7/24
    */
    private String getSql(Object target){

        try{
            // 获取 StatementHandler 对象
            StatementHandler statementHandler = (StatementHandler) target;
            // 获取 BoundSql 对象
            // BoundSql对象中带有即将执行的SQL语句和SQL参数
            BoundSql boundSql = statementHandler.getBoundSql();

            if(ObjectUtils.isNull(configuration)){
                final ParameterHandler parameterHandler = statementHandler.getParameterHandler();
                this.configuration = (Configuration) FieldUtils.readField(parameterHandler, "configuration", true);
            }

            return formatSql(boundSql,configuration);
        }catch (Exception e){
            log.warn("获取 SQL 语句失败：{}", target, e);
            return "无法解析的 SQL 语句";
        }
    }

    private String formatSql(BoundSql boundSql,Configuration configuration){
        String sql = boundSql.getSql();
        // 获取参数映射列表
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        // 获取参数对象
        Object parameterObject = boundSql.getParameterObject();
        // 判断是否为空
        if (StringUtils.isEmpty(sql) || Objects.isNull(configuration)) {
            return "";
        }
        // 获取 TypeHandlerRegistry 对象
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();

        // 移除 SQL 字符串中的空格、换行符等
        sql = sql.replaceAll("[\n\r ]+", " ");

        // 过滤掉输出参数的参数映射
        if (parameterMappings == null) {
            return sql;
        }

        parameterMappings = parameterMappings.stream()
                .filter(it -> it.getMode() != ParameterMode.OUT)
                .collect(Collectors.toList());

        // 使用 StringBuilder 保存格式化后的 SQL
        final StringBuilder result = new StringBuilder(sql);

        // 解析问号并替换参数
        for (int i = result.length(); i > 0; i--) {
            if (result.charAt(i - 1) != '?') {
                continue;
            }
            ParameterMapping parameterMapping = parameterMappings.get(parameterMappings.size() - 1);
            Object value;
            String propertyName = parameterMapping.getProperty();
            // 判断绑定的附加参数中是否有对应的属性名
            if (boundSql.hasAdditionalParameter(propertyName)) {
                value = boundSql.getAdditionalParameter(propertyName);
            } else if (parameterObject == null) {
                value = null;
            } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                value = parameterObject;
            } else {
                // 使用 MetaObject 获取属性值
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                value = metaObject.getValue(propertyName);
            }
            if (value != null) {
                // 判断参数类型，如果是需要添加括号的类型，则添加单引号
                String type = value.getClass().getSimpleName();
                if (NEED_BRACKETS.contains(type)) {
                    result.replace(i - 1, i, "'" + value + "'");
                } else {
                    result.replace(i - 1, i, value.toString());
                }
            } else {
                // 参数值为空时，替换为 "null"
                result.replace(i - 1, i, "null");
            }
            // 移除已处理的参数映射
            parameterMappings.remove(parameterMappings.size() - 1);
        }
        return result.toString();
    }


}