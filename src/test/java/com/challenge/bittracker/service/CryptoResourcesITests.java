package com.challenge.bittracker.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.challenge.bittracker.BitTrackerApplication;
import com.challenge.bittracker.domain.Crypto;
import com.challenge.bittracker.repository.CryptoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest(classes = BitTrackerApplication.class)
@AutoConfigureMockMvc
public class CryptoResourcesITests {

    private static final String DEFAULT_NAME = "BTC";

    private static final String UPDATED_NAME = "BTCUPD";
    
    private static final String DEFAULT_CURRENCY = "USD";

    private static final Double DEFAULT_PRICE = 40000.0;

    private static final Double SECOND_PRICE = 60000.0;

    private static final Double DEFAULT_MAX_PRICE = 60000.0;

    private static final Double DEFAULT_AVG_PRICE = 50000.0;

    private static final Double DEFAULT_PD = 20.0;
    
    private static final String DEFAULT_TIMESTAMP = "2021-01-01T12:00:00";

    private static final String SECOND_TIMESTAMP = "2021-01-02T15:00:00";

    private static final String DEFAULT_INITIAL_TIMESTAMP = "2021-01-01T01:00:00";

    private static final String DEFAULT_FINAL_TIMESTAMP = "2021-01-02T23:59:00";
    
    @Autowired
    private CryptoService cryptoService;

    @Autowired
    private PriceInfoService priceInfoService;

    @Autowired
    private CryptoRepository cryptoRepository;

    @Autowired
    private MockMvc mockMvc;
    
    private Crypto crypto = new Crypto();

    @BeforeEach
    public void init() {
    	cryptoRepository.deleteAll();
    	crypto = new Crypto();
    	crypto.setName(DEFAULT_NAME);
    	crypto.setPrice(DEFAULT_PRICE);
    	crypto.setCurrency(DEFAULT_CURRENCY);
    	crypto.setTimestamp(DEFAULT_TIMESTAMP);
    }
    
    @Test
    public void assertThatCanCreateCrypto() throws Exception {
    	mockMvc.perform(post("/api/cryptos")
    			.content(new ObjectMapper().writeValueAsString(crypto))
    			.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isCreated())
		        .andExpect(jsonPath("$.id").exists());

    	Optional<Crypto> maybeCrypto = cryptoService.findByName(DEFAULT_NAME);

    	assertThat(maybeCrypto).isPresent();
    	assertThat(maybeCrypto.get().getPrice()).isEqualTo(DEFAULT_PRICE);
    	assertThat(maybeCrypto.get().getCurrency()).isEqualTo(DEFAULT_CURRENCY);
    	assertThat(maybeCrypto.get().getTimestamp()).isEqualTo(DEFAULT_TIMESTAMP);
    }

    @Test
    public void assertThatCanUpdateCrypto() throws Exception {
    	cryptoService.create(crypto);

    	crypto.setName(UPDATED_NAME);
    	
    	Optional<Crypto> maybeCrypto = cryptoService.findByName(DEFAULT_NAME);
    	assertThat(maybeCrypto).isPresent();

    	mockMvc.perform(put("/api/cryptos/" + maybeCrypto.get().getId())
    			.content(new ObjectMapper().writeValueAsString(crypto))
    			.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$.id").exists())
		        .andExpect(jsonPath("$.name").value(UPDATED_NAME));

    	Optional<Crypto> maybeUpdatedCrypto = cryptoService.findByName(UPDATED_NAME);

    	assertThat(maybeUpdatedCrypto).isPresent();
    	assertThat(maybeUpdatedCrypto.get().getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    public void assertThatCanDeleteCrypto() throws JsonProcessingException, Exception {
    	cryptoService.create(crypto);

    	Optional<Crypto> maybeCrypto = cryptoService.findByName(DEFAULT_NAME);
    	assertThat(maybeCrypto).isPresent();
    	
    	mockMvc.perform(delete("/api/cryptos/" + maybeCrypto.get().getId())
    			.content(new ObjectMapper().writeValueAsString(crypto))
    			.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk());
  
    	Optional<Crypto> maybeDeletedCrypto = cryptoService.findByName(DEFAULT_NAME);
    	assertThat(maybeDeletedCrypto).isNotPresent();
    }

    @Test
    public void assertMaxPrice() {
    	cryptoService.create(crypto);

    	crypto.setPrice(SECOND_PRICE);

    	cryptoService.create(crypto);

    	Double cryptoMaxPrice = cryptoService.findMaxPrice();
    	assertThat(cryptoMaxPrice).isEqualTo(DEFAULT_MAX_PRICE);
    }

    @Test
    public void assertAvgPrice() {
    	cryptoService.create(crypto);
    	
    	Crypto newCrypto = new Crypto();
    	newCrypto.setName(UPDATED_NAME);
    	newCrypto.setCurrency(DEFAULT_CURRENCY);
    	newCrypto.setPrice(SECOND_PRICE);
    	newCrypto.setTimestamp(SECOND_TIMESTAMP);
    	cryptoService.create(newCrypto);

    	Double cryptoAvgPrice = cryptoService.findAveragePriceBetweenTimestamps(DEFAULT_INITIAL_TIMESTAMP, DEFAULT_FINAL_TIMESTAMP).getAsDouble();
    	assertThat(cryptoAvgPrice).isEqualTo(DEFAULT_AVG_PRICE);
    }

    @Test
    public void assertPorcentualDifferenceInPrice() {
    	cryptoService.create(crypto);
    	
    	Crypto newCrypto = new Crypto();
    	newCrypto.setName(UPDATED_NAME);
    	newCrypto.setCurrency(DEFAULT_CURRENCY);
    	newCrypto.setPrice(SECOND_PRICE);
    	newCrypto.setTimestamp(SECOND_TIMESTAMP);
    	cryptoService.create(newCrypto);

    	Double cryptoMaxPrice = cryptoService.findMaxPrice();
    	System.out.println(cryptoMaxPrice);
    	assertThat(cryptoMaxPrice).isEqualTo(DEFAULT_MAX_PRICE);

    	Double cryptoAvgPrice = cryptoService.findAveragePriceBetweenTimestamps(DEFAULT_INITIAL_TIMESTAMP, DEFAULT_FINAL_TIMESTAMP).getAsDouble();
    	assertThat(cryptoAvgPrice).isEqualTo(DEFAULT_AVG_PRICE);

    	Double cryptoPDPrice = priceInfoService.getPercentageDifference(cryptoAvgPrice, cryptoMaxPrice);
    	assertThat(cryptoPDPrice).isEqualTo(DEFAULT_PD);
    }
}
