package org.mybatis.generator.linitly;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.linitly.controller.ControllerConstant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommonUtil {

    public static String lowerFirst(String oldStr){
        char[]chars = oldStr.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    public static String getObjectNameByType(String type) {
        String objectName = null;
        int index = type.lastIndexOf('.');
        if (index == -1) {
            objectName = type;
        } else {
            objectName = type.substring(index + 1);
        }
        return objectName;
    }

    public static String getModelDtoType(IntrospectedTable introspectedTable) {
        return getObjectNameByType(introspectedTable.getJavaModelDtoType());
    }

    public static Parameter getModelDtoParameter(IntrospectedTable introspectedTable) {
        return new Parameter(new FullyQualifiedJavaType(getModelDtoType(introspectedTable)), CommonConstant.DTO_NAME);
    }

    public static String getNotEmptyConstant(IntrospectedColumn column) {
        return column.getActualColumnName().toUpperCase() + ValidConstant.EMPTY_ERROR_CONSTANT_SUFFIX;
    }

    public static String getMaxSizeConstant(IntrospectedColumn column) {
        return ValidConstant.MAX_CONSTANT_PREFIX + column.getActualColumnName().toUpperCase() + ValidConstant.SIZE_CONSTANT_SUFFIX;
    }

    public static String getSizeErrorConstant(IntrospectedColumn column) {
        return column.getActualColumnName().toUpperCase() + ValidConstant.SIZE_ERROR_CONSTANT_SUFFIX;
    }

    public static String getRangeErrorConstant(IntrospectedColumn column) {
        return column.getActualColumnName().toUpperCase() + ValidConstant.RANGE_ERROR_CONSTANT_SUFFIX;
    }

    public static String appendGroups() {
        StringBuilder sb = new StringBuilder();
        return sb.append("{").append(ControllerConstant.INSERT_VALID_GROUP_CLASS).append(", ").append(ControllerConstant.UPDATE_VALID_GROUP_CLASS).append("}").toString();
    }
}
