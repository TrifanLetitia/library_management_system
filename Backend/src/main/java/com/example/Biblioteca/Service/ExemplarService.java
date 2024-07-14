package com.example.Biblioteca.Service;

import com.example.Biblioteca.Domain.*;
import com.example.Biblioteca.Mapper.CarteMapper;
import com.example.Biblioteca.Mapper.ExemplarMapper;
import com.example.Biblioteca.Repository.CarteRepository;
import com.example.Biblioteca.Repository.ExemplarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExemplarService {
    @Autowired
    private ExemplarRepository exemplarRepository;

    @Autowired
    private ExemplarMapper exemplarMapper;

    @Autowired
    private CarteService carteService;

    @Autowired
    private CarteMapper carteMapper;
    @Autowired
    private CarteRepository carteRepository;

    public List<ExemplarDTO> getAllExemplare() {
        List<Exemplar> exemplarList = exemplarRepository.findAll();
        return exemplarMapper.toDtoList(exemplarList);
    }

    public List<Exemplar> getAllExemplareByBookId(UUID id_carte){
        List<Exemplar> exemplarList = new ArrayList<>();
        for(Exemplar exemplar: exemplarRepository.findAll()){
            if(exemplar.getCarte().getId() == id_carte){
                exemplarList.add(exemplar);
            }
        }
        return exemplarList;
    }

    public ExemplarDTO getExemplarById(UUID id) {
        Exemplar exemplar = exemplarRepository.findById(id).orElse(null);
        if (exemplar == null) {
            throw new MissingResourceException("Entity not found", "Donor", id.toString());
        }
        return exemplarMapper.toDto(exemplar);
    }

    public ExemplarDTO createExemplar(ExemplarDTO exemplarDTO) {
        Exemplar exemplar = exemplarMapper.toEntity(exemplarDTO);
        Exemplar createdExemplar = exemplarRepository.save(exemplar);
        //carteService.addExemplar(createdExemplar.getCarte(), exemplar);
        return exemplarMapper.toDto(createdExemplar);
    }

    public ExemplarDTO updateExemplar(UUID id, ExemplarDTO exemplarDTO) {
        Exemplar existingExemplar = exemplarRepository.findById(id).orElse(null);

        if (existingExemplar == null) {
            throw new MissingResourceException("Entity not found", "Donor", id.toString());
        }

        exemplarMapper.updateExemplarFromDto(exemplarDTO, existingExemplar);
        System.out.println(existingExemplar);
        Exemplar updatedExemplar = exemplarRepository.save(existingExemplar);
        return exemplarMapper.toDto(updatedExemplar);
    }

    public void deleteExemplar(UUID id) {
        Carte carte = getExemplarById(id).getCarte();
        CarteDTO carteDTO = new CarteDTO();
        carteDTO = carteMapper.toDto(carte);
        Set<Exemplar> exemplare = carte.getExemplare();
        exemplare.remove(getExemplarById(id));
        carteDTO.setExemplare(exemplare);
        carteService.updateCarte(carte.getId(), carteDTO);
        exemplarRepository.deleteById(id);
    }

    public Optional<Exemplar> findById(UUID id) {
        return exemplarRepository.findById(id);
    }

    public  List<Exemplar> findByCarteIdAndStatus(UUID id_carte, Status status) {
        List<Exemplar> exemplarList = new ArrayList<>();
        for(Exemplar exemplar: exemplarRepository.findAll()){
            if(exemplar.getCarte().getId().equals(id_carte) && exemplar.getStatus().equals(status)){
                exemplarList.add(exemplar);
            }
        }
        return exemplarList;
    }

    public List<ExemplarDTO> getAllExemplareReturnate(){
        List<ExemplarDTO> exemplarDTOList = new ArrayList<>();
        for(Exemplar exemplar: exemplarRepository.findAll()){
            if(exemplar.getStatus().equals(Status.returnata)){
                exemplarDTOList.add(exemplarMapper.toDto(exemplar));
            }
        }
        return exemplarDTOList;
    }
}
