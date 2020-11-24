package org.mybatis.generator.linitly;

import java.text.SimpleDateFormat;

public interface CommonConstant {

    String ROOT_PACKAGE = "org.linitly.boot";

    String BASE_PACKAGE = ROOT_PACKAGE + ".base";

    String BUSINESS_PACKAGE = ROOT_PACKAGE + ".business";

    String AUTOWIRED = "@Autowired";

    String AUTOWIRED_IMPORT = "org.springframework.beans.factory.annotation.Autowired";

    String AUTHOR = "linitly-generator";

    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    String MODEL_CLASS_PACKAGE = CommonConstant.BASE_PACKAGE + ".helper.entity";

    String DTO_NAME = "dto";
}
