package com.example.walleesdk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallee.sdk.ApiException;
import com.wallee.sdk.model.AccountListResponse;
import com.wallee.sdk.model.CurrencyListResponse;
import com.wallee.sdk.service.AccountsService;
import com.wallee.sdk.service.CurrenciesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AccountsServiceTest {

    @Autowired
    private AccountsService accountsService;


    @Test
    public void testGetAccounts() throws ApiException, JsonProcessingException {

        AccountListResponse accounts = accountsService.getAccounts(null, null, null, null, null);

        System.out.println(accounts);

        assertEquals("chenjiwen123123@gmail.com", accounts.getData().get(0).getName());
    }
}
