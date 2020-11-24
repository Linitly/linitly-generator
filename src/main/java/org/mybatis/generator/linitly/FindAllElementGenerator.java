package org.mybatis.generator.linitly;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;

public class FindAllElementGenerator extends AbstractXmlElementGenerator {

    private boolean isSimple;

    public FindAllElementGenerator(boolean isSimple) {
        super();
        this.isSimple = isSimple;
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");
        parentElement.addElement(answer);

        answer.addAttribute(new Attribute(
                "id", introspectedTable.getSelectAllStatementId())); //$NON-NLS-1$

        FullyQualifiedJavaType parameterType;
        if (isSimple) {
            parameterType = new FullyQualifiedJavaType(
                    introspectedTable.getBaseRecordType());
        } else {
            parameterType = introspectedTable.getRules()
                    .calculateAllFieldsClass();
        }

        answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
                parameterType.getFullyQualifiedName()));
        if (introspectedTable.getRules().generateResultMapWithBLOBs()) {
            answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
                    introspectedTable.getResultMapWithBLOBsId()));
        } else {
            answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
                    introspectedTable.getBaseResultMapId()));
        }

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT "); //$NON-NLS-1$
        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(getBaseColumnListElement());

        sb.setLength(0);
        sb.append("FROM "); //$NON-NLS-1$
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        XmlElement dynamicElement = new XmlElement("where"); //$NON-NLS-1$
        answer.addElement(dynamicElement);

        boolean firstColumn = true;
        for (IntrospectedColumn introspectedColumn :
                ListUtilities.removeGeneratedAlwaysColumns(introspectedTable.getAllColumns())) {
            sb.setLength(0);
            sb.append(introspectedColumn.getJavaProperty());
            sb.append(" != null");
            if (introspectedColumn.getJdbcTypeName().equals("BLOB") || introspectedColumn.getJdbcTypeName().equals("VARCHAR")) {
                sb.append(" AND ").append(introspectedColumn.getJavaProperty()).append(" != ''");
            }
            XmlElement isNotNullElement = new XmlElement("if"); //$NON-NLS-1$
            isNotNullElement.addAttribute(new Attribute("test", sb.toString())); //$NON-NLS-1$
            dynamicElement.addElement(isNotNullElement);

            sb.setLength(0);
            if (firstColumn) {
                firstColumn = false;
            } else {
                sb.append("AND ");
            }
            sb.append(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn));
            if (introspectedColumn.getJdbcTypeName().equals("BLOB") || introspectedColumn.getJdbcTypeName().equals("VARCHAR")) {
                sb.append(" LIKE CONCAT('%' ").append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn)).append(", '%')");
            } else {
                sb.append(" = "); //$NON-NLS-1$
                sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            }
            isNotNullElement.addElement(new TextElement(sb.toString()));
        }
    }
}
