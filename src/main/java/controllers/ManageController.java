package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManageController extends GenericController {


    @GetMapping("/login")
    public String login(){
        return "login";
    }

//    @GetMapping(value="/logout")
//    public String logout(){
//        return "logout";
//    }
//
//    @GetMapping(value="/denied")
//    public String denied(){
//        return "denied";
//    }

}
