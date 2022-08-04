package org.ccs.generator.constants;

/**
 * 支持代码生成的类型
 * @author chenjh
 */
public enum GeneratorType {
    JQGRID("ftl", "ftl"),
    EASYUI("easyui", "easyui");
    private final String code;
    private final String directory;

    GeneratorType(String code, String directory) {
        this.code = code;
        this.directory = directory;
    }

    public String getCode() {
        return code;
    }

    public String getDirectory() {
        return directory;
    }

}
