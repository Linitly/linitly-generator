package org.mybatis.generator.linitly.dto;

import org.mybatis.generator.linitly.CommonConstant;

public interface ModelDtoSuperClassConstant {

    String CLASS_NAME = "BaseDTO";

    String FULL_CLASS_NAME = CommonConstant.MODEL_CLASS_PACKAGE + "." + CLASS_NAME;

    String[] FIELD_NAME = {"id", "created_user_id", "created_time", "last_modified_user_id", "last_modified_time", "enabled"};
}
