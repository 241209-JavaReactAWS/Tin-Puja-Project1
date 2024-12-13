package com.petApp.adoption.service;

import com.petApp.adoption.entity.Pet;
import com.petApp.adoption.entity.TransactionalLog;
import com.petApp.adoption.repository.PetRepository;
import com.petApp.adoption.util.Codes;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import static com.petApp.adoption.util.Codes.CREATE_PET;


@Service
@Slf4j
public class PetService {

    PetRepository petRepository;
    TransactionalLogService transactionalLogService;
    CheckValidation checkValidation;

    @Autowired
    public PetService(PetRepository petRepository, TransactionalLogService transactionalLogService, CheckValidation checkValidation) {
        this.petRepository = petRepository;
        this.transactionalLogService = transactionalLogService;
        this.checkValidation = checkValidation;
    }

    public Pet createPet(Pet pet) throws Exception {

        try{
            Pet newPet = new Pet();
            newPet.setAge(pet.getAge());
            newPet.setName(pet.getName());
            newPet.setPetCondition(pet.getPetCondition());
            newPet.setGender(pet.getGender());
            newPet.setBreed(pet.getBreed());

            log.info("Starting pet registration validation");
            checkValidation.checkRegisterValdiation(newPet);

            Pet createdPet = petRepository.save(newPet);

            log.info("Logging transaction for registering pet");
            TransactionalLog record = new TransactionalLog();
            record.setPet(createdPet.getName());
            record.setUsername("NA");
            record.setDescription(CREATE_PET);

            transactionalLogService.createTransactionalLog(record);

            return createdPet;
        } catch (BadRequestException ex){
            throw new BadRequestException("Pet registration failed : bad request");
        }
    }
}
