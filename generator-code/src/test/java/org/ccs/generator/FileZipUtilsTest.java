package org.ccs.generator;

import org.ccs.generator.util.FileZipUtils;
import org.junit.jupiter.api.Test;

public class FileZipUtilsTest {
    @Test
    void createZipFile(){
        String path="D:\\workspace\\jview\\generator\\generator-code\\target\\generated-sources\\dflsystem\\DflUser";
        FileZipUtils.createZipFile(path);
    }
}
