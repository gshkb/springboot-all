package gen;


import org.apache.commons.io.*;
import org.jboss.forge.roaster.*;
import org.jboss.forge.roaster.model.*;
import org.jboss.forge.roaster.model.source.*;
import org.jooq.codegen.*;
import org.jooq.meta.*;
import org.jooq.tools.*;

import java.io.*;
import java.util.*;

import static gen.Config.*;


/**
 * Created by jimmy on 2017/3/7.
 */
public class CustomJavaGenerator extends JavaGenerator {

    private static final JooqLogger log = JooqLogger.getLogger(CustomJavaGenerator.class);

    protected void generateTable(TableDefinition table, JavaWriter out) {
        super.generateTable(table, out);
        generatePojo0(table, out);
        try {
            generateRepository(table);
            generateService(table);
           // generateAdminController(table);
           // generateRepositoryTest(table);
            //generateServiceTest(table);
//            generateControllerTest(table);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void generateRepository(TableDefinition table) throws Exception {
        String className = createClassName(table, GenType.repository);
        String dir = getTargetDirectory();
        String packageName = createPackeageName(table, GenType.repository);
        String pkg = packageName.replaceAll("\\.", "/");
        String fileName = className;
        File file = new File(dir + "/" + pkg, fileName + ".java");

        if (file.exists()) {
//            String javaCode = FileUtils.readFileToString(file);
//            JavaUnit unit = Roaster.parseUnit(javaCode);
//            JavaClassSource myClass = unit.getGoverningType();
//            //分析java method
//            List<MethodSource<JavaClassSource>> methods = myClass.getMethods();
//            Set set = methods.stream()
//                             .filter(method -> method.isPublic())
//                             .map(method -> method.getName())
//                             .collect(Collectors.toSet());
//
//            for (TypedElementDefinition<?> column : getTypedElements(table)) {
//                String javaMemberName = getStrategy().getJavaMemberName(column, GeneratorStrategy.Mode.POJO);
//                String methodName = "findBy" + StringUtils.toCamelCase(javaMemberName);
//                if (!set.contains(methodName)) {
//
//                }
//            }

        } else {
//            JavaClassSource javaClass = Roaster.create(JavaClassSource.class);
            JavaWriter out = createJavaWriter(className, dir, packageName);
            if (out == null) {
                return;
            }
            String superClass = out.ref(Config.COM_CHANGFU_RISK_REPOSITORY_ABSTRACT_CRUDREPOSITORY);
//
            String recodeClassName = getStrategy().getJavaPackageName(table, GeneratorStrategy.Mode.RECORD) + "." + getStrategy()
                    .getJavaClassName(table, GeneratorStrategy.Mode.RECORD);
            String pojoClassName = getStrategy().getJavaPackageName(table, GeneratorStrategy.Mode.POJO) + "." + getStrategy()
                    .getJavaClassName(table, GeneratorStrategy.Mode.POJO);
            out.println("package %s;", packageName);
            out.println();
            //// TODO: 2017/8/23  Import static
            out.printImports();
            out.print("import static " + Config.PACK_NAME + "domain.Tables.*;");
            out.println();
            printClassJavadoc(out, table);
            String slf4jLog = out.ref("lombok.extern.slf4j.Slf4j");
            out.println("@%s", slf4jLog);
            out.println("@%s", out.ref("org.springframework.stereotype.Repository"));
            String pkClass = "Long";
            //// TODO: 2017/8/22 pk
            out.println("public class %s extends %s<%s, %s, " + "%s> {", className, superClass, out.ref(recodeClassName),
                    pkClass, out.ref(pojoClassName));
            out.println();
            out.tab(1).println("@%s", out.ref("org.springframework.beans.factory.annotation.Autowired"));
            out.tab(1).println("public %s(%s dslContext) {", className, out.ref("org.jooq.DSLContext"));
            String tableName = table.getName().toUpperCase();
            out.tab(2).println("super(dslContext, %s, %s.ID, %s.class);", tableName, tableName, out.ref(pojoClassName));
            out.tab(1).println("}");
            //分页
            out.println("}");
            closeJavaWriter(out);

        }


//
    }


    public String getTestTargetDirectory() {
        return "src/test/java";
    }


    private void generateRepositoryTest(TableDefinition table) {
        String serviceClassName = createClassName(table, GenType.repository_test);
        String dir = getTestTargetDirectory();
        String packageName = createPackeageName(table, GenType.repository_test);

        String pkg = packageName.replaceAll("\\.", "/");
        String fileName = serviceClassName;
        File file = new File(dir + "/" + pkg, fileName + ".java");
        if (file.exists()) {

            return;
        }

        JavaWriter out = createJavaWriter(serviceClassName, dir, packageName);
        if (out == null) {
            return;
        }
        String repositoryClassName = out.ref(createPackeageName(table, GenType.repository) + "." + createClassName(table,
                GenType.repository));
        String repositoryClassNameInstance = StringUtils.toCamelCaseLC(repositoryClassName);
        String slf4jLog = out.ref("lombok.extern.slf4j.Slf4j");
        out.ref("cn.gshkb.jooq.JooqApplicationTests");
        out.println("package %s;", packageName);
        out.println();
        out.printImports();
        out.println();
        printClassJavadoc(out, table);
        out.println("@%s", slf4jLog);
        out.println("public class %s extends  JooqApplicationTests {", serviceClassName);
        out.println();
        out.tab(1).println("@%s", out.ref("org.springframework.beans.factory.annotation.Autowired"));
        out.tab(1).println("private %s %s;", repositoryClassName, repositoryClassNameInstance);
        //生成分页，查询方式


        out.println("}");
        closeJavaWriter(out);
    }

    private void generateServiceTest(TableDefinition table) {
        String serviceClassName = createClassName(table, GenType.service_test);
        String dir = getTestTargetDirectory();
        String packageName = createPackeageName(table, GenType.service_test);


        String pkg = packageName.replaceAll("\\.", "/");
        String fileName = serviceClassName;
        File file = new File(dir + "/" + pkg, fileName + ".java");
        if (file.exists()) {
            return;
        }

        JavaWriter out = createJavaWriter(serviceClassName, dir, packageName);
        if (out == null) {
            return;
        }
        String repositoryClassName = out.ref(createPackeageName(table, GenType.service) + "." + createClassName(table,
                GenType.service));
        String repositoryClassNameInstance = StringUtils.toCamelCaseLC(repositoryClassName);
        String slf4jLog = out.ref("lombok.extern.slf4j.Slf4j");
        out.ref("cn.gshkb.jooq.JooqApplicationTests");
        out.println("package %s;", packageName);
        out.println();
        out.printImports();
        out.println();
        printClassJavadoc(out, table);
        out.println("@%s", slf4jLog);
        out.println("public class %s  extends  JooqApplicationTests {", serviceClassName);
        out.println();
        out.tab(1).println("@%s", out.ref("org.springframework.beans.factory.annotation.Autowired"));
        out.tab(1).println("private %s %s;", repositoryClassName, repositoryClassNameInstance);
        //生成分页，查询方式


        out.println("}");
        closeJavaWriter(out);
    }

    private void generateControllerTest(TableDefinition table) {
        String serviceClassName = createClassName(table, GenType.controller_test);
        String dir = getTestTargetDirectory();
        String packageName = createPackeageName(table, GenType.controller_test);
        String pkg = packageName.replaceAll("\\.", "/");
        String fileName = serviceClassName;
        File file = new File(dir + "/" + pkg, fileName + ".java");
        if (file.exists()) {
            return;
        }


        JavaWriter out = createJavaWriter(serviceClassName, dir, packageName);
        if (out == null) {
            return;
        }
        String repositoryClassName = out.ref(createPackeageName(table, GenType.controller_test) + "." + createClassName(table,
                GenType.controller_test));
        String repositoryClassNameInstance = StringUtils.toCamelCaseLC(repositoryClassName);
        String slf4jLog = out.ref("lombok.extern.slf4j.Slf4j");
        out.println("package %s;", packageName);
        out.println();
        out.printImports();
        out.println();
        printClassJavadoc(out, table);
        out.println("@%s", slf4jLog);
        out.println("public class %s  extends  JooqApplicationTests {", serviceClassName);
        out.println();
        out.tab(1).println("@%s", out.ref("org.springframework.beans.factory.annotation.Autowired"));
        out.tab(1).println("private %s %s;", repositoryClassName, repositoryClassNameInstance);
        //生成分页，查询方式
        out.println("}");
        closeJavaWriter(out);
    }

    private void generateService(TableDefinition table) {

        String serviceClassName = createClassName(table, GenType.service);
        String dir = getTargetDirectory();
        String packageName = createPackeageName(table, GenType.service);
        String pkg = packageName.replaceAll("\\.", "/");
        String fileName = serviceClassName;
        File file = new File(dir + "/" + pkg, fileName + ".java");
        if (file.exists()) {
            log.info("file is exits");
            return;
        }

        JavaWriter out = createJavaWriter(serviceClassName, dir, packageName);
        if (out == null) {
            return;
        }
        String repositoryClassName = out.ref(createPackeageName(table, GenType.repository) + "." + createClassName(table,
                GenType.repository));
        String repositoryClassNameInstance = repositoryClassName.substring(0, 1).toLowerCase() + repositoryClassName
                .substring(1);
        String pojoClassName = getStrategy().getJavaPackageName(table, GeneratorStrategy.Mode.POJO) + "." + getStrategy()
                .getJavaClassName(table, GeneratorStrategy.Mode.POJO);
        String tableName = table.getName().toUpperCase();
        String slf4jLog = out.ref("lombok.extern.slf4j.Slf4j");
        String service = out.ref("org.springframework.stereotype.Service");
        out.ref("org.springframework.data.domain.Page");
        out.ref("org.jooq.Condition");
        out.ref("org.springframework.data.domain.Pageable");
        out.ref("java.util.List");
        out.ref("java.util.Collection");

        out.println("package %s;", packageName);
        out.println();
        out.printImports();
        out.println();
        out.print("import static " + Config.PACK_NAME + "domain.Tables.*;");
        printClassJavadoc(out, table);
        out.println("@%s", slf4jLog);
        out.println("@%s", service);
        out.println("public class %s  {", serviceClassName);
        out.println();
        out.tab(1).println("@%s", out.ref("org.springframework.beans.factory.annotation.Autowired"));
        out.tab(1).println("private %s %s;", repositoryClassName, repositoryClassNameInstance);
        out.println();

        out.println();
        out.println();
        //分页
        out.tab(1).println(" public Page<%s> findbyPageAndCondition(Pageable pageable, Collection<? extends " +
                "Condition> condition) {", out.ref(pojoClassName));
        out.tab(2).println(" return  %s.findByPageable(pageable, condition);", repositoryClassNameInstance);
        out.println("}");
        out.println();
        out.println();
        //根据ID查找单个对象
        out.tab(1).println("public %s findById(Long id){", out.ref(pojoClassName));
        out.tab(2).println("return %s.fetchOne(%s.ID,id);", repositoryClassNameInstance, tableName);
        out.tab(1).println("}");

        //新增
        out.tab(1).println("public %s insert(%s pojo){", out.ref(pojoClassName), out.ref(pojoClassName));
        out.tab(2).println("pojo.setCreateAt(DateTools.getCurrentDateTime());");
        out.tab(2).println("pojo.setDelFlag(false);");
        out.tab(2).println("pojo = %s.insert(pojo);", repositoryClassNameInstance);
        out.tab(2).println("log.info(\"新增%s成功。{}。\", pojo.toString());", table.getComment());
        out.tab(2).println("return pojo;");
        out.tab(1).println("}");

        //修改
        out.tab(1).println("public %s update(%s pojo){", out.ref(pojoClassName), out.ref(pojoClassName));
        out.tab(2).println("%s dbPojo = this.findById(pojo.getId());", out.ref(pojoClassName));
        out.tab(2).println("if(dbPojo==null){");
        out.tab(2).println(" return null;}");
       /* out.tab(2)
           .println("%s.copyPropertiesIgnoreNull(pojo, dbPojo);", out.ref("com.xuebei.tec.common.util.BeanUtil"));*/
        out.tab(2).println("dbPojo.setUpdateAt(DateTools.getCurrentDateTime());", repositoryClassNameInstance);
        out.tab(2).println("%s.update(dbPojo);", repositoryClassNameInstance);
        out.tab(2).println("log.info(\"修改%s成功。{}。\", dbPojo.toString());", table.getComment());
        out.tab(2).println("return dbPojo;");
        out.tab(1).println("}");

        //删除
        out.tab(1).println("public void delete(Long id){", out.ref(pojoClassName), out.ref(pojoClassName));
        out.tab(2).println("%s dbPojo = this.findById(id);", out.ref(pojoClassName));
        out.tab(2).println("dbPojo.setUpdateAt(DateTools.getCurrentDateTime());");
        out.tab(2).println("dbPojo.setDelFlag(true);", repositoryClassNameInstance);
        out.tab(2).println("%s.update(dbPojo);", repositoryClassNameInstance);
        out.tab(2).println("log.info(\"删除%s成功。{}。\", dbPojo.toString());", table.getComment());
        out.tab(1).println("}");

        //TODO 生成其他查询方法

        out.println("}");
        closeJavaWriter(out);

    }

    private void generateAdminController(TableDefinition table) {
        String modelName = StringUtils.toCamelCaseLC(table.getOutputName()
                                                          .replace(' ', '_')
                                                          .replace('-', '_')
                                                          .replace('.', '_'));
       /* if (table.getName().contains(Config.FD_TABLE) || table.getName().contains(Config.MX_TABLE)) {
            return;
        }*/
        String controllerClassName = createClassName(table, GenType.controller);
        String dir = getTargetDirectory();
        String packageName = createPackeageName(table, GenType.controller);
        String pkg = packageName.replaceAll("\\.", "/");
        String fileName = controllerClassName;
        File file = new File(dir + "/" + pkg, fileName + ".java");
        if (file.exists()) {
            log.info("file is exits");
            return;
        }
        JavaWriter out = createJavaWriter(controllerClassName, dir, packageName);
        if (out == null) {
            return;
        }
        String pojoClassName = getStrategy().getJavaPackageName(table, GeneratorStrategy.Mode.POJO) + "." + getStrategy()
                .getJavaClassName(table, GeneratorStrategy.Mode.POJO);
        String serviceClassName = out.ref(createPackeageName(table, GenType.service) + "." + createClassName(table,
                GenType.service));
        String serviceClassNameInstance = serviceClassName.substring(0, 1).toLowerCase() + serviceClassName
                .substring(1);
        String slf4jLog = out.ref("lombok.extern.slf4j.Slf4j");
        String restController = out.ref("org.springframework.web.bind.annotation.RestController");
        String api = out.ref("io.swagger.annotations.Api");
        out.ref("io.swagger.annotations.ApiOperation");
        String mapping = out.ref("org.springframework.web.bind.annotation.RequestMapping");

        out.ref("org.springframework.data.domain.Page");
        out.ref("org.jooq.Condition ");
        out.ref("org.springframework.data.domain.Pageable");
        out.ref("java.util.List");
        out.ref("java.util.Collection");
        out.ref("javax.validation.Valid");
        out.ref("org.apache.shiro.authz.annotation.RequiresPermissions");
        out.ref("org.springframework.beans.factory.annotation.Autowired");
        out.ref("org.springframework.data.domain.Sort");
        out.ref("org.springframework.data.web.PageableDefault");
        out.ref("org.springframework.stereotype.Controller");
        out.ref("org.springframework.web.bind.annotation.RequestMethod");
        out.ref("org.springframework.web.bind.annotation.RestController");
        out.ref("org.springframework.web.bind.annotation.ResponseBody");
        out.ref("org.springframework.web.bind.annotation.PathVariable");
        out.ref("org.springframework.web.bind.annotation.RequestBody");
        out.ref("org.springframework.web.bind.annotation.RequestBody");
        out.ref("com.google.common.collect.Lists");


        out.println("package %s;", packageName);
        out.println();
        out.printImports();
        out.println();
        printClassJavadoc(out, table);
        out.println("@%s", slf4jLog);
        out.println("@%s", restController);
        out.println("@%s(\"/%s\")", mapping, modelName);
        out.println("@%s(value = \"%s\", description = \"%s\")", api, modelName, table.getComment());
        out.println("public class %s  {", controllerClassName);
        out.println();
        out.tab(1).println("@%s", out.ref("org.springframework.beans.factory.annotation.Autowired"));
        out.tab(1).println("private %s %s;", serviceClassName, serviceClassNameInstance);

        out.println();
        out.println();
        out.println();

        //分页
        out.tab(1).println("@ApiOperation(value = \"%s列表\", notes = \"对应权限项:%s:indexPage, " +
                "url后跟&page=0&size=10&sort=排序字段,desc/asc\")", table.getComment(), modelName);
        out.tab(1).println("@RequestMapping(value = \"indexPage\", method = RequestMethod.GET)");
        out.tab(1).println("@RequiresPermissions(\"%s:indexPage\")", modelName);
        out.tab(1).println("@ResponseBody");
        out.tab(1)
           .println("public ResultDo<Page> indexPage(@PageableDefault(sort = \"id\", direction = Sort.Direction.DESC) Pageable pageable) {");
        out.tab(2).println("ResultDo<Page> resultDo = ResultDo.build();");
        out.tab(2).println("Collection<Condition> collections = Lists.newArrayList();");
        out.tab(2)
           .println("Page<%s> page =%s.findbyPageAndCondition(pageable, collections);", out.ref(pojoClassName), serviceClassNameInstance);
        out.tab(2).println("resultDo.setResult(page);");
        out.tab(2).println("return resultDo;");
        out.tab(1).println("}");
        out.println();
        out.println();

        //根据ID查找单个对象
        out.tab(1).println("@ApiOperation(value = \"%s查看\", notes = \"对应权限项:%s:view\")", table.getComment(), modelName);
        out.tab(1).println("@RequestMapping(value = \"view/{id}\", method = RequestMethod.GET)");
        out.tab(1).println("@RequiresPermissions(\"%s:view\")", modelName);
        out.tab(1).println("@ResponseBody");
        out.tab(1).println("public ResultDo<%s> view(@PathVariable Long id){", out.ref(pojoClassName));
        out.tab(2).println("ResultDo<%s> resultDo = ResultDo.build();", out.ref(pojoClassName));
        out.tab(2).println("%s pojo = %s.findById(id);", out.ref(pojoClassName), serviceClassNameInstance);

        out.tab(2).println("if(pojo == null){");
        out.tab(2).println(" resultDo.setErrorCode(MessCodeConstant.DATA_NOT_ERROR);");
        out.tab(2).println("}else{");
        out.tab(2).println(" resultDo.setResult(pojo);");
        out.tab(2).println("}");
        out.tab(2).println("return resultDo;");
        out.tab(1).println("}");
        out.println();
        out.println();
        //新增
        out.tab(1)
           .println("@ApiOperation(value = \"%s新增\", notes = \"对应权限项:%s:create\")", table.getComment(), modelName);
        out.tab(1).println("@RequestMapping(value = \"create\", method = RequestMethod.POST)");
        out.tab(1).println("@RequiresPermissions(\"%s:create\")", modelName);
        out.tab(1).println("@LogInfoAnnotation(moduleName = \"%s管理\", desc = \"%s新增\")", table.getComment(), table
                .getComment());
        out.tab(1).println("@ResponseBody");
        out.tab(1)
           .println("public ResultDo<%s> create( @Valid @RequestBody %s pojo){", out.ref(pojoClassName), out.ref(pojoClassName));
        out.tab(2).println("ResultDo<%s> resultDo = ResultDo.build();", out.ref(pojoClassName));
        out.tab(2).println("pojo = %s.insert(pojo);", serviceClassNameInstance);
        out.tab(2).println("resultDo.setResult(pojo);");
        out.tab(2).println("return resultDo;");
        out.tab(1).println("}");
        out.println();
        out.println();
//
//        //修改
        out.tab(1)
           .println("@ApiOperation(value = \"%s修改\", notes = \"对应权限项:%s:update\")", table.getComment(), modelName);
        out.tab(1).println("@RequestMapping(value = \"update\", method = RequestMethod.POST)");
        out.tab(1).println("@RequiresPermissions(\"%s:update\")", modelName);
        out.tab(1).println("@LogInfoAnnotation(moduleName = \"%s管理\", desc = \"%s修改\")", table.getComment(), table
                .getComment());
        out.tab(1).println("@ResponseBody");
        out.tab(1)
           .println("public ResultDo<%s>  update(@Valid @RequestBody %s pojo){", out.ref(pojoClassName), out.ref(pojoClassName));
        out.tab(2).println("ResultDo<%s> resultDo = ResultDo.build();", out.ref(pojoClassName));
        out.tab(2).println("pojo = %s.update(pojo);", serviceClassNameInstance);
        out.tab(2).println("resultDo.setResult(pojo);");
        out.tab(2).println("return resultDo;");
        out.tab(1).println("}");
//
//        //删除
        out.tab(1).println("@ApiOperation(value = \"%s删除\", notes = \"对应权限项:%s:delete\")", table.getComment(),
                modelName);
        out.tab(1).println("@RequestMapping(value = \"delete/{id}\", method = RequestMethod.POST)");
        out.tab(1).println("@RequiresPermissions(\"%s:delete\")", modelName);
        out.tab(1).println("@LogInfoAnnotation(moduleName = \"%s管理\", desc = \"%s删除\")", table.getComment(), table
                .getComment());
        out.tab(1).println("@ResponseBody");
        out.tab(1).println("public ResultDo delete(@PathVariable Long id){");
        out.tab(2).println("ResultDo resultDo = ResultDo.build();");
        out.tab(2).println("%s.delete(id);", serviceClassNameInstance);
        out.tab(2).println("return resultDo;");
        out.tab(1).println("}");




        out.println("}");
        closeJavaWriter(out);
    }


    private String createClassName(TableDefinition table, GenType genType) {
        String modelName = StringUtils.toCamelCase(table.getOutputName()
                                                        .replace(' ', '_')
                                                        .replace('-', '_')
                                                        .replace('.', '_'));
        StringBuilder result = new StringBuilder();
        result.append(modelName);
        switch (genType) {
            case repository:
                result.append("Repository");
                break;
            case service:
                result.append("Service");
                break;
            case controller:
                result.append("Controller");
                break;
            case service_test:
                result.append("ServiceTest");
                break;
            case controller_test:
                result.append("ControllerTest");
                break;
            case repository_test:
                result.append("RepositoryTest");
                break;
            default:
                getStrategy().getJavaPackageName(table, GeneratorStrategy.Mode.DEFAULT);
        }
        return result.toString();
    }

    private String createPackeageName(TableDefinition table, GenType genType) {
        String result = Config.PACK_NAME;
        String webResult = Config.CONTROLLER_PACK_NAME;
        switch (genType) {
            case repository:
                result = result + "repository";
                break;
            case service:
                result = result + "service";
                break;
            case controller:
                result = webResult + "controller.admin";
                break;
            case service_test:
                result = result + "service";
                break;
            case controller_test:
                result = webResult + "controller.admin";
                break;
            case repository_test:
                result = result + "repository";
                break;
            default:
                result = getStrategy().getJavaPackageName(table, GeneratorStrategy.Mode.DEFAULT);
        }
        return result.toString();
    }


    enum GenType {
        service, controller, repository, service_test, controller_test, repository_test
    }


    private JavaWriter createJavaWriter(String className, String dir, String packageName) {
        String pkg = packageName.replaceAll("\\.", "/");
        String fileName = className;
        File file = new File(dir + "/" + pkg, fileName + ".java");
        if (file.exists()) {
            return null;
        }
        JavaWriter out = newJavaWriter(file);
        log.info("Generating   ", out.file().getName());
        return out;
    }

//
//    protected void generatePojo(TableDefinition table, JavaWriter out) {
//        generatePojo0(table, out);
//    }

    private final void generatePojo0(Definition tableOrUDT, JavaWriter out) {
        String className = getStrategy().getJavaClassName(tableOrUDT, GeneratorStrategy.Mode.POJO);
        String pkg = getStrategy().getJavaPackageName(tableOrUDT, GeneratorStrategy.Mode.POJO).replaceAll("\\.", "/");
        String fileName = className;
        String dir = getTargetDirectory();
        File file = new File(dir + "/" + pkg, fileName + ".java");
        if (file.exists()) {
            try {
                String                             javaCode = FileUtils.readFileToString(file);
                JavaUnit                           unit     = Roaster.parseUnit(javaCode);
                JavaClassSource                    myClass  = unit.getGoverningType();
                List<FieldSource<JavaClassSource>> fields   = myClass.getFields();
                Set<String>                        set      = buildDefaultSet();
                for (FieldSource fieldSource : fields) {
                    set.add(fieldSource.getName());
                }
                int maxLength = 0;
                for (TypedElementDefinition<?> column : getTypedElements(tableOrUDT)) {
                    maxLength = Math.max(maxLength, out.ref(getJavaType(column.getType(), GeneratorStrategy.Mode.POJO))
                                                       .length());
                }
                boolean isWrite = false;
                for (TypedElementDefinition<?> column : getTypedElements(tableOrUDT)) {
                    String javaMemberName = getStrategy().getJavaMemberName(column, GeneratorStrategy.Mode.POJO);
                    if (!set.contains(javaMemberName)) {
                        isWrite = true;
                        String ref = getJavaType(column.getType(), GeneratorStrategy.Mode.POJO);
                        String rightPad = StringUtils.rightPad(out.ref(getJavaType(column.getType(), GeneratorStrategy.Mode.POJO)),
                                maxLength);
                        log.debug("");
                        String annotation = " @ApiModelProperty(value = \"" + column.getComment() + "\")";
                        myClass.addImport(ref);
                        myClass.addField(annotation + " protected " + rightPad + " " + javaMemberName);
                    }
                }
                if (isWrite) {
                    FileUtils.writeStringToFile(file, myClass.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            createNewApiPojo(file, tableOrUDT, out, className);
        }

    }

    private void createNewApiPojo(File file, Definition tableOrUDT, JavaWriter out, String className) {


        final String    superName         = getStrategy().getJavaClassExtends(tableOrUDT, GeneratorStrategy.Mode.POJO);
        String          fullJavaClassName = getStrategy().getFullJavaClassName(tableOrUDT, GeneratorStrategy.Mode.POJO);
        JavaClassSource javaClass         = Roaster.create(JavaClassSource.class);
        javaClass.setPackage(COM_CHANGFU_RISK_POJO);
        javaClass.setName(className);
        javaClass.setPublic();
        javaClass.setSuperType(superName);
        /*swagger 和 lombok注解*/
       /* javaClass.addImport("io.swagger.annotations.ApiModel");
        javaClass.addImport("io.swagger.annotations.ApiModelProperty");
        javaClass.addImport("lombok.Data");
        javaClass.addImport("lombok.AllArgsConstructor");
        javaClass.addImport("lombok.NoArgsConstructor");
        javaClass.addImport("lombok.EqualsAndHashCode");
        String comment = escapeEntities(tableOrUDT.getComment());
        javaClass.addAnnotation(" io.swagger.annotations.ApiModel")
                 .setLiteralValue("value", "\"" + className + "\"")
                 .setLiteralValue("description", "\"" + comment + "\"");
        javaClass.addAnnotation("lombok.Data");
        javaClass.addAnnotation(" lombok.AllArgsConstructor");
        javaClass.addAnnotation(" lombok.NoArgsConstructor");
        javaClass.addAnnotation(" lombok.EqualsAndHashCode");*/
        int maxLength = 0;
        Set<String> set = buildDefaultSet();
        for (TypedElementDefinition<?> column : getTypedElements(tableOrUDT)) {
            maxLength = Math.max(maxLength, out.ref(getJavaType(column.getType(), GeneratorStrategy.Mode.POJO))
                                               .length());
        }
        for (TypedElementDefinition<?> column : getTypedElements(tableOrUDT)) {
            String javaMemberName = getStrategy().getJavaMemberName(column, GeneratorStrategy.Mode.POJO);
            if (!set.contains(javaMemberName)) {
                String ref = getJavaType(column.getType(), GeneratorStrategy.Mode.POJO);
                String rightPad = StringUtils.rightPad(out.ref(getJavaType(column.getType(), GeneratorStrategy.Mode.POJO)),
                        maxLength);
                String annotation = " @ApiModelProperty(value = \"" + column.getComment() + "\")";
                javaClass.addImport(ref);
                javaClass.addField(annotation + " protected " + rightPad + " " + javaMemberName);
            }
        }
        try {
            FileUtils.writeStringToFile(file, javaClass.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Set<String> buildDefaultSet() {
        Set<String> set = new HashSet<String>();
        set.add("createAt");
        set.add("updateAt");
        set.add("delFlag");
        set.add("version");
        return set;
    }

    private static final <T> List<T> list(T... objects) {
        List<T> result = new ArrayList<T>();

        if (objects != null)
            for (T object : objects) {
                if (object != null && !"".equals(object))
                    result.add(object);
            }

        return result;
    }

    private List<? extends TypedElementDefinition<? extends Definition>> getTypedElements(Definition definition) {
        if (definition instanceof TableDefinition) {
            return ((TableDefinition) definition).getColumns();
        } else if (definition instanceof UDTDefinition) {
            return ((UDTDefinition) definition).getAttributes();
        } else if (definition instanceof RoutineDefinition) {
            return ((RoutineDefinition) definition).getAllParameters();
        } else {
            throw new IllegalArgumentException("Unsupported type : " + definition);
        }
    }

    @SuppressWarnings("unchecked")
    private static final <T> List<T> list(T first, List<T> remaining) {
        List<T> result = new ArrayList<T>();

        result.addAll(list(first));
        result.addAll(remaining);

        return result;
    }

    private static final <T> List<T> first(Collection<T> objects) {
        List<T> result = new ArrayList<T>();

        if (objects != null) {
            for (T object : objects) {
                result.add(object);
                break;
            }
        }

        return result;
    }

    private static final <T> List<T> remaining(Collection<T> objects) {
        List<T> result = new ArrayList<T>();

        if (objects != null) {
            result.addAll(objects);

            if (result.size() > 0)
                result.remove(0);
        }

        return result;
    }


}
