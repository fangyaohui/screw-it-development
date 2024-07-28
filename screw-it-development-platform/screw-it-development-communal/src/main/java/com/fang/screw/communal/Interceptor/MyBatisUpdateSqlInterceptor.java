package com.fang.screw.communal.Interceptor;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @FileName MyBatisSqlInterceptor
 * @Description 为MyBatis执行SQL自动插入某些公共条件 或 自动插入参数
 * @Author yaoHui
 * @date 2024-07-28
 **/
@Slf4j
@Component
@Intercepts({
        @Signature(type = Executor.class,
                method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(
                type = Executor.class,
                method = "update",
                args = {MappedStatement.class, Object.class}
        ),
//        @Signature(
//                type = StatementHandler.class,
//                method = "batch",
//                args = {Statement.class})
})
public class MyBatisUpdateSqlInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        // 获取 SQL 命令
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();

        //只判断新增和修改
        if (SqlCommandType.INSERT.equals(sqlCommandType) || SqlCommandType.UPDATE.equals(sqlCommandType)) {
            // 获取参数
//            Object parameter = invocation.getArgs()[1];
            // 获取参数
            Object parameter = invocation.getArgs()[1];
            setParameter(parameter, sqlCommandType);
//            //批量操作时
//            if (parameter instanceof MapperMethod.ParamMap) {
//                MapperMethod.ParamMap map = (MapperMethod.ParamMap) parameter;
//                Object obj = map.get("list");
//                List<?> list = (List<?>) obj;
//                if (list != null) {
//                    for (Object o : list) {
//                        setParameter(o, sqlType);
//                    }
//                }
//            } else {
//                setParameter(parameter, sqlType);
//            }
        }
        return invocation.proceed();
    }

    public void setParameter(Object parameter, SqlCommandType sqlCommandType) throws Throwable {
        Class<?> aClass = parameter.getClass();
//        Field[] declaredFields;
        List<Field[]> declaredFields = new ArrayList<>();
        // 获取当前类和父类的属性
        while(ObjectUtils.isNotEmpty(aClass) && aClass != Object.class){
            declaredFields.add(aClass.getDeclaredFields());
            aClass = aClass.getSuperclass();
        }

        for (Field[] fields : declaredFields){
            for (Field field : fields){
                TableField annotation = field.getAnnotation(TableField.class);
                if (ObjectUtils.isEmpty(annotation)){
                    continue;
                }
                if (SqlCommandType.INSERT.equals(sqlCommandType)) { // insert 语句插入 createBy
                    String value = annotation.value();
                    if(StringUtils.equals(value,"create_time")){
                        field.setAccessible(true);
                        field.set(parameter, LocalDateTime.now());
                    }
                    if(StringUtils.equals(value,"update_time")){
                        field.setAccessible(true);
                        field.set(parameter, LocalDateTime.now());
                    }
                }

                if (SqlCommandType.UPDATE.equals(sqlCommandType)) { // update 语句插入 updateTime
                    String value = annotation.value();
                    if(StringUtils.equals(value,"update_time")){
                        field.setAccessible(true);
                        field.set(parameter, LocalDateTime.now());
                    }
                }
            }
        }

    }

}