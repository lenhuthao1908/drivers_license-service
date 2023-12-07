package com.example.backend.controllers.Religion;

import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.Religion;
import com.example.backend.services.ReligionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/religion")
public class ReligionController {
    @Autowired
    private ReligionService religionService;

    @PostMapping("")
    public ResponseEntity<Religion> createReligion(@RequestBody Religion religion) {
        return new ResponseEntity<>(religionService.createReligion(religion), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Optional<Religion>> updateReligion(@RequestBody Religion religion, @PathVariable(name = "id") int id) {
        religion.setId(id);
        return ResponseEntity.ok(religionService.updateReligion(religion));
    }

    @GetMapping("")
    public ResponseEntity<List<Religion>> ReligionList() {
        return ResponseEntity.ok(religionService.religionList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Religion>> findReligionById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(religionService.findReligionById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteReligionById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(religionService.deleteReligionById(id));
    }

}
