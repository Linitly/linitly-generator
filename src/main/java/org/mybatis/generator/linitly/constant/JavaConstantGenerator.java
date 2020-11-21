package org.mybatis.generator.linitly.constant;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.linitly.*;
import org.mybatis.generator.linitly.controller.ControllerConstant;
import org.mybatis.generator.linitly.dto.ModelDtoSuperClassConstant;

import java.sql.Types;
import java.util.*;

import static org.mybatis.generator.internal.util.JavaBeansUtil.getJavaControllerField;

public class JavaConstantGenerator extends AbstractJavaGenerator {

    public JavaConstantGenerator(String project) {
        super(project);
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        CommentGenerator commentGenerator = context.getCommentGenerator();

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                introspectedTable.getJavaConstantType());

        Interface interfaze = new Interface(type);
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(interfaze);
        commentGenerator.addLinitlyClassComment(interfaze);

        setFields(interfaze, introspectedTable);

        List<CompilationUnit> answer = new ArrayList<>();
        answer.add(interfaze);
        return answer;
    }

    private void setFields(Interface interfaze, IntrospectedTable introspectedTable) {
        List<IntrospectedColumn> introspectedColumns = introspectedTable.getAllColumns();
        for (IntrospectedColumn column : introspectedColumns) {
            if (Arrays.asList(ModelDtoSuperClassConstant.FIELD_NAME).contains(column.getActualColumnName())) {
                continue;
            }
            if (column.getJdbcType() == Types.VARCHAR || column.getJdbcType() == Types.LONGVARCHAR) {
                // 字符类型
                if (!column.isNullable()) {
                    interfaze.addField(getNotEmptyTipField(column));
                }
                interfaze.addField(getSizeField(column));
                interfaze.addField(getSizeTipField(column));
            }
            if (column.getJdbcType() == Types.INTEGER || column.getJdbcType() == Types.BIGINT || column.getJdbcType() == Types.DECIMAL) {
                // 数字类型
                if (!column.isNullable()) {
                    interfaze.addField(getNotEmptyTipField(column));
                }
                interfaze.addField(getMaxRangeTipField(column));
            }
        }
    }

    private Field getMaxRangeTipField(IntrospectedColumn column) {
        return new Field(CommonUtil.getRangeErrorConstant(column), FullyQualifiedJavaType.getStringInstance(), "\"" + column.getRemarks() + ValidConstant.RANGE_ERROR_TIP_SUFFIX + "\"");
    }

    private Field getSizeTipField(IntrospectedColumn column) {
        return new Field(CommonUtil.getSizeErrorConstant(column), FullyQualifiedJavaType.getStringInstance(), "\"" + column.getRemarks() + ValidConstant.SIZE_ERROR_TIP_SUFFIX + "\"");
    }

    private Field getSizeField(IntrospectedColumn column) {
        return new Field(CommonUtil.getMaxSizeConstant(column), FullyQualifiedJavaType.getIntInstance(), Integer.toString(column.getLength()));
    }

    private Field getNotEmptyTipField(IntrospectedColumn column) {

        return new Field(CommonUtil.getNotEmptyConstant(column), FullyQualifiedJavaType.getStringInstance(), "\"" + column.getRemarks() + ValidConstant.EMPTY_ERROR_TIP_SUFFIX + "\"");
    }
}
