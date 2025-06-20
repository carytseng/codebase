package cn.oj.codebase.file;

import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;

import java.io.*;

/**
 * @description 
 * @author 郑剑锋 
 * @updateTime 2022/1/14 11:18 上午
 */
public class FileDemo {

    private void demo1() throws IOException {
        InputStream initialStream = new FileInputStream(new File("src/main/resources/sample.txt"));
        byte[] buffer = new byte[initialStream.available()];
        initialStream.read(buffer);

        File targetFile = new File("src/main/resources/targetFile.tmp");
        OutputStream outStream = new FileOutputStream(targetFile);
        outStream.write(buffer);
    }

    private void demo2() throws IOException {
        InputStream initialStream = new FileInputStream(new File("src/main/resources/sample.txt"));
        byte[] buffer = new byte[initialStream.available()];
        initialStream.read(buffer);
        File targetFile = new File("src/main/resources/targetFile.tmp");
        Files.write(buffer, targetFile);
    }

    private void demo3() throws IOException {
        InputStream initialStream = FileUtils.openInputStream(new File("src/main/resources/sample.txt"));
        File targetFile = new File("src/main/resources/targetFile.tmp");
        FileUtils.copyInputStreamToFile(initialStream, targetFile);
    }

    private void demo4() throws IOException {
        File targetFile = new File("src/main/resources/targetFile.tmp");
        try (InputStream initialStream = new FileInputStream(new File("src/main/resources/sample.txt"));
             OutputStream outStream = new FileOutputStream(targetFile)) {
            byte[] buffer = new byte[8 * 1024];
            int bytesRead;
            while ((bytesRead = initialStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
