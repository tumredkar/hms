package com.bits.hms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bits.hms.entity.User;
import com.bits.hms.service.UserService;
import com.bits.hms.validator.UserValidator;

/**
 * @author: Naichuan Zhang
 * @create: 02-Nov-2019
 **/


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping(value = "/register")
    public String register(Model model) {
        model.addAttribute("userForm", new User());
        return "register";
    }

    @PostMapping(value = "/register")
    public String register(@ModelAttribute("userForm") User userForm, BindingResult result, ModelMap model) {
        userValidator.validate(userForm, result);

        if (result.hasErrors()) {
            return "register";
        }

        userService.save(userForm);

        model.addAttribute("username", userForm.getUsername());

        return "redirect:/login";
    }

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String showLoginPage(ModelMap model){
        return "login";
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public String showWelcomePage(ModelMap model, @RequestParam String name, @RequestParam String password){
        boolean isValidUser = userService.validateUser(name, password);
        if (!isValidUser) {
            model.put("errorMessage", "Invalid Credentials");
            return "login";
        }
        model.put("name", name);
        model.put("password", password);

        model.addAttribute("username", name);
        model.addAttribute("login", "true");

        return "home";
    }


//    @PostMapping(value="/login")
//    public String login(@RequestAttribute("username") String username, @RequestAttribute("password") String password, ModelMap model, BindingResult result){
//        userValidator.validateLogin(result, username, password);
//        if (result.hasErrors()) {
////            model.addAttribute("errorMessage", "Invalid Credentials");
//            return "login";
//        }
//        model.addAttribute("username", username);
//        model.addAttribute("login", "true");
//
//        return "home";
//    }
    @PostMapping(value = "/")
    public String logout(ModelMap model) {
        model.addAttribute("logout", "true");
        return "home";
    }
}