package cn.cary.codebase.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * @program: codebase
 * @description:
 * @author: 郑剑锋
 * @create: 2021-04-26 16:09
 **/
@Slf4j
public class LoadFile {

    public static void main(String[] args) throws IOException {
        InputStream inputStream = LoadFile.class.getClassLoader().getResourceAsStream("structuredSearch.json");
        String text = IOUtils.toString(inputStream,"utf8");
    }
}
