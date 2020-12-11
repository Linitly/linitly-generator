## linitly-generator

本项目是基于`mybatis-generator 1.4.0`版本进行修改，为生成适用于项目的相关文件，可以为后端生成一套基本CRUD文件，避免简单的重复工作。

环境要求：JDK1.8+

**重要**：本项目以`linitly-boot`为脚手架，生成文件内部分基础信息**以此脚手架为基础**，但不完全依赖于此脚手架；主要为该脚手架定制；

GitHub地址：https://github.com/Linitly/linitly-boot.git

Gitee地址：https://gitee.com/linitly/linitly-boot.git

开发人员可修改生成后的文件，也可以修改源码来适用于自身项目。

### 效果演示

#### xml配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
        PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>
    <!-- 数据库驱动:选择你的本地硬盘上面的数据库驱动包-->
    <classPathEntry location="mysql-connector-java-3.1.13-bin.jar"/>
    <context id="mysql" targetRuntime="MyBatis3">
        <commentGenerator>
            <!-- 是否去除自动生成的注释 true：是 ： false:否 -->
            <property name="suppressAllComments" value="false"/>
            <!-- 将数据库中表的字段描述信息添加到注释 -->
            <property name="addRemarkComments" value="true"/>
            <!-- 是否生成注释代时间戳 -->
            <property name="suppressDate" value="false"/>
			<property name="javaFileEncoding" value="UTF-8"/>
        </commentGenerator>
        <!--数据库链接URL，用户名、密码 -->
        <jdbcConnection driverClass="com.mysql.jdbc.Driver" connectionURL="jdbc:mysql://127.0.0.1:3306/xxx?characterEncoding=utf8" userId="root" password="root">
        </jdbcConnection>
<!-- 默认false，把JDBC DECIMAL 和 NUMERIC 类型解析为 Integer，为 true时把JDBC DECIMAL 和 NUMERIC 类型解析为java.math.BigDecimal -->
        <javaTypeResolver>
            <property name="forceBigDecimals" value="true"/>
        </javaTypeResolver>
        <!-- 生成模型的包名和位置-->
        <javaModelGenerator targetPackage="org.linitly.boot.business.entity" targetProject="src">
            <!-- enableSubPackages:是否让schema作为包的后缀 -->
            <property name="enableSubPackages" value="true"/>
        </javaModelGenerator>
		<!-- 生成dto的包名和位置 -->
		<javaModelDtoGenerator targetPackage="org.linitly.boot.business.dto" targetProject="src"/>
		<!-- 生成vo的包名和位置 -->
		<javaModelVoGenerator targetPackage="org.linitly.boot.business.vo" targetProject="src"/>
        <!-- 生成controller的包名和位置 -->
        <javaControllerGenerator targetPackage="org.linitly.boot.business.controller" targetProject="src"/>
        <!-- 生成service的包名和位置 -->
        <javaServiceGenerator targetPackage="org.linitly.boot.business.service" targetProject="src"/>
		<!-- 生成constant的包名和位置 -->
		<javaConstantGenerator targetPackage="org.linitly.boot.business.constant" targetProject="src"/>
        <!-- 生成DAO的包名和位置-->
        <javaClientGenerator type="XMLMAPPER" targetPackage="org.linitly.boot.business.dao" targetProject="src">
            <property name="enableSubPackages" value="true"/>
        </javaClientGenerator>
        <!-- 生成映射文件的包名和位置-->
        <sqlMapGenerator targetPackage="mapper.business" targetProject="src">
            <property name="enableSubPackages" value="true"/>
        </sqlMapGenerator>
        <!-- 要生成的表 tableName是数据库中的表名或视图名 domainObjectName是实体类名-->
		<table tableName="test_generator" domainObjectName="TestGenerator"/>
    </context>
