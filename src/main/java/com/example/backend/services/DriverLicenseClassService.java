package com.example.backend.services;

import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.DriverLicenseClass;
import com.example.backend.exceptions.CustomErrorMessage;
import com.example.backend.repositories.DriverLicenseClassRepository;
import com.example.backend.utils.NonNullPropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class DriverLicenseClassService {
    @Autowired
    private DriverLicenseClassRepository driverLicenseClassRepository;

    public DriverLicenseClass createDriverLicenseClass(DriverLicenseClass driverLicenseClass) {
        return driverLicenseClassRepository.save(driverLicenseClass);
    }

    public Optional<DriverLicenseClass> updateDriverLicenseClass(DriverLicenseClass driverLicenseClass) {
        return driverLicenseClassRepository.findById(driverLicenseClass.getId()).map(update -> {
            NonNullPropertiesUtils.copyNonNullProperties(driverLicenseClass, update);
            return driverLicenseClassRepository.save(update);
        });
    }

    public List<DriverLicenseClass> DriverLicenseClassList() {
        return driverLicenseClassRepository.findAll();
    }

    public Optional<DriverLicenseClass> findDriverLicenseClassById(int id) {
        var result = driverLicenseClassRepository.findById(id);
        if (result.isEmpty()){
            throw new IllegalArgumentException(CustomErrorMessage.NOT_FOUND_BY_ID);
        }
        return result;
    }

    public ResponseMessage deleteDriverLicenseClassById(int id) {
        ResponseMessage message = new ResponseMessage();
        var isResult = driverLicenseClassRepository.findById(id);
        if(isResult.isPresent()){
            driverLicenseClassRepository.deleteById(id);
            message.setMessage("delete driver license class by id successfully!");
        }else{
            message.setMessage("delete driver license class by id failed!");
        }
        return message;
    }
}
