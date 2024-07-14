package com.example.Biblioteca.Controller;

import com.example.Biblioteca.Domain.User;
import com.example.Biblioteca.Domain.UserDTO;
import com.example.Biblioteca.Domain.AuthenticationRequest;
import com.example.Biblioteca.Mapper.AbonatMapper;
import com.example.Biblioteca.Repository.UserRepository;
import com.example.Biblioteca.Service.UserService;
import com.example.Biblioteca.Service.ServiceObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class UserController {

    ServiceObserver serviceObserver;
    @Autowired
    private UserRepository abonatRepository;
    @Autowired
    private UserService abonatService;
    @Autowired
    private AbonatMapper abonatMapper;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllAbonati() {
        List<UserDTO> abonati = abonatService.getAllAbonati();
        return ResponseEntity.ok(abonati);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getAbonatById(@PathVariable UUID id) {
        UserDTO abonat = abonatService.getAbonatById(id);
        return ResponseEntity.ok(abonat);
    }

    @PostMapping
    public ResponseEntity<User> createAbonat(@RequestBody UserDTO abonatDTO) {
        UserDTO createdAbonat = abonatService.createAbonat(abonatDTO);
        User user = abonatService.getAbonatByCnp(createdAbonat.getCnp());
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateAbonat(@PathVariable UUID id, @RequestBody UserDTO abonatDTO) {
        UserDTO updateAbonat = abonatService.updateAbonat(id, abonatDTO);
        return ResponseEntity.ok(updateAbonat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAbonat(@PathVariable UUID id) {
        abonatService.deleteAbonat(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerAbonat(@RequestBody UserDTO abonatDTO) {
        try {
            abonatService.createAbonat(abonatDTO);
            return ResponseEntity.ok("Abonat registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register abonat: " + e.getMessage());
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        String cnp = request.getCnp();
        String password = request.getPassword();
        User donor = abonatRepository.getAbonatByCnp(cnp);
        UserDTO donorDTO = abonatMapper.toDto(donor);
        if (donor.getPassword().equals(password)) {
            return new ResponseEntity<>(donor, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().body("Autenasdstificare eșuată!");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logOut(@RequestParam String cnp) {
        try {
            abonatService.logOut(cnp);
            return ResponseEntity.ok("Successfully logged out user: " + cnp);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
