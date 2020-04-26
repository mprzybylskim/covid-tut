package mprzybylskim.tutorials.covid19tracker.services;

import mprzybylskim.tutorials.covid19tracker.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class VirusDataService {
    public static final String DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private List<LocationStats> stats = new ArrayList<>();
    /*Spring calls methods annotated with @PostConstruct only once, just after the initialization of bean properties. Keep in mind that these methods will run even if there is nothing to initialize.

The method annotated with @PostConstruct can have any access level but it can't be static.*/
    @PostConstruct
    @Scheduled(cron = "* 12 * * * *")
    public void fetchData() throws IOException, URISyntaxException, InterruptedException {
        List<LocationStats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(DATA_URL))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
        StringReader in = new StringReader(response.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
        for (CSVRecord record : records) {
            LocationStats locationStat = new LocationStats();
            locationStat.setState(record.get("Province/State"));
            locationStat.setCountry(record.get("Country/Region"));
            int latest = Integer.valueOf((record.get(record.size()-1)));
            int previous = Integer.valueOf(record.get(record.size()-2));
            locationStat.setTotalCases(latest);
            locationStat.setDifferenceADay(latest-previous);
            newStats.add(locationStat);
        }
        this.stats=newStats;
    }

    public List<LocationStats> getStats() {
        return stats;
    }
}
