package com.petApp.adoption.service;

import com.petApp.adoption.entity.Pet;
import com.petApp.adoption.entity.TransactionalLog;
import com.petApp.adoption.repository.PetRepository;
import com.petApp.adoption.util.Codes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.petApp.adoption.util.Codes.CREATE_PET;


@Service
@Slf4j
public class PetService {

//    @Autowired
//    Codes codes;

    PetRepository petRepository;
    TransactionalLogService transactionalLogService;

    @Autowired
    public PetService(PetRepository petRepository, TransactionalLogService transactionalLogService) {
        this.petRepository = petRepository;
        this.transactionalLogService = transactionalLogService;
    }

    public Pet createPet(Pet pet) {
        Pet newPet = new Pet();
        newPet.setAge(pet.getAge());
        newPet.setName(pet.getName());
        newPet.setPetCondition(pet.getPetCondition());
        newPet.setGender(pet.getGender());
        newPet.setBreed(pet.getBreed());
        Pet createdPet = petRepository.save(newPet);

        TransactionalLog record = new TransactionalLog();
        record.setPet(createdPet.getName());
        record.setUsername("NA");
        record.setDescription(CREATE_PET);

        transactionalLogService.createTransactionalLog(record);

        return createdPet;
    }
}
