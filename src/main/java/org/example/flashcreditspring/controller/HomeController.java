package org.example.flashcreditspring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller

public class HomeController {



    @GetMapping("/gay")
    public String gay() {
        return "listOfApproveLoan";
    }
}
