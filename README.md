# java代码生成工具
* 一款spring代码生成工具，可自定义模板生成不同的代码，支持MySQL、Oracle、SQL Server、PostgreSQL。
* 生成mybatis mapper,po,biz,controller全套后台代码
* 支持生成easyui代码
* 支持生成jqgrid代码
* 支持生成layui代码
* 支持生成swagger文档代码
* 支持基于uri的限制，配置允许动态修改
* 通过jdbc支持多种数据库:mysql,postgreSql,oracle等
* 演示示例：http://generate.opendfl.org.cn/index.html

## 代码架构
* springboot/springmvc
* tk.mybatis
* mysql/postgreSql等
* easyui/jqgrid/layui
* po,mapper(dao),biz,controller及页面全部生成
* 支持基本功能：查询、增加、修改、删除。
* 演示示例：http://generate-demo.opendfl.org.cn/index.html


## 原理及特性
* 基于数据库表，读取表的所有属性，备注等，用于生成代码。
* java代码与页面easyui或jqgrid代码一次生成，根据需要选择对应的页面。
* 生成的代码可用于springboot或springmvc

## 使用要求
* 尽量要有注释，否则自动取属性名
* 配置generatorConfig.xml
* 运行CodeGeneratorBiz.java