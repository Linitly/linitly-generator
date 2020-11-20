package org.mybatis.generator.linitly;

public interface ModelSuperClassConstant {

    String CLASS_NAME = "BaseEntity";

    String FULL_CLASS_NAME = CommonConstant.MODEL_CLASS_PACKAGE + "." + CLASS_NAME;

    String[] FIELD_NAME = {"id", "created_user_id", "created_time", "last_modified_user_id", "last_modified_time", "enabled"};
}
