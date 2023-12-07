package com.example.backend.services;

import com.example.backend.dto.request.DetailProfileCreateRequest;
import com.example.backend.dto.request.DetailProfileUpdateRequest;
import com.example.backend.dto.response.DetailProfileResponse;
import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.*;
import com.example.backend.exceptions.CustomErrorMessage;
import com.example.backend.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DetailProfileService {
    @Autowired
    private DetailProfileRepository detailProfileRepository;
    @Autowired
    private NationRepository nationRepository;
    @Autowired
    private ReligionRepository religionRepository;
    @Autowired
    private ExaminationRepository examinationRepository;
    @Autowired
    private OfficerRepository officerRepository;
    @Autowired
    private DriverLicenseClassRepository driverLicenseClassRepository;
    @Autowired
    private DriverLicenseDurationRepository driverLicenseDurationRepository;
    @Autowired
    private DetailExaminationsRepository detailExaminationsRepository;
    @Autowired
    private DriverLicenseRepository driverLicenseRepository;
    @Autowired
    private ProfileRepository profileRepository;



    public DetailProfile createDetailProfile(DetailProfileCreateRequest detailProfileCreateRequest) {
             Profile profile = profileRepository.findById(detailProfileCreateRequest.getProfileId())
                    .orElseThrow(() -> new EntityNotFoundException("detailProfileCreateRequest profile not found with ID: " + detailProfileCreateRequest.getProfileId()));


            DetailProfile detailProfile = new DetailProfile();
            detailProfile.setResultTheoretical(detailProfileCreateRequest.getResultTheoretical());
            detailProfile.setResultPractice(detailProfileCreateRequest.getResultPractice());
            detailProfile.setProfile(profile);

            if (detailProfile.getResultTheoretical() >= 21 ) {
                if (detailProfile.getResultPractice() >= 80) {
                    detailProfile.setResult("PASS");
                }
                else detailProfile.setResult("FAILED");
            }
            else detailProfile.setResult("FAILED");

            if (detailProfile.getResult().equals("FAILED")){
                detailProfile.setDriverLicenseId(0);
            }else {
                DriverLicense driverLicense = driverLicenseRepository.findById(detailProfileCreateRequest.getDriverLicenseId())
                        .orElseThrow(() -> new EntityNotFoundException("detailProfileCreateRequest driverLicense not found with ID: " + detailProfileCreateRequest.getDriverLicenseId()));

                detailProfile.setDriverLicenseId(driverLicense);
            }

            return detailProfileRepository.save(detailProfile);

    }

    public Optional<DetailProfile> updateDetailProfile(DetailProfileUpdateRequest detailProfileUpdateRequest) {
        Profile profile = profileRepository.findById(detailProfileUpdateRequest.getProfileId())
                .orElseThrow(() -> new EntityNotFoundException("Profile not found with ID: " + detailProfileUpdateRequest.getProfileId()));
        var result = detailProfileRepository.findById(detailProfileUpdateRequest.getId()).map(update -> {
            if (detailProfileUpdateRequest.getResultTheoretical() != null) {
                update.setResultTheoretical(detailProfileUpdateRequest.getResultTheoretical());
            }
            if (detailProfileUpdateRequest.getResultPractice() != null) {
                update.setResultPractice(detailProfileUpdateRequest.getResultPractice());
            }
            if (detailProfileUpdateRequest.getProfileId() != 0) {
                update.setProfileId(profile);
            }
            if (detailProfileUpdateRequest.getResultPractice() > 100 || detailProfileUpdateRequest.getResultPractice() < 0) {
                throw new IllegalArgumentException(CustomErrorMessage.VALUE_UPDATE_ILLEGAL);
            }
            if (detailProfileUpdateRequest.getResultTheoretical() > 100 || detailProfileUpdateRequest.getResultTheoretical() < 0) {
                throw new IllegalArgumentException(CustomErrorMessage.VALUE_UPDATE_ILLEGAL);
            }

            if (detailProfileUpdateRequest.getResultPractice() >= 80 && detailProfileUpdateRequest.getResultPractice() <= 100.0 &&
                    detailProfileUpdateRequest.getResultTheoretical() >= 21 && detailProfileUpdateRequest.getResultTheoretical() <= 25) {
                update.setResult("PASS");
            } else update.setResult("FAILED");
            if (detailProfileUpdateRequest.getDriverLicenseId() != 0 &&
                    detailProfileUpdateRequest.getResultPractice() >= 80 && detailProfileUpdateRequest.getResultPractice() <= 100.0 &&
                    detailProfileUpdateRequest.getResultTheoretical() >= 21 && detailProfileUpdateRequest.getResultTheoretical() <= 25) {
                DriverLicense driverLicense = driverLicenseRepository.findById(detailProfileUpdateRequest.getDriverLicenseId())
                        .orElseThrow(() -> new EntityNotFoundException("DriverLicense not found with ID: " + detailProfileUpdateRequest.getDriverLicenseId()));
                update.setDriverLicenseId(driverLicense);
            }
            else {
                update.setDriverLicenseId(null);
            }

            return detailProfileRepository.save(update);
        });
        return result;
    }



    public List<DetailProfileResponse> detailProfileResponseList() {
        return detailProfileRepository.findAll()
                .stream()
                .map(tmp -> {
                    DetailProfileResponse response = new DetailProfileResponse();
                    response.setId(tmp.getId());
                    response.setResultTheoretical(tmp.getResultTheoretical());
                    response.setResultPractice(tmp.getResultPractice());
                    response.setResult(tmp.getResult());
                    Profile profile = tmp.getProfileId();
                    if (profile != null) {
                        response.setEmail(profile.getEmail());
                        response.setName(profile.getName());
                        response.setSex(profile.getSex());
                        response.setIdcard(profile.getIdcard());
                        response.setPhone(profile.getPhone());
                        response.setImage(profile.getImage());
                        response.setNote(profile.getNote());
                        response.setProfileId(profile.getId());
                        Nation nation = tmp.getProfileId().getNationId();
                        if (nation != null) {
                            response.setNationName(nation.getNationName());
                        }
                        Religion religion = tmp.getProfileId().getReligionId();
                        if (religion != null) {
                            response.setReligionName(religion.getReligionName());
                        }
                        response.setProvince(profile.getProvince());
                        response.setDistrict(profile.getDistrict());
                        response.setWards(profile.getWards());
                        Examinations examinations = tmp.getProfileId().getExaminationsId();
                        if (examinations != null) {
                            response.setExaminationsName(examinations.getExaminationsName());
                            response.setDateExamination(examinations.getExaminationsDate());
                        }
                    }
                    DriverLicense driverLicense = tmp.getDriverLicenseId();
                    if (driverLicense != null) {
                        DriverLicenseClass aClass = tmp.getDriverLicenseId().getDriverLicenseClassId();
                        if (aClass != null) {
                            response.setDriverLicenseClass(aClass.getDriverLicenseClassName());
                        }
                        DriverLicenseDuration duration = tmp.getDriverLicenseId().getDriverLicenseDurationId();
                        if (duration != null) {
                            response.setDriverLicenseDuration(duration.getDuration());
                        }
                    }
                    return response;
                })
                .collect(Collectors.toList());
    }

    public Optional<DetailProfileResponse> findDetailProfile(int id) {
        var result = detailProfileRepository.findById(id)
                .map(tmp -> {
                    DetailProfileResponse response = new DetailProfileResponse();
                    response.setId(tmp.getId());
                    response.setResultTheoretical(tmp.getResultTheoretical());
                    response.setResultPractice(tmp.getResultPractice());
                    response.setResult(tmp.getResult());
                    Profile profile = tmp.getProfileId();
                    if (profile != null) {
                        response.setEmail(profile.getEmail());
                        response.setName(profile.getName());
                        response.setSex(profile.getSex());
                        response.setIdcard(profile.getIdcard());
                        response.setPhone(profile.getPhone());
                        response.setImage(profile.getImage());
                        response.setNote(profile.getNote());
                        response.setProfileId(profile.getId());
                        Nation nation = tmp.getProfileId().getNationId();
                        if (nation != null) {
                            response.setNationName(nation.getNationName());
                        }
                        Religion religion = tmp.getProfileId().getReligionId();
                        if (religion != null) {
                            response.setReligionName(religion.getReligionName());
                        }
                        response.setProvince(profile.getProvince());
                        response.setDistrict(profile.getDistrict());
                        response.setWards(profile.getWards());
                        Examinations examinations = tmp.getProfileId().getExaminationsId();
                        if (examinations != null) {
                            response.setExaminationsName(examinations.getExaminationsName());
                            response.setDateExamination(examinations.getExaminationsDate());
                        }
                    }
                    DriverLicense driverLicense = tmp.getDriverLicenseId();
                    if (driverLicense != null) {
                        DriverLicenseClass aClass = tmp.getDriverLicenseId().getDriverLicenseClassId();
                        if (aClass != null) {
                            response.setDriverLicenseClass(aClass.getDriverLicenseClassName());
                        }
                        DriverLicenseDuration duration = tmp.getDriverLicenseId().getDriverLicenseDurationId();
                        if (duration != null) {
                            response.setDriverLicenseDuration(duration.getDuration());
                        }
                    }
                    return response;
                });
        if (result.isEmpty()) {
            throw new IllegalArgumentException(CustomErrorMessage.NOT_FOUND_BY_ID);
        }
        return result;
    }

    public ResponseMessage deleteDetailProfileById(int id) {
        ResponseMessage message = new ResponseMessage();
        var isResult = detailProfileRepository.findById(id);
        if(isResult.isPresent()){
            detailProfileRepository.deleteById(id);
            message.setMessage("delete detail profile by id successfully!");
        }else{
            message.setMessage("delete detail profile by id failed!");
        }
        return message;
    }


}
