package com.example.backend.controllers.Nation;

import com.example.backend.dto.response.ResponseMessage;
import com.example.backend.entities.Nation;
import com.example.backend.services.NationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/nation")
public class NationController {
    @Autowired
    private NationService nationService;

    @PostMapping()
    public ResponseEntity<Nation> createNation(@RequestBody Nation nation) {
        return new ResponseEntity<>(nationService.createNation(nation), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Optional<Nation>> updateNation(@RequestBody Nation nation, @PathVariable(name = "id") int id) {
        nation.setId(id);
        return ResponseEntity.ok(nationService.updateNation(nation));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Nation>> findNationById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(nationService.findNationById(id));
    }

    @GetMapping()
    public ResponseEntity<List<Nation>> nationList() {
        return ResponseEntity.ok(nationService.nationList());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteNationById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(nationService.deleteNationById(id));
    }
}
