package com.example.airdns.global.awss3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class S3FileUtilTest {

    @Autowired
    private AmazonS3Client amazonS3Client;

    @Autowired
    private S3FileUtil s3FileUtil;

    @Test
    @DisplayName("S3 연결 테스트")
    void s3UploadTest() throws IOException {
        String fileName = "uploadTestImg";
        String contentType = "png";
        String filePath = "src/test/resources/uploadTestImg.png";
        FileInputStream fileInputStream = new FileInputStream(filePath);

        MultipartFile file = new MockMultipartFile(fileName, fileName + "." + contentType, contentType, fileInputStream);

        String fileUrl = s3FileUtil.uploadFile(file);
    }


}