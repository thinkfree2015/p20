package com.efeiyi.parser.service.impl;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.CannedAccessControlList;
import com.aliyun.openservices.oss.model.ObjectMetadata;
import com.aliyun.openservices.oss.model.PutObjectResult;
import com.efeiyi.parser.service.AliOssUploadManager;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Created by Administrator on 2016/2/29.
 * 重写文件上传方法
 */
@Service
public class AliOssUploadManagerImpl implements AliOssUploadManager {

    private static String accessKeyId = "maTnALCpSvWjxyAy";
    private static String accessKeySecret = "0Ou6P67WhuSHESKrwJClFqCKo5BuBf";

    public AliOssUploadManagerImpl() {
    }

    @Override
    public Boolean uploadFile(InputStream content, String bucketName, String uploadName, Long size) throws Exception {
        OSSClient client = new OSSClient("http://oss-cn-beijing.aliyuncs.com", accessKeyId, accessKeySecret);
        boolean exists = client.doesBucketExist(bucketName);
        if(!exists) {
            client.createBucket(bucketName);
            client.setBucketAcl(bucketName, CannedAccessControlList.PublicReadWrite);
        }

        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentLength(size);
        PutObjectResult result = client.putObject(bucketName, uploadName, content, meta);
        System.out.println(result.getETag());
        return Boolean.valueOf(true);
    }
}
