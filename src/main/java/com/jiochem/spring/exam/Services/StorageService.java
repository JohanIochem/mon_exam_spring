package com.jiochem.spring.exam.Services;

import com.jiochem.spring.exam.Config.Uploadconfig;
import com.jiochem.spring.exam.Exception.WrongFileTypeException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class StorageService {
    private final Path rootLocation;
    private final String uploadFolder;
    private List<String> allowedImageExtension;

    public StorageService(Uploadconfig properties){
        this.rootLocation = Paths.get("src/main/resources/static/"+properties.getLocation());
        this.uploadFolder = properties.getLocation();
        this.allowedImageExtension = properties.getUploadImageTypes();

    }

    public String store(MultipartFile file) throws IOException, WrongFileTypeException {
        if (!this.allowedImageExtension.contains(file.getContentType())){
            throw  new WrongFileTypeException();
        }
        String filePath = UUID.randomUUID()+"-"+file.getOriginalFilename();
        Path destinationFile = this.rootLocation.resolve(
                        Paths.get(filePath))
                .normalize().toAbsolutePath();
        Files.copy(file.getInputStream(), destinationFile,
                StandardCopyOption.REPLACE_EXISTING);
        filePath= "/"+uploadFolder+"/"+filePath;

        return filePath;
    }
    public void init() throws IOException{
        Files.createDirectories(rootLocation);
    }
}
