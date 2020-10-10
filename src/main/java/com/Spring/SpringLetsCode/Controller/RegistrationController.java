package com.Spring.SpringLetsCode.Controller;

import com.Spring.SpringLetsCode.domain.DTO.CaptchaResponseDTO;
import com.Spring.SpringLetsCode.Model.User;
import com.Spring.SpringLetsCode.service.UserService;
import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Value("${recaptcha.secret}")
    String secret;

    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @GetMapping("/registration")
    String login(@ModelAttribute("message") String message) {
        return "/registration";
    }

    @PostMapping("/registration")
    public String addUser(
           // @Nullable
            @RequestParam("g-recaptcha-response") String captchaResponse,
                          @RequestParam("password2") String password2,
                          @Valid User user,
                          BindingResult bindingResult,
                          Model model) {
        String url = String.format(CAPTCHA_URL, secret ,captchaResponse);
        CaptchaResponseDTO response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDTO.class);

                if(!response.isSuccess()) {
                  model.addAttribute("captchaError","Fill captcha");
                }

        boolean isConfirmEmpty = StringUtils.isEmpty(password2);
        if (isConfirmEmpty) {
            model.addAttribute("password2Error", "Password confirmation cannot be empty");
        }
        if (user.getPassword() != null && !user.getPassword().equals(password2)) {
            model.addAttribute("passwordError", "Passwords are different!");
        }
        if (isConfirmEmpty || bindingResult.hasErrors() || !response.isSuccess()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
        }


        boolean containAnyError = model.asMap().keySet().stream()
                .anyMatch(key -> key.contains("Error"));
        if (containAnyError) {
            return "registration";
        }
        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }

        return "redirect:/login";
    }
/*
    @PostMapping("/registration")
    public String addUser(
                          @Valid User user,
                          BindingResult bindingResult,
                          Model model) {
        if(user.getPassword() != null && !user.getPassword().equals(user.getPassword2())) {
           model.addAttribute("passwordError","Passwords are different");
        }
        if(bindingResult.hasErrors()) {
          Map<String,String> errors =  ControllerUtils.getErrors(bindingResult);

          model.mergeAttributes(errors);
          return "registration";
        }

        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exists!");
            return "registration";
        }
        return "redirect:/login";
    }*/

    @GetMapping("/activate/{code}")
    public String activate(Model model,
                           @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code is not found!");
        }
        return "login";
    }


}

