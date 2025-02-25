package com.example.java_mail.Controller;

import com.example.java_mail.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class EmailController {
    @Autowired
    private EmailService emailService;

    @GetMapping("/email-form")
    public  String showEmailForm(){
    return "email-form";
    }

    @PostMapping("/send_email")
    public String sendEmail(@RequestParam String subject, @RequestParam String message, Model model){
        try{
            emailService.sendEmail(subject, message);
            model.addAttribute("message", "Correo enviado con exito!");
        } catch (Exception e){
            e.printStackTrace();
            model.addAttribute("error", "Error al enviar el correo: " +
                    e.getMessage());
        }
        return "email-form";
    }
}
