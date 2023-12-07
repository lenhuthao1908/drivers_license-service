package com.example.backend.controllers.Examination;

import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.Examinations;
import com.example.backend.services.ExaminationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/examination")
public class ExaminationController {
    @Autowired
    private ExaminationService examinationService;

    @PostMapping()
    public ResponseEntity<Examinations> createExamination(@RequestBody Examinations examination) {
        return new ResponseEntity<>(examinationService.createExamination(examination), HttpStatus.CREATED);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Optional<Examinations>> updateExamination(@RequestBody Examinations examination, @PathVariable(name = "id") int id) {
        examination.setId(id);
        return ResponseEntity.ok(examinationService.updateExamination(examination));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Examinations>> findExaminationById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(examinationService.findExaminationById(id));
    }

    @GetMapping()
    public ResponseEntity<List<Examinations>> examinationsList() {
        return ResponseEntity.ok(examinationService.examinationsList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteExaminationById(@PathVariable(name = "id") int id) {
        return new ResponseEntity<>(examinationService.deleteExaminationById(id), HttpStatus.OK);
    }
}
