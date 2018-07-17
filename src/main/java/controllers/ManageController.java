package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ManageController extends GenericController {

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @GetMapping(value = "/403")
    public String denied() {

        return "403";
    }

}
