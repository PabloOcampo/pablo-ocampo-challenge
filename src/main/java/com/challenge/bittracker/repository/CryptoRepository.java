package com.challenge.bittracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.challenge.bittracker.domain.Crypto;

@Repository
public interface CryptoRepository extends CrudRepository<Crypto, Long> {

    Optional<Crypto> findByName(String name);

    Optional<Crypto> findByTimestamp(String timestamp);

    @Query("SELECT cryptoCoin FROM Crypto cryptoCoin WHERE cryptoCoin.timestamp BETWEEN ?1 AND ?2")
    List<Crypto> findPricesBetweenTimestamps(String initialTimestamp, String finalTimestamp);

    @Query("SELECT max(cryptoCoin.price) FROM Crypto cryptoCoin")
    Double findMaxPrice(); 
}
