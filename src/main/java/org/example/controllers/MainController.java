package org.example.controllers;

import org.example.entities.Message;
import org.example.entities.User;
import org.example.repositories.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/")
    public String greeting(Model model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Model model) {
        model.addAttribute("messages", messageRepo.findAll());
        return "main";
    }

    @PostMapping("/main")
    public String add(
                    @AuthenticationPrincipal User user,
                    @RequestParam("text") String text,
                    @RequestParam("tag") String tag,
                    Model model) {
        Message message = new Message(text, tag, user);
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

    @DeleteMapping("/{id}")
    @Transactional
    public String delete(@PathVariable("id") int id) {
        messageRepo.deleteMessagesById(id);
        return "redirect:/main";
    }
}
