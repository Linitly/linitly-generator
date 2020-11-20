package org.mybatis.generator.linitly.vo;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.linitly.*;
import org.mybatis.generator.linitly.controller.ControllerConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.mybatis.generator.internal.util.JavaBeansUtil.getJavaControllerField;

public class JavaModelVoGenerator extends AbstractJavaGenerator {

    public JavaModelVoGenerator(String project) {
        super(project);
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        CommentGenerator commentGenerator = context.getCommentGenerator();

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                introspectedTable.getJavaControllerType());

        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);
        commentGenerator.addLinitlyClassComment(topLevelClass);

        setImportedTypes(topLevelClass);
        setAnnotations(topLevelClass, table);
        setFields(topLevelClass, introspectedTable);
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

    private void setFindAllMethod(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, FullyQualifiedTable table) {
        IntrospectedColumn primaryKeyColumn = introspectedTable.getPrimaryKeyColumns().get(0);

        Method method = new Method(introspectedTable.getSelectAllStatementId());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addAnnotation(ControllerConstant.POST_MAPPING + "(\"/" + introspectedTable.getSelectAllStatementId() + "\")");
        method.addAnnotation(SwaggerConstant.API_OPERATION + "(value = \"" + CSEnum.FIND_ALL.getCnPrefix() + table.getRemark() + CSEnum.FIND_ALL.getCnSuffix() + "\")");

        FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
        FullyQualifiedJavaType listType = new FullyQualifiedJavaType(table.getDomainObjectName());
        returnType.addTypeArgument(listType);
        method.setReturnType(returnType);

        String pageNumber = "pageNumber";
        String pageSize = "pageSize";

        Parameter pageNumberParameter = new Parameter(FullyQualifiedJavaType.getIntInstance(), pageNumber);
        pageNumberParameter.addAnnotation(ControllerConstant.REQUEST_PARAM + "(defaultValue = " + ControllerConstant.ADMIN_COMMON_CONSTANT_TYPE + "." + ControllerConstant.PAGE_NUMBER + ")");
        method.addParameter(0, pageNumberParameter);

        Parameter pageSizeParameter = new Parameter(FullyQualifiedJavaType.getIntInstance(), pageSize);
        pageSizeParameter.addAnnotation(ControllerConstant.REQUEST_PARAM + "(defaultValue = " + ControllerConstant.ADMIN_COMMON_CONSTANT_TYPE + "." + ControllerConstant.PAGE_SIZE + ")");
        method.addParameter(1, pageSizeParameter);

        Parameter parameter = new Parameter(new FullyQualifiedJavaType(table.getDomainObjectName()), CommonUtil.lowerFirst(table.getDomainObjectName()));
        parameter.addAnnotation(ControllerConstant.REQUEST_BODY + "(required = false)");
        method.addParameter(2, parameter);

        StringBuilder sb = new StringBuilder();

        sb.append(ControllerConstant.PAGE_HELPER_TYPE);
        sb.append(".startPage(");
        sb.append(pageNumber);
        sb.append(", ");
        sb.append(pageSize);
        sb.append(", \"");
        sb.append(primaryKeyColumn.getJavaProperty());
        sb.append(" desc\");");
        method.addBodyLine(sb.toString());

        sb.setLength(0);
        sb.append("return ");
        sb.append(CommonUtil.lowerFirst(new FullyQualifiedJavaType(introspectedTable.getJavaServiceType()).getShortName()));
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
        method.addAnnotation(ControllerConstant.PAGINATION);
        method.addAnnotation(ControllerConstant.POST_MAPPING + "(\"/" + introspectedTable.getSelectByPrimaryKeyStatementId() + "/{" + primaryKeyColumn.getJavaProperty() + "}\")");
        method.addAnnotation(SwaggerConstant.API_OPERATION + "(value = \"" + CSEnum.FIND_BY_ID.getCnPrefix() + table.getRemark() + "\")");

        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(table.getDomainObjectName());
        method.setReturnType(returnType);

        Parameter parameter = new Parameter(primaryKeyColumn.getFullyQualifiedJavaType(), primaryKeyColumn.getJavaProperty());
        parameter.addAnnotation(ControllerConstant.PATH_VARIABLE);
        method.addParameter(parameter);

        StringBuilder sb = new StringBuilder();
        sb.append("return ");
        sb.append(CommonUtil.lowerFirst(new FullyQualifiedJavaType(introspectedTable.getJavaServiceType()).getShortName()));
        sb.append(".");
        sb.append(introspectedTable.getSelectByPrimaryKeyStatementId());
        sb.append("(");
        sb.append(primaryKeyColumn.getJavaProperty());
        sb.append(");");
        method.addBodyLine(sb.toString());

        topLevelClass.addMethod(method);
    }

    private void setDeleteMethod(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, FullyQualifiedTable table) {
        IntrospectedColumn primaryKeyColumn = introspectedTable.getPrimaryKeyColumns().get(0);

        Method method = new Method(introspectedTable.getDeleteByPrimaryKeyStatementId());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addAnnotation(ControllerConstant.POST_MAPPING + "(\"/" + introspectedTable.getDeleteByPrimaryKeyStatementId() + "/{" + primaryKeyColumn.getJavaProperty() + "}\")");
        method.addAnnotation(SwaggerConstant.API_OPERATION + "(value = \"" + CSEnum.DELETE.getCnPrefix() + table.getRemark() + "\")");

        Parameter parameter = new Parameter(primaryKeyColumn.getFullyQualifiedJavaType(), primaryKeyColumn.getJavaProperty());
        parameter.addAnnotation(ControllerConstant.PATH_VARIABLE);
        method.addParameter(parameter);

        StringBuilder sb = new StringBuilder();
        sb.append(CommonUtil.lowerFirst(new FullyQualifiedJavaType(introspectedTable.getJavaServiceType()).getShortName()));
        sb.append(".");
        sb.append(introspectedTable.getDeleteByPrimaryKeyStatementId());
        sb.append("(");
        sb.append(primaryKeyColumn.getJavaProperty());
        sb.append(");");
        method.addBodyLine(sb.toString());

        topLevelClass.addMethod(method);
    }

    private void setUpdateMethod(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, FullyQualifiedTable table) {
        Method method = new Method(introspectedTable.getUpdateByPrimaryKeyStatementId());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addAnnotation(ControllerConstant.POST_MAPPING + "(\"/" + introspectedTable.getUpdateByPrimaryKeyStatementId() + "\")");
        method.addAnnotation(SwaggerConstant.API_OPERATION + "(value = \"" + CSEnum.UPDATE.getCnPrefix() + table.getRemark() + "\")");

        Parameter parameter = new Parameter(new FullyQualifiedJavaType(table.getDomainObjectName()), CommonUtil.lowerFirst(table.getDomainObjectName()));
        parameter.addAnnotation(ControllerConstant.REQUEST_BODY);
        parameter.addAnnotation(ValidConstant.VALIDATED + "({" + ControllerConstant.INSERT_VALID_GROUP_CLASS + ", " + ControllerConstant.UPDATE_VALID_GROUP_CLASS + "})");
        method.addParameter(0, parameter);

        Parameter bindingResultParameter = new Parameter(new FullyQualifiedJavaType(ControllerConstant.BINDING_RESULT_TYPE),
                CommonUtil.lowerFirst(ControllerConstant.BINDING_RESULT_TYPE));
        method.addParameter(1, bindingResultParameter);

        StringBuilder sb = new StringBuilder();
        sb.append(CommonUtil.lowerFirst(new FullyQualifiedJavaType(introspectedTable.getJavaServiceType()).getShortName()));
        sb.append(".");
        sb.append(introspectedTable.getUpdateByPrimaryKeyStatementId());
        sb.append("(");
        sb.append(CommonUtil.lowerFirst(table.getDomainObjectName()));
        sb.append(");");
        method.addBodyLine(sb.toString());

        topLevelClass.addMethod(method);
    }

    private void setInsertMethod(TopLevelClass topLevelClass, IntrospectedTable introspectedTable, FullyQualifiedTable table) {
        Method method = new Method(introspectedTable.getInsertStatementId());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addAnnotation(ControllerConstant.POST_MAPPING + "(\"/" + introspectedTable.getInsertStatementId() + "\")");
        method.addAnnotation(SwaggerConstant.API_OPERATION + "(value = \"" + CSEnum.INSERT.getCnPrefix() + table.getRemark() + "\")");

        Parameter parameter = new Parameter(new FullyQualifiedJavaType(table.getDomainObjectName()), CommonUtil.lowerFirst(table.getDomainObjectName()));
        parameter.addAnnotation(ControllerConstant.REQUEST_BODY);
        parameter.addAnnotation(ValidConstant.VALIDATED + "({" + ControllerConstant.INSERT_VALID_GROUP_CLASS + "})");
        method.addParameter(0, parameter);

        Parameter bindingResultParameter = new Parameter(new FullyQualifiedJavaType(ControllerConstant.BINDING_RESULT_TYPE),
                CommonUtil.lowerFirst(ControllerConstant.BINDING_RESULT_TYPE));
        method.addParameter(1, bindingResultParameter);

        StringBuilder sb = new StringBuilder();
        sb.append(CommonUtil.lowerFirst(new FullyQualifiedJavaType(introspectedTable.getJavaServiceType()).getShortName()));
        sb.append(".");
        sb.append(introspectedTable.getInsertStatementId());
        sb.append("(");
        sb.append(CommonUtil.lowerFirst(table.getDomainObjectName()));
        sb.append(");");
        method.addBodyLine(sb.toString());

        topLevelClass.addMethod(method);
    }

    private void setFields(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        Field field = getJavaControllerField(introspectedTable);
        topLevelClass.addField(field);
    }

    private void setAnnotations(TopLevelClass topLevelClass, FullyQualifiedTable table) {
        List<String> annos = new ArrayList<>();
        annos.add(ControllerConstant.RESULT);
        annos.add(ControllerConstant.REST_CONTROLLER);
        annos.add(ControllerConstant.REQUEST_MAPPING + "(\"/" + CommonUtil.lowerFirst(table.getDomainObjectName()) + "\")");
        annos.add(SwaggerConstant.API + "(tags = \"" + table.getRemark() + "管理\")");
        topLevelClass.addAllAnnotation(annos);
    }

    private void setImportedTypes(TopLevelClass topLevelClass) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<>();
        importedTypes.add(new FullyQualifiedJavaType(CommonConstant.AUTOWIRED_IMPORT));
        importedTypes.add(new FullyQualifiedJavaType(SwaggerConstant.API_IMPORT));
        importedTypes.add(new FullyQualifiedJavaType(SwaggerConstant.API_OPERATION_IMPORT));
        importedTypes.add(new FullyQualifiedJavaType(ControllerConstant.WEB_ANNO_IMPORT));
        importedTypes.add(new FullyQualifiedJavaType(ControllerConstant.RESULT_IMPORT));
        importedTypes.add(new FullyQualifiedJavaType(ControllerConstant.PAGINATION_IMPORT));
        importedTypes.add(new FullyQualifiedJavaType(ControllerConstant.BINDING_RESULT_IMPORT));
        importedTypes.add(new FullyQualifiedJavaType(ControllerConstant.PAGE_HELPER_IMPORT));
        importedTypes.add(new FullyQualifiedJavaType(ControllerConstant.INSERT_VALID_GROUP_IMPORT));
        importedTypes.add(new FullyQualifiedJavaType(ControllerConstant.UPDATE_VALID_GROUP_IMPORT));
        importedTypes.add(new FullyQualifiedJavaType(ControllerConstant.ADMIN_COMMON_CONSTANT_IMPORT));
        importedTypes.add(new FullyQualifiedJavaType(ValidConstant.VALIDATED_IMPORT));
        importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getJavaServiceType()));
        importedTypes.add(new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()));
        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());
        topLevelClass.addImportedTypes(importedTypes);
    }
}
