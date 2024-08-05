package com.fang.screw.domain.enums;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @FileName DemoEnum
 * @Description
 * @Author yaoHui
 * @date 2024-08-05
 **/
@AllArgsConstructor
@Getter
public enum DemoEnum {

//    WEB_API(1,"Web API", WebAPIConnectDTO.class, DatasourceCategoryEnum.API),
//    MYSQL(2,"MySQL", MySQLConnectDTO.class, DatasourceCategoryEnum.DB),
//    POSTGRESQL(3,"PostgreSQL", PostgreSQLConnectDTO.class, DatasourceCategoryEnum.DB),
//    ELASTICSEARCH(4,"ElasticSearch", ElasticSearchConnectDTO.class, DatasourceCategoryEnum.DB),
//    EXCEL(5,"Excel", ExcelImportBaseDTO.class, DatasourceCategoryEnum.FILE_IMPORT),
//    CSV(6,"CSV", null, DatasourceCategoryEnum.FILE_IMPORT),
//    ENUM(7,"Enum", null, DatasourceCategoryEnum.WEB_SPIDER),
//    METHOD_INVOKE(8,"Method Invoke", null, DatasourceCategoryEnum.OTHER),
//    ROCKET_MQ(9,"RocketMQ", null, DatasourceCategoryEnum.MESSAGE_QUEUE),
//    MQTT(10,"MQTT", null, DatasourceCategoryEnum.MESSAGE_QUEUE),
//    WEB_SOCKET(11,"WebSocket", WebSocketConnectDTO.class, DatasourceCategoryEnum.WEB_SOCKET),
//    WEB_PAGE_SPIDER(12,"Web Page Spider", null, DatasourceCategoryEnum.WEB_SPIDER),
//    SDK(13,"SDK", null, DatasourceCategoryEnum.SDK)
//    ;
//
//    private final Integer code;
//    private final String desc;
//    private final Class<?> connParamClass;
//    private final DatasourceCategoryEnum datasourceCategoryEnum;
//
//    /**
//     * @Description 获取当前类所有的属性包括其父类的属性
//     * @param clazz
//     * @return {@link List <  Field > }
//     * @Author yaoHui
//     * @Date 2024/6/25
//     */
//    public static List<Map<String,String>> getAllFields(Class<?> clazz){
//        if(ObjectUtils.isEmpty(clazz)){
//            return null;
//        }
//        List<Map<String,String>> mapList = new ArrayList<>();
//        while (clazz != null) {
//            // 获取当前类声明的所有字段
//            Field[] declaredFields = clazz.getDeclaredFields();
//            for (Field field : declaredFields){
//
//                String fieldName = field.getName();
//                Class<?> fieldTypeName = field.getType();
//                Map<String,String> mapTemp = new HashMap<>();
//                mapTemp.put("fieldName",fieldName);
//
//                if(field.isAnnotationPresent(MetaPropertyInfo.class)){
//                    MetaPropertyInfo metaPropertyInfo = field.getAnnotation(MetaPropertyInfo.class);
//                    PropertyShownTypeEnum shownTypeEnum = metaPropertyInfo.shownType();
//                    String requestUrl = metaPropertyInfo.requestUrl();
//                    String dataType = metaPropertyInfo.dataType();
//                    String alias = metaPropertyInfo.alias();
//                    String defaultValue = metaPropertyInfo.defaultValue();
//                    switch (shownTypeEnum.getCode()){
//                        case 1:
//                            mapTemp.put("fieldType","1");
//                            break;
//                        case 2:
//                            mapTemp.put("fieldType","2");
//                            break;
//                        case 3:
//                            mapTemp.put("fieldType","3");
//                            break;
//                        case 4:
//                            mapTemp.put("fieldType","4");
//                            mapTemp.put("requestUrl",requestUrl);
//                            break;
//                        case 5:
//                            mapTemp.put("fieldType","5");
//                            break;
//                        case 6:
//                            mapTemp.put("fieldType","6");
//                            break;
//                    }
//                    mapList.add(mapTemp);
//                    continue;
//                }
//
//
//                if(fieldTypeName.equals(Integer.class)){
//                    mapTemp.put("fieldType","1");
//                }else if (fieldTypeName.equals(String.class)){
//                    mapTemp.put("fieldType","2");
//                }else{
//                    mapTemp.put("fieldType","3");
//                }
//                mapList.add(mapTemp);
//            }
//            // 获取父类
//            clazz = clazz.getSuperclass();
//        }
//        return mapList;
//    }
//
//    /**
//     * @Description 通过DsTypeName获取该类型所有需要输入的属性参数
//     * @param dsTypeName
//     * @return {@link List< Map< String, String>> }
//     * @Author yaoHui
//     * @Date 2024/6/25
//     */
//    public static List<Map<String,String>> getAllFieldByDsTypeName(String dsTypeName){
//        for (DatasourceTypeEnum constant : DatasourceTypeEnum.values()) {
//            if (constant.getDesc().equals(dsTypeName)){
//                Class<?> fieldClass = constant.getConnParamClass();
//                return getAllFields(fieldClass);
//            }
//        }
//        // 如果没有找到对应的Name的类，可以抛出一个异常或者返回一个默认值
//        throw new IllegalArgumentException("No DatasourceTypeEnum found for dsTypeName: " + dsTypeName);
//    }
//
//    /**
//     * @Description 根据传入的DsCategoryCode获取对应的DsType
//     * @param dsCategoryCode
//     * @return {@link List< Map< String, String>> }
//     * @Author yaoHui
//     * @Date 2024/6/25
//     */
//    public static List<Map<String, String>> getAllAsMapByDsCategoryCode(int dsCategoryCode) {
//        List<Map<String,String>> DatasourceTypeList = new ArrayList<>();
//        for (DatasourceTypeEnum constant : DatasourceTypeEnum.values()) {
//            if (constant.datasourceCategoryEnum.getCode() == dsCategoryCode){
//                Map<String, String> map = new HashMap<>();
//                map.put("dsType", constant.getCode().toString());
//                map.put("dsTypeName", constant.getDesc());
//                DatasourceTypeList.add(map);
//            }
//        }
//        return DatasourceTypeList;
//    }
//
//    public static List<Map<String, String>> getAllAsMap() {
//        List<Map<String,String>> DatasourceTypeList = new ArrayList<>();
//        for (DatasourceTypeEnum constant : DatasourceTypeEnum.values()) {
//            Map<String, String> map = new HashMap<>();
//            map.put("dsType", constant.getCode().toString());
//            map.put("dsTypeName", constant.getDesc());
//            DatasourceTypeList.add(map);
//        }
//        return DatasourceTypeList;
//    }
//
//    public static String getDescByCode(Integer code) {
//        for (DatasourceTypeEnum category : DatasourceTypeEnum.values()) {
//            if (category.getCode().equals(code)) {
//                return category.getDesc();
//            }
//        }
//        // 如果没有找到对应的code，可以抛出一个异常或者返回一个默认值
//        throw new IllegalArgumentException("No DatasourceTypeEnum found for code: " + code);
//    }
//
//    public static Integer getCodeByDesc(String desc) {
//        for (DatasourceTypeEnum datasourceTypeEnum : DatasourceTypeEnum.values()) {
//            if (datasourceTypeEnum.getDesc().equals(desc)) {
//                return datasourceTypeEnum.getCode();
//            }
//        }
//        // 如果没有找到对应的desc，可以抛出一个异常或者返回一个默认值
//        throw new IllegalArgumentException("No DatasourceTypeEnum found for desc: " + desc);
//    }
//
//    /**
//     * 根据编码查找数据源类型枚举对象
//     * @param code
//     * @return
//     */
//    public static DatasourceTypeEnum getDsTypeEnumByCode(Integer code) {
//        for (DatasourceTypeEnum datasourceTypeEnum : DatasourceTypeEnum.values()) {
//            if (datasourceTypeEnum.getCode().equals(code)) {
//                return datasourceTypeEnum;
//            }
//        }
//
//        return null;
//    }

}