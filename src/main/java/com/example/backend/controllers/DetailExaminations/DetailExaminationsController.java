package com.example.backend.controllers.DetailExaminations;

import com.example.backend.dto.request.DetailExaminationCreateRequest;
import com.example.backend.dto.request.DetailExaminationUpdateRequest;
import com.example.backend.dto.response.DetailExaminationsResponse;
import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.DetailExaminations;
import com.example.backend.services.DetailExaminationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/detailexminations")
public class DetailExaminationsController {
    @Autowired
    private DetailExaminationsService detailExaminationsService;

    @PostMapping("")
    public ResponseEntity<DetailExaminations> createDetailExaminations(@RequestBody DetailExaminationCreateRequest detailExaminationCreateRequest) {
        return new ResponseEntity<>(detailExaminationsService.createDetailExaminations(detailExaminationCreateRequest), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Optional<DetailExaminations>> updateDetailExaminations(@RequestBody DetailExaminationUpdateRequest detailExaminationUpdateRequest, @PathVariable(name = "id") int id) {
        detailExaminationUpdateRequest.setId(id);
        return ResponseEntity.ok(detailExaminationsService.updateDetailExaminations(detailExaminationUpdateRequest));
    }

    @GetMapping("")
    public ResponseEntity<List<DetailExaminationsResponse>> DetailExaminationsList() {
        return ResponseEntity.ok(detailExaminationsService.DetailExaminationsList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<DetailExaminationsResponse>> findDetailExaminationsById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(detailExaminationsService.findDetailExaminationsById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteDetailExaminationsById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(detailExaminationsService.deleteDetailExaminationsById(id));
    }
}
