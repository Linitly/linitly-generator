package org.mybatis.generator.linitly;

public interface MapperConstant {

    String EXTENDS_VALUE = CommonConstant.BASE_PACKAGE + ".dao.BaseBeanMapper.BaseEntityMap";

    String PARAM_ANNOTATION = "@Param";

    String PARAM_IMPORT = "org.apache.ibatis.annotations.Param";

    String DELETE_BACKUP_ANNOTATION = "@DeleteBackup";

    String DELETE_BACKUP_IMPORT = CommonConstant.BASE_PACKAGE + ".annotation.DeleteBackup";
}
