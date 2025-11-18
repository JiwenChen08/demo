package com.example.mockmvc;

import com.example.mockmvc.dto.StoreDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/stores")
public class StoreController {


    @GetMapping("/{storeId}")
    public ResponseEntity<?> query(@PathVariable("storeId") Long storeId) {

        String StoreJson = """
                {
                  "id": 101,
                  "storeNo": "ST-20250201",
                  "name": "Berlin QuickLunch Store",
                  "address": "Alexanderplatz 5, 10178 Berlin, Germany",
                  "email": "berlin-store@quicklunch.com",
                  "phone": "+49 30 12345678",
                  "status": 1
                }
                
                """;
        return ResponseEntity.ok(StoreJson);
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody StoreDTO storeDTO) {

        storeDTO.setId(102L);
        return ResponseEntity.created(URI.create("/stores/102")).body(storeDTO);
    }

}
