package ChurchManagementSystem.CMS.core;

import ChurchManagementSystem.CMS.core.Exception.CustomRequestException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

@ControllerAdvice
public class BaseController {
    protected void isValidDocumentType(MultipartFile file) {
        if (!Objects.equals(file.getContentType(), "application/pdf") && !Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
            throw new CustomRequestException("Invalid document type. Supported types are PDF or DOCX.", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
    }

    protected void isValidApkType(MultipartFile file) {
        if (!Objects.equals(file.getContentType(), "application/vnd.android.package-archive")) {
            throw new CustomRequestException("Invalid document type. Supported types are Apk.", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
    }

    protected String generateNewFilename(String originalFilename) {
        // Generate a new filename, for example, using UUID
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        return UUID.randomUUID().toString() + extension;
    }

    //Process Entity List
    protected <T, R> List<R> processLongList(List<Long> idList, JpaRepository<T, Long> repository, Function<T, R> mapper, String entityName) {
        List<R> dataList = new ArrayList<>();
        for (Long entityId : idList) {
            Optional<T> findData = repository.findById(entityId);
            if (findData.isPresent()) {
                dataList.add(mapper.apply(findData.get()));
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, entityName + " with ID :" + entityId + " not found");
            }
        }
        return dataList;
    }
}
