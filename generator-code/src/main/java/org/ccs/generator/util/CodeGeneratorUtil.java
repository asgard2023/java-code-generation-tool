package org.ccs.generator.util;

import cn.hutool.core.io.FileUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import lombok.extern.slf4j.Slf4j;
import org.ccs.generator.bean.Config;
import org.ccs.generator.bean.Table;
import org.ccs.generator.config.ConfigLoader;
import org.ccs.generator.exceptions.DataNotExistException;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * 代码生成
 */
@Slf4j
public class CodeGeneratorUtil {
    private CodeGeneratorUtil() {

    }

    public static final String VERSION = "2.3.23";

    public static String generateCode(Config config) throws Exception {
        DBHelper dbHelper = (DBHelper) ConfigLoader.getBean("mySqlHelper");
        Table t = dbHelper.getTable(config.getTableName());
        if (t == null) {
            log.error("----generateCode--tableName={} unexist", config.getTableName());
            throw new DataNotExistException("table", "表:" + config.getTableName() + "，未找到");
        }
        t.setEntityName(config.getEntityName());
        t.setProject(config.getModelName());
        t.setModule(config.getPkg());
        t.setSimpleModule(t.getModule().substring(t.getModule().lastIndexOf(".") + 1));
        t.setAuthor(config.getAuthor());
        t.setEqualsSearchColumns(config.getEqualsSearchColumns());
        t.setLikeSearchColumns(config.getLikeSearchColumns());
        t.setCompany(config.getCompany());
        t.setCopyright(config.getCopyright());
        t.setHideGridColumns(config.getHideGridColumns());
        t.setSwagger(config.isSwagger());

        try {
            //外键关联
            t.setFKColumns(config.getFkColumns());
        } catch (Exception e) {
            log.warn("----generateCode--fKColumns={}", config.getFkColumns(), e);
        }

        try {
            //code转text
            t.setRenderColumns(config.getRenderColumns());
        } catch (Exception e) {
            log.warn("----generateCode--renderColumns={}", config.getRenderColumns(), e);
        }
        try {
            //下拉绑定
            t.setDictColumns(config.getDictColumns());
        } catch (Exception e) {
            log.warn("----generateCode--dictColumns={}", config.getDictColumns(), e);
        }
        log.info("----load table info ok");
        String path = gerenateCode(t, ConfigLoader.getClassPath() + "/generated-sources");
        log.info("Generated success:" + ConfigLoader.getClassPath() + "\\generated-sources");
        return path;
    }

    /**
     * 代码生成
     *
     * @param table
     * @param path
     */
    public static String gerenateCode(Table table, String path) {
        if (path.endsWith("/"))
            path = path.substring(0, path.length() - 1);
        String curPath = path + "/" + table.getProject() + "/" + table.getEntityName() + "/";

        genereateCode("biz.ftl", table, curPath + table.getEntityName() + "Biz.java");
        genereateCode("ibiz.ftl", table, curPath + "/I" + table.getEntityName() + "Biz.java");
        genereateCode("controller.ftl", table, curPath + table.getEntityName() + "Controller.java");
        genereateCode("mapper.ftl", table, curPath + table.getEntityName() + "Mapper.java");
        genereateCode("mapper_xml.ftl", table, curPath + table.getEntityName() + "-mapper.xml");
        genereateCode("po.ftl", table, curPath + "/" + table.getEntityName() + "Po.java");

        //easyui
        genereateCode("easyui/easyui_html.ftl", table, curPath + "/easyui/" + DbFieldTypeUtil.toLowerCaseFirstChar(table.getEntityName()) + ".html");
        genereateCode("easyui/easyui_js.ftl", table, curPath + "/easyui/" + DbFieldTypeUtil.toLowerCaseFirstChar(table.getEntityName()) + ".js");
        //jqgrid
        genereateCode("jqgrid/jqgrid_html.ftl", table, curPath + "/jqgrid/" + DbFieldTypeUtil.toLowerCaseFirstChar(table.getEntityName()) + ".html");
        genereateCode("jqgrid/jqgrid_js.ftl", table, curPath + "/jqgrid/" + DbFieldTypeUtil.toLowerCaseFirstChar(table.getEntityName()) + ".js");
        return curPath;

    }

    /**
     * 生成代码文件
     *
     * @param ftl        模板文件名
     * @param model
     * @param outputName 输出目标路径
     */
    public static void genereateCode(String ftl, Object model, String outputName) {
        Configuration cfg = new Configuration(new Version(VERSION));
        cfg.setClassForTemplateLoading(DbFieldTypeUtil.class, DbFieldTypeUtil.ROOT);
        cfg.setDefaultEncoding("UTF-8");

        File target = new File(outputName);
        FileUtil.del(outputName);
        File dir = new File(target.getParent());
        if (!dir.exists())
            dir.mkdirs();

        try (FileOutputStream fos = new FileOutputStream(target, true);
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             PrintWriter writer = new PrintWriter(bos, true);
        ) {
            Template template = cfg.getTemplate(ftl);
            template.process(model, writer);
        } catch (Exception ee) {
            log.error("---genereateCode--ftl={}", ftl, ee);
        }

    }
}
