package com.becoder.user_management_app.Controller;

import com.becoder.user_management_app.Entity.UserDtls;
import com.becoder.user_management_app.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/createUser")
    public String createUser(UserDtls userDtls) {
        userService.createUser(userDtls);
        return "redirect:/login";
    }

    @GetMapping("/live-camera")
    public String liveCamera() {
        return "live";
    }

    @GetMapping("/analytics")
    public String analytics() {
        return "analytics";
    }
}