package com.my.mrp.controllers;

import com.my.mrp.models.Bedroom;
import com.my.mrp.models.Room;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppController {
    Bedroom bedRoom;
    Bedroom kidRoom;
    Room kitchen;
    Room livingRoom;
    Room bathRoom;
    Room closet;

    Cookie cookie;

    public AppController() {
        bedRoom = new Bedroom("bedRoom", true, 0);
        kidRoom = new Bedroom("kidRoom", false, 0);
        kitchen = new Room("kitchen", false);
        livingRoom = new Room("livingRoom", false);
        bathRoom = new Room("bathRoom", false);
        closet = new Room("closet", false);
        cookie = new Cookie("time", String.valueOf(0));
        cookie.setMaxAge(-1);
    }

    private void addModel(Model model){
        model.addAttribute("kitchen", kitchen);
        model.addAttribute("livingRoom", livingRoom);
        model.addAttribute("bathRoom", bathRoom);
        model.addAttribute("closet", closet);
        model.addAttribute("bedRoom", bedRoom);
        model.addAttribute("kidRoom", kidRoom);
        model.addAttribute("kitchen_t", kitchen.getTemperature());
        model.addAttribute("livingRoom_t", livingRoom.getTemperature());
        model.addAttribute("bedRoom_t", bedRoom.getTemperature());
        model.addAttribute("closet_t", closet.getTemperature());
        model.addAttribute("bathRoom_t", bathRoom.getTemperature());
        model.addAttribute("kidRoom_t", kidRoom.getTemperature());
    }
    @GetMapping("/")
    public String main(HttpServletRequest request, HttpServletResponse response, Model model) {
        addModel(model);
        Cookie[] cookies = request.getCookies();
        String value = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("time")) {
                    value = cookie.getValue();
                    break;
                }
            }
        }
        if(value.equals(""))
            response.addCookie(cookie);
        return "index";
    }
    @PostMapping("/turnLightKitchen")
    public String turnLightKitchen(@RequestParam String lightKitchen, Model model){
        kitchen.setTurn(lightKitchen.equals("On"));
        addModel(model);
        return "redirect:/";
    }
    @PostMapping("/turnLightLivingRoom")
    public String turnLightLivingRoom(@RequestParam String lightLivingRoom, Model model){
        livingRoom.setTurn(lightLivingRoom.equals("On"));
        System.out.println(model.containsAttribute("tikidRoom_tmer"));
        addModel(model);
        return "redirect:/";
    }
    @PostMapping("/turnLightBathRoom")
    public String turnLightBathRoom(@RequestParam String lightBathRoom, Model model){
        bathRoom.setTurn(lightBathRoom.equals("On"));
        addModel(model);
        return "redirect:/";
    }
    @PostMapping("/turnLightCloset")
    public String turnLightCloset(@RequestParam String lightCloset, Model model){
        closet.setTurn(lightCloset.equals("On"));
        addModel(model);
        return "redirect:/";
    }
    @PostMapping("/turnLightBedRoom")
    public String turnLightBedRoom(@RequestParam String lightBedRoom, Model model){
        bedRoom.setLight(Double.parseDouble(lightBedRoom));
        addModel(model);
        System.out.println(bedRoom.getLight());
        return "redirect:/";
    }
    @PostMapping("/turnLightKidRoom")
    public String turnLightKidRoom(@RequestParam String lightKidRoom, Model model){
        kidRoom.setLight(Double.parseDouble(lightKidRoom));
        addModel(model);
        System.out.println(kidRoom.getLight());
        return "redirect:/";
    }
    @PostMapping("/checkTemperature")
    public String checkTemperature(Model model, HttpServletResponse response){
        livingRoom.generate();
        bathRoom.generate();
        bedRoom.generate();
        kitchen.generate();
        kidRoom.generate();
        closet.generate();
        addModel(model);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
