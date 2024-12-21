package com.petApp.adoption.service;

import com.petApp.adoption.models.Pet;
import com.petApp.adoption.models.TransactionalLog;
import com.petApp.adoption.models.enums.PetStatus;
import com.petApp.adoption.repository.PetRepository;
import com.petApp.adoption.util.Codes;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    public Pet adoptPet(Integer petId) {
        Optional<Pet> pet = petRepository.findById(petId);
        if (pet.isPresent() && pet.get().getStatus() == PetStatus.ACTIVE) {
            pet.get().setStatus(PetStatus.ADOPTED);
            petRepository.save(pet.get());
        }
        return null;
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
    public Pet fetchPetById(Integer petId ) throws Exception {
      Optional<Pet> retreivePet = petRepository.findById(petId);
      if(retreivePet.isPresent()){
          log.info("Logging transaction for registering pet");
          TransactionalLog record = new TransactionalLog();
          record.setPet(retreivePet.get().getName());
          record.setUsername("NA");
          record.setDescription(Codes.FETCH_PET);
          transactionalLogService.createTransactionalLog(record);
          return retreivePet.get();
      }else{
          throw new BadRequestException("Pet id not found");
      }
    }
    public List<Pet> fetchAll() throws Exception {
            List<Pet> fetchAll = petRepository.findAll();
            if (!fetchAll.isEmpty()){
                log.info("Logging transaction for registering pet");
                TransactionalLog record = new TransactionalLog();
                record.setPet("N/A");
                record.setUsername("NA");
                record.setDescription(Codes.FETCH_ALL_PET);
                transactionalLogService.createTransactionalLog(record);
                return fetchAll;
            } else {
                throw new IllegalArgumentException("fetchAll");
            }
    }

    public String deletPetById(Integer petId) throws Exception {
        Optional<Pet> retreivePet = petRepository.findById(petId);
        if (retreivePet.isPresent()) {
            log.info("Logging transaction for registering pet");
            TransactionalLog record = new TransactionalLog();
            record.setPet(retreivePet.get().getName());
            record.setUsername("NA");
            record.setDescription(Codes.DELETE_PET);
            transactionalLogService.createTransactionalLog(record);
            petRepository.deleteById(petId);
            String result = "Pet Deleted Successfully";
            return result;
        } else {
            throw new BadRequestException("Pet id not found");
        }
    }

    public Pet updatePetById(Pet pet) throws Exception {
        Optional<Pet> retreivePet = petRepository.findById(pet.getPetId());
        if (retreivePet.isPresent()){
            log.info("Validation Check");
//            checkValidation.checkRegisterValdiation(pet);
            Pet updatePet = new Pet();
            updatePet.setPetId(pet.getPetId());
            updatePet.setBreed(pet.getBreed());
            updatePet.setName(pet.getName());
            updatePet.setAge(pet.getAge());
            updatePet.setPetCondition(pet.getPetCondition());
            updatePet.setGender(pet.getGender());
            Pet updatedPet = petRepository.save(updatePet);
            log.info("Logging transaction for registering pet");
            TransactionalLog record = new TransactionalLog();
            record.setPet(retreivePet.get().getName());
            record.setUsername("NA");
            record.setDescription(Codes.UPDATE_PET);
            transactionalLogService.createTransactionalLog(record);
            return updatedPet;
        }else{
            throw new BadRequestException("Pet Modifcation failed. Check all fields");
        }
    }
}
