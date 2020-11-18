package org.mybatis.generator.linitly;

import java.text.SimpleDateFormat;

public interface RootClassConstant {

    String CLASS_PACKAGE = "org.linitly.boot.business.helper.entity";

    String CLASS_NAME = "BaseEntity";

    String FULL_CLASS_NAME = CLASS_PACKAGE + "." + CLASS_NAME;

    String[] FIELD_NAME = {"id", "created_user_id", "created_time", "last_modified_user_id", "last_modified_time", "enabled"};

    String AUTHOR = "linitly-generator";

    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm");
}
