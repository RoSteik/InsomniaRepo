package iot.lviv.ua.insomnia;

import iot.lviv.ua.insomnia.logic.DataService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InsomniaApplication {

    public static void main(String[] args) {
        SpringApplication.run(InsomniaApplication.class, args);

//        DataService dt = new DataService();
//        String text = dt.getTextFromFile();
//        System.out.println(dt.lastDataBlock(text));
    }

}
