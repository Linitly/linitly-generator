package org.mybatis.generator.linitly.vo;

import org.mybatis.generator.linitly.CommonConstant;

public interface ModelVoSuperClassConstant {

    String CLASS_NAME = "BaseVO";

    String FULL_CLASS_NAME = CommonConstant.MODEL_CLASS_PACKAGE + "." + CLASS_NAME;

    String[] FIELD_NAME = {"id", "created_user_id", "created_time", "last_modified_user_id", "last_modified_time", "enabled"};
}
