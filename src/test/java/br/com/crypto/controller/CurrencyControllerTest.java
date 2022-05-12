package br.com.crypto.controller;

import br.com.crypto.controller.dto.CurrencyDTO;
import br.com.crypto.controller.request.CurrencyRequest;
import br.com.crypto.model.Currency;
import br.com.crypto.service.CurrencyService;
import br.com.crypto.service.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(CurrencyController.class)
public class CurrencyControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CurrencyService currencyService;

    @InjectMocks
    private CurrencyController currencyController;

    private final UUID id = UUID.randomUUID();
//    private final OffsetDateTime now = OffsetDateTime.now();
    private final OffsetDateTime now = OffsetDateTime.parse("2022-05-12T10:05:29.378514454-03:00");
    Currency currencyBitcoinValidate = Currency.builder()
            .id(id)
            .nameCrypto("Bitcoin")
            .code("BTC")
            .createdAt(now)
            .build();

    CurrencyRequest currencyRequestBitcoinValidate = CurrencyRequest.builder()
            .nameCrypto("Bitcoin")
            .code("BTC")
            .build();

    CurrencyDTO currencyDTOBitcoinValidate = new CurrencyDTO(id, "Bitcoin", "BTC", now);

    // GET
    @Test
    @DisplayName("200 - Get /currency - return currency JsonArray")
    public void findCurrencyReturnCurrencyJsonArray() throws Exception {
        String nameCrypto = "Bitcoin";
        String code = "BTC";

        List<CurrencyDTO> currencyList = new ArrayList<>();
        currencyList.add(currencyDTOBitcoinValidate);

        when(currencyService.findCurrency(eq(nameCrypto), eq(code))).thenReturn(currencyList);

        mvc.perform(MockMvcRequestBuilders.get("/currency")
                        // Validate the content type, ṕaram and response code
                        .accept(MediaType.APPLICATION_JSON)
                        .param("nameCrypto", currencyBitcoinValidate.getNameCrypto())
                        .param("code", currencyBitcoinValidate.getCode()))
                .andExpect(status().isOk())
                .andDo(print())

                // Validate the returned fields
                .andExpect(jsonPath("$[0].id", is(currencyBitcoinValidate.getId().toString())))
                .andExpect(jsonPath("$[0].nameCrypto", is(currencyBitcoinValidate.getNameCrypto())))
                .andExpect(jsonPath("$[0].code", is(currencyBitcoinValidate.getCode())))
                .andExpect(jsonPath("$[0].createdAt", is(currencyBitcoinValidate.getCreatedAt().toString())));
    }


    @Test
    @DisplayName("200 - Get /currency - return empty")
    public void findCurrencyReturnEmpty() throws Exception {
        String nameCrypto = "Ethereum";
        String code = "ETH";

        List<CurrencyDTO> currencyList = new ArrayList<>();

        when(currencyService.findCurrency(eq(nameCrypto), eq(code))).thenReturn(currencyList);

        mvc.perform(MockMvcRequestBuilders.get("/currency")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("nameCrypto", nameCrypto)
                        .param("code", code))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("200 - Get /currency - find by nameCrypto empty return JsonArray")
    public void findCurrencyByNameCryptoEmptyReturnCurrencyJsonArray() throws Exception {
        String nameCrypto = "";
        String code = "BTC";

        List<CurrencyDTO> currencyList = new ArrayList<>();
        currencyList.add(currencyDTOBitcoinValidate);

        when(currencyService.findCurrency(eq(nameCrypto), eq(code))).thenReturn(currencyList);

        mvc.perform(MockMvcRequestBuilders.get("/currency")
                        // Validate the content type, param and response code
                        .accept(MediaType.APPLICATION_JSON)
                        .param("nameCrypto", nameCrypto)
                        .param("code", code))
                .andExpect(status().isOk())
                .andDo(print())

                // Validate the returned fields
                .andExpect(jsonPath("$[0].id", is(currencyBitcoinValidate.getId().toString())))
                .andExpect(jsonPath("$[0].nameCrypto", is(currencyBitcoinValidate.getNameCrypto())))
                .andExpect(jsonPath("$[0].code", is(currencyBitcoinValidate.getCode())))
                .andExpect(jsonPath("$[0].createdAt", is(currencyBitcoinValidate.getCreatedAt().toString())));
    }

    @Test
    @DisplayName("200 - Get /currency - find by code empty return JsonArray")
    public void findCurrencyByCodeEmptyReturnJsonArrayEmpty() throws Exception {
        String nameCrypto = "Bitcoin";
        String code = "";

        List<CurrencyDTO> currencyList = new ArrayList<>();
        currencyList.add(currencyDTOBitcoinValidate);

        when(currencyService.findCurrency(eq(nameCrypto), eq(code))).thenReturn(currencyList);

        mvc.perform(MockMvcRequestBuilders.get("/currency")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("nameCrypto", nameCrypto)
                        .param("code", code))
                .andExpect(status().isOk())
                .andDo(print())

                .andExpect(jsonPath("$[0].id", is(currencyBitcoinValidate.getId().toString())))
                .andExpect(jsonPath("$[0].nameCrypto", is(currencyBitcoinValidate.getNameCrypto())))
                .andExpect(jsonPath("$[0].code", is(currencyBitcoinValidate.getCode())))
                .andExpect(jsonPath("$[0].createdAt", is(currencyBitcoinValidate.getCreatedAt().toString())));
    }

    @Test
    @DisplayName("200 - Get /currency - find by nameCrypto empty and code empty return currency JsonArray")
    public void findCurrencyByNameCryptoEmptyAndCodeEmptyReturnJsonArrayEmpty() throws Exception {
        String nameCrypto = "";
        String code = "";

        List<CurrencyDTO> currencyList = new ArrayList<>();
        currencyList.add(currencyDTOBitcoinValidate);

        when(currencyService.findCurrency(eq(nameCrypto), eq(code))).thenReturn(currencyList);

        mvc.perform(MockMvcRequestBuilders.get("/currency")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("nameCrypto", nameCrypto)
                        .param("code", code))
                .andExpect(status().isOk())
                .andDo(print())

                .andExpect(jsonPath("$[0].id", is(currencyBitcoinValidate.getId().toString())))
                .andExpect(jsonPath("$[0].nameCrypto", is(currencyBitcoinValidate.getNameCrypto())))
                .andExpect(jsonPath("$[0].code", is(currencyBitcoinValidate.getCode())))
                .andExpect(jsonPath("$[0].createdAt", is(currencyBitcoinValidate.getCreatedAt().toString())));
    }

    //POST
    @Test
    @DisplayName("201 - Post /currency")
    public void saveCurrency() throws Exception {

        mvc.perform(post("/currency")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(currencyRequestBitcoinValidate)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("201 - Post /currency - NameCrypto Empty")
    public void saveCurrencyNameCryptoEmpty() throws Exception {

        CurrencyRequest currencyRequest = CurrencyRequest.builder()
                .nameCrypto("")
                .code("BTC")
                .build();

        mvc.perform(post("/currency")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(currencyRequest)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("Empty Field")))
                .andExpect(jsonPath("$.message", is("nameCrypto: " + currencyRequest.getNameCrypto() + ", code: " + currencyRequest.getCode())));
    }

    @Test
    @DisplayName("201 - Post /currency - Code Empty")
    public void saveCurrencyCodeEmpty() throws Exception {


        CurrencyRequest currencyRequest = CurrencyRequest.builder()
                .nameCrypto("Bitcoin")
                .code("")
                .build();

        ResultActions result = mvc.perform(post("/currency")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(currencyRequest)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("Empty Field")))
                .andExpect(jsonPath("$.message", is("nameCrypto: " + currencyRequest.getNameCrypto() + ", code: " + currencyRequest.getCode())));
    }

    //Put
    @Test
    @DisplayName("200 - Put /currency")
    public void updateCurrency() throws Exception {

        UUID id = UUID.randomUUID();

        mvc.perform(put("/currency/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(currencyRequestBitcoinValidate)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(currencyService, times(1)).update(any(), any());  // quantas vezes o metodo foi chamado
    }

    @Test
    @DisplayName("404 - Put /currency - nameCrypto empty")
    public void updateCurrencyNameCryptoEmpty() throws Exception {

        UUID id = UUID.randomUUID();

        CurrencyRequest currencyRequest = CurrencyRequest.builder()
                .nameCrypto("Bitcoin")
                .code("")
                .build();

        mvc.perform(put("/currency/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(currencyRequest)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("Empty Field")))
                .andExpect(jsonPath("$.message", is("nameCrypto: " + currencyRequest.getNameCrypto() + ", code: " + currencyRequest.getCode())));
    }

    @Test
    @DisplayName("404 - Put /currency - code empty")
    public void updateCurrencyCodeEmpty() throws Exception {

        UUID id = UUID.randomUUID();

        CurrencyRequest currencyRequest = CurrencyRequest.builder()
                .nameCrypto("")
                .code("BTC")
                .build();

        mvc.perform(put("/currency/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(currencyRequest)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("Empty Field")))
                .andExpect(jsonPath("$.message", is("nameCrypto: " + currencyRequest.getNameCrypto() + ", code: " + currencyRequest.getCode())));
    }

    @Test
    @DisplayName("404 - Put /currency - id not found")
    public void updateCurrencyByIdNotFound() throws Exception {

        UUID id = UUID.randomUUID();

        ResourceNotFoundException exception = new ResourceNotFoundException("id: " + id);
        doThrow(exception).when(currencyService).update(id, currencyRequestBitcoinValidate);

        ResultActions result = mvc.perform(put("/currency/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(currencyRequestBitcoinValidate)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)));

        assertThat(result).isNotNull();
        result.andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value())); // validar o retorno
    }


    //Delete
    @Test
    @DisplayName("200 - Delete /currency")
    public void deleteCurrency() throws Exception {

        UUID id = UUID.randomUUID();

        mvc.perform(delete("/currency/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        verify(currencyService, times(1)).deleteById(any());
    }

    @Test
    @DisplayName("200 - Delete /currency - id not found")
    public void deleteCurrencyByIdNotFound() throws Exception {

        UUID id = UUID.randomUUID();

        ResourceNotFoundException exception = new ResourceNotFoundException("id: " + id);
        doThrow(exception).when(currencyService).deleteById(id);


        ResultActions result = mvc.perform(delete("/currency/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(currencyService, times(1)).deleteById(any());  // quantas vezes o metodo foi chamado
        assertThat(result).isNotNull();
        result.andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value())); // validar o retorno
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}