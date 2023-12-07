package com.example.backend.services;

import com.example.backend.config.FileUploadUtil;
import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.exceptions.CustomErrorMessage;
import com.example.backend.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;

@Service
public class UploadService {
    @Autowired
    private ProfileRepository profileRepository;

    public Optional<ResponseMessage> updateImage(int id, MultipartFile image) {
        String imageName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
        String uploadDir = "D:\\TuongDi\\LVTN\\Code\\backend\\uploads";
        String filePath = Paths.get(uploadDir, imageName).toString();
        FileUploadUtil.saveFile(uploadDir, imageName, image);

        String imageUrl = "http://localhost:8080/image/" + imageName;

        ResponseMessage message = new ResponseMessage();
        Optional<ResponseMessage> updateImageProfile;
        try {
            updateImageProfile = profileRepository.findById(id).map(update -> {
                update.setImage(imageUrl);
                profileRepository.save(update);
                message.setMessage("Update image profile successfully");
                return message;
            });
        } catch (Exception e) {
            throw new IllegalArgumentException(CustomErrorMessage.CAN_NOT_UPDATE_IMAGE);
        }
        return updateImageProfile;
    }

    public Optional<ResponseMessage> updateFile(int id, MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String uploadDir = "D:\\TuongDi\\LVTN\\Code\\backend\\uploads";
        FileUploadUtil.saveFile(uploadDir, fileName, file);

        String fileUrl = "http://localhost:8080/file/" + fileName;

        ResponseMessage message = new ResponseMessage();
        Optional<ResponseMessage> updateFileProfile;
        try {
            updateFileProfile = profileRepository.findById(id).map(update -> {
                update.setFile(fileUrl);
                profileRepository.save(update);
                message.setMessage("Update file profile successfully");
                return message;
            });
        } catch (Exception e) {
            throw new IllegalArgumentException(CustomErrorMessage.CAN_NOT_UPDATE_FILE);
        }
        return updateFileProfile;
    }
}
