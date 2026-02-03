package com.sa.leanning.CarProject.frontController;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/home"})
    public String home(Model model) {

        // later you can load from DB
        model.addAttribute("brands", List.of(
                "Apple", "Samsung", "Xiaomi", "Oppo"
        ));

        return "home";
    }
}

