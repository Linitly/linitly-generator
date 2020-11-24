package org.mybatis.generator.linitly.dto;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.linitly.*;
import org.mybatis.generator.linitly.controller.ControllerConstant;

import java.sql.Types;
import java.util.*;

import static org.mybatis.generator.internal.util.JavaBeansUtil.getBasicJavaBeansField;
import static org.mybatis.generator.internal.util.JavaBeansUtil.getJavaBeansField;
import static org.mybatis.generator.internal.util.JavaBeansUtil.getJavaControllerField;

public class JavaModelDtoGenerator extends AbstractJavaGenerator {

    public JavaModelDtoGenerator(String project) {
        super(project);
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        CommentGenerator commentGenerator = context.getCommentGenerator();

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                introspectedTable.getJavaModelDtoType());

        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);
        topLevelClass.setSuperClass(new FullyQualifiedJavaType(ModelDtoSuperClassConstant.FULL_CLASS_NAME));

        commentGenerator.addLinitlyClassComment(topLevelClass);

        setImportedTypes(topLevelClass);
        setAnnotations(topLevelClass, table);
        setFields(topLevelClass, introspectedTable);

        List<CompilationUnit> answer = new ArrayList<>();
        answer.add(topLevelClass);
        return answer;
    }

    private void setFields(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> introspectedColumns = introspectedTable.getAllColumns();
        String constantName = CommonUtil.getObjectNameByType(introspectedTable.getJavaConstantType());
        for (IntrospectedColumn column : introspectedColumns) {
            if (Arrays.asList(ModelDtoSuperClassConstant.FIELD_NAME).contains(column.getActualColumnName())) {
                continue;
            }
            Field field = getBasicJavaBeansField(column);
            field.addAnnotation(getModelPropertyAnno(column));
            if (column.getJdbcType() == Types.VARCHAR || column.getJdbcType() == Types.LONGVARCHAR) {
                // 字符类型
                if (!column.isNullable()) {
                    field.addAnnotation(getNotBlankAnno(column, constantName));
                }
                field.addAnnotation(getSizeAnno(column, constantName));
            }
            if (column.getJdbcType() == Types.INTEGER || column.getJdbcType() == Types.BIGINT || column.getJdbcType() == Types.DECIMAL) {
                // 数字类型
                if (!column.isNullable()) {
                    field.addAnnotation(getNotNullAnno(column, constantName));
                }
                field.addAnnotation(getRangeAnno(column, constantName));
            }
            topLevelClass.addField(field);
        }
    }

    private String getRangeAnno(IntrospectedColumn column, String constantName) {
        StringBuilder sb = new StringBuilder();
        sb.append(ValidConstant.RANGE);
        sb.append("(message = ");
        sb.append(constantName);
        sb.append(".");
        sb.append(CommonUtil.getRangeErrorConstant(column));
        sb.append(", groups = ");
        sb.append(CommonUtil.appendGroups());
        sb.append(")");
        return sb.toString();
    }

    private String getNotNullAnno(IntrospectedColumn column, String constantName) {
        StringBuilder sb = new StringBuilder();
        sb.append(ValidConstant.NOT_NULL);
        sb.append("(message = ");
        sb.append(constantName);
        sb.append(".");
        sb.append(CommonUtil.getNotEmptyConstant(column));
        sb.append(", groups = ");
        sb.append(CommonUtil.appendGroups());
        sb.append(")");
        return sb.toString();
    }

    private String getModelPropertyAnno(IntrospectedColumn introspectedColumn) {
        String remarks = introspectedColumn.getRemarks();
        StringBuilder anno = new StringBuilder();
        anno.append(SwaggerConstant.API_MODEL_PROPERTY);
        anno.append("(value = \"");
        anno.append(remarks);
        anno.append("\"");
        if (!introspectedColumn.isNullable()) {
            anno.append(", required = true");
        }
        anno.append(")");
        return anno.toString();
    }

    private String getNotBlankAnno(IntrospectedColumn column, String constantName) {
        StringBuilder sb = new StringBuilder();
        sb.append(ValidConstant.NOT_BLANK);
        sb.append("(message = ");
        sb.append(constantName);
        sb.append(".");
        sb.append(CommonUtil.getNotEmptyConstant(column));
        sb.append(", groups = ");
        sb.append(CommonUtil.appendGroups());
        sb.append(")");
        return sb.toString();
    }

    private String getSizeAnno(IntrospectedColumn column, String constantName) {
        StringBuilder sb = new StringBuilder();
        sb.append(ValidConstant.SIZE);
        sb.append("(max = ");
        sb.append(constantName);
        sb.append(".");
        sb.append(CommonUtil.getMaxSizeConstant(column));
        sb.append(", message = ");
        sb.append(constantName);
        sb.append(".");
        sb.append(CommonUtil.getSizeErrorConstant(column));
        sb.append(", groups = ");
        sb.append(CommonUtil.appendGroups());
        sb.append(")");
        return sb.toString();
    }

    private void setAnnotations(TopLevelClass topLevelClass, FullyQualifiedTable table) {
        List<String> annos = new ArrayList<>();
        topLevelClass.addAllAnnotation(Arrays.asList(LombokConstant.ANNOTATIONS));
        topLevelClass.addAnnotation(SwaggerConstant.API_MODEL + "(value = \"" + table.getRemark() + "DTO\")");
        topLevelClass.addAllAnnotation(annos);
    }

    private void setImportedTypes(TopLevelClass topLevelClass) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<>();
        importedTypes.add(new FullyQualifiedJavaType(ModelDtoSuperClassConstant.FULL_CLASS_NAME));
        importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getJavaConstantType()));

        Arrays.asList(LombokConstant.IMPORTS).forEach(e -> importedTypes.add(new FullyQualifiedJavaType(e)));
        importedTypes.add(new FullyQualifiedJavaType(SwaggerConstant.API_MODEL_IMPORT));
        importedTypes.add(new FullyQualifiedJavaType(SwaggerConstant.API_MODEL_PROPERTY_IMPORT));

        importedTypes.add(new FullyQualifiedJavaType(ValidConstant.JAVAX_VALID_IMPORT));
        importedTypes.add((new FullyQualifiedJavaType(ValidConstant.RANGE_IMPORT)));

        importedTypes.add(new FullyQualifiedJavaType(ControllerConstant.INSERT_VALID_GROUP_IMPORT));
        importedTypes.add(new FullyQualifiedJavaType(ControllerConstant.UPDATE_VALID_GROUP_IMPORT));

        topLevelClass.addImportedTypes(importedTypes);
    }
}
