package com.example.Biblioteca.Service;

import com.example.Biblioteca.Domain.Exemplar;
import com.example.Biblioteca.Domain.ExemplarDTO;
import com.example.Biblioteca.Domain.ExemplarImprumutat;
import com.example.Biblioteca.Repository.ExemplarImprumutatRepository;
import com.example.Biblioteca.Repository.ExemplarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.UUID;

@Service
public class ExemplarImprumutatService {
    @Autowired
    private ExemplarImprumutatRepository exemplarImprumutatRepository;
    @Autowired
    private ExemplarService exemplarService;

    public List<ExemplarImprumutat> getAllExemplareImprumutate() {
        return exemplarImprumutatRepository.findAll();
    }

    public ExemplarImprumutat getExemplareImprumutatByExemplarId(UUID id) {
        for(ExemplarImprumutat exemplarImprumutat: exemplarImprumutatRepository.findAll()) {
            if(exemplarImprumutat.getExemplar().getId().equals(id)) {
                return exemplarImprumutat;
            }
        }
        return null;
    }

    public void deleteExemplareImprumutatByExemplarId(UUID id) {
        exemplarImprumutatRepository.deleteById(getExemplareImprumutatByExemplarId(id).getId());
    }

    public List<ExemplarImprumutat> getAllExemplareImprumutateByUserId(UUID id_user){
        List<ExemplarImprumutat> exemplarImprumutatList = new ArrayList<>();
        for(ExemplarImprumutat exemplarImprumutat: exemplarImprumutatRepository.findAll()){
            if(exemplarImprumutat.getImprumut().getUser().getId().equals(id_user)){
                exemplarImprumutatList.add(exemplarImprumutat);
            }
        }
        return exemplarImprumutatList;
    }

    public List<ExemplarImprumutat> getAllByImprumutId(UUID id_imprumut){
        List<ExemplarImprumutat> exemplarImprumutatList = new ArrayList<>();
        for(ExemplarImprumutat exemplarImprumutat: exemplarImprumutatRepository.findAll()){
            if(exemplarImprumutat.getImprumut().getId().equals(id_imprumut)){
                exemplarImprumutatList.add(exemplarImprumutat);
            }
        }
        return exemplarImprumutatList;
    }

    public ExemplarImprumutat getExemplarImprumutatById(UUID id) {
        return exemplarImprumutatRepository.getExemplarImprumutatById(id);
    }

    public ExemplarImprumutat createExemplarImprumutat(ExemplarImprumutat exemplarImprumutat) {
        return exemplarImprumutatRepository.save(exemplarImprumutat);
    }
}
