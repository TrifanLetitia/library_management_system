package com.example.Biblioteca.Domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

public class Bibliotecar extends BaseEntity{
    private String name;
    private String cnp;
    private String password;
}
