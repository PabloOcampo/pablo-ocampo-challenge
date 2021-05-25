package com.challenge.bittracker.service;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

@SpringBootTest(classes = BitTrackerApplication.class)
@AutoConfigureMockMvc
public class PriceInfoResourcesITests {

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
    public void assertPriceInfoServiceResponse() throws Exception {
    	cryptoService.create(crypto);
    	
    	Crypto newCrypto = new Crypto();
    	newCrypto.setName(UPDATED_NAME);
    	newCrypto.setCurrency(DEFAULT_CURRENCY);
    	newCrypto.setPrice(SECOND_PRICE);
    	newCrypto.setTimestamp(SECOND_TIMESTAMP);
    	cryptoService.create(newCrypto);

    	mockMvc.perform(get("/api/cryptos/priceinfo?initialTimestamp=" + DEFAULT_INITIAL_TIMESTAMP + "&finalTimestamp=" + DEFAULT_FINAL_TIMESTAMP)
    			.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$.initialTimestamp", is(DEFAULT_INITIAL_TIMESTAMP)))
		        .andExpect(jsonPath("$.finalTimestamp", is(DEFAULT_FINAL_TIMESTAMP)))
		        .andExpect(jsonPath("$.avgPriceBetweenTimestamps", is(DEFAULT_AVG_PRICE)))
		        .andExpect(jsonPath("$.maxPrice", is(DEFAULT_MAX_PRICE)))
		        .andExpect(jsonPath("$.percentageDifference", is(DEFAULT_PD)));
    }
}
