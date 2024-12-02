//package ChurchManagementSystem.CMS.modules.storage;
//
//import io.minio.*;
//import io.minio.errors.MinioException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.MalformedURLException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.util.List;
//import java.util.stream.Stream;
//
//@Service
//@Slf4j
//public class StorageServiceImpl implements StorageService{
//    @Value("${minio.url}")
//    private String endpoint;
//    @Value("${minio.port}")
//    private Integer port;
//    @Value("${minio.access_key}")
//    private String accessKey;
//    @Value("${minio.secret-key}")
//    private String secretKey;
//    @Value("${minio.bucket}")
//    private String bucket;
//
//    @Override
//    public MinioClient initMinioClient(){
//        return MinioClient.builder()
//                .endpoint(endpoint, port, false)
//                .credentials(accessKey, secretKey)
//                .build();
//    }
//
//    @Override
//    public Stream<Path>loadAll(){ return null; };
//
//    @Override
//    public Path load(String path){return Paths.get(path);}
//
//    @Override
//    public Resource loadAsResource(String path) {
//        try {
//            Path file = load(path);
//            Resource urlResource = new UrlResource(file.toUri());
//
//            if (urlResource.exists() || urlResource.isReadable()) return urlResource;
//            else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not read file");
//        } catch (MalformedURLException e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not read file: " + e);
//        }
//    }
//
//    @Override
//    public void deleteAllFileS3(List<String> path) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
//
//    }
//
//    @Override
//    public ObjectWriteResponse storeToS3(String filename, MultipartFile file) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
//        try(InputStream inputStream = file.getInputStream()) {
//            MinioClient minioClient = initMinioClient();
//
//            // Make 'cms-project' bucket if not exist.
//            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
//
//            // Make a new bucket called 'cms-project'.
//            if (!exists) minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
//
//            return minioClient.putObject(
//                    PutObjectArgs.builder()
//                            .bucket(bucket)
//                            .object(filename.replace("\\", "/").trim())
//                            .stream(inputStream, file.getSize(), -1)
//                            .contentType(file.getContentType())
//                            .build()
//            );
//
//        } catch (MinioException e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed upload file to S3: " + e);
//        }
//    }
//
//    @Override
//    public InputStream getFileFromS3(String path) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
//        try {
//            MinioClient minioClient = initMinioClient();
//            return minioClient.getObject(
//                    GetObjectArgs.builder().bucket(bucket).object(path).build()
//            );
//        } catch (MinioException e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed get file from s3: " + e);
//        }
//    }
//
//
//}
