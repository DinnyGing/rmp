package com.my.mrp.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.my.mrp.models.AverageTemperature;
import com.my.mrp.models.Heating;
import com.my.mrp.models.Room;
import com.my.mrp.repo.AverageTemperatureRepository;
import com.my.mrp.repo.HeatingService;
import com.my.mrp.repo.RoomService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Controller
@PropertySource("classpath:temp.properties")
@CrossOrigin
public class AppController {
    Room bedRoom;
    Room kidRoom;
    Room kitchen;
    Room livingRoom;
    Room bathRoom;
    Room closet;
    RoomService roomRepository;
    HeatingService heatingRepository;
    AverageTemperatureRepository averageTemperatureRepository;
    @Value(value = "${temperature.N1}")
    double N1;
    @Value(value = "${temperature.N2}")
    double N2;

    @Autowired
    public AppController(RoomService roomRepository, HeatingService heatingRepository, AverageTemperatureRepository averageTemperatureRepository) {
        this.roomRepository = roomRepository;
        this.heatingRepository = heatingRepository;
        this.averageTemperatureRepository = averageTemperatureRepository;
        init();
    }
    private void init(){
        Heating heatingBedRoom = heatingRepository.save( new Heating(1, false));
        Heating heatingKidRoom = heatingRepository.save( new Heating(2, false));
        Heating heatingKitchen = heatingRepository.save( new Heating(3, false));
        Heating heatingLivingRoom = heatingRepository.save( new Heating(4, false));
        Heating heatingBathRoom = heatingRepository.save( new Heating(5, false));
        Heating heatingCloset = heatingRepository.save( new Heating(6, false));
        bedRoom = roomRepository.save(new Room("bedRoom", true, 0, heatingBedRoom));
        kidRoom = roomRepository.save(new Room("kidRoom", false, 0, heatingKidRoom));
        kitchen = roomRepository.save(new Room("kitchen", false, 0, heatingKitchen));
        livingRoom = roomRepository.save(new Room("livingRoom", false, 0, heatingLivingRoom));
        bathRoom = roomRepository.save(new Room("bathRoom", false, 0, heatingBathRoom));
        closet = roomRepository.save(new Room("closet", false, 0, heatingCloset));
    }

