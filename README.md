## linitly-generator

### 配置说明

由于`xml`文件的格式是受`dtd`文件的规范和约束的，但无法去改变`mybatis`的`dtd`文件，且其本身提供的标签大部分仍要使用，所以仍基于`mybatis`的配置文件，在此基础上添加标签，使代码能正常读取运行。

新增`javaModelDtoGenerator`、`javaModelVoGenerator`、`javaControllerGenerator`、`javaServiceGenerator`、`javaConstantGenerator`标签；标签具有两个属性：`targetPackage`和`targetProject`，表示目标包名和目标目录；分别为生成DTO文件、VO文件、Controller文件、Service文件、Constant文件的配置。

**重要：**由于代码的修改多数为硬编码，只为适应脚手架的需要而修改生成代码工具；且文件之间有关联性，因此建议使用者保持这些标签，如果需要修改，修改生成后的文件。

### 代码说明

本人新增的代码文件位于`org.mybatis.generator.linitly`包下

#### 启动类

文件位于`org.mybatis.generator.api.ShellRunner`，主要代码位于`106-117`行；

#### 读取配置

`org.mybatis.generator.config.xml.MyBatisGeneratorConfigurationParser`文件第81行代码：`parseConfiguration`方法，为读取配置文件信息的开始入口；读取主要配置文件信息的逻辑，在此文件的151行`parseContext`方法。

#### 读取数据库表信息

`org.mybatis.generator.internal.db.DatabaseIntrospector`文件第169行代码；`introspectTables`方法，为读取表信息的主要逻辑入口。

#### 获取文件信息

`org.mybatis.generator.config.Context`文件499行，`generateFiles`方法为获取要生成的文件的信息和主要入口。

#### 生成文件

`org.mybatis.generator.api.MyBatisGenerator`文件第275行代码，为生成文件的主要逻辑入口，`writeGeneratedJavaFile`方法和`writeGeneratedXmlFile`方法是生成java文件和xml文件的方法。

### 主要文件

#### 注释文件

`org.mybatis.generator.internal.DefaultCommentGenerator`文件为默认注释的配置文件。

#### entity、vo文件

`org.mybatis.generator.codegen.mybatis3.model.BaseRecordGenerator`文件的`getCompilationUnits`方法为生成entity文件和vo文件的主要逻辑。

#### dao文件

`org.mybatis.generator.codegen.mybatis3.javamapper.JavaMapperGenerator`文件的`getCompilationUnits`方法为生成dao文件的主要逻辑。

#### mapper文件

`org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator`文件的`getSqlMapElement`方法为生成mapper文件的主要逻辑入口。

#### 其它文件

其它文件的主要逻辑见`org.mybatis.generator.linitly`包下对应的包下`Generator`文件。

### 代码主要逻辑

1. 读取xml配置文件，将配置封装成配置文件并保存；
2. 通过数据库驱动，读取数据库表信息，封装成表信息并保存；
3. 通过获取的数据库表信息，循环获取要生成的文件信息，包括java文件、xml文件和kotlin文件，但kotlin文件我们基本不用，主要是前二者；封装成文件对象保存；
4. 循环获取到的文件，将文件信息读取并格式化成字符串，最后通过文件输出流写出文件；