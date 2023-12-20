package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UIController {

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/add")
    public String Add() {
        return "add";
    }

    @GetMapping("/update")
    public String update() {
        return "update";
    }

    @GetMapping("/search")
    public String Search() {
        return "search";
    }

    @GetMapping("/viewall")
    public String viewAll() {
        return "viewall";
    }

    @GetMapping("/delete")
    public String delete() {
        return "delete";
    }
}
