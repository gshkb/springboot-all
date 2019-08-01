## JOOQ 简介:
官网: [https://www.jooq.org] 
```
  1.jooq是什么?
     jooq(Java Object Oriented Querying)java面向对象查询，是一种ORM框架，轻量，简单并且足够灵活
  2.jooq解决了什么问题?
     1.相对于mybatis jooq免去了在xml文件中写sql的麻烦
     2.保留了传统ORM 的优点，简单操作性，安全性，类型安全等。不需要复杂的配置，并且可以利用Java 8 Stream API 做更加复杂的数据转换
     3.支持主流的RDMS和更多的特性，如self-joins，union，存储过程，复杂的子查询等等
     4.丰富的Fluent API和完善文档
     5.runtime schema mapping 可以支持多个数据库schema访问。简单来说使用一个连接池可以访问N个DB schema，使用比较多的就是SaaS应用的多租户场景
     6.代码够简单和清晰。遇到不会写的sql可以充分利用IDEA代码提示功能轻松完成,提高开发效率
  3.jooq的框架原理?
```
## flywayDB 简介
  官网: [https://flywaydb.org] 
  ```$xslt
  1.flywayBD是什么?
        Flyway是一个开源数据库迁移工具。它强烈支持简单性和约定优于配置.他是支撑代码工程化的强有力的保障.可以高效安全的持续维护,
     项目开发中的数据库迭代.
        它基于7个基本命令:Migrate(迁移), Clean(清理), Info(信息), Validate(验证), Undo(撤销), Baseline(基线) and Repair(修复).
        支持的数据库包括 Oracle， SQL Server（包括Amazon RDS和Azure SQL数据库， DB2， MySQL（包括Amazon RDS，Azure数据库和Google Cloud SQL）， Aurora MySQL， MariaDB， Percona XtraDB群集， PostgreSQL（包括Amazon RDS，Azure数据库， Google Cloud SQL和Heroku）， Aurora PostgreSQL， Redshift， CockroachDB，
         SAP HANA， Sybase ASE， Informix， H2， HSQLDB等
  2.flyway如何运作 
        详情见: https://flywaydb.org/getstarted/how  
  ```
  * 注意:项目中的 resourse/db/migration 目录下的sql文件 必须以 V开头,代表版本号 ,中间有两个"-".否则
  项目启动时不能执行sql 
  * flywaydb和jooq代码生成都支持mvn插件的形式运行