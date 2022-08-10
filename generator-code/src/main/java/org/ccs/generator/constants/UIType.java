package org.ccs.generator.constants;

/**
 * 支持代码生成的类型
 * @author chenjh
 */
public enum UIType {
    JQGRID("jqgrid", "jqgrid"),
    EASYUI("easyui", "easyui"),
    LAYUI("layui", "layui");
    private final String type;
    private final String directory;

    public static String[] uiTypes={JQGRID.type, EASYUI.type, LAYUI.type};

    UIType(String type, String directory) {
        this.type = type;
        this.directory = directory;
    }

    public String getType() {
        return type;
    }

    public String getDirectory() {
        return directory;
    }

    public static UIType parse(String type){
        UIType[] values=UIType.values();
        for(UIType uiType: values){
            if(uiType.getType().equals(type)){
                return uiType;
            }
        }
        return null;
    }

}
