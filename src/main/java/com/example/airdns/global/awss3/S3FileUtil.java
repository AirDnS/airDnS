package com.example.airdns.global.awss3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.example.airdns.global.exception.AWSCustomException;
import com.example.airdns.global.exception.GlobalExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class S3FileUtil {
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFile(MultipartFile file, String prefix) {
        try {
            String fileName = prefix + file.getOriginalFilename();

            ObjectMetadata metadata= new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);

            return amazonS3Client.getUrl(bucket, fileName).toString();
        } catch (IOException e) {
            throw new AWSCustomException(GlobalExceptionCode.AWS_S3_FILE_UPLOAD_FAIL);
        }
    }

    public String uploadFile(MultipartFile file) {
        return uploadFile(file, "");
    }

    public void removeFile(String fileUrl, String prefix) {
        String fileName = fileUrl.substring(fileUrl.indexOf(prefix));

        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
    }


}
