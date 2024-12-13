package com.petApp.adoption.service;

import com.petApp.adoption.entity.Pet;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
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
            throw new BadRequestException("Failed Validation");
        } else {
            log.info("Pet registration form validation passed");
            passesValidation = true;
            return passesValidation;
        }

    }

}
