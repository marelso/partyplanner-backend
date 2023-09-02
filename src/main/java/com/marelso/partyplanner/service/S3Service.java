package com.marelso.partyplanner.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private AmazonS3 s3client;

    @Value("${aws.endpointUrl}")
    private String endpointUrl;
    @Value("${aws.bucket}")
    private String bucket;
    @Value("${aws.accessKey}")
    private String accessKey;
    @Value("${aws.secretKey}")
    private String secretKey;

    public String upload(MultipartFile multipartFile) {
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            uploadFileTos3bucket(fileName, file);
            file.delete();
            return endpointUrl + "/" + bucket + "/" + fileName;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void delete(String fileUrl) {
        s3client.deleteObject(new DeleteObjectRequest(bucket + "/", fileUrl.substring(fileUrl.lastIndexOf("/") + 1)));
    }

    @PostConstruct
    private void initializeAmazon() {
        this.s3client = new AmazonS3Client(new BasicAWSCredentials(this.accessKey, this.secretKey));
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucket, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
    }
}