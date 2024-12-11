package com.petApp.adoption.repository;

import com.petApp.adoption.entity.Account;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository

public interface AccountRepository extends CrudRepository<Account,Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Account VALUES ({account.username, account.email, account.role, account.password)", nativeQuery = true)
    public abstract void saveUser (@Param("account") Account account);

}