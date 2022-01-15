package com.webnewsproject.controller;

import com.webnewsproject.domain.Roles;
import com.webnewsproject.domain.Status;
import com.webnewsproject.service.RolesService;
import com.webnewsproject.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class StatusController {
    @Autowired
    private StatusService statusService;

    @GetMapping("/admin/manage-status")
    public String manageStatus(Model model){
        model.addAttribute("status", statusService.findAll());
        return "admin/adminManageStatus";
    }

    @GetMapping("/admin/add-status")
    public String addStatus(Model model){
        model.addAttribute("status",new Status());
        return "admin/adminAddStatus";
    }
    @PostMapping("/admin/add-status")
    public String doAddStatus(@ModelAttribute("status")Status status){
        statusService.save(status);
        return "redirect:/admin/manage-status";
    }

    @GetMapping("/admin/manage-status/delete/{id}")
    public String delete(@PathVariable("id")int id){
        Status status = statusService.findById(id);

        if (status != null){
            statusService.delete(status);
            return "redirect:/admin/manage-status";
        }
        return "error/404";
    }

    @GetMapping("/admin/manage-status/edit/{id}")
    public String edit(@PathVariable("id")int id, Model model){
        Status status = statusService.findById(id);

        if (status != null){
            model.addAttribute("status",status);
            return "admin/adminAddStatus";
        }
        return "error/404";

    }

}
