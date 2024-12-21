package com.petApp.adoption.controller;

import com.petApp.adoption.models.Pet;
import com.petApp.adoption.service.PetService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pet")
public class PetController {
    PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('pet-admin')")
    ResponseEntity<Pet> registerPets(@RequestBody Pet pet) throws Exception {
        Pet result = petService.createPet(pet);
        if(result != null){
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/fetch")
    ResponseEntity<Pet> fetchPetById(@RequestParam Integer petId) throws Exception {
        Pet result = petService.fetchPetById(petId);
        if (result != null){
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/fetchAll")
    ResponseEntity<List<Pet>>  fetchAll() throws Exception {
        List <Pet> result = petService.fetchAll();
        if (!result.isEmpty()){
            return new ResponseEntity<>(result , HttpStatus.OK);
        }else{
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        }
    }
    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('pet-admin')")
    ResponseEntity<String> deletePetById(@RequestParam Integer petId) throws Exception {
        String result = petService.deletPetById(petId);
        if (result != null){
            return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
        }else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/update")
    @PreAuthorize("hasRole('pet-admin')")
    ResponseEntity<Pet> updatePetById(@RequestBody Pet pet) throws Exception {
        Pet result =  petService.updatePetById(pet);
        if (result != null){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/adopt")
    ResponseEntity<Pet> adoptPetById(@RequestBody Integer petId) throws Exception {
        Pet adoptedPet = petService.adoptPet(petId);
        if (adoptedPet != null) return new ResponseEntity<>(adoptedPet, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