    private void addModel(Model model){
        model.addAttribute("kitchen", kitchen);
        model.addAttribute("livingRoom", livingRoom);
        model.addAttribute("bathRoom", bathRoom);
        model.addAttribute("closet", closet);
        model.addAttribute("bedRoom", bedRoom);
        model.addAttribute("kidRoom", kidRoom);
        model.addAttribute("kitchen_t", kitchen.getHeating().getTemperature()  +
                isHeated(kitchen));
        model.addAttribute("livingRoom_t", livingRoom.getHeating().getTemperature() +
                isHeated(livingRoom));
        model.addAttribute("bedRoom_t", bedRoom.getHeating().getTemperature() +
                isHeated(bedRoom));
        model.addAttribute("closet_t", closet.getHeating().getTemperature() +
                isHeated(closet));
        model.addAttribute("bathRoom_t", bathRoom.getHeating().getTemperature() +
                isHeated(bathRoom));
        model.addAttribute("kidRoom_t", kidRoom.getHeating().getTemperature() +
                isHeated(kidRoom));
        model.addAttribute("N1", N1);
        model.addAttribute("N2", N2);
    }
    private String isHeated(Room room){
        return room.getHeating().isHeating() ? "°C is heating" : " isn`t heating";
    }
    @GetMapping("/")
    public String main(Model model) {
        addModel(model);
        return "index";
    }
    @PostMapping("/turnHeating")
    public String turnHeating(@RequestBody Room room) {
        switch (room.getName()){
            case "livingRoom":
                livingRoom.setHeating(room.getHeating());
                break;
            case "bathRoom":
                bathRoom.setHeating(room.getHeating());
                break;
            case "bedRoom":
                bedRoom.setHeating(room.getHeating());
                break;
            case "kitchen":
                kitchen.setHeating(room.getHeating());
                break;
            case "kidRoom":
                kidRoom.setHeating(room.getHeating());
                break;
            case "closet":
                closet.setHeating(room.getHeating());
                break;
        }
        roomRepository.save(room);
        System.out.println("best");
        return "index";
    }
    @PostConstruct
    public void startDaemonThread() {
        Thread daemonThread = new Thread(() -> {
            while (true) {
                try {
                    livingRoom.setTemperature(N1, N2);
                    bathRoom.setTemperature(N1, N2);
                    bedRoom.setTemperature(N1, N2);
                    kitchen.setTemperature(N1, N2);
                    kidRoom.setTemperature(N1, N2);
                    closet.setTemperature(N1, N2);
                    double average = averageTemp();
                    roomRepository.save(livingRoom);
                    roomRepository.save(bathRoom);
                    roomRepository.save(bedRoom);
                    roomRepository.save(kitchen);
                    roomRepository.save(kidRoom);
                    roomRepository.save(closet);
                    averageTemperatureRepository.save(new AverageTemperature(average));
                    Thread.sleep(3700*60 );
                    Model model = new ConcurrentModel();
                    main(model);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        daemonThread.setDaemon(true);
        daemonThread.start();
    }
    private double averageTemp(){
        return (getTempRoom(livingRoom) + getTempRoom(kidRoom) + getTempRoom(kitchen) +
                getTempRoom(closet) + getTempRoom(bathRoom) + getTempRoom(bedRoom))/6;
    }
    private double getTempRoom(Room room){
        return room.getHeating().getTemperature();
    }
    @PostMapping("/turnLightKitchen")
    public String turnLightKitchen(@RequestParam String lightKitchen, Model model){
        System.out.println(lightKitchen.equals("On"));
        kitchen.setTurn(lightKitchen.equals("On"));
        roomRepository.save(kitchen);
        addModel(model);
        return "redirect:/";
    }
    @PostMapping("/turnLightLivingRoom")
    public String turnLightLivingRoom(@RequestParam String lightLivingRoom, Model model){
        livingRoom.setTurn(lightLivingRoom.equals("On"));
        roomRepository.save(livingRoom);
        addModel(model);
        return "redirect:/";
    }
    @PostMapping("/turnLightBathRoom")
    public String turnLightBathRoom(@RequestParam String lightBathRoom, Model model){
        bathRoom.setTurn(lightBathRoom.equals("On"));
        roomRepository.save(bathRoom);
        addModel(model);
        return "redirect:/";
    }
    @PostMapping("/turnLightCloset")
    public String turnLightCloset(@RequestParam String lightCloset, Model model){
        closet.setTurn(lightCloset.equals("On"));
        roomRepository.save(closet);
        addModel(model);
        return "redirect:/";
    }
    @PostMapping("/turnLightBedRoom")
    public String turnLightBedRoom(@RequestParam String lightBedRoom, Model model){
        bedRoom.setLight(Double.parseDouble(lightBedRoom));
        roomRepository.save(bedRoom);
        addModel(model);
        return "redirect:/";
    }
    @PostMapping("/turnLightKidRoom")
    public String turnLightKidRoom(@RequestParam String lightKidRoom, Model model){
        kidRoom.setLight(Double.parseDouble(lightKidRoom));
        roomRepository.save(kidRoom);
        addModel(model);
        return "redirect:/";
    }
    @PostMapping("/change")
    public String changeN1N2(@RequestParam String N1, @RequestParam String N2, Model model){
        if(Double.parseDouble(N1) < Double.parseDouble(N2)
                && (Double.parseDouble(N1) > 12 || Double.parseDouble(N1) <= 30)  &&
                (Double.parseDouble(N2) >13 || Double.parseDouble(N2) <=31)){
            Properties properties = new Properties();
            try (FileOutputStream fos = new FileOutputStream("src/main/resources/temp.properties")) {
                properties.setProperty("temperature.N1", N1);
                properties.setProperty("temperature.N2", N2);
                properties.store(fos, "Updated temperature thresholds");
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.N1 = Double.parseDouble(N1);
            this.N2 = Double.parseDouble(N2);
            addModel(model);
        }
        return "redirect:/";
    }
    private String time(LocalDateTime localDateTime){
        String s = String.valueOf(localDateTime).split("T")[1];
        return Integer.parseInt(s.split(":")[0]) + "." + s.split(":")[1];
    }
    @GetMapping("/about")
    public String averageTempPage(Model model){
        List<AverageTemperature> averageTemperatures = averageTemperatureRepository.findAll();
        List<String> times = averageTemperatures.stream().map(i -> time(i.getDate()))
                .collect(Collectors.toList());
        List<String> temperatures = averageTemperatures.stream().map(i -> String.valueOf(i.getTemperature()))
                .collect(Collectors.toList());

        model.addAttribute("times", times);
        model.addAttribute("temperatures", temperatures);
        return "about";
    }
}
