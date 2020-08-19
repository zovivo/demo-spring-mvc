package com.controller;

import com.exception.CustomException;
import com.input.create.UserFormCreate;
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

@Controller
public class UserController {

    private static final Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/","/list"})
    public ModelAndView index() {
        logger.info("home page");
        ModelAndView model = new ModelAndView("index");
        model.addObject("userSearch", new User());
        model.addObject("users", userService.getAll());
        return model;
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
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

    @RequestMapping(value = "/user/{id}/delete")
    public String delete(@PathVariable("id") Long id, final RedirectAttributes redirectAttributes) {
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
    public String add(Model model) {
        UserFormCreate userFormCreater = new UserFormCreate();
        logger.info("model: "+model.toString());
        model.addAttribute("userFormCreate", userFormCreater);
        return "user-add";

    }

    @RequestMapping(value = "/user/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {

        UserFormUpdate userFormUpdate = new UserFormUpdate(userService.findOne(id));
        model.addAttribute("userFormUpdate", userFormUpdate);

        return "user-edit";

    }

    @RequestMapping(value = "/update-user", method = RequestMethod.POST)
    public String update(@ModelAttribute UserFormUpdate userFormUpdate) {
        userService.update(userFormUpdate);
        return "redirect:/";

    }

    @RequestMapping(value = "/create-user", method = RequestMethod.POST)
    public String create(@ModelAttribute("userFormCreate") @Valid UserFormCreate userFormCreate, BindingResult result) throws CustomException {
        if (result.hasErrors()) {
            return "user-form-create";
        }
        logger.info("create user");
        logger.info("user: "+userFormCreate.toString());
        User user = userService.create(userFormCreate);
        return "redirect:/";
    }

}
