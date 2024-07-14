package com.example.Biblioteca.Service;

import com.example.Biblioteca.Domain.Role;
import com.example.Biblioteca.Domain.User;
import com.example.Biblioteca.Domain.UserDTO;
import com.example.Biblioteca.Mapper.AbonatMapper;
import com.example.Biblioteca.Repository.UserRepository;
import com.example.Biblioteca.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {
    @Autowired
    private UserRepository abonatRepository;
    private RoleRepository roleRepository;

    @Autowired
    private AbonatMapper abonatMapper;

    private final Map<String, ServiceObserver> loggedUsers = new ConcurrentHashMap();

    public UserService(UserRepository userRepository, RoleRepository roleRepository){
        this.abonatRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    public List<UserDTO> getAllAbonati() {
        List<User> abonati = abonatRepository.findAll();
        return abonatMapper.toDtoList(abonati);
    }

    public UserDTO getAbonatById(UUID id) {
        User abonat = abonatRepository.findById(id).orElse(null);
        if (abonat == null) {
            throw new MissingResourceException("Entity not found", "Donor", id.toString());
        }
        return abonatMapper.toDto(abonat);
    }

    public User getUserById(UUID id) {
        User abonat = abonatRepository.findById(id).orElse(null);
        return abonat;
    }

    public UserDTO createAbonat(UserDTO abonatDTO) {
        User abonat = abonatMapper.toEntity(abonatDTO);
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_ABONAT");
        roles.add(userRole);
        abonat.setRoles(roles);
        User createdAbonat = abonatRepository.save(abonat);
        return abonatMapper.toDto(createdAbonat);
    }

    public UserDTO updateAbonat(UUID id, UserDTO abonatDTO) {
        User existingAbonat = abonatRepository.findById(id).orElse(null);

        if (existingAbonat == null) {
            throw new MissingResourceException("Entity not found", "Donor", id.toString());
        }

        abonatMapper.updateAbonatFromDto(abonatDTO, existingAbonat);
        User updatedAbonat = abonatRepository.save(existingAbonat);
        return abonatMapper.toDto(updatedAbonat);
    }

    public void deleteAbonat(UUID id) {
        abonatRepository.deleteById(id);
    }

    public Optional<User> findById(UUID id) {
        return abonatRepository.findById(id);
    }

    public User getAbonatByCnp(String cnp){
        return abonatRepository.getAbonatByCnp(cnp);
    }

    public void registerAbonat(String name, String password, String cnp, String adress, String phone){
        UUID uuid = UUID.randomUUID();
        UserDTO abonatDTO = new UserDTO(uuid, name, password, cnp, adress, phone);
        this.createAbonat(abonatDTO);
    }


    public boolean authenticateUser(String cnp, String password, ServiceObserver client) {
        User abonat = this.abonatRepository.getAbonatByCnp(cnp);
        if (abonat!=null && abonat.getPassword().equals(password)) {
            if (this.loggedUsers.containsKey(cnp)) {
                throw new RuntimeException("User already logged in!");
            }
        } else {
            throw new RuntimeException("Authentication failed!");
        }
        return true;
    }

    public void logOut(String user) {
        ServiceObserver observer = (ServiceObserver)this.loggedUsers.remove(user);
        if (observer == null) {
            throw new RuntimeException("User is not logged in!");
        }
    }
}
