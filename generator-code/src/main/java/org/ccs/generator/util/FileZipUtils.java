package org.ccs.generator.util;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
public class FileZipUtils {
    private FileZipUtils() {

    }


    /**
     * @param resourcesPath
     * @throws Exception
     */
    public static void createZipFile(String resourcesPath) {
        try {
            File fileToZip = new File(resourcesPath);
            if (fileToZip.getName().endsWith(".zip")) {
                fileToZip.deleteOnExit();
            }
            FileOutputStream outputStream = new FileOutputStream(fileToZip.getPath() + ".zip");
            CheckedOutputStream cos = new CheckedOutputStream(outputStream, new CRC32());
            ZipOutputStream out = new ZipOutputStream(cos, StandardCharsets.UTF_8);
            zipDirectory(fileToZip, "", out);

            out.flush();
            out.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void zipFilesDowload(String resourcesPath, HttpServletResponse response) {
        resourcesPath = resourcesPath.replaceAll("\\\\", "/");
        if (resourcesPath.endsWith("/")) {
            resourcesPath = resourcesPath.substring(0, resourcesPath.length() - 1);
        }
        String fileName = resourcesPath.substring(resourcesPath.lastIndexOf("/") + 1);
        try (
                ServletOutputStream out = response.getOutputStream();
                FileInputStream fis = new FileInputStream(resourcesPath + ".zip");
        ) {
            //下载压缩包
            response.setContentType("application/zip");

            log.info("----zipFilesDowload--resourcesPath={} fileName={}", resourcesPath, fileName);
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName + ".zip", "UTF-8"));

            int len = 0;
            byte[] buffer = new byte[1024];

            while ((len = fis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
                out.flush();
            }
            response.flushBuffer();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 原压缩方法，重复压缩之后，会出现问题，现优化如下<br>
     *
     * @param fileToZip
     * @param fileName
     * @param zipOut
     * @throws IOException
     */
    public static void zipDirectory(File fileToZip, String fileName, ZipOutputStream zipOut) {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            fileName = fileName.length() == 0 ? "" : fileName + "/";
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipDirectory(childFile, fileName + childFile.getName(), zipOut);
            }
            return;
        }
        try (FileInputStream fis = new FileInputStream(fileToZip);) {
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
