/**
 * @authors RoSteik, Oleg (Telegram: @RoSteik)
 * Project: Insomnia
 * Package: iot.lviv.ua.insomnia.logic
 * Class: DataService
 */

package iot.lviv.ua.insomnia.logic;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DataService {

    public String getTextFromFile() {
        String filePath = "src/main/resources/arduinoData/putty3.txt";
        File file = new File(filePath);
        String text = null;

        try (Scanner scanner = new Scanner(file)) {
            StringBuilder result = new StringBuilder();
            while (scanner.hasNextLine()) {
                result.append(scanner.nextLine()).append("\n");
            }

            text = result.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return text;
    }

    public String[] divByPattern(String text, String pattern) {
        return text.split(pattern);
    }

    public String[] divOnBlocks(String text) {
        String pattern = "---+";
        return divByPattern(text, pattern);
    }

    public String lastDataBlock(String text) {
        String[] dataBlock = text.split("---+");
        return dataBlock[dataBlock.length-2];
    }

    public String[] divOnLine(String text) {
        String pattern = "\n";
        return divByPattern(text, pattern);
    }

    public String[] divOnAccelDataBlocks(String block) {
        String[] lines = new String[2];

        if (divOnLine(block).length > 2) {
            for (int i = 0; i < 2; i++) {
                lines[i] = divOnLine(block)[i] + "\n";
            }
        } else {
            for (int i = 0; i < 2; i++) {
                lines[i] = "Not";
            }
        }
        return lines;
    }

    public String[] divOnGPS_DataBlocks(String block) {
        String[] lines = new String[9];

        if (divOnLine(block).length > 14) {
            for (int i = 0; i < 9; i++) {
                lines[i] = divOnLine(block)[i + 6] + "\n";
            }
        } else {
            for (int i = 0; i < 8; i++) {
                lines[i] = "Not";
            }
        }
        return lines;

    }

    public String getDateTime(String timeLine) {
        Pattern pattern = Pattern.compile("\\d{1,2}/?\\d{1,2}/?\\d{4}\\s?\\d{1,2}:?\\d{1,2}:?\\d{1,2}\\s?\\d{3}");
        Matcher time = pattern.matcher(timeLine);
        if (time.find()) {
            return timeLine.substring(time.start(), time.end());
        }
        return "Not found";

    }




}
