package com.example.Biblioteca.Service;

import com.example.Biblioteca.Domain.*;
import com.example.Biblioteca.Mapper.AbonatMapper;
import com.example.Biblioteca.Mapper.CarteMapper;
import com.example.Biblioteca.Mapper.ImprumutMapper;
import com.example.Biblioteca.Repository.CarteRepository;
import com.example.Biblioteca.Repository.ExemplarRepository;
import com.example.Biblioteca.Repository.ImprumutRepository;
import com.example.Biblioteca.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ImprumutService {

    @Autowired
    private ImprumutRepository imprumutRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ImprumutMapper imprumutMapper;

    @Autowired
    private ExemplarImprumutatService exemplarImprumutatService;

    @Autowired
    private ExemplarService exemplarService;

    @Autowired
    private ExemplarRepository exemplarRepository;

    public List<ImprumutDTO> getAllImprumuturi() {
        List<Imprumut> imprumutList = imprumutRepository.findAll();
        return imprumutMapper.toDtoList(imprumutList);
    }

    public ImprumutDTO getImprumutById(UUID id) {
        Imprumut imprumut = imprumutRepository.findById(id).orElse(null);
        if (imprumut == null) {
            throw new MissingResourceException("Entity not found", "Donor", id.toString());
        }
        return imprumutMapper.toDto(imprumut);
    }

    public ImprumutDTO createImprumut(ImprumutCreationRequest imprumutCreationRequest) {
        User user = userService.getUserById(imprumutCreationRequest.getId_user());
        ImprumutDTO imprumutDTO = new ImprumutDTO();
        imprumutDTO.setUser(user);
        imprumutDTO.setData_imprumut(imprumutCreationRequest.getDataImprumut());
        imprumutDTO.setData_restituire(imprumutCreationRequest.getDataRestituire());
        imprumutDTO.setId(imprumutCreationRequest.getId_user());
        Imprumut imprumut = imprumutMapper.toEntity(imprumutDTO);
        Imprumut createdImprumut = imprumutRepository.save(imprumut);
        for(UUID id_exemplar: imprumutCreationRequest.getExemplarImprumutatRequest().getId_carti()) {
            List<Exemplar> exemplareDisponibile = exemplarService.findByCarteIdAndStatus(id_exemplar, Status.disponibila);
            Exemplar exemplar = exemplareDisponibile.get(0);
            exemplar.setStatus(Status.imprumutata);
            exemplarRepository.save(exemplar);
            ExemplarImprumutat createdExemplar = new ExemplarImprumutat();
            createdExemplar.setImprumut(createdImprumut);
            createdExemplar.setExemplar(exemplar);
            exemplarImprumutatService.createExemplarImprumutat(createdExemplar);
        }
        return imprumutMapper.toDto(createdImprumut);
    }

    public List<Imprumut> getImprumutByUserId(UUID id_user) {
        List<Imprumut> imprumutList = new ArrayList<>();
        for(Imprumut imprumut : imprumutRepository.findAll()) {
            if (imprumut.getUser().getId().equals(id_user)) {
                imprumutList.add(imprumut);
            }
        }
        return imprumutList;
    }

    public ImprumutDTO updateImprumut(UUID id, ImprumutDTO imprumutDTO) {
        Imprumut existingImprumut = imprumutRepository.findById(id).orElse(null);

        if (existingImprumut == null) {
            throw new MissingResourceException("Entity not found", "Donor", id.toString());
        }

        imprumutMapper.updateImprumutFromDto(imprumutDTO, existingImprumut);
        Imprumut updated = imprumutRepository.save(existingImprumut);
        return imprumutMapper.toDto(updated);
    }

    public void deleteImprumut(UUID id) {
        imprumutRepository.deleteById(id);
    }

    public Optional<Imprumut> findById(UUID id) {
        return imprumutRepository.findById(id);
    }

    public List<Imprumut> getAllImprumuturiNerestiuiteByUserId(UUID id) {
        List<Imprumut> imprumutList = new ArrayList<>();
        for(Imprumut imprumut : imprumutRepository.findAll()) {
            if(imprumut.getUser().getId().equals(id) && imprumut.getDataResituire().equals("-")) {
                imprumutList.add(imprumut);
            }
        }
        return imprumutList;
    }

    public List<Exemplar> getAllExemplareImprumutateByImprumutId(UUID id) {
        List<Exemplar> exemplarList = new ArrayList<>();
        for(ExemplarImprumutat exemplar : exemplarImprumutatService.getAllByImprumutId(id)) {
            exemplarList.add(exemplar.getExemplar());
        }
        return exemplarList;
    }

    public List<List<ExemplarImprumutat>> getListExemplareImprumutateByImprumut(UUID id_user) {
        List<List<ExemplarImprumutat>> exemplareImprumutateList = new ArrayList<>();
        for(Imprumut imprumut : getAllImprumuturiNerestiuiteByUserId(id_user)) {
            exemplareImprumutateList.add(exemplarImprumutatService.getAllByImprumutId(imprumut.getId()));
        }
        return exemplareImprumutateList;
    }
}
