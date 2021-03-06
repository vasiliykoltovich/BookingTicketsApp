package controllers;

import beans.models.User;
import beans.services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UsersController extends GenericController {

    @Autowired
    private UploadService uploadService;

    @GetMapping("/getUserByName/{name}")
    public ModelAndView getUserByName(@PathVariable("name") String name) {
        List<User> users = userService.getUsersByName(name);
        ModelAndView view = new ModelAndView("users");
        view.addObject("users", users);
        return view;

    }

    @GetMapping("/getUserById/{id}")
    public ModelAndView getUserById(@PathVariable("id") String id) {
        User user = userService.getById(Long.valueOf(id));
        List<User> users=new ArrayList<>();
        users.add(user);
        ModelAndView view = new ModelAndView("users");
        view.addObject("users", users);
        return view;

    }

    @GetMapping("/getUserByEmail/{email}")
    public ModelAndView getUserByEmail(@PathVariable("email") String email) {
        User user = userService.getUserByEmail(email);
        List<User> users=new ArrayList<>();
        users.add(user);
        ModelAndView view = new ModelAndView("users");
        view.addObject("users", users);
        return view;
    }

    @PostMapping(path = "/createUser", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ModelAndView createUser(@RequestBody User userRequest) {
        User user = userService.register(new User(userRequest.getEmail(), userRequest.getName(), userRequest.getBirthday()));
        List<User> users=new ArrayList<>();
        users.add(user);
                ModelAndView view = new ModelAndView("users");
                view.addObject("users", users);
        return view;
    }

    @PostMapping(path = "/createUser")
    @ResponseStatus(HttpStatus.CREATED)
    public ModelAndView createUser(@RequestParam String email, @RequestParam String name,
                                           @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        User user = userService.register(new User(email, name, date));
        List<User> users=new ArrayList<>();
        users.add(user);
        ModelAndView view = new ModelAndView("users");
        view.addObject("users", users);
        return view;
    }

    @DeleteMapping("/deleteUser")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@RequestBody User userRequest) {
        userService.remove(userRequest);
    }


    @DeleteMapping("/deleteUserByEmail")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@RequestParam("email") String email) {
        User user=userService.getUserByEmail(email);
        userService.remove(user);
    }




    @PostMapping("/loadUsers")
    public ModelAndView loadUser(@RequestParam MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        ModelAndView view = new ModelAndView("users");
        List<User> newUsers=new ArrayList<>();

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return view;
        }

        try {
            List<User> list= uploadService.uploadUserFile(file);
            for(User us:list){
                newUsers.add(userService.register(us));
            }

        } catch (IOException e) {
            throw  new RuntimeException(e);
        }

        view.addObject("users", newUsers);
        return view;

    }


//    //pdf
//    @GetMapping(value = "/getBookedTickets", produces = MediaType.APPLICATION_PDF_VALUE)
//    public ModelAndView getBookedTickets(@RequestParam("email") String email) {
//        User user = userService.getUserByEmail(email);
////        List<Ticket> bookedTickets =
//        ModelAndView  view = new ModelAndView("users");
//        view.addObject("users", user);
//        return view;
//    }


}
