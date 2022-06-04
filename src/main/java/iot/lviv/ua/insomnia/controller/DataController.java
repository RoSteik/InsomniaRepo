/**
 * @author RoSteik (Telegram: @RoSteik)
 * Project: Insomnia
 * Package: iot.lviv.ua.insomnia.controller
 * Class: DataController
 */

package iot.lviv.ua.insomnia.controller;

import iot.lviv.ua.insomnia.logic.DataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/all")
public class DataController {

    private final DataService dataService;

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }


    @GetMapping("/accel")
    public String[] getAccelData() {
        String[] text = dataService.divOnBlocks(dataService.getTextFromFile());
        String[] result = new String[text.length];

        int i=0;
        for (String dataBlock: text) {
            result[i] = dataService.divOnAccelDataBlocks(dataBlock)[1];
            i++;
        }
        return result;
    }

    @GetMapping("/gps")
    public String getGPS_Data() {
        return dataService.lastDataBlock(dataService.getTextFromFile());
    }

    @GetMapping("/index")
    public String getDataFromHtml() {
        return dataService.findDataFromHtml();
    }



}
















