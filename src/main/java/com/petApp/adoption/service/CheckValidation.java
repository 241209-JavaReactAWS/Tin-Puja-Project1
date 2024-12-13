package com.petApp.adoption.service;

import com.petApp.adoption.entity.Pet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CheckValidation {

    public Boolean checkRegisterValdiation(Pet pet) throws Exception {

        Boolean passesValidation = false;

        if((pet.getAge() ==null) ||
            (pet.getBreed() ==null) ||
                (pet.getPetCondition() ==null) ||
                (pet.getName() ==null) ||
                (pet.getGender() ==null) ){
            log.info("Pet registration failed");
            throw new Exception("Failed Validation");
        } else {
            passesValidation = true;
            return passesValidation;
        }

    }

}
