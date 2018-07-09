package beans.controllers;

import beans.models.User;
import beans.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class UsersController extends GenericController {


    @GetMapping("/getUserByName/{name}")
    public ModelAndView getUserByName(@PathVariable("name") String name){
        List<User> users=userService.getUsersByName(name);
        ModelAndView view = new ModelAndView("users");
        view.addObject("users", users);
        return view;

    }

    @GetMapping("/getUserById/{id}")
    public ModelAndView getUserById(@PathVariable("id") String id){
        User users=userService.getById(Long.valueOf(id));
        ModelAndView view = new ModelAndView("users");
        view.addObject("users", users);
        return view;

    }


    @GetMapping("/getUserByEmail/{email}")
    public ModelAndView getUserByEmail(@PathVariable("email") String email){
        User user=userService.getUserByEmail(email);
        ModelAndView view = new ModelAndView("users");
        view.addObject("users", user);
        return view;
    }


    @PostMapping(path="/createUser", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createUser(@RequestBody User userRequest){
        User users=userService.register(new User(userRequest.getEmail(), userRequest.getName(), userRequest.getBirthday()));
//        ModelAndView view = new ModelAndView("users");
//        view.addObject("users", users);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @PostMapping(path="/createUser")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> createUser(@RequestParam String email,
                                           @RequestParam String name,
                                           @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        User user=userService.register(new User(email, name, date));
//        ModelAndView view = new ModelAndView("users");
//        view.addObject("users", users);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@RequestBody User userRequest){
        userService.remove(userRequest);
    }



    @GetMapping("/getBookedTickets")
    public ModelAndView getBookedTickets(@PathVariable("email") String email){
        User user=userService.getUserByEmail(email);
        ModelAndView view = new ModelAndView("users");
        view.addObject("users", user);
        return view;
    }


    @GetMapping("/getUsers")
    public ModelAndView getUsers(){
        List<User> user=userService.getUsersByName("Vasil Koltovich");
        ModelAndView view = new ModelAndView("users");
        view.addObject("users", user);
        return view;
    }
}
