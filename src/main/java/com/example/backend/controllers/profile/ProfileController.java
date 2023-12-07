package com.example.backend.controllers.profile;

import com.example.backend.dto.request.ProfileCreateRequest;
import com.example.backend.dto.request.ProfileStatusUpdateRequest;
import com.example.backend.dto.request.ProfileUpdateRequest;
import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.Profile;
import com.example.backend.enums.ProfileStatus;
import com.example.backend.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService service;

    @PostMapping("")
    public ResponseEntity<ResponseMessage> createProfile(@RequestParam("email") String email,
                                        @RequestParam("name") String name,
                                        @RequestParam("dateofbirth") LocalDate dateofbirth,
                                        @RequestParam("sex") String sex,
                                        @RequestParam("idcard") String idcard,
                                        @RequestParam("phone") String phone,
                                        @RequestParam("image") MultipartFile image,
                                        @RequestParam("file") MultipartFile file,
                                        @RequestParam("note") String note,
                                        @RequestParam("nation_id") String nation_id,
                                        @RequestParam("religion_id") String religion_id,
                                        @RequestParam("province") String province,
                                        @RequestParam("district") String district,
                                        @RequestParam("wards") String wards,
                                        @RequestParam("examinations_id") String examinations_id ) {

        ProfileCreateRequest profileCreateRequest = new ProfileCreateRequest();
        profileCreateRequest.setEmail(email);
        profileCreateRequest.setName(name);
        profileCreateRequest.setDateofbirth(dateofbirth);
        profileCreateRequest.setSex(sex);
        profileCreateRequest.setIdcard(idcard);
        profileCreateRequest.setPhone(phone);
        profileCreateRequest.setNote(note);
        profileCreateRequest.setNationId(Integer.parseInt(nation_id));
        profileCreateRequest.setReligionId(Integer.parseInt(religion_id));
        profileCreateRequest.setProvince(province);
        profileCreateRequest.setDistrict(district);
        profileCreateRequest.setWards(wards);
        profileCreateRequest.setExaminationsId(Integer.parseInt(examinations_id));
            return new ResponseEntity<>(service.createProfile(profileCreateRequest, image, file), HttpStatus.CREATED);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<Optional<Profile>> updateProfile(@PathVariable(value = "id") String id, @RequestBody ProfileUpdateRequest profileUpdateRequest) {
        profileUpdateRequest.setId(id);
        System.out.println(profileUpdateRequest.getId());
        return ResponseEntity.ok(service.updateProfile(profileUpdateRequest));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable(value = "id") int id, @RequestBody ProfileStatusUpdateRequest profileStatusUpdateRequest) {
        String profileStatus = profileStatusUpdateRequest.getProfileStatus();
        service.updateStatus(id, profileStatus);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Profile>> getAllProfile() {
        return ResponseEntity.ok(service.listProfile());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Profile>> getProfileById(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(service.findProfileById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteProfileById(@PathVariable(value = "id") int id) {
        return ResponseEntity.ok(service.deleteProfileById(id));
    }

    @GetMapping("/idCard/{idCard}")
    public ResponseEntity<List<Profile>> getAllProfileByIdCard(@PathVariable(value = "idCard") String idCard) {
        return ResponseEntity.ok(service.listProfileByIdCard(idCard));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Profile>> getAllProfileByName(@PathVariable(value = "name") String name) {
        return ResponseEntity.ok(service.listProfileByName(name));
    }

    @GetMapping("/reverse")
    public ResponseEntity<List<Profile>> getAllProfileReversed() {
        return ResponseEntity.ok(service.listProfileReversed());
    }
}
