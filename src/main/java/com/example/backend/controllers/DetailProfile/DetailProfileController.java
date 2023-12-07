package com.example.backend.controllers.DetailProfile;

import com.example.backend.dto.request.DetailProfileCreateRequest;
import com.example.backend.dto.request.DetailProfileUpdateRequest;
import com.example.backend.dto.response.DetailProfileResponse;
import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.DetailProfile;
import com.example.backend.entities.Profile;
import com.example.backend.services.DetailProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/detailprofile")
public class DetailProfileController {
    @Autowired
    private DetailProfileService detailProfileService;

    @PostMapping("")
    public ResponseEntity<DetailProfile> createDetailProfile(@RequestBody DetailProfileCreateRequest detailProfileCreateRequest) {
        return new ResponseEntity<>(detailProfileService.createDetailProfile(detailProfileCreateRequest), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Optional<DetailProfile>> updateDetailProfile(@RequestBody DetailProfileUpdateRequest detailProfileUpdateRequest, @PathVariable(name = "id") int id) {
        detailProfileUpdateRequest.setId(id);
        return ResponseEntity.ok(detailProfileService.updateDetailProfile(detailProfileUpdateRequest));
    }

    @GetMapping("")
    public ResponseEntity<List<DetailProfileResponse>> DetailProfileList() {
        return ResponseEntity.ok(detailProfileService.detailProfileResponseList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<DetailProfileResponse>> findDetailProfileById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(detailProfileService.findDetailProfile(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteDetailProfile(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(detailProfileService.deleteDetailProfileById(id));
    }

}
