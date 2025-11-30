package com.FreteGen.FreteGen.service;

import com.FreteGen.FreteGen.dto.FreightQuoteDTO;
import com.FreteGen.FreteGen.dto.FreightRequestDTO;
import com.FreteGen.FreteGen.repository.ProductRepository;
import com.FreteGen.FreteGen.user.Products;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class MelhorEnvioService {

    private final RestTemplate restTemplate;
    private final HttpHeaders headers;
    private final ProductRepository productRepository;

    public MelhorEnvioService(RestTemplate restTemplate, HttpHeaders melhorEnvioHeaders, ProductRepository productRepository) {
        this.restTemplate = restTemplate;
        this.headers = melhorEnvioHeaders;
        this.productRepository = productRepository;
    }

    public List<FreightQuoteDTO> calculateShipping(FreightRequestDTO dto) {

        Products product = productRepository.findByName(dto.getProductName())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Map<String, Object> body = Map.of(

                "from", Map.of("postal_code", dto.getFromPostalCode()),
                "to", Map.of("postal_code", dto.getToPostalCode()),
                "package", Map.of(
                        "weight", product.getWeightKg(),
                        "height", product.getHeightCm(),
                        "width", product.getWidthCm(),
                        "length", product.getLengthCm()
                ),
                "options", Map.of(
                        "insurance_value", product.getDeclaredValue()
                )
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        String url = "https://www.melhorenvio.com.br/api/v2/me/shipment/calculate";
        ResponseEntity<FreightQuoteDTO[]> response =
                restTemplate.postForEntity(url, entity, FreightQuoteDTO[].class);

        return List.of(response.getBody());
    }
}
