package cn.oj.codebase.minio;

import io.minio.*;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author 郑剑锋
 * @version 1.0.0
 * @ClassName MinioUtil.java
 * @Description TODO
 * @createTime 2021年11月26日 16:25:00
 */
public class MinioUtil {

    private static MinioClient client;

    static {
        try {
            client = MinioClient.builder()
                    .endpoint(new URL("http://127.0.0.1:9000"))
                    .credentials("admin", "admin123")
                    .build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传本地文件
     */
    public static ObjectWriteResponse upload(String bucket, String localFileName, String remoteFileName) throws Exception{
        File file = new File(localFileName);
        FileInputStream fileInputStream = new FileInputStream(file);
        return client.putObject(PutObjectArgs.builder()
                .stream(fileInputStream, file.length(), PutObjectArgs.MIN_MULTIPART_SIZE)
                .object(remoteFileName)
                .bucket(bucket)
                .build());
    }

    /**
     * 上传MultipartFile
     */
    public static ObjectWriteResponse upload(String bucket, MultipartFile file, String remoteFileName) throws Exception {
        return client.putObject(PutObjectArgs.builder()
                .bucket(bucket)
                .stream(file.getInputStream(), file.getSize(), PutObjectArgs.MIN_MULTIPART_SIZE)
                .object(remoteFileName)
                .build());
    }

    /**
     * 下载文件到本地
     */
    public static void downLocal(String bucket, String remoteFileName, String localFileName) throws Exception {
        client.downloadObject(DownloadObjectArgs.builder()
                .bucket(bucket)
                .object(remoteFileName)
                .filename(localFileName)
                .build());
    }

    /**
     * 下载文件写入HttpServletResponse
     */
    public static void downResponse(String bucket, String remoteFileName, HttpServletResponse response) throws Exception {
        GetObjectResponse object = client.getObject(GetObjectArgs.builder()
                .bucket(bucket)
                .object(remoteFileName)
                .build());
        response.setHeader("Content-Disposition", "attachment;filename=" + remoteFileName.substring(remoteFileName.lastIndexOf("/")+1));
        response.setContentType("application/force-download");
        response.setCharacterEncoding("UTF-8");
        IOUtils.copy(object, response.getOutputStream());
    }



}

