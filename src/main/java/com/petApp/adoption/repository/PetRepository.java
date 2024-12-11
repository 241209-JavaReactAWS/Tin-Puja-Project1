package com.petApp.adoption.repository;

import com.petApp.adoption.entity.Account;
import com.petApp.adoption.entity.Pets;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PetRepository extends CrudRepository<Pets,Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Pets VALUES (pets.name, pets.breed, pets.age, pets.petCondition,pets.gender)", nativeQuery = true)
    public abstract void savePets (@Param("pets") Pets pets);

}