</generatorConfiguration>
```

#### 数据库表ddl

```sql
CREATE TABLE `test_generator` (
  `id`                          bigint            NOT NULL  AUTO_INCREMENT    COMMENT '主键',
  `test_aa`                     VARCHAR(16)       NOT NULL  DEFAULT ''        COMMENT 'aaaaa',
  `test_bb`                     VARCHAR(100)                DEFAULT ''        COMMENT 'bbbbb',
  `test_cc`                     INT                         DEFAULT 0         COMMENT 'ccccc',
  `test_dd`                     bigint            NOT NULL  DEFAULT 0         COMMENT 'ddddd',
  `test_ee`                     datetime          DEFAULT CURRENT_TIMESTAMP   COMMENT 'eeeee',
  `test_ff`                     text                                          COMMENT 'ffffff',
  `test_gg`                     DOUBLE(5, 2)      NOT NULL  DEFAULT 0         COMMENT 'gggggg',
  `enabled`                     int(1)            NOT NULL  DEFAULT 1         COMMENT '启用状态(1:启用(默认);0:禁用;)',
  `created_user_id`             bigint            NOT NULL  DEFAULT 0         COMMENT '创建人id',
  `created_time`                datetime          DEFAULT CURRENT_TIMESTAMP   COMMENT '创建时间',
  `last_modified_user_id`       bigint            NOT NULL  DEFAULT 0         COMMENT '最后修改人id',
  `last_modified_time`          datetime          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  PRIMARY KEY (id)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '测试生成' ROW_FORMAT = Dynamic;
```

#### mapper文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="org.linitly.boot.business.dao.TestGeneratorMapper">
    <resultMap extends="org.linitly.boot.base.dao.BaseBeanMapper.BaseEntityMap" id="BaseResultMap" type="org.linitly.boot.business.entity.TestGenerator">
        <result column="test_aa" jdbcType="VARCHAR" property="testAa" />
        <result column="test_bb" jdbcType="VARCHAR" property="testBb" />
        <result column="test_cc" jdbcType="INTEGER" property="testCc" />
        <result column="test_dd" jdbcType="BIGINT" property="testDd" />
        <result column="test_ee" jdbcType="TIMESTAMP" property="testEe" />
        <result column="test_ff" jdbcType="LONGVARCHAR" property="testFf" />
        <result column="test_gg" jdbcType="DOUBLE" property="testGg" />
    </resultMap>

    <sql id="Base_Column_List">
        id, test_aa, test_bb, test_cc, test_dd, test_ee, test_ff, test_gg, enabled, created_user_id, 
        created_time, last_modified_user_id, last_modified_time
    </sql>

    <select id="findById" parameterType="java.lang.Long" resultMap="BaseResultMap">
        SELECT 
        <include refid="Base_Column_List" />
        FROM test_generator
        WHERE id = #{id, jdbcType=BIGINT}
    </select>

    <delete id="deleteById" parameterType="java.lang.Long">
        DELETE FROM test_generator
        WHERE id = #{id, jdbcType=BIGINT}
    </delete>

    <insert id="insert" parameterType="org.linitly.boot.business.entity.TestGenerator">
        INSERT INTO test_generator (id, test_aa, test_bb, 
            test_cc, test_dd, test_ee, 
            test_ff, test_gg, created_user_id, 
            last_modified_user_id)
        VALUES (#{id, jdbcType=BIGINT}, #{testAa, jdbcType=VARCHAR}, #{testBb, jdbcType=VARCHAR}, 
            #{testCc, jdbcType=INTEGER}, #{testDd, jdbcType=BIGINT}, #{testEe, jdbcType=TIMESTAMP}, 
            #{testFf, jdbcType=LONGVARCHAR}, #{testGg, jdbcType=DOUBLE}, #{createdUserId, jdbcType=BIGINT}, 
            #{lastModifiedUserId, jdbcType=BIGINT})
    </insert>

    <update id="updateById" parameterType="org.linitly.boot.business.entity.TestGenerator">
        UPDATE test_generator
        SET test_aa = #{testAa, jdbcType=VARCHAR},
            test_bb = #{testBb, jdbcType=VARCHAR},
            test_cc = #{testCc, jdbcType=INTEGER},
            test_dd = #{testDd, jdbcType=BIGINT},
            test_ee = #{testEe, jdbcType=TIMESTAMP},
            test_ff = #{testFf, jdbcType=LONGVARCHAR},
            test_gg = #{testGg, jdbcType=DOUBLE},
            last_modified_user_id = #{lastModifiedUserId, jdbcType=BIGINT}
        WHERE id = #{id, jdbcType=BIGINT}
    </update>

    <select id="findAll" parameterType="org.linitly.boot.business.entity.TestGenerator" resultMap="BaseResultMap">
        SELECT 
        <include refid="Base_Column_List" />
        FROM test_generator
        <where>
            <if test="testAa != null and testAa != ''">
                test_aa LIKE CONCAT('%', #{testAa, jdbcType=VARCHAR}, '%')
            </if>
            <if test="testBb != null and testBb != ''">
                AND test_bb LIKE CONCAT('%', #{testBb, jdbcType=VARCHAR}, '%')
            </if>
            <if test="testCc != null">
                AND test_cc = #{testCc, jdbcType=INTEGER}
            </if>
            <if test="testDd != null">
                AND test_dd = #{testDd, jdbcType=BIGINT}
            </if>
            <if test="testEe != null">
                AND test_ee = #{testEe, jdbcType=TIMESTAMP}
            </if>
            <if test="testFf != null">
                AND test_ff = #{testFf, jdbcType=LONGVARCHAR}
            </if>
            <if test="testGg != null">
                AND test_gg = #{testGg, jdbcType=DOUBLE}
            </if>
            <if test="enabled != null">
                AND enabled = #{enabled, jdbcType=INTEGER}
            </if>
            <if test="createdTime != null">
                AND created_time = #{createdTime, jdbcType=TIMESTAMP}
            </if>
        </where>
    </select>

    <insert id="insertSelective" parameterType="org.linitly.boot.business.entity.TestGenerator">
        INSERT INTO test_generator
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <if test="id != null">
                id,
            </if>
            <if test="testAa != null">
                test_aa,
            </if>
            <if test="testBb != null">
                test_bb,
            </if>
            <if test="testCc != null">
                test_cc,
            </if>
            <if test="testDd != null">
                test_dd,
            </if>
            <if test="testEe != null">
                test_ee,
            </if>
            <if test="testFf != null">
                test_ff,
            </if>
            <if test="testGg != null">
                test_gg,
            </if>
            <if test="createdUserId != null">
                created_user_id,
            </if>
            <if test="lastModifiedUserId != null">
                last_modified_user_id,
            </if>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
            <if test="id != null">
                #{id, jdbcType=BIGINT},
            </if>
            <if test="testAa != null">
                #{testAa, jdbcType=VARCHAR},
            </if>
            <if test="testBb != null">
                #{testBb, jdbcType=VARCHAR},
            </if>
            <if test="testCc != null">
                #{testCc, jdbcType=INTEGER},
            </if>
            <if test="testDd != null">
                #{testDd, jdbcType=BIGINT},
            </if>
            <if test="testEe != null">
                #{testEe, jdbcType=TIMESTAMP},
            </if>
            <if test="testFf != null">
                #{testFf, jdbcType=LONGVARCHAR},
            </if>
            <if test="testGg != null">
                #{testGg, jdbcType=DOUBLE},
            </if>
            <if test="createdUserId != null">
                #{createdUserId, jdbcType=BIGINT},
            </if>
            <if test="lastModifiedUserId != null">
                #{lastModifiedUserId, jdbcType=BIGINT},
            </if>
        </trim>
    </insert>

    <update id="updateByIdSelective" parameterType="org.linitly.boot.business.entity.TestGenerator">
        UPDATE test_generator
        <set>
            <if test="testAa != null">
                test_aa = #{testAa, jdbcType=VARCHAR},
            </if>
            <if test="testBb != null">
                test_bb = #{testBb, jdbcType=VARCHAR},
            </if>
            <if test="testCc != null">
                test_cc = #{testCc, jdbcType=INTEGER},
            </if>
            <if test="testDd != null">
                test_dd = #{testDd, jdbcType=BIGINT},
            </if>
            <if test="testEe != null">
                test_ee = #{testEe, jdbcType=TIMESTAMP},
            </if>
            <if test="testFf != null">
                test_ff = #{testFf, jdbcType=LONGVARCHAR},
            </if>
            <if test="testGg != null">
                test_gg = #{testGg, jdbcType=DOUBLE},
            </if>
            <if test="lastModifiedUserId != null">
                last_modified_user_id = #{lastModifiedUserId, jdbcType=BIGINT},
            </if>
        </set>
        where id = #{id, jdbcType=BIGINT}
    </update>
</mapper>
```

#### dao层文件

```java
package org.linitly.boot.business.dao;

import java.util.List;
import org.linitly.boot.base.annotation.DeleteBackup;
import org.linitly.boot.business.entity.TestGenerator;

/**
 * @author: linitly-generator
 * @date: 2020-12-11 13:33
 * @description: 
 */
public interface TestGeneratorMapper {

    @DeleteBackup
    int deleteById(Long id);

    int insert(TestGenerator testGenerator);

    TestGenerator findById(Long id);

    int updateById(TestGenerator testGenerator);

    List<TestGenerator> findAll(TestGenerator testGenerator);

    int insertSelective(TestGenerator testGenerator);

    int updateByIdSelective(TestGenerator testGenerator);
}
```

#### service文件

```java
package org.linitly.boot.business.service;

import java.util.List;
import org.linitly.boot.business.dao.TestGeneratorMapper;
import org.linitly.boot.business.dto.TestGeneratorDTO;
import org.linitly.boot.business.entity.TestGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: linitly-generator
 * @date: 2020-12-11 13:33
 * @description: 
 */
@Service
public class TestGeneratorService {

    @Autowired
    private TestGeneratorMapper testGeneratorMapper;

    public void insert(TestGeneratorDTO dto) {
        TestGenerator testGenerator = new TestGenerator();
        BeanUtils.copyProperties(dto, testGenerator);
        testGeneratorMapper.insertSelective(testGenerator);
    }

    public void updateById(TestGeneratorDTO dto) {
        TestGenerator testGenerator = new TestGenerator();
        BeanUtils.copyProperties(dto, testGenerator);
        testGeneratorMapper.updateByIdSelective(testGenerator);
    }

    public TestGenerator findById(Long id) {
        return testGeneratorMapper.findById(id);
    }

    public List<TestGenerator> findAll(TestGenerator testGenerator) {
        return testGeneratorMapper.findAll(testGenerator);
    }

    public void deleteById(Long id) {
        testGeneratorMapper.deleteById(id);
    }
}
```

#### controller文件

```java
package org.linitly.boot.business.controller;

import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.linitly.boot.base.annotation.Pagination;
import org.linitly.boot.base.annotation.Result;
import org.linitly.boot.base.constant.admin.AdminCommonConstant;
import org.linitly.boot.base.helper.groups.InsertValidGroup;
import org.linitly.boot.base.helper.groups.UpdateValidGroup;
import org.linitly.boot.business.dto.TestGeneratorDTO;
import org.linitly.boot.business.entity.TestGenerator;
import org.linitly.boot.business.service.TestGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author: linitly-generator
 * @date: 2020-12-11 13:33
 * @description: 
 */
@Result
@RestController
@RequestMapping("/testGenerator")
@Api(tags = "测试生成管理")
public class TestGeneratorController {

    @Autowired
    private TestGeneratorService testGeneratorService;

    @PostMapping("/insert")
    @ApiOperation(value = "添加测试生成")
    public void insert(@RequestBody @Validated({InsertValidGroup.class}) TestGeneratorDTO dto, BindingResult bindingResult) {
        testGeneratorService.insert(dto);
    }

    @PostMapping("/updateById")
    @ApiOperation(value = "修改测试生成")
    public void updateById(@RequestBody @Validated({UpdateValidGroup.class}) TestGeneratorDTO dto, BindingResult bindingResult) {
        testGeneratorService.updateById(dto);
    }

    @PostMapping("/findById/{id}")
    @ApiOperation(value = "根据id查询测试生成")
    public TestGenerator findById(@PathVariable Long id) {
        return testGeneratorService.findById(id);
    }

    @Pagination
    @PostMapping("/findAll")
    @ApiOperation(value = "查询测试生成列表")
    public List<TestGenerator> findAll(@RequestParam(defaultValue = AdminCommonConstant.PAGE_NUMBER) int pageNumber, @RequestParam(defaultValue = AdminCommonConstant.PAGE_SIZE) int pageSize, @RequestBody(required = false) TestGenerator testGenerator) {
        PageHelper.startPage(pageNumber, pageSize, "id desc");
        return testGeneratorService.findAll(testGenerator);
    }

    @PostMapping("/deleteById/{id}")
    @ApiOperation(value = "根据id删除测试生成")
    public void deleteById(@PathVariable Long id) {
        testGeneratorService.deleteById(id);
    }
}
```

#### entity文件

```java
package org.linitly.boot.business.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;
import org.linitly.boot.base.helper.entity.BaseEntity;

/**
 * @author: linitly-generator
 * @date: 2020-12-11 13:33
 * @description: 
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "测试生成")
public class TestGenerator extends BaseEntity {

    @ApiModelProperty(value = "aaaaa")
    private String testAa;

    @ApiModelProperty(value = "bbbbb")
    private String testBb;

    @ApiModelProperty(value = "ccccc")
    private Integer testCc;

    @ApiModelProperty(value = "ddddd")
    private Long testDd;

    @ApiModelProperty(value = "eeeee")
    private Date testEe;

    @ApiModelProperty(value = "ffffff")
    private String testFf;

    @ApiModelProperty(value = "gggggg")
    private Double testGg;
}
```

#### dto文件

```java
package org.linitly.boot.business.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;
import org.linitly.boot.base.helper.entity.BaseDTO;
import org.linitly.boot.base.helper.groups.InsertValidGroup;
import org.linitly.boot.base.helper.groups.UpdateValidGroup;
import org.linitly.boot.business.constant.TestGeneratorConstant;

/**
 * @author: linitly-generator
 * @date: 2020-12-11 13:33
 * @description: 
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "测试生成DTO")
public class TestGeneratorDTO extends BaseDTO {

    @ApiModelProperty(value = "aaaaa", required = true)
    @NotBlank(message = TestGeneratorConstant.TEST_AA_EMPTY_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @Size(max = TestGeneratorConstant.MAX_TEST_AA_SIZE, message = TestGeneratorConstant.TEST_AA_SIZE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private String testAa;

    @ApiModelProperty(value = "bbbbb")
    @Size(max = TestGeneratorConstant.MAX_TEST_BB_SIZE, message = TestGeneratorConstant.TEST_BB_SIZE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private String testBb;

    @ApiModelProperty(value = "ccccc")
    @Range(message = TestGeneratorConstant.TEST_CC_RANGE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private Integer testCc;

    @ApiModelProperty(value = "ddddd", required = true)
    @NotNull(message = TestGeneratorConstant.TEST_DD_EMPTY_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    @Range(message = TestGeneratorConstant.TEST_DD_RANGE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private Long testDd;

    @ApiModelProperty(value = "eeeee")
    private java.util.Date testEe;

    @ApiModelProperty(value = "ffffff")
    @Size(max = TestGeneratorConstant.MAX_TEST_FF_SIZE, message = TestGeneratorConstant.TEST_FF_SIZE_ERROR, groups = {InsertValidGroup.class, UpdateValidGroup.class})
    private String testFf;

    @ApiModelProperty(value = "gggggg", required = true)
    private Double testGg;
}
```

#### vo文件

```java
package org.linitly.boot.business.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;
import org.linitly.boot.base.helper.entity.BaseVO;

/**
 * @author: linitly-generator
 * @date: 2020-12-11 13:33
 * @description: 
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "测试生成VO")
public class TestGeneratorVO extends BaseVO {

    @ApiModelProperty(value = "aaaaa")
    private String testAa;

    @ApiModelProperty(value = "bbbbb")
    private String testBb;

    @ApiModelProperty(value = "ccccc")
    private Integer testCc;

    @ApiModelProperty(value = "ddddd")
    private Long testDd;

    @ApiModelProperty(value = "eeeee")
    private Date testEe;

    @ApiModelProperty(value = "ffffff")
    private String testFf;

    @ApiModelProperty(value = "gggggg")
    private Double testGg;
}
```

#### constant文件

```java
package org.linitly.boot.business.constant;

/**
 * @author: linitly-generator
 * @date: 2020-12-11 13:33
 * @description: 
 */
public interface TestGeneratorConstant {

    String TEST_AA_EMPTY_ERROR = "aaaaa不能为空";

    int MAX_TEST_AA_SIZE = 16;

    String TEST_AA_SIZE_ERROR = "aaaaa长度不符合限制";

    int MAX_TEST_BB_SIZE = 100;

    String TEST_BB_SIZE_ERROR = "bbbbb长度不符合限制";

    String TEST_CC_RANGE_ERROR = "ccccc大小不符合限制";

    String TEST_DD_EMPTY_ERROR = "ddddd不能为空";

    String TEST_DD_RANGE_ERROR = "ddddd大小不符合限制";

    int MAX_TEST_FF_SIZE = 65535;

    String TEST_FF_SIZE_ERROR = "ffffff长度不符合限制";
}
```

### 分支说明

`master`：最新可用RELEASE版本；当前版本`1.4.0`；

`1.4.0`：为修改`mybatis-generator 1.4.0`源码后第一版可使用RELEASE版本；

`resource`：`mybatis-generator 1.4.0`版本源码备份；

`dev`：开发分支，不建议使用；