package com.fang.screw.communal.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @FileName TreeBuildUtils
 * @Description
 * @Author yaoHui
 * @date 2024-08-05
 **/
public class TreeBuildUtils {

    public static <T> List<Tree<Long>> buildTree(List<T> treeList) throws IllegalAccessException {
        List<TreeNode<Long>> nodeList = CollUtil.newArrayList();

        for (T tempNode : treeList) {
            Long id = (Long) getFieldValue(tempNode, "id");
            Long parentId = (Long) getFieldValue(tempNode, "parentId");
            String comment = (String) getFieldValue(tempNode,"comment");
            nodeList.add(new TreeNode<>(id, parentId,comment,null));
        }

        return TreeUtil.build(nodeList, -1L);  // Assuming 0L is the root parentId
    }

    /**
    * @Description 通过反射获取类的属性值
    * @param obj
    * @param fieldName
    * @return {@link Object }
    * @Author yaoHui
    * @Date 2024/8/5
    */
    private static <T> Object getFieldValue(T obj, String fieldName) throws IllegalAccessException {
        Field field;
        try {
            field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Field " + fieldName + " not found", e);
        }
    }

}