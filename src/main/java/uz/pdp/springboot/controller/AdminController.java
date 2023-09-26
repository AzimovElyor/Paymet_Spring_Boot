package uz.pdp.springboot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.springboot.dto.StatisticsDto;
import uz.pdp.springboot.service.UserService;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    @GetMapping("/statistics")
    public ResponseEntity<StatisticsDto> statistics(){
        StatisticsDto statisticsData = userService.getStatisticsData();
        return new ResponseEntity<>(statisticsData, HttpStatus.OK);
    }

}
