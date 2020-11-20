package org.mybatis.generator.linitly;

public interface ValidConstant {

    String NOT_BLANK = "@NotBlank";

    String NOT_NULL = "@NotNull";

    String SIZE = "@Size";

    String VALIDATED = "@Validated";

    String RANGE = "@Range";

    String JAVAX_VALID_IMPORT = "javax.validation.constraints.*";

    String VALIDATED_IMPORT = "org.springframework.validation.annotation.Validated";

    String RANGE_IMPORT = "org.hibernate.validator.constraints.Range";
}
