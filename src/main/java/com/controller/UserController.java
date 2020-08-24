package com.controller;

import com.exception.CustomException;
import com.input.create.UserFormCreate;
import com.input.login.UserFormLogin;
import com.input.search.UserSearch;
import com.input.update.UserFormUpdate;
import com.model.User;
import com.service.impl.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"", "/", "/list"})
    public ModelAndView list() {
        logger.info("list user page");
        ModelAndView model = new ModelAndView("list");
        model.addObject("userSearch", new UserSearch());
        model.addObject("users", userService.getAll());
        return model;
    }

    @RequestMapping(value = "/search")
    public ModelAndView search(@ModelAttribute UserSearch userSearch) {
        logger.info("list user page with search");
        ModelAndView model = new ModelAndView("list");
        List<User> users = userService.search(userSearch);
        model.addObject("users", users);
        return model;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model model) {
        logger.info("detail user");
        User user = userService.findOne(id);
        if (user == null) {
            model.addAttribute("css", "danger");
            model.addAttribute("msg", "user not found");
        }
        model.addAttribute("user", user);
        return "user";
    }

    @RequestMapping(value = "/{id}/delete")
    public String delete(@PathVariable("id") Long id, final RedirectAttributes redirectAttributes) {
        logger.info("delete user");
        if (userService.delete(id) > 0) {
            redirectAttributes.addFlashAttribute("css", "success");
            redirectAttributes.addFlashAttribute("msg", "User is deleted!");
        } else {
            redirectAttributes.addFlashAttribute("css", "error");
            redirectAttributes.addFlashAttribute("msg", "Delete user fails!!!!");
        }
        return "redirect:/user/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        UserFormCreate userFormCreate = new UserFormCreate();
        logger.info("model: " + model.toString());
        model.addAttribute("userFormCreate", userFormCreate);
        return "user-add";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {
        UserFormUpdate userFormUpdate = new UserFormUpdate(userService.findOne(id));
        model.addAttribute("userFormUpdate", userFormUpdate);
        return "user-edit";
    }

    @RequestMapping(value = "/update-user", method = RequestMethod.POST)
    public String update(@ModelAttribute("userFormUpdate") @Valid UserFormUpdate userFormUpdate, BindingResult result) {
        if (result.hasErrors()) {
            return "user-edit";
        }
        userService.update(userFormUpdate);
        return "redirect:/user/list";
    }

    @RequestMapping(value = "/create-user", method = RequestMethod.POST)
    public String create(@ModelAttribute("userFormCreate") @Valid UserFormCreate userFormCreate, BindingResult result) throws CustomException {
        if (result.hasErrors()) {
            return "user-add";
        }
        logger.info("create user");
        logger.info("user: " + userFormCreate.toString());
        User user = userService.create(userFormCreate);
        return "redirect:/user/list";
    }

    // Login form
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginForm(Model model) {
        UserFormLogin userFormLogin = new UserFormLogin();
        model.addAttribute("userFormLogin", userFormLogin);
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute("userFormLogin") UserFormLogin userFormLogin, Model model) {
        User user = userService.login(userFormLogin);
        if (user != null)
            return "redirect:/user/list";
        else
            return "login";
    }

}
