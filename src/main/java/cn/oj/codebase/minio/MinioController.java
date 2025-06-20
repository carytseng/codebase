package cn.oj.codebase.minio;

import io.minio.ObjectWriteResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * @author 郑剑锋
 * @version 1.0.0
 * @ClassName MinioController.java
 * @Description TODO
 * @createTime 2021年11月26日 16:34:00
 */
@RestController
@RequestMapping("/minio")
public class MinioController {

    /**
     * 上传本地文件
     */
    @GetMapping("/uploadLocalFile")
    public String uploadLocalFile() throws Exception{
        ObjectWriteResponse resp = MinioUtil.upload("test01", "E:\\测试文件.txt", getDatePath()+"test.txt");
        return "上传成功";
    }
    /**
     * 上传MultipartFile
     */
    @PostMapping("/uploadMultipartFile")
    public String uploadMultipartFile(MultipartFile file) throws Exception {
        ObjectWriteResponse resp = MinioUtil.upload("test01", file, getDatePath() + file.getOriginalFilename());
        return "上传成功";
    }

    private String getDatePath(){
        LocalDateTime now = LocalDateTime.now();
        return String.format("/%s/%s/%s/", now.getYear(), now.getMonthValue(), now.getDayOfMonth());
    }

    /**
     * 下载文件到本地
     */
    @PostMapping("/downLocal")
    public String downLocal() throws Exception {
        MinioUtil.downLocal("test01", "/2021/9/27/test.txt", "test.txt");
        return "下载成功";
    }

    /**
     * 下载文件写入HttpServletResponse
     */
    @GetMapping("/downResponse")
    public void downResponse(HttpServletResponse response) throws Exception {
        MinioUtil.downResponse("test01", "/2021/9/27/test.txt", response);
    }
}
