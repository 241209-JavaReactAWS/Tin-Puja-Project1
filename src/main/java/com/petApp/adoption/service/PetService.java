package com.petApp.adoption.service;

import com.petApp.adoption.entity.Pets;
import com.petApp.adoption.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetService {
    PetRepository petRepository;
    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }
    public Pets createPets (Pets pets){
        Pets newPets = new Pets();
        newPets.setAge(pets.getAge());
        newPets.setName(pets.getName());
        newPets.setPetCondition(pets.getPetCondition());
        newPets.setGender(pets.getGender());
        newPets.setBreed(pets.getBreed());
        petRepository.save(newPets);
        return newPets;
    }
}
