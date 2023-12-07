package com.example.backend.services;

import com.example.backend.dto.request.DriverLicenseCreateRequest;
import com.example.backend.dto.request.DriverLicenseUpdateRequest;
import com.example.backend.dto.response.DriverLicenseResponse;
import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.DriverLicense;
import com.example.backend.entities.DriverLicenseClass;
import com.example.backend.entities.DriverLicenseDuration;
import com.example.backend.entities.Officer;
import com.example.backend.exceptions.CustomErrorMessage;
import com.example.backend.repositories.DriverLicenseClassRepository;
import com.example.backend.repositories.DriverLicenseDurationRepository;
import com.example.backend.repositories.DriverLicenseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class DriverLicenseService {
    @Autowired
    private DriverLicenseRepository driverLicenseRepository;
    @Autowired
    private DriverLicenseClassRepository driverLicenseClassRepository ;
    @Autowired
    private DriverLicenseDurationRepository driverLicenseDurationRepository;

    public DriverLicense createDriverLicense(DriverLicenseCreateRequest driverLicenseCreateRequest) {
        DriverLicenseClass aClass = driverLicenseClassRepository.findById(driverLicenseCreateRequest.getDriverLicenseClassId())
                .orElseThrow(() -> new EntityNotFoundException("driverLicenseCreateRequest not found with ID: " + driverLicenseCreateRequest.getDriverLicenseClassId()));

        DriverLicenseDuration duration = driverLicenseDurationRepository.findById(driverLicenseCreateRequest.getDriverLicenseDurationId())
                .orElseThrow(() -> new EntityNotFoundException("driverLicenseCreateRequest not found with ID: " + driverLicenseCreateRequest.getDriverLicenseDurationId()));

        Random random = new Random();
        StringBuilder randomSequence = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            long randomNumber = random.nextInt(10);
            randomSequence.append(randomNumber);
        }
        Long code = Long.parseLong(randomSequence.toString());

        DriverLicense driverLicense = new DriverLicense();
        driverLicense.setLicenseDate(driverLicenseCreateRequest.getLicenseDate());
        driverLicense.setCode(code);
        driverLicense.setDriverLicenseClassId(aClass);
        driverLicense.setDriverLicenseDurationId(duration);
        return driverLicenseRepository.save(driverLicense);
    }

    public Optional<DriverLicense> updateDriverLicense(DriverLicenseUpdateRequest driverLicenseUpdateRequest) {
        DriverLicenseClass aClass = driverLicenseClassRepository.findById(driverLicenseUpdateRequest.getDriverLicenseClassId())
                .orElseThrow(() -> new EntityNotFoundException("driverLicenseUpdateRequest not found with ID: " + driverLicenseUpdateRequest.getDriverLicenseClassId()));

        DriverLicenseDuration duration = driverLicenseDurationRepository.findById(driverLicenseUpdateRequest.getDriverLicenseDurationId())
                .orElseThrow(() -> new EntityNotFoundException("driverLicenseUpdateRequest not found with ID: " + driverLicenseUpdateRequest.getDriverLicenseDurationId()));

        var result = driverLicenseRepository.findById(driverLicenseUpdateRequest.getId()).map(update -> {
            if (driverLicenseUpdateRequest.getLicenseDate() != null)
                update.setLicenseDate(driverLicenseUpdateRequest.getLicenseDate());
            if (driverLicenseUpdateRequest.getCode() != null)
                update.setCode(driverLicenseUpdateRequest.getCode());
            if (driverLicenseUpdateRequest.getDriverLicenseClassId() != 0)
                update.setDriverLicenseClassId(aClass);
            if (driverLicenseUpdateRequest.getDriverLicenseDurationId() != 0)
                update.setDriverLicenseDurationId(duration);
            return driverLicenseRepository.save(update);
        });
        if (result.isEmpty()) {
            throw new IllegalArgumentException(CustomErrorMessage.FAILED_UPDATE);
        }
        return result;
    }

    public List<DriverLicenseResponse> DriverLicenseList() {
        return driverLicenseRepository.findAll()
                .stream()
                .map(tmp -> {
                    DriverLicenseResponse driverLicenseResponse = new DriverLicenseResponse();
                    driverLicenseResponse.setId(tmp.getId());
                    driverLicenseResponse.setLicenseDate(tmp.getLicenseDate());
                    driverLicenseResponse.setCode(tmp.getCode());
                    DriverLicenseClass aClass = tmp.getDriverLicenseClassId();
                    if(aClass != null) {
                        driverLicenseResponse.setDriverLicenseClassName(aClass.getDriverLicenseClassName());

                    }
                    DriverLicenseDuration duration = tmp.getDriverLicenseDurationId();
                    if(duration != null) {
                        driverLicenseResponse.setDriverLicenseDurationName(duration.getDuration());
                    }
                    return driverLicenseResponse;
                })
                .collect(Collectors.toList());

    }

    public Optional<DriverLicenseResponse> findDriverLicenseById(int id) {
        var result = driverLicenseRepository.findById(id).map(driverLicense -> {
            String driverLicenseClassName = driverLicenseClassRepository.findById(driverLicense.getDriverLicenseClassId().getId()).map(DriverLicenseClass::getDriverLicenseClassName)
                    .orElse(null);

            String durationName = driverLicenseDurationRepository.findById(driverLicense.getDriverLicenseDurationId().getId()).map(DriverLicenseDuration::getDuration)
                    .orElse(null);

            DriverLicenseResponse driverLicenseResponse = new DriverLicenseResponse();
            driverLicenseResponse.setId(driverLicense.getId());
            driverLicenseResponse.setLicenseDate(driverLicense.getLicenseDate());
            driverLicenseResponse.setCode(driverLicense.getCode());
            driverLicenseResponse.setDriverLicenseClassName(driverLicenseClassName);
            driverLicenseResponse.setDriverLicenseDurationName(durationName);
            return driverLicenseResponse;
        });
        if (result.isEmpty()) {
            throw new IllegalArgumentException(CustomErrorMessage.NOT_FOUND_BY_ID);
        }
        return result;
    }


    public ResponseMessage deleteDriverLicenseClassById(int id) {
        ResponseMessage message = new ResponseMessage();
        var isResult = driverLicenseRepository.findById(id);
        if(isResult.isPresent()){
            driverLicenseRepository.deleteById(id);
            message.setMessage("delete driver license by id successfully!");
        }else{
            message.setMessage("delete driver license by id failed!");
        }
        return message;
    }
}
