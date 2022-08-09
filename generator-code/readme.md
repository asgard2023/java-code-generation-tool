# code-gen-spring 代码生成工具
* 一款spring代码生成工具，可自定义模板生成不同的代码，支持MySQL、Oracle、SQL Server、PostgreSQL。
* 生成mybatis mapper,po,biz,controller全套后台代码
* 生成easyui代码
* 生成jqgrid代码
* 生成layui代码
* 支持基于uri的限制，配置允许动态修改
* 通过jdbc支持多种数据库:mysql,postgreSql,oracle等

## 原理及特性
* 基于数据库表，读取表的所有属性，备注等，用于生成代码。
* java代码与页面easyui或jqgrid代码一次生成，根据需要选择对应的页面。
* 生成的代码可用于springboot或springmvc

## 使用要求
* 数据库名表、表的属性尽量要有注释，以便于生成代码的注释否则自动取属性名
* 配置generatorConfig.xml
* 运行单元测试CodeGeneratorUtilTest.generateCode
* web生成：http://localhost:8080/index.html

## 演示示例
http://generate-demo.opendfl.org.cn/index.html