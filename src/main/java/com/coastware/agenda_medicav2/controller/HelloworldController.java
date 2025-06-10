package com.coastware.agenda_medicav2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/helloWorld")
public class HelloworldController {

    @GetMapping
    public String helloWorld() {
        return "Hello World";
    }
}
