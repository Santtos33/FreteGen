package com.FreteGen.FreteGen.controller;

import com.FreteGen.FreteGen.dto.FreightQuoteDTO;
import com.FreteGen.FreteGen.dto.FreightRequestDTO;
import com.FreteGen.FreteGen.service.MelhorEnvioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/freight")
public class FreightController {

    private final MelhorEnvioService melhorEnvioService;

    public FreightController(MelhorEnvioService melhorEnvioService) {
        this.melhorEnvioService = melhorEnvioService;
    }

    @PostMapping("/calculate-product")
    public ResponseEntity<?> calculateByProduct(@RequestBody FreightRequestDTO dto) {
        List<FreightQuoteDTO> quotes = melhorEnvioService.calculateShipping(dto);
        return ResponseEntity.ok(quotes);
    }
}
