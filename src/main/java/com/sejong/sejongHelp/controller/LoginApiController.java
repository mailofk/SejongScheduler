package com.sejong.sejongHelp.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sejong.sejongHelp.dto.*;
import com.sejong.sejongHelp.service.LoginValidationService;
import com.sejong.sejongHelp.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class LoginApiController {

    private final LoginValidationService loginValidationService;
    private final MemberService memberService;

    //회원가입 진행 메서드
    @PostMapping("/api/join")
    public ApiSuccessForm joinMember(@RequestBody JoinForm joinForm) {

        ApiSuccessForm apiSuccessForm = new ApiSuccessForm();
        apiSuccessForm.setResult(true);

        if (memberService.checkDuplicate(joinForm.getStudentId())) {
            apiSuccessForm.setResult(false);
        }
        if (apiSuccessForm.isResult()) {
            memberService.save(joinForm);
        }

        return apiSuccessForm;
    }

    //오로지 인증 용도로만 사욯할 수 있게끔 진행
    @PostMapping("/api/validation")
    public ApiSuccessForm getValidation(@RequestBody ValidationForm validationForm) {

        String url = "https://auth.imsejong.com/auth?DosejongSession";
        boolean isValidated = loginValidationService.loginValidate(url, validationForm);

        ApiSuccessForm apiSuccessForm = new ApiSuccessForm();
        apiSuccessForm.setResult(isValidated);

        return apiSuccessForm;
    }

    //실제 사용할 api
    //수정필요
    @RequestMapping("/api/login-page")
    public ResponseEntity<String> loginV2(@RequestBody ValidationForm validationForm) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://auth.imsejong.com/auth?DosejongSession";

        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, validationForm, String.class);

            ObjectMapper obj = new ObjectMapper();
            JsonNode jsonNode = obj.readTree(responseEntity.getBody());

            String sendResult = jsonNode.get("msg").asText();
            String validationResult = jsonNode.get("result").get("code").asText();

            return new ResponseEntity(jsonNode, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }


}
