package dev.MusicSpring.service;


import io.minio.*;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@Slf4j
@Service
public class MinioService {
    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;


    public void deleteFile(String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName + ".jpg")
                    .build());
            System.out.println("File deleted successfully.");
        } catch (Exception e) {
            System.err.println("Error deleting file from MinIO: " + e.getMessage());
        }
    }
    public void deleteTrackFile(String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName + ".mp3")
                    .build());
            System.out.println("File deleted successfully.");
        } catch (Exception e) {
            System.err.println("Error deleting file from MinIO: " + e.getMessage());
        }
    }

    public String saveTrackToMinio(String trackBase64, String fileName, InputStream inputStream, String contentType) {
        try {
            byte[] trackBytes = Base64Utils.decodeFromString(trackBase64);
            InputStream trackStream = new ByteArrayInputStream(trackBytes);
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName + ".mp3")
                    .contentType(contentType)
                    .stream(trackStream, trackStream.available(), -1)
                    .build());

            System.out.println("File uploaded successfully to MinIO.");
            String minioUrl = "http://127.0.0.1:9000/" + bucketName + "/" + fileName + ".mp3";

            return minioUrl;
        }
        catch (IOException | InvalidKeyException | NoSuchAlgorithmException | MinioException e)
        {
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }
    }

    public String saveImgToMinio(String imgBse64, String fileName, InputStream inputStream, String contentType) {
        try {
            byte[] trackBytes = Base64Utils.decodeFromString(imgBse64);
            InputStream trackStream = new ByteArrayInputStream(trackBytes);
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName + ".jpg")
                    .contentType(contentType)
                    .stream(trackStream, trackStream.available(), -1)
                    .build());
            System.out.println("File uploaded successfully to MinIO.");
            String minioUrl = "http://127.0.0.1:9000/" + bucketName + "/" + fileName + ".jpg";

            return minioUrl;
        }
        catch (IOException | InvalidKeyException | NoSuchAlgorithmException | MinioException e)
        {
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }
    }

    public String updateTrackInMinio(String newTrackBase64, String existingFileName, String contentType) {
        try {
            byte[] newTrackBytes = Base64Utils.decodeFromString(newTrackBase64);
            InputStream newTrackStream = new ByteArrayInputStream(newTrackBytes);
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(existingFileName)
                    .contentType(contentType)
                    .stream(newTrackStream, newTrackStream.available(), -1)
                    .build());
            System.out.println("Successfully update.");
            String minioUrl = "http://127.0.0.1:9000/" + bucketName + "/" + existingFileName;

            return minioUrl;
        }
        catch (IOException | InvalidKeyException | NoSuchAlgorithmException | MinioException e)
        {
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }
    }

    public String updateImgMinio(String newTrackBase64, String existingFileName, String contentType) {
        try {
            byte[] newTrackBytes = Base64Utils.decodeFromString(newTrackBase64);
            InputStream newTrackStream = new ByteArrayInputStream(newTrackBytes);
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(existingFileName)
                    .contentType(contentType)
                    .stream(newTrackStream, newTrackStream.available(), -1)
                    .build());

            System.out.println("Successfully update.");
            String minioUrl = "http://127.0.0.1:9000/" + bucketName + "/" + existingFileName;

            return minioUrl;
        }
        catch (IOException | InvalidKeyException | NoSuchAlgorithmException | MinioException e)
        {
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }
    }

}