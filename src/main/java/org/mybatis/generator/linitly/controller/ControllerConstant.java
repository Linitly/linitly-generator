package org.mybatis.generator.linitly.controller;

import org.mybatis.generator.linitly.CommonConstant;

public interface ControllerConstant {

    String RESULT = "@Result";

    String RESULT_IMPORT = CommonConstant.BASE_PACKAGE + ".annotation.Result";

    String PAGINATION = "@Pagination";

    String PAGINATION_IMPORT = CommonConstant.BASE_PACKAGE + ".annotation.Pagination";

    String REST_CONTROLLER = "@RestController";

    String REQUEST_MAPPING = "@RequestMapping";

    String POST_MAPPING = "@PostMapping";

    String REQUEST_BODY = "@RequestBody";

    String REQUEST_PARAM = "@RequestParam";

    String PATH_VARIABLE = "@PathVariable";

    String WEB_ANNO_IMPORT = "org.springframework.web.bind.annotation.*";

    String BINDING_RESULT_TYPE = "BindingResult";

    String BINDING_RESULT_IMPORT = "org.springframework.validation.BindingResult";

    String PAGE_HELPER_TYPE = "PageHelper";

    String PAGE_HELPER_IMPORT = "com.github.pagehelper.PageHelper";

    String INSERT_VALID_GROUP_CLASS = "InsertValidGroup.class";

    String UPDATE_VALID_GROUP_CLASS = "UpdateValidGroup.class";

    String INSERT_VALID_GROUP_IMPORT = CommonConstant.BASE_PACKAGE + ".helper.groups.InsertValidGroup";

    String UPDATE_VALID_GROUP_IMPORT = CommonConstant.BASE_PACKAGE + ".helper.groups.UpdateValidGroup";

    String ADMIN_COMMON_CONSTANT_TYPE = "AdminCommonConstant";

    String PAGE_NUMBER = "PAGE_NUMBER";

    String PAGE_SIZE = "PAGE_SIZE";

    String ADMIN_COMMON_CONSTANT_IMPORT = CommonConstant.BASE_PACKAGE + ".constant.admin.AdminCommonConstant";
}
