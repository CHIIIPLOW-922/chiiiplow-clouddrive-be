package com.chiiiplow.clouddrive.component;

import com.google.common.collect.Multimap;
import io.minio.CreateMultipartUploadResponse;
import io.minio.ListPartsResponse;
import io.minio.MinioAsyncClient;
import io.minio.ObjectWriteResponse;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.XmlParserException;
import io.minio.messages.Part;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CompletableFuture;

/**
 * 自定义 minio 异步客户端
 *
 * @author yangzhixiong
 * @date 2025/01/14
 */
public class CustomMinioAsyncClient extends MinioAsyncClient {


    public CustomMinioAsyncClient(MinioAsyncClient client) {
        super(client);
    }

    /**
     * 创建分段上传异步
     *
     * @param bucketName       存储桶名称
     * @param region           地区
     * @param objectName       对象名称
     * @param headers          头
     * @param extraQueryParams 额外查询参数
     * @return {@link CompletableFuture}<{@link CreateMultipartUploadResponse}>
     * @throws InsufficientDataException 数据不足异常
     * @throws InternalException         内部异常
     * @throws InvalidKeyException       无效密钥异常
     * @throws IOException               io异常
     * @throws NoSuchAlgorithmException  没有这样算法例外
     * @throws XmlParserException        XML 解析器异常
     */
    @Override
    public CompletableFuture<CreateMultipartUploadResponse> createMultipartUploadAsync(String bucketName, String region, String objectName, Multimap<String, String> headers, Multimap<String, String> extraQueryParams) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return super.createMultipartUploadAsync(bucketName, region, objectName, headers, extraQueryParams);
    }

    /**
     * 完成分段异步上传
     *
     * @param bucketName       存储桶名称
     * @param region           地区
     * @param objectName       对象名称
     * @param uploadId         上传 ID
     * @param parts            部件
     * @param extraHeaders     额外标头
     * @param extraQueryParams 额外查询参数
     * @return {@link CompletableFuture}<{@link ObjectWriteResponse}>
     * @throws InsufficientDataException 数据不足异常
     * @throws InternalException         内部异常
     * @throws InvalidKeyException       无效密钥异常
     * @throws IOException               io异常
     * @throws NoSuchAlgorithmException  没有这样算法例外
     * @throws XmlParserException        XML 解析器异常
     */
    @Override
    public CompletableFuture<ObjectWriteResponse> completeMultipartUploadAsync(String bucketName, String region, String objectName, String uploadId, Part[] parts, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return super.completeMultipartUploadAsync(bucketName, region, objectName, uploadId, parts, extraHeaders, extraQueryParams);
    }

    public CompletableFuture<ListPartsResponse> listMultipart(String bucketName, String region, String objectName, Integer maxParts, Integer partNumberMarker, String uploadId, Multimap<String, String> extraHeaders, Multimap<String, String> extraQueryParams) throws InsufficientDataException, InternalException, InvalidKeyException, IOException, NoSuchAlgorithmException, XmlParserException {
        return super.listPartsAsync(bucketName, region, objectName, maxParts, partNumberMarker, uploadId, extraHeaders, extraQueryParams);
    }
}
