package Week4.controller;

import Week4.model.Series;
import Week4.service.CricketGameService;
import Week4.service.Impl.CricketGameServiceImpl;
import Week4.service.SeriesService;
import Week4.service.Impl.SeriesServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {
    private SeriesService seriesService;
    private CricketGameService cricketGameService;

    public ServiceController(SeriesServiceImpl seriesService, CricketGameServiceImpl cricketGameService) {
        this.seriesService = seriesService;
        this.cricketGameService = cricketGameService;
    }

    @PostMapping("/startSeries")
    public ResponseEntity<String> startSeries(@RequestBody Series series){
        if(series.getNumnberOfOvers() <= 0 || series.getNumberOfMatches() <= 0){
            return new ResponseEntity<>("Invalid parameters passed", HttpStatus.BAD_REQUEST);
        }
        else{
            seriesService.addSeries(series);

            for(int i=0;i<series.getNumberOfMatches();i++){
                cricketGameService.startGame(series.getNumnberOfOvers(),series);
            }
            return new ResponseEntity<>("Series completed successfully", HttpStatus.OK);
        }
    }
}
