package com.example.backend.services;

import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.DriverLicenseDuration;
import com.example.backend.exceptions.CustomErrorMessage;
import com.example.backend.repositories.DriverLicenseDurationRepository;
import com.example.backend.utils.NonNullPropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverLicenseDurationService {
    @Autowired
    private DriverLicenseDurationRepository driverLicenseDurationRepository;

    public DriverLicenseDuration createDriverLicenseDuration(DriverLicenseDuration driverLicenseDuration) {
        return driverLicenseDurationRepository.save(driverLicenseDuration);
    }

    public Optional<DriverLicenseDuration> updateDriverLicenseDuration(DriverLicenseDuration driverLicenseDuration) {
        return driverLicenseDurationRepository.findById(driverLicenseDuration.getId()).map(update -> {
            NonNullPropertiesUtils.copyNonNullProperties(driverLicenseDuration, update);
            return driverLicenseDurationRepository.save(update);
        });
    }

    public List<DriverLicenseDuration> DriverLicenseDurationList() {
        return driverLicenseDurationRepository.findAll();
    }

    public Optional<DriverLicenseDuration> findDriverLicenseDurationById(int id) {
        var result = driverLicenseDurationRepository.findById(id);
        if (result.isEmpty()){
            throw new IllegalArgumentException(CustomErrorMessage.NOT_FOUND_BY_ID);
        }
        return result;
    }

    public ResponseMessage deleteDriverLicenseDurationById(int id) {
        ResponseMessage message = new ResponseMessage();
        var isResult = driverLicenseDurationRepository.findById(id);
        if(isResult.isPresent()){
            driverLicenseDurationRepository.deleteById(id);
            message.setMessage("delete driver license duration by id successfully!");
        }else{
            message.setMessage("delete driver license duration by id failed!");
        }
        return message;
    }
}
