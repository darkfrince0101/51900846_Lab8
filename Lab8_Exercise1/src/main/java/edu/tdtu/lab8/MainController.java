package edu.tdtu.lab8;

import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;


@Controller
public class MainController {


    @Controller
    public class HomeController {

        @GetMapping("/")
        public String home() {
            return "index";
        }

        @GetMapping("/contact")
        public String contactGet() {
            return "contact";
        }

        @PostMapping("/contact")
        public String contactPost(@RequestParam String name, @RequestParam String email, @RequestParam String message, Model model) {
            model.addAttribute("name", name);
            model.addAttribute("email", email);
            model.addAttribute("message", message);
            return "contact-result";
        }

        @GetMapping("/about")
        @ResponseBody
        public String about() {
            return "About this site";
        }

        @ExceptionHandler({HttpRequestMethodNotSupportedException.class, Exception.class})
        public String handleException() {
            return "error";
        }

    }

}
