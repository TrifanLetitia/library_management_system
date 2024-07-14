package com.example.Biblioteca.Controller;

import com.example.Biblioteca.Domain.ExemplarImprumutat;
import com.example.Biblioteca.Domain.ImprumutDTO;
import com.example.Biblioteca.Domain.User;
import com.example.Biblioteca.Service.ExemplarService;
import com.example.Biblioteca.Service.ImprumutService;
import com.example.Biblioteca.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ExemplarImprumutatController
{
    @Autowired
    private ImprumutService imprumutService;
    @Autowired
    private ExemplarService exemplarService;
    @Autowired
    private UserService userService;


}
