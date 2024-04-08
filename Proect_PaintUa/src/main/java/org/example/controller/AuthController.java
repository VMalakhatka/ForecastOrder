package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "auth")
public class AuthController {

//    private final UserService userService;
//
//    @Autowired
//    public AuthController( UserService userService) {
//        this.userService = userService;
//    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "auth/login";
    }

//    @GetMapping("/registration")
//    public String getRegistrationPage(@ModelAttribute("registrationForm") UserRegistrationFormData userFormData) {
//        return "auth/registration";
//    }
//
//    @PostMapping("/registration")
//    public String completeRegistration(
//            @ModelAttribute("registrationForm") UserRegistrationFormData userFormData,
//            BindingResult result
//    ) {
//        userValidator.validate(userFormData, result);
//        if (result.hasErrors()) return "auth/registration";
//        userService.createUser(userFormData);
//        return "redirect:/login";
//    }
}
