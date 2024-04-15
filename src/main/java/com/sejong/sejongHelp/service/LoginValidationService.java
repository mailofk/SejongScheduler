package com.sejong.sejongHelp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sejong.sejongHelp.dto.ValidationForm;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginValidationService {

    public boolean loginValidate(String url, ValidationForm validationForm) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, validationForm, String.class);

            ObjectMapper obj = new ObjectMapper();
            JsonNode jsonNode = obj.readTree(responseEntity.getBody());

            String sendResult = jsonNode.get("msg").asText();
            String validationResult = jsonNode.get("result").get("code").asText();

            if (sendResult.equals("success") && validationResult.equals("success")) {
                return true;
            }
            return false;

        } catch (Exception e) {
            return false;
        }
    }
}
