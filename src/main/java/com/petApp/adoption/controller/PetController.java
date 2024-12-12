package com.petApp.adoption.controller;

import com.petApp.adoption.entity.Account;
import com.petApp.adoption.entity.Pets;
import com.petApp.adoption.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pet")
public class PetController {
    PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping("/create")
    ResponseEntity<Pets> registerPets(@RequestBody Pets pets) {
        Pets result = petService.createPets(pets);
        return ResponseEntity.ok(result);
    }
}
