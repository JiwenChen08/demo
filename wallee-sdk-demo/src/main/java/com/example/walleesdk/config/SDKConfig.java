package com.example.walleesdk.config;

import com.wallee.sdk.ApiClient;
import com.wallee.sdk.service.AccountsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SDKConfig {

    @Bean
    ApiClient apiClient() {
        // this account has only one permission of api "/api/v2.0/accounts"
        return new ApiClient(152823L, "wdBQPuTABijs0anuZa/apqTgY3oL4bOBuR0MS80unv8=");
    }

    @Bean
    AccountsService accountsService(ApiClient apiClient) {
        return new AccountsService(apiClient);
    }


}
