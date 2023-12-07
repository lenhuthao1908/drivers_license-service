package com.example.backend.services;

import com.example.backend.config.FileUploadUtil;
import com.example.backend.dto.request.ProfileCreateRequest;
import com.example.backend.dto.request.ProfileStatusUpdateRequest;
import com.example.backend.dto.request.ProfileUpdateRequest;
import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.*;
import com.example.backend.enums.ProfileStatus;
import com.example.backend.enums.ProfileType;
import com.example.backend.exceptions.CustomErrorMessage;
import com.example.backend.repositories.*;
import com.example.backend.utils.NonNullPropertiesUtils;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityNotFoundException;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private NationRepository nationRepository;
    @Autowired
    private ReligionRepository religionRepository;
    @Autowired
    private ExaminationRepository examinationRepository;

    @Autowired
    private DetailProfileRepository detailProfileRepository;
    @Autowired
    private EmailService emailService;



    public ResponseMessage createProfile(ProfileCreateRequest profileCreateRequest, MultipartFile image, MultipartFile file) {
        String imageName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String uploadDir = "D:\\TuongDi\\LVTN\\Code\\backend\\uploads";
        FileUploadUtil.saveFile(uploadDir, imageName, image);
        FileUploadUtil.saveFile(uploadDir, fileName, file);

        String imageUrl = "http://localhost:8080/image/" + imageName;
        String fileUrl =  "http://localhost:8080/file/" + fileName;

        Nation nation = nationRepository.findById(profileCreateRequest.getNationId())
                .orElseThrow(() -> new EntityNotFoundException("profileCreateRequest not found with ID: " + profileCreateRequest.getNationId()));

        Religion religion = religionRepository.findById(profileCreateRequest.getReligionId())
                .orElseThrow(() -> new EntityNotFoundException("profileCreateRequest not found with ID: " + profileCreateRequest.getReligionId()));

        Examinations examinations = examinationRepository.findById(profileCreateRequest.getExaminationsId())
                .orElseThrow(() -> new EntityNotFoundException("profileCreateRequest not found with ID: " + profileCreateRequest.getExaminationsId()));

        System.out.println(nation);
        System.out.println(religion);
        System.out.println(examinations);

        Profile profile = new Profile();
        profile.setEmail(profileCreateRequest.getEmail());
        profile.setName(profileCreateRequest.getName());
        profile.setDateofbirth(profileCreateRequest.getDateofbirth());
        profile.setSex(profileCreateRequest.getSex());
        profile.setIdcard(profileCreateRequest.getIdcard());
        profile.setPhone(profileCreateRequest.getPhone());
        profile.setImage(imageUrl);
        profile.setFile(fileUrl);
        profile.setNote(profileCreateRequest.getNote());
        profile.setNation(nation);
        profile.setReligion(religion);
        profile.setProvince(profileCreateRequest.getProvince());
        profile.setDistrict(profileCreateRequest.getDistrict());
        profile.setWards(profileCreateRequest.getWards());
        profile.setExaminations(examinations);
        if (examinations.getExaminationsQuantity() == 0) {
            throw new IllegalArgumentException(CustomErrorMessage.QUANTITY_PROFILE_REGISTER_FULL);
        } else {
            int i = examinations.getExaminationsQuantity() - 1;
            examinations.setExaminationsQuantity(i);
        }
        LocalDate date = LocalDate.now();
        Period period = Period.between(profile.getDateofbirth(), date);
        int age = period.getYears();
        ResponseMessage message = new ResponseMessage();

        for(Profile item : this.listProfile()) {
            System.out.println("profile:" + item.getIdcard());
            if (item.getIdcard().equals(profile.getIdcard())) {
                item.setType(ProfileType.REVERSE_PROFILE);
                profileRepository.save(item);
                emailService.sendReminderEmailsReverseProfile(item, date);
                message.setMessage("Reverse profile");
                return message;
            }
        }
        if (age >= 18) {
            emailService.sendReminderEmailsCreateProfile(profile, date);
            emailService.sendReminderEmails(profile);
            profileRepository.save(profile);
            message.setMessage("Register profile successfully!");
        } else {
            emailService.sendReminderEmailsNotCreateProfile(profile, date);
            message.setMessage("Can't register profile!");
        }

        return message;
    }


    public Optional<Profile> updateProfile(ProfileUpdateRequest profileUpdateRequest) {
        var retrievedProfile = profileRepository.findById(Integer.valueOf(profileUpdateRequest.getId()));
        if(Objects.isNull(retrievedProfile)) {
            throw new IllegalArgumentException(CustomErrorMessage.NOT_FOUND_BY_ID);
        }
        System.out.println(profileUpdateRequest);
        Nation nation = nationRepository.findById(profileUpdateRequest.getNationId()).orElse(null);
        Religion religion = religionRepository.findById(profileUpdateRequest.getReligionId()).orElse(null);
        Examinations examinations = examinationRepository.findById(profileUpdateRequest.getExaminationsId()).orElse(null);

        return profileRepository.findById(Integer.valueOf(profileUpdateRequest.getId())).map(profile -> {
            if (profileUpdateRequest.getEmail() != null)
                profile.setEmail(profileUpdateRequest.getEmail());
            if (profileUpdateRequest.getName() != null)
                profile.setName(profileUpdateRequest.getName());
            if (profileUpdateRequest.getDateofbirth() != null)
                profile.setDateofbirth(profileUpdateRequest.getDateofbirth());
            if (profileUpdateRequest.getSex() != null)
                profile.setSex(profileUpdateRequest.getSex());
            if (profileUpdateRequest.getIdcard() != null)
                profile.setIdcard(profileUpdateRequest.getIdcard());
            if (profileUpdateRequest.getPhone() != null)
                profile.setPhone(profileUpdateRequest.getPhone());
            if (profileUpdateRequest.getNote() != null)
                profile.setNote(profileUpdateRequest.getNote());
            if (profileUpdateRequest.getNationId() != 0)
                profile.setNation(nation);
            if (profileUpdateRequest.getReligionId() != 0)
                profile.setReligion(religion);
            if (profileUpdateRequest.getProvince() != null)
                profile.setProvince(profileUpdateRequest.getProvince());
            if (profileUpdateRequest.getDistrict() != null)
                profile.setDistrict(profileUpdateRequest.getDistrict());
            if (profileUpdateRequest.getWards() != null)
                profile.setWards(profileUpdateRequest.getWards());
            if (profileUpdateRequest.getExaminationsId() != 0)
                profile.setExaminations(examinations);
            if (profileUpdateRequest.getProfileStatus() != null)
                profile.setProfileStatus(ProfileStatus.valueOf(profileUpdateRequest.getProfileStatus()));
            return profileRepository.save(profile);
        });
    }

    public void updateStatus(int id, String profileStatus) {
        var retrievedProfile = profileRepository.findById(id).get();
        if(Objects.isNull(retrievedProfile)) {
            throw new IllegalArgumentException(CustomErrorMessage.NOT_FOUND_BY_ID);
        }
        System.out.println(retrievedProfile);
        retrievedProfile.setProfileStatus(ProfileStatus.valueOf(profileStatus));
        LocalDate date = LocalDate.now();
        if(profileStatus.equals(ProfileStatus.NOT_APPROVE.name())) {
            emailService.sendReminderEmailsNotApprove(retrievedProfile.getEmail(), date);
        } else if (profileStatus.equals(ProfileStatus.APPROVED.name())) {
            emailService.sendReminderEmailsApprove(retrievedProfile.getEmail(), date);
        }
        profileRepository.save(retrievedProfile);
    }

    public List<Profile> listProfile() {
        return profileRepository.findAll();
    }

    public Optional<Profile> findProfileById(int id) {
        var result = profileRepository.findById(id);
        if(result.isEmpty()){
            throw new IllegalArgumentException(CustomErrorMessage.NOT_FOUND_BY_ID);
        }
        return result;
    }

    public ResponseMessage deleteProfileById(int id) {
        ResponseMessage message = new ResponseMessage();
        var isResult = profileRepository.findById(id);
        try {
            String imageName = isResult.get().getImage().substring(isResult.get().getImage().lastIndexOf('/') + 1);
            Path imagePath = Paths.get("uploads", imageName);
            String fileName = isResult.get().getFile().substring(isResult.get().getFile().lastIndexOf('/') + 1);
            Path filePath = Paths.get("uploads", fileName);
            Files.deleteIfExists(imagePath);
            Files.deleteIfExists(filePath);
            profileRepository.deleteById(id);
            message.setMessage("delete religion by id successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            message.setMessage("delete religion by id failed!");
        }
        return message;
    }

    public List<Profile> listProfileByIdCard(String idCard) {
        return profileRepository.findAllByidcard(idCard);
    }
    public List<Profile> listProfileByName(String name) {
        return profileRepository.findAllByName(name);
    }

    public List<Profile> listProfileReversed() {
        List<Profile> profileList = new ArrayList<>();
        List<DetailProfile> detailProfileList = detailProfileRepository.findAll();

        for(DetailProfile detailProfile : detailProfileList) {
            if(detailProfile.getResult().equals("FAILED")) {
                profileList.add(detailProfile.getProfileId());
            }
        }
        return profileList;
    }
}
