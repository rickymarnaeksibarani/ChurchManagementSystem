package ChurchManagementSystem.CMS.modules.storage;

import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Stream;

public interface StorageService {
    MinioClient initMinioClient();
    Stream<Path> loadAll();
    Path load(String path);
    Resource loadAsResource(String path);
    void deleteAllFileS3(List<String> path) throws IOException, NoSuchAlgorithmException, InvalidKeyException;
    ObjectWriteResponse storeToS3(String filename, MultipartFile file) throws IOException, NoSuchAlgorithmException, InvalidKeyException;
    InputStream getFileFromS3(String path) throws IOException, NoSuchAlgorithmException, InvalidKeyException;
}
