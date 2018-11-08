package com.marryou.metadata.service.ailiyun;


import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * oss服务类
 *
 * @author xph
 */
@Service
public class OSSService {

    @Value("${oss.access.id}")
    private String accessId;
    @Value("${oss.access.key}")
    private String accessKey;
    @Value("${oss.endpoint}")
    private String ossEndpoint;
    @Value("${oss.bucket.name}")
    private String bucketName;
    @Value("${oss.server.url}")
    private String serverUrl;

    private OSSClient client;

    /**
     * 上传图片,并删除本地文件
     *
     * @param key
     * @param url
     * @return
     * @throws IOException
     */
    public String uploadFileAndDeleteLocalFile(String key, String url) throws IOException {
        File file = new File(url);
        OSSClient client = getOSSClient();
        PutObjectResult result = client.putObject(bucketName, key, file);
        //删除本地文件
        if (file.exists()){
            file.delete();
        }
        return serverUrl + key;
    }

    /**
     * 上传图片
     *
     * @param key
     * @param file
     * @return
     * @throws IOException
     */
    public String uploadFile(String key, MultipartFile file) throws IOException {
        OSSClient client = getOSSClient();
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(file.getSize());
        objectMeta.setContentType(file.getContentType());
        objectMeta.setContentDisposition("inline");
        InputStream input = file.getInputStream();
        PutObjectResult result = client.putObject(bucketName, key, input, objectMeta);
        return serverUrl + key;
    }

    /**
     * 上传文件
     *
     * @param key
     * @param contentType
     * @param file
     * @return
     * @throws IOException
     */
    public String uploadFile(String key, String contentType, File file) throws IOException {
        OSSClient client = getOSSClient();
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(file.length());
        objectMeta.setContentType(contentType);
        objectMeta.setContentDisposition("inline");
        InputStream input = new FileInputStream(file);
        PutObjectResult result = client.putObject(bucketName, key, input, objectMeta);
        return serverUrl + key;
    }

    /**
     * 文件上传
     * @param key
     * @param contentType
     * @param length
     * @param inputStream
     * @return
     * @throws IOException
     */
    public String uploadFile(String key, String contentType, long length, InputStream inputStream) throws IOException {
        OSSClient client = getOSSClient();
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(length);
        objectMeta.setContentType(contentType);
        objectMeta.setContentDisposition("inline");
        PutObjectResult result = client.putObject(bucketName, key, inputStream, objectMeta);
        return serverUrl + key;
    }

    /**
     * 删除文件
     *
     * @return
     * @throws IOException
     */
    public void deleteFile(String url) throws IOException {
        String key = getKey(url);
        OSSClient client = getOSSClient();
        client.deleteObject(bucketName, key);
        return;
    }

    private OSSClient getOSSClient() {
        // 可以使用ClientConfiguration对象设置代理服务器、最大重试次数等参数。
        ClientConfiguration config = new ClientConfiguration();
        if (client == null) {
            client = new OSSClient(ossEndpoint, accessId, accessKey, config);
            // 创建bucket
            //client.createBucket(bucketName);
            // 设置bucket的访问权限，public-read-write权限
            //client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
        }
        return client;
    }

    private String getKey(String url) {
        if (!url.contains(serverUrl)) {
            return null;
        }
        return url.split(serverUrl)[1];
    }


}
