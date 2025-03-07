package com.xzc.lease.web.admin.service.impl;

import com.xzc.lease.common.minio.MinioProperties;
import com.xzc.lease.web.admin.service.FileService;
import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private MinioProperties properties;
    @Autowired
    private MinioClient client;

    @Override
    public String upload(MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        boolean bucketExists = client.bucketExists(BucketExistsArgs.builder().bucket(properties.getBucketName()).build());
        if (!bucketExists) {
            client.makeBucket(MakeBucketArgs.builder().bucket(properties.getBucketName()).build());
            client.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(properties.getBucketName()).config(createBucketPolicyConfig(properties.getBucketName())).build());
        }

        String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
        client.putObject(
                PutObjectArgs.builder() // 创建一个PutObjectArgs.Builder对象，用于构建上传文件的参数
                        .bucket(properties.getBucketName()) // 指定文件要上传到的存储桶名称
                        .object(filename) // 指定文件在存储桶中的唯一名称（即对象名称）
                        .stream(file.getInputStream(), file.getSize(), -1) // 指定文件的数据流、文件大小和分片大小
                        .contentType(file.getContentType()) // 指定文件的MIME类型
                        .build());// 构建PutObjectArgs对象

        return String.join("/", properties.getEndpoint(), properties.getBucketName(), filename);

    }

    private String createBucketPolicyConfig(String bucketName) {

        return """
                {
                  "Statement" : [ {
                    "Action" : "s3:GetObject",
                    "Effect" : "Allow",
                    "Principal" : "*",
                    "Resource" : "arn:aws:s3:::%s/*"
                  } ],
                  "Version" : "2012-10-17"
                }
                """.formatted(bucketName);
    }
}
