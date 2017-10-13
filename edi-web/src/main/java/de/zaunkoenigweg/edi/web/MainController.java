package de.zaunkoenigweg.edi.web;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String greeting(Model model) {
        model.addAttribute("timestamp", LocalDateTime.now());
        return "start";
    }

}
