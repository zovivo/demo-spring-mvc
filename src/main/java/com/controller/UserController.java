package com.controller;

import com.input.create.UserFormCreate;
import com.input.update.UserFormUpdate;
import com.model.User;
import com.service.impl.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/","/list"})
    public ModelAndView index() {
        logger.info("home page");
        ModelAndView model = new ModelAndView("views/user/index");
        model.addObject("userSearch", new User());
        model.addObject("users", userService.getAll());
        return model;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model model) {
        logger.info("detail student");
        User user = userService.findOne(id);
        if (user == null) {
            model.addAttribute("css", "danger");
            model.addAttribute("msg", "user not found");
        }
        model.addAttribute("user", user);
        return "views/user/user";
    }

    @RequestMapping(value = "/user/{id}/delete")
    public String deleteStudent(@PathVariable("id") Long id, final RedirectAttributes redirectAttributes) {
        logger.info("delete user");
        if (userService.delete(id) > 0) {
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", "User is deleted!");
        } else {
            redirectAttributes.addFlashAttribute("css", "error");
            redirectAttributes.addFlashAttribute("msg", "Delete user fails!!!!");
        }

        return "redirect:/";

    }

    @RequestMapping(value = "/user/add", method = RequestMethod.GET)
    public String newStudent(Model model) {
        UserFormCreate userFormCreater = new UserFormCreate();
        logger.info("model: "+model.toString());
        model.addAttribute("userFormCreate", userFormCreater);
        return "views/user/user-form-create";

    }

    @RequestMapping(value = "/user/{id}/edit", method = RequestMethod.GET)
    public String editStudent(@PathVariable("id") Long id, Model model) {

        User user = userService.findOne(id);
        model.addAttribute("userFormUpdate", user);

        return "views/user/user-form-update";

    }

    @RequestMapping(value = "/update-user", method = RequestMethod.POST)
    public String updateStudent(@ModelAttribute UserFormUpdate userFormUpdate) {
        logger.info("update user");
        User user = new User(userFormUpdate.getId(),userFormUpdate.getUserName(),userFormUpdate.getEmail());
        userService.update(user);
        return "redirect:/";

    }

    @RequestMapping(value = "/create-user", method = RequestMethod.POST)
    public String createStudent(@ModelAttribute UserFormCreate userFormCreate) {
        logger.info("create user");
        logger.info("user: "+userFormCreate.toString());
        User user = userService.create(userFormCreate);
        return "redirect:/";
    }

}
