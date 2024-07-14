package com.example.Biblioteca.Service;

import com.example.Biblioteca.Domain.*;
import com.example.Biblioteca.Mapper.CarteMapper;
import com.example.Biblioteca.Repository.CarteRepository;
import com.example.Biblioteca.Repository.ExemplarRepository;
import com.example.Biblioteca.Repository.RoleRepository;
import com.example.Biblioteca.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CarteService {

    @Autowired
    private CarteRepository carteRepository;

    @Autowired
    private CarteMapper carteMapper;
    @Autowired
    private ExemplarRepository exemplarRepository;

    public List<CarteDTO> getAllCarti() {
        List<Carte> carti = carteRepository.findAll();
        for(Carte carte: carti){
            Set<Exemplar> exemplarSet = getExemplareByBookId(carte.getId());
            if (exemplarSet == null) {
                exemplarSet = new HashSet<>();
            }
            carte.getExemplare().clear();
            carte.getExemplare().addAll(exemplarSet);
        }
        return carteMapper.toDtoList(carti);
    }

    public Integer getNumberOfExemplareDisponibile(UUID id){
        int no = 0;
        for(Exemplar exemplar : exemplarRepository.findAll()){
            if(exemplar.getCarte().getId().equals(id) && exemplar.getStatus().equals(Status.disponibila)){
                no = no + 1;
            }
        }
        return no;
    }

    public CarteDTO getCarteById(UUID id) {
        Carte carte = carteRepository.findById(id).orElse(null);
        if (carte == null) {
            throw new MissingResourceException("Entity not found", "Donor", id.toString());
        }
        Set<Exemplar> exemplarSet = getExemplareByBookId(carte.getId());
        if (exemplarSet == null) {
            exemplarSet = new HashSet<>();
        }
        carte.getExemplare().clear();
        carte.getExemplare().addAll(exemplarSet);
        return carteMapper.toDto(carte);
    }

    public Set<Exemplar> getExemplareByBookId(UUID bookId) {
        Set<Exemplar> exemplarSet = new HashSet<>();
        for(Exemplar exemplar : exemplarRepository.findAll()){
            if(exemplar.getCarte().getId().equals(bookId)){
                exemplarSet.add(exemplar);
            }
        }
        return exemplarSet;
    }

    public CarteDTO createCarte(CarteDTO carteDTO) {
        Carte carte = carteMapper.toEntity(carteDTO);
        Carte createdCarte = carteRepository.save(carte);
        return carteMapper.toDto(createdCarte);
    }

    public CarteDTO updateCarte(UUID id, CarteDTO carteDTO) {
        Carte existingCarte = carteRepository.findById(id).orElse(null);

        if (existingCarte == null) {
            throw new MissingResourceException("Entity not found", "Donor", id.toString());
        }

        carteMapper.updateCarteFromDto(carteDTO, existingCarte);
        System.out.println(existingCarte);
        Carte updatedCarte = carteRepository.save(existingCarte);
        return carteMapper.toDto(updatedCarte);
    }

    public void deleteCarte(UUID id) {
        Set<Exemplar> exemplare = getCarteById(id).getExemplare();
        for(Exemplar exemplar: exemplare){
            exemplarRepository.deleteById(exemplar.getId());
        }
        carteRepository.deleteById(id);
    }

    public Optional<Carte> findById(UUID id) {
        return carteRepository.findById(id);
    }

    public void addExemplar(Carte carte, Exemplar exemplar){
        carteRepository.getCarteById(carte.getId()).addExemplar(exemplar);
        System.out.println(carteRepository.getCarteById(carte.getId()));
    }
}
