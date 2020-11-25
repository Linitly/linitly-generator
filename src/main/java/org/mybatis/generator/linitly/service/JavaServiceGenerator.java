package org.mybatis.generator.linitly.service;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.linitly.CommonConstant;
import org.mybatis.generator.linitly.CommonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.mybatis.generator.internal.util.JavaBeansUtil.getJavaServiceField;

public class JavaServiceGenerator extends AbstractJavaGenerator {

    public JavaServiceGenerator(String project) {
        super(project);
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        CommentGenerator commentGenerator = context.getCommentGenerator();

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                introspectedTable.getJavaServiceType());

        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);
        commentGenerator.addLinitlyClassComment(topLevelClass);

        setImportedTypes(topLevelClass);
        setAnnotations(topLevelClass);
        setFields(topLevelClass, context, introspectedTable);
        setMethods(topLevelClass, introspectedTable, table);

        List<CompilationUnit> answer = new ArrayList<>();
        answer.add(topLevelClass);
        return answer;
    }

    private void setMethods(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, FullyQualifiedTable table) {
        setInsertMethod(topLevelClass, introspectedTable, table);
        setUpdateMethod(topLevelClass, introspectedTable, table);
        setFindByIdMethod(topLevelClass, introspectedTable, table);
        setFindAllMethod(topLevelClass, introspectedTable, table);
        setDeleteMethod(topLevelClass, introspectedTable, table);
    }

    private void setDeleteMethod(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, FullyQualifiedTable table) {
        IntrospectedColumn primaryKeyColumn = introspectedTable.getPrimaryKeyColumns().get(0);

        Method method = new Method(introspectedTable.getDeleteByPrimaryKeyStatementId());
        method.setVisibility(JavaVisibility.PUBLIC);
        Parameter parameter = new Parameter(primaryKeyColumn.getFullyQualifiedJavaType(), primaryKeyColumn.getJavaProperty());
        method.addParameter(parameter);

        StringBuilder sb = new StringBuilder();
        sb.append(CommonUtil.lowerFirst(new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType()).getShortName()));
        sb.append(".");
        sb.append(introspectedTable.getDeleteByPrimaryKeyStatementId());
        sb.append("(");
        sb.append(primaryKeyColumn.getJavaProperty());
        sb.append(");");
        method.addBodyLine(sb.toString());

        topLevelClass.addMethod(method);
    }

    private void setFindAllMethod(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, FullyQualifiedTable table) {
        Method method = new Method(introspectedTable.getSelectAllStatementId());
        method.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
        FullyQualifiedJavaType listType = new FullyQualifiedJavaType(table.getDomainObjectName());
        returnType.addTypeArgument(listType);
        method.setReturnType(returnType);

        Parameter parameter = new Parameter(new FullyQualifiedJavaType(table.getDomainObjectName()), CommonUtil.lowerFirst(table.getDomainObjectName()));
        method.addParameter(parameter);

        StringBuilder sb = new StringBuilder();
        sb.append("return ");
        sb.append(CommonUtil.lowerFirst(new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType()).getShortName()));
        sb.append(".");
        sb.append(introspectedTable.getSelectAllStatementId());
        sb.append("(");
        sb.append(CommonUtil.lowerFirst(table.getDomainObjectName()));
        sb.append(");");
        method.addBodyLine(sb.toString());

        topLevelClass.addMethod(method);
    }

    private void setFindByIdMethod(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, FullyQualifiedTable table) {
        IntrospectedColumn primaryKeyColumn = introspectedTable.getPrimaryKeyColumns().get(0);

        Method method = new Method(introspectedTable.getSelectByPrimaryKeyStatementId());
        method.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(table.getDomainObjectName());
        method.setReturnType(returnType);
        Parameter parameter = new Parameter(primaryKeyColumn.getFullyQualifiedJavaType(), primaryKeyColumn.getJavaProperty());
        method.addParameter(parameter);

        StringBuilder sb = new StringBuilder();
        sb.append("return ");
        sb.append(CommonUtil.lowerFirst(new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType()).getShortName()));
        sb.append(".");
        sb.append(introspectedTable.getSelectByPrimaryKeyStatementId());
        sb.append("(");
        sb.append(primaryKeyColumn.getJavaProperty());
        sb.append(");");
        method.addBodyLine(sb.toString());

        topLevelClass.addMethod(method);
    }

    private void setUpdateMethod(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, FullyQualifiedTable table) {
        Method method = new Method(introspectedTable.getUpdateByPrimaryKeyStatementId());
        method.setVisibility(JavaVisibility.PUBLIC);
        Parameter parameter = CommonUtil.getModelDtoParameter(introspectedTable);
        method.addParameter(parameter);

        StringBuilder sb = new StringBuilder();
        addNewObjectLine(sb, table, method);
        addCopyPropertyLine(sb, method, CommonConstant.DTO_NAME, CommonUtil.lowerFirst(table.getDomainObjectName()));

        sb.append(CommonUtil.lowerFirst(new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType()).getShortName()));
        sb.append(".");
        sb.append(introspectedTable.getUpdateByPrimaryKeySelectiveStatementId());
        sb.append("(");
        sb.append(CommonUtil.lowerFirst(table.getDomainObjectName()));
        sb.append(");");
        method.addBodyLine(sb.toString());

        topLevelClass.addMethod(method);
    }

    private void setInsertMethod(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, FullyQualifiedTable table) {
        Method method = new Method(introspectedTable.getInsertStatementId());
        method.setVisibility(JavaVisibility.PUBLIC);

        Parameter parameter = CommonUtil.getModelDtoParameter(introspectedTable);
        method.addParameter(parameter);

        StringBuilder sb = new StringBuilder();
        addNewObjectLine(sb, table, method);
        addCopyPropertyLine(sb, method, CommonConstant.DTO_NAME, CommonUtil.lowerFirst(table.getDomainObjectName()));


        sb.append(CommonUtil.lowerFirst(new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType()).getShortName()));
        sb.append(".");
        sb.append(introspectedTable.getInsertSelectiveStatementId());
        sb.append("(");
        sb.append(CommonUtil.lowerFirst(table.getDomainObjectName()));
        sb.append(");");
        method.addBodyLine(sb.toString());

        topLevelClass.addMethod(method);
    }

    private void setImportedTypes(TopLevelClass topLevelClass) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<>();
        importedTypes.add(new FullyQualifiedJavaType(CommonConstant.AUTOWIRED_IMPORT));
        importedTypes.add(new FullyQualifiedJavaType(ServiceConstant.SERVICE_IMPORT));
        importedTypes.add(new FullyQualifiedJavaType(ServiceConstant.BEAN_UTILS_IMPORT));
        importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType()));
        importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getJavaModelDtoType()));
        importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());
        topLevelClass.addImportedTypes(importedTypes);
    }

    private void addNewObjectLine(StringBuilder sb, FullyQualifiedTable table, Method method) {
        sb.append(table.getDomainObjectName());
        sb.append(" ");
        sb.append(CommonUtil.lowerFirst(table.getDomainObjectName()));
        sb.append(" = new ");
        sb.append(table.getDomainObjectName());
        sb.append("();");
        method.addBodyLine(sb.toString());
        sb.setLength(0);
    }

    private void addCopyPropertyLine(StringBuilder sb, Method method, String dtoName, String modelName) {
        sb.append(ServiceConstant.BEAN_UTILS);
        sb.append(".copyProperties(");
        sb.append(dtoName);
        sb.append(", ");
        sb.append(modelName);
        sb.append(");");
        method.addBodyLine(sb.toString());
        sb.setLength(0);
    }

    private void setFields(TopLevelClass topLevelClass, Context context, IntrospectedTable introspectedTable) {
        Field field = getJavaServiceField(introspectedTable);
        topLevelClass.addField(field);
    }

    private void setAnnotations(TopLevelClass topLevelClass) {
        topLevelClass.addAnnotation(ServiceConstant.SERVICE);
    }
}
