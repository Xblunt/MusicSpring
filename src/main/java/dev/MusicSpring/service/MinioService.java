package dev.MusicSpring.service;


import io.minio.*;
import io.minio.errors.MinioException;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class MinioService {

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;

//    public void uploadFile(MinioClient minioClient, String bucketName, String objectName, InputStream inputStream, long size, String contentType) {
//        try {
//            ObjectWriteResponse response = minioClient.putObject(PutObjectArgs.builder()
//                    .bucket(bucketName)
//                    .object(objectName)
//                    .stream(inputStream, size, -1)
//                    .contentType(contentType)
//                    .build());
//            System.out.println("File uploaded successfully. ETag: " + response.etag());
//        } catch (Exception e) {
//            System.err.println("Error uploading file to MinIO: " + e.getMessage());
//        }
//    }
//    public void updateFile(MinioClient minioClient, String bucketName, String objectName, InputStream inputStream, long size, String contentType) {
//        try {
//            minioClient.putObject(PutObjectArgs.builder()
//                    .bucket(bucketName)
//                    .object(objectName)
//                    .stream(inputStream, size, -1)
//                    .contentType(contentType)
//                    .build());
//            System.out.println("File updated successfully.");
//        } catch (Exception e) {
//            System.err.println("Error updating file in MinIO: " + e.getMessage());
//        }
//    }
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
//    public InputStream getFile(String bucketName, String objectName) throws IOException, InvalidKeyException, NoSuchAlgorithmException {
//        try {
//            return minioClient.getObject(GetObjectArgs.builder()
//                    .bucket(bucketName)
//                    .object(objectName)
//                    .build());
//        } catch (MinioException e) {
//            e.printStackTrace();
//            throw new IOException("Error while getting file from MinIO", e);
//        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }
//public List<FileDTO> getListObjects() {
//    List<FileDTO> objects = new ArrayList<>();
//    try {
//        Iterable<Result<Item>> result = minioClient.listObjects(ListObjectsArgs.builder()
//                .bucket(bucketName)
//                .recursive(true)
//                .build());
//        for (Result<Item> item : result) {
//            objects.add(FileDTO.builder()
//                    .filename(item.get().objectName())
//                    .size(item.get().size())
//                    .url(getPreSignedUrl(item.get().objectName()))
//                    .build());
//        }
//        return objects;
//    } catch (Exception e) {
//        log.error("Happened error when get list objects from minio: ", e);
//    }
//
//    return objects;
//}
//
//    public FileDTO uploadFile(FileDTO request) {
//        try {
//            minioClient.putObject(PutObjectArgs.builder()
//                    .bucket(bucketName)
//                    .object(request.getFile().getOriginalFilename())
//                    .stream(request.getFile().getInputStream(), request.getFile().getSize(), -1)
//                    .build());
//        } catch (Exception e) {
//            log.error("Happened error when upload file: ", e);
//        }
//        return FileDTO.builder()
//                .title(request.getTitle())
//                .description(request.getDescription())
//                .size(request.getFile().getSize())
//                .url(getPreSignedUrl(request.getFile().getOriginalFilename()))
//                .filename(request.getFile().getOriginalFilename())
//                .build();
//    }

//    public InputStream getObject(String filename) {
//        InputStream stream;
//        try {
//            stream = minioClient.getObject(GetObjectArgs.builder()
//                    .bucket(bucketName)
//                    .object(filename)
//                    .build());
//        } catch (Exception e) {
//            log.error("Happened error when get list objects from minio: ", e);
//            return null;
//        }
//
//        return stream;
//    }




//    private String getPreSignedUrl(String filename ) {
//        return "http://127.0.0.1:9000/".concat(filename);
//    }
    public String saveTrackToMinio(String trackBase64, String fileName, InputStream inputStream, String contentType) {
        try {
            byte[] trackBytes = Base64Utils.decodeFromString(trackBase64);
//            String fileName = "track_" + System.currentTimeMillis() + ".mp3";

            InputStream trackStream = new ByteArrayInputStream(trackBytes);

            // Upload the track to MinIO
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName + ".mp3")
                    .contentType(contentType)
                    .stream(trackStream, trackStream.available(), -1)
                    .build());

            System.out.println("File uploaded successfully to MinIO.");

            // Generate a MinIO URL for the uploaded track
            String minioUrl = "http://127.0.0.1:9000/" + bucketName + "/" + fileName + ".mp3";

            return minioUrl;
        } catch (IOException | InvalidKeyException | NoSuchAlgorithmException | MinioException e) {
            // Exception handling
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }
    }
    public String saveImgToMinio(String imgBse64, String fileName, InputStream inputStream, String contentType) {
        try {
            byte[] trackBytes = Base64Utils.decodeFromString(imgBse64);
//            String fileName = "img_" + System.currentTimeMillis() + ".jpg";

            InputStream trackStream = new ByteArrayInputStream(trackBytes);

            // Upload the track to MinIO
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName + ".jpg")
                    .contentType(contentType)
                    .stream(trackStream, trackStream.available(), -1)
                    .build());

            System.out.println("File uploaded successfully to MinIO.");

            // Generate a MinIO URL for the uploaded track
            String minioUrl = "http://127.0.0.1:9000/" + bucketName + "/" + fileName + ".jpg";

            return minioUrl;
        } catch (IOException | InvalidKeyException | NoSuchAlgorithmException | MinioException e) {
            // Exception handling
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
        } catch (IOException | InvalidKeyException | NoSuchAlgorithmException | MinioException e) {

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
        } catch (IOException | InvalidKeyException | NoSuchAlgorithmException | MinioException e) {

            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }
    }
}