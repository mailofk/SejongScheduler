package com.sejong.sejongHelp.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sejong.sejongHelp.domain.Member;
import com.sejong.sejongHelp.domain.ToDoList;
import com.sejong.sejongHelp.dto.*;
import com.sejong.sejongHelp.service.LoginValidationService;
import com.sejong.sejongHelp.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LoginApiController {

    private final LoginValidationService loginValidationService;
    private final MemberService memberService;

    //회원가입 진행 메서드
    @PostMapping("/api/join")
    public String joinMember(@RequestBody JoinForm joinForm) {
        memberService.save(joinForm);

//        return "redirect:/login"; //리다이렉트 문제로 UsernamePasswordAuthenticationFilter가 적용되어서, 이렇게 할 수 없음
        //애초에 회원가입 후, 바로 로그인 페이지로 리다이렉트 하는 것은 좋은 방법이 아님

        return "redirect:/main";
    }

    //오로지 인증 용도로만 사욯할 수 있게끔 진행
    @PostMapping("/api/validation")
    @ResponseBody
    public ResponseEntity<String> getValidation(@RequestBody ValidationForm validationForm, Model model) {

        String url = "https://auth.imsejong.com/auth?DosejongSession";
        boolean isValidated = loginValidationService.loginValidate(url, validationForm);

        return new ResponseEntity<String>(String.valueOf(isValidated), HttpStatus.OK);
    }

    //실제 사용할 api
    @RequestMapping("/api/login-page")
    @ResponseBody
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

//    @GetMapping("/api/member-list")
//    @ResponseBody
//    public List<ToDoListForm> getList(@AuthenticationPrincipal CustomUserDetails member) {
//        Long memberId = member.getMemberId();
//        return memberService.findLists(memberId);
//    }

}
