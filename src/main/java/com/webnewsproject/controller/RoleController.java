package com.webnewsproject.controller;

import com.webnewsproject.domain.Roles;
import com.webnewsproject.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RoleController {
    @Autowired
    private RolesService rolesService;
    @GetMapping("/admin/manage-role")
    public String manageRole(Model model){
        model.addAttribute("roles", rolesService.findAll());
        return "admin/adminManageRole";
    }

    @GetMapping("/admin/add-role")
    public String addRole(Model model){
        model.addAttribute("role",new Roles());
        return "admin/adminAddRole";
    }
    @PostMapping("/admin/add-role")
    public String doAddRole(@ModelAttribute("role")Roles role){
        rolesService.save(role);
        return "redirect:/admin/manage-role";
    }

    @GetMapping("/admin/manage-role/delete/{id}")
    public String delete(@PathVariable("id")int id){
        Roles role = rolesService.findById(id);

        if (role != null){
            rolesService.delete(role);
            return "redirect:/admin/manage-role";
        }
        return "error/404";
    }

    @GetMapping("/admin/manage-role/edit/{id}")
    public String edit(@PathVariable("id")int id, Model model){
        Roles role = rolesService.findById(id);

        if (role != null){
            model.addAttribute("role",role);
            return "admin/adminAddRole";
        }
        return "error/404";

    }

}
