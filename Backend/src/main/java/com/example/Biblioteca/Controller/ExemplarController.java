package com.example.Biblioteca.Controller;

import com.example.Biblioteca.Domain.*;
import com.example.Biblioteca.Mapper.CarteMapper;
import com.example.Biblioteca.Mapper.ExemplarMapper;
import com.example.Biblioteca.Repository.CarteRepository;
import com.example.Biblioteca.Service.CarteService;
import com.example.Biblioteca.Service.ExemplarImprumutatService;
import com.example.Biblioteca.Service.ExemplarService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import netscape.javascript.JSObject;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/exemplare")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class ExemplarController {
    @Autowired
    private ExemplarService exemplarService;

    @Autowired
    private CarteRepository carteRepository;


    @Autowired
    private ExemplarMapper exemplarMapper;
    @Autowired
    private ExemplarImprumutatService exemplarImprumutatService;

    @GetMapping
    public ResponseEntity<List<ExemplarDTO>> getAllExemplare() {
        List<ExemplarDTO> exemplarDTOS = exemplarService.getAllExemplare();
        return ResponseEntity.ok(exemplarDTOS);
    }

    @GetMapping("/{id}/book")
    public ResponseEntity<List<Exemplar>> getAllByBookId(@PathVariable UUID id){
        List<Exemplar> exemplarDTOS = exemplarService.getAllExemplareByBookId(id);
        return ResponseEntity.ok(exemplarDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExemplarDTO> getExemplarById(@PathVariable UUID id) {
        ExemplarDTO exemplarDTO = exemplarService.getExemplarById(id);
        return ResponseEntity.ok(exemplarDTO);
    }

    @PostMapping
    public ResponseEntity<ExemplarDTO> createExemplar(@RequestBody ExemplarCreationRequest request) {
        ExemplarDTO newExemplar = new ExemplarDTO();
        newExemplar.setCarte(carteRepository.getCarteById(UUID.fromString(request.getId_carte())));
        newExemplar.setStatus(request.getStatus());
        ExemplarDTO createdExemplar = exemplarService.createExemplar(newExemplar);
        return new ResponseEntity<>(createdExemplar, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ExemplarDTO> updateExemplar(@PathVariable UUID id, @RequestBody ExemplarDTO exemplarDTO) {
        System.out.println(exemplarDTO);
        ExemplarDTO updated = exemplarService.updateExemplar(id, exemplarDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExemplar(@PathVariable UUID id) {
        exemplarService.deleteExemplar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/updateStatus")
    public ResponseEntity<ExemplarDTO> updateExemplarStatus(@PathVariable UUID id, @RequestBody String status) {
        System.out.println(id);
        ExemplarDTO exemplarDTO = exemplarService.getExemplarById(id);
        exemplarDTO.setStatus(Status.returnata.toString());
        System.out.println(exemplarDTO);
        ExemplarDTO updated = exemplarService.updateExemplar(id, exemplarDTO);
        exemplarImprumutatService.deleteExemplareImprumutatByExemplarId(id);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/updateStatus2")
    public ResponseEntity<ExemplarDTO> updateExemplarStatusDisp(@PathVariable UUID id) {
        System.out.println(id);
        ExemplarDTO exemplarDTO = exemplarService.getExemplarById(id);
        exemplarDTO.setStatus(Status.disponibila.toString());
        System.out.println(exemplarDTO);
        ExemplarDTO updated = exemplarService.updateExemplar(id, exemplarDTO);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/updateStatus3")
    public ResponseEntity<ExemplarDTO> updateExemplarStatusPierd(@PathVariable UUID id) {
        System.out.println(id);
        ExemplarDTO exemplarDTO = exemplarService.getExemplarById(id);
        exemplarDTO.setStatus(Status.pierduta.toString());
        System.out.println(exemplarDTO);
        ExemplarDTO updated = exemplarService.updateExemplar(id, exemplarDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/returnate")
    public ResponseEntity<List<ExemplarDTO>> getAllExemplareReturnate() {
        List<ExemplarDTO> exemplarDTOS = exemplarService.getAllExemplareReturnate();
        for(ExemplarDTO exemplarDTO: exemplarDTOS){
            System.out.println(exemplarDTO);
        }
        return ResponseEntity.ok(exemplarDTOS);
    }
}
