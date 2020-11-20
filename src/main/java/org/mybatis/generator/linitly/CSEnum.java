package org.mybatis.generator.linitly;

public enum CSEnum {

    FIND_ALL("查询", "列表"),
    INSERT("添加", ""),
    UPDATE("修改", ""),
    FIND_BY_ID("根据id查询", ""),
    DELETE("根据id删除", ""),
    ;

    CSEnum(String cnPrefix, String cnSuffix) {
        this.cnPrefix = cnPrefix;
        this.cnSuffix = cnSuffix;
    }

    private String cnPrefix;

    private String cnSuffix;

    public String getCnPrefix() {
        return cnPrefix;
    }

    public String getCnSuffix() {
        return cnSuffix;
    }
}
