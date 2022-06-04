/**
 * @authors RoSteik, Oleg (Telegram: @RoSteik)
 * Project: Insomnia
 * Package: iot.lviv.ua.insomnia.logic
 * Class: DataService
 */

package iot.lviv.ua.insomnia.logic;

import org.springframework.stereotype.Service;


import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DataService {

    public String getTextFromFile() {
        String filePath = "src/main/resources/arduinoData/putty.txt";
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
        return dataBlock[dataBlock.length - 2];
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

    public void putLastDataIntoHtmlFile() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/arduinoData/putty.txt"));
             OutputStream fileOutputStream = new FileOutputStream("src/main/resources/arduinoData/insomnia.html");
             PrintStream printStream = new PrintStream(fileOutputStream)) {


            String[] copy = divOnLine(lastDataBlock(getTextFromFile()));
            String[] stringByLine = new String[copy.length * 2 + 1];

            System.out.println(lastDataBlock(getTextFromFile()));

            String temporaryVariable = "";

            String htmlHeader = "<html><head>";
            htmlHeader += "<title>Insomnia</title>";
            htmlHeader += "</head><body>";
            String htmlFooter = "</body></html>";

            int lineNumber = copy.length;
            stringByLine[0] = "<meta http-equiv=\"Refresh\" content=\"2\" />\n";
            for (int i = 0; i < lineNumber; i++) {
                stringByLine[2 * i + 1] = copy[i];
                stringByLine[2 * (i + 1)] = "<br>";
            }

            for (int i = 0; i < lineNumber * 2; i++) {
                temporaryVariable = htmlHeader + stringByLine[0];
                stringByLine[0] = temporaryVariable;

                temporaryVariable = stringByLine[i] + htmlFooter;
                stringByLine[i] = temporaryVariable;

                printStream.println(stringByLine[i]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String findDataFromHtml() {
        putLastDataIntoHtmlFile();
        String filePath = "src/main/resources/arduinoData/insomnia.html";
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


}
