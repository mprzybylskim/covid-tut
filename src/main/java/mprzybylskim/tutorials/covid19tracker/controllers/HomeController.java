package mprzybylskim.tutorials.covid19tracker.controllers;

import mprzybylskim.tutorials.covid19tracker.models.LocationStats;
import mprzybylskim.tutorials.covid19tracker.services.VirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    VirusDataService virusDataService;

    @GetMapping("/")
    public String home(Model model) {
        List<LocationStats> list = virusDataService.getStats();
        int totalCases = list.stream().mapToInt(stat -> stat.getTotalCases()).sum();
        int totalNewCases = list.stream().mapToInt(stat -> stat.getDifferenceADay()).sum();
        model.addAttribute("totalCases", totalCases);
        model.addAttribute("totalNewCases", totalNewCases);
        model.addAttribute("locationStats", virusDataService.getStats());
        return "home";//name of html
    }
}
