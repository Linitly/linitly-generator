package org.mybatis.generator.linitly.controller;

import org.mybatis.generator.config.PropertyHolder;

import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * @author: linxiunan
 * @date: 2020/11/19 16:12
 * @descrption:
 */
public class JavaControllerGeneratorConfiguration extends PropertyHolder {


    private String targetPackage;

    private String targetProject;

    public JavaControllerGeneratorConfiguration() {
        super();
    }

    public String getTargetProject() {
        return targetProject;
    }

    public void setTargetProject(String targetProject) {
        this.targetProject = targetProject;
    }

    public String getTargetPackage() {
        return targetPackage;
    }

    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
    }

    public void validate(List<String> errors, String contextId) {
        if (!stringHasValue(targetProject)) {
            errors.add(getString("ValidationError.0", contextId)); //$NON-NLS-1$
        }

        if (!stringHasValue(targetPackage)) {
            errors.add(getString("ValidationError.12", //$NON-NLS-1$
                    "JavaModelGenerator", contextId)); //$NON-NLS-1$
        }
    }
}
