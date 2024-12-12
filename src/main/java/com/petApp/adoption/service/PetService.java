package com.petApp.adoption.service;

import com.petApp.adoption.entity.Pet;
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
    public Pet createPet(Pet pet) {
        Pet newPet = new Pet();
        newPet.setAge(pet.getAge());
        newPet.setName(pet.getName());
        newPet.setPetCondition(pet.getPetCondition());
        newPet.setGender(pet.getGender());
        newPet.setBreed(pet.getBreed());
        Pet createdPet = petRepository.save(newPet);
        return createdPet;
    }
}
