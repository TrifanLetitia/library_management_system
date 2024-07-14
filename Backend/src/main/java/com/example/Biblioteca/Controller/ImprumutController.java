package com.example.Biblioteca.Controller;

import com.example.Biblioteca.Domain.*;
import com.example.Biblioteca.Service.CarteService;
import com.example.Biblioteca.Service.ImprumutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/imprumuturi")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ImprumutController {
    @Autowired
    private ImprumutService imprumutService;

    @GetMapping
    public ResponseEntity<List<ImprumutDTO>> getAllImprumuturi() {
        List<ImprumutDTO> imprumutDTOS = imprumutService.getAllImprumuturi();
        return ResponseEntity.ok(imprumutDTOS);
    }

    @GetMapping("/{id}/user")
    public  ResponseEntity<List<List<ExemplarImprumutat>>> getAllImprumuturiNerestituiteByUserId(@PathVariable UUID id){
        List<List<ExemplarImprumutat>> exemplare = imprumutService.getListExemplareImprumutateByImprumut(id);
        for(List<ExemplarImprumutat> list : exemplare){
            System.out.println(list);
        }
        return ResponseEntity.ok(exemplare);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImprumutDTO> getImprumutById(@PathVariable UUID id) {
        ImprumutDTO imprumutDTO = imprumutService.getImprumutById(id);
        return ResponseEntity.ok(imprumutDTO);
    }

    @PostMapping
    public ResponseEntity<ImprumutDTO> createImprumut(@RequestBody ImprumutCreationRequest imprumutCreationRequest) {
        System.out.println(imprumutCreationRequest.getDataRestituire());
        ImprumutDTO createdImprumut = imprumutService.createImprumut(imprumutCreationRequest);
        return new ResponseEntity<>(createdImprumut, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ImprumutDTO> updateImprumut(@PathVariable UUID id, @RequestBody ImprumutDTO imprumutDTO) {
        System.out.println(imprumutDTO);
        ImprumutDTO updated = imprumutService.updateImprumut(id, imprumutDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImprumut(@PathVariable UUID id) {
        imprumutService.deleteImprumut(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/updateDate")
    public ResponseEntity<ImprumutDTO> updateDate(@PathVariable UUID id, @RequestBody String date) {
        ImprumutDTO imprumutDTO = imprumutService.getImprumutById(id);
        imprumutDTO.setData_restituire(date);
        ImprumutDTO updated = imprumutService.updateImprumut(id, imprumutDTO);
        return ResponseEntity.ok(updated);
    }
}
