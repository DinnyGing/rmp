package com.my.mrp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.mrp.models.Room;
import com.my.mrp.repo.HeatingService;
import com.my.mrp.repo.RoomService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

@Transactional
@EnableTransactionManagement
@PropertySource("classpath:temp.properties")
public class Frame extends JFrame {
    private Room bedRoom;
    private Room kidRoom;
    private Room kitchen;
    private Room livingRoom;
    private Room bathRoom;
    private Room closet;
    RoomService roomRepository;
    HeatingService heatingRepository;
    @Value(value = "${temperature.N1}")
    double N1;
    @Value(value = "${temperature.N2}")
    double N2;

    public Frame(RoomService roomRepository, HeatingService heatingRepository) {
        this.roomRepository = roomRepository;
        this.heatingRepository = heatingRepository;
        initializeRooms();
        createUI();

        startDaemonThread();
    }



    private void initializeRooms() {
        bedRoom = roomRepository.findById("bedRoom");
        kidRoom = roomRepository.findById("kidRoom");
        kitchen = roomRepository.findById("kitchen");
        livingRoom = roomRepository.findById("livingRoom");
        bathRoom = roomRepository.findById("bathRoom");
        closet = roomRepository.findById("closet");
    }
    private void updateRooms() {
        bedRoom = roomRepository.findById("bedRoom");
        kidRoom = roomRepository.findById("kidRoom");
        kitchen = roomRepository.findById("kitchen");
        livingRoom = roomRepository.findById("livingRoom");
        bathRoom = roomRepository.findById("bathRoom");
        closet = roomRepository.findById("closet");
        getContentPane().removeAll();
        createUI();
        revalidate();
        repaint();
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JTextField n1Field = new JTextField(String.valueOf(N1), 10);
        JTextField n2Field = new JTextField(String.valueOf(N2), 10);
        JButton updateButton = new JButton("Update");

        updateButton.addActionListener(e -> {
            try {
                N1 = Double.parseDouble(n1Field.getText());
                N2 = Double.parseDouble(n2Field.getText());
                Properties properties = new Properties();
                try (FileOutputStream fos = new FileOutputStream("src/main/resources/temp.properties")) {
                    properties.setProperty("temperature.N1", String.valueOf(N1));
                    properties.setProperty("temperature.N2", String.valueOf(N2));
                    properties.store(fos, "Updated temperature thresholds");
                } catch (IOException exe) {
                    exe.printStackTrace();
                }
                updateRooms();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(Frame.this, "Invalid input. Please enter numeric values.");
            }
        });

        buttonPanel.add(new JLabel("Heating turn on, when temperature is: "));
        buttonPanel.add(n1Field);
        buttonPanel.add(new JLabel("Heating turn off, when temperature is: "));
        buttonPanel.add(n2Field);
        buttonPanel.add(updateButton);

        return buttonPanel;
    }
    private void createUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Room Management System");
        setSize(890, 690);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 3));



        addRoomPanel(mainPanel, bedRoom);
        addRoomPanel(mainPanel, kidRoom);
        addRoomPanel(mainPanel, kitchen);
        addRoomPanel(mainPanel, livingRoom);
        addRoomPanel(mainPanel, bathRoom);
        addRoomPanel(mainPanel, closet);

        add(mainPanel);

        mainPanel.add(createButtonPanel());
    }

    private void addRoomPanel(JPanel mainPanel, Room room) {
        JPanel roomPanel = new JPanel();
        roomPanel.setLayout(new GridLayout(2, 2));
        roomPanel.setBorder(BorderFactory.createTitledBorder(room.getName()));

        JLabel temperatureLabel = new JLabel("Temperature: " + room.getHeating().getTemperature());
        JLabel heatingStatusLabel = new JLabel("Heating: " + (room.getHeating().isHeating() ? "On" : "Off"));
        JButton toggleHeatingButton = new JButton(room.getHeating().isHeating() ? "Turn Off Heating" : "Turn On Heating");
        toggleHeatingButton.addActionListener(e -> {
            boolean newHeatingStatus = !room.getHeating().isHeating();
            room.getHeating().setHeating(newHeatingStatus);
            heatingStatusLabel.setText("Heating: " + (newHeatingStatus ? "On" : "Off"));
            toggleHeatingButton.setText(newHeatingStatus ? "Turn Off Heating" : "Turn On Heating");
            saveRoomData(room);
        });

        roomPanel.add(temperatureLabel);
        roomPanel.add(heatingStatusLabel);
        roomPanel.add(toggleHeatingButton);

        mainPanel.add(roomPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    public void saveRoomData(Room room) {
        try {
            URL url = new URL("http://localhost:8081/turnHeating");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            ObjectMapper mapper = new ObjectMapper();
            String jsonInputString = mapper.writeValueAsString(room);
            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                outputStream.write(input, 0, input.length);
            }
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void startDaemonThread() {
        Thread daemonThread = new Thread(() -> {
            while (true) {
                try {
                    SwingUtilities.invokeLater(() -> {
                        updateRooms();
                    });
                    Thread.sleep(3700*60);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        daemonThread.setDaemon(true);
        daemonThread.start();
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MrpApplication.class, args);
        RoomService roomRepository = context.getBean(RoomService.class);
        HeatingService heatingRepository = context.getBean(HeatingService.class);
        SwingUtilities.invokeLater(() -> {
            Frame frame = new Frame(roomRepository, heatingRepository);
            frame.setVisible(true);
        });
    }
}
