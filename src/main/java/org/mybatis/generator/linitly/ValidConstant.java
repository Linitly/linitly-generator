package org.mybatis.generator.linitly;

public interface ValidConstant {

    String NOT_BLANK = "@NotBlank";

    String NOT_NULL = "@NotNull";

    String SIZE = "@Size";

    String RANGE = "@Range";

    String VALIDATED = "@Validated";

    String JAVAX_VALID_IMPORT = "javax.validation.constraints.*";

    String VALIDATED_IMPORT = "org.springframework.validation.annotation.Validated";

    String RANGE_IMPORT = "org.hibernate.validator.constraints.Range";



    String EMPTY_ERROR_CONSTANT_SUFFIX = "_EMPTY_ERROR";

    String EMPTY_ERROR_TIP_SUFFIX = "不能为空";

    String MAX_CONSTANT_PREFIX = "MAX_";

    String SIZE_CONSTANT_SUFFIX = "_SIZE";

    String SIZE_ERROR_TIP_SUFFIX = "长度不符合限制";

    String SIZE_ERROR_CONSTANT_SUFFIX = "_SIZE_ERROR";

    String RANGE_ERROR_TIP_SUFFIX = "_RANGE";

    String RANGE_ERROR_CONSTANT_SUFFIX = "大小不符合限制";
}
