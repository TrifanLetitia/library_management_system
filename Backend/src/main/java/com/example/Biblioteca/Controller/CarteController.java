package com.example.Biblioteca.Controller;

import com.example.Biblioteca.Domain.CarteDTO;
import com.example.Biblioteca.Repository.ExemplarRepository;
import com.example.Biblioteca.Service.CarteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/carti")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class CarteController {

    @Autowired
    private CarteService carteService;
    @Autowired
    private ExemplarRepository exemplarRepository;

    @GetMapping
    public ResponseEntity<List<CarteDTO>> getAllCarti() {
        List<CarteDTO> carteDTOS = carteService.getAllCarti();
        return ResponseEntity.ok(carteDTOS);
    }

    @GetMapping("{id}")
    public ResponseEntity<CarteDTO> getCarteById(@PathVariable UUID id) {
        System.out.println(id);
        CarteDTO carteDTO = carteService.getCarteById(id);
        return ResponseEntity.ok(carteDTO);
    }

    @PostMapping
    public ResponseEntity<CarteDTO> createCarte(@RequestBody CarteDTO carteDTO) {
        CarteDTO createdCarte = carteService.createCarte(carteDTO);
        return new ResponseEntity<>(createdCarte, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/no")
    public ResponseEntity<Integer> getNoOfEx(@PathVariable UUID id){
        return ResponseEntity.ok(carteService.getNumberOfExemplareDisponibile(id));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<CarteDTO> updateCarte(@PathVariable UUID id, @RequestBody CarteDTO carteDTO) {
        CarteDTO updated = carteService.updateCarte(id, carteDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarte(@PathVariable UUID id) {
        carteService.deleteCarte(id);
        return ResponseEntity.noContent().build();
    }
}
