package org.example.controllers;

import org.example.entities.Message;
import org.example.repositories.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {
    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping
    public String main(Model model) {
        model.addAttribute("messages", messageRepo.findAll());
        return "main";
    }

    @PostMapping
    public String add(@RequestParam("text") String text,
                      @RequestParam("tag") String tag,
                      Model model) {
        Message message = new Message(text, tag);
        messageRepo.save(message);

        model.addAttribute("messages", messageRepo.findAll());
        return "main";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam("tag") String tag, Model model) {
        if(tag != null && !tag.isEmpty()) {
            model.addAttribute("messages", messageRepo.findByTag(tag));
        } else {
            model.addAttribute("messages", messageRepo.findAll());
        }
        return "main";
    }
}
