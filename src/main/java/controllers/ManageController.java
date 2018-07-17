package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ManageController extends GenericController {

//    @GetMapping("/login")
//    public ModelAndView login(@RequestParam(value = "error",required = false) String error,
//                        @RequestParam(value = "logout",	required = false) String logout) {
//
//        ModelAndView model = new ModelAndView();
//        if (error != null) {
//            model.addObject("error", "Invalid Credentials provided.");
//        }
//
//        if (logout != null) {
//            model.addObject("message", "Logged out successfully.");
//        }
//
//        model.setViewName("login");
//        return model;
//    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String logout() {
        return "login";
    }


    @GetMapping(value = "/403")
    public String denied() {
        return "403";
    }

}
