package uz.pdp.springboot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springboot.dto.ServiceRequestDto;
import uz.pdp.springboot.dto.ServiceResponseDto;
import uz.pdp.springboot.service.ServiceEntitiyService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/service")

public class ServiceEntityController {
    private final ServiceEntitiyService service;
    @PostMapping("/create")
    public ResponseEntity<ServiceResponseDto> create(@Valid @RequestBody ServiceRequestDto requestDto){
        ServiceResponseDto save = service.save(requestDto);
        return new ResponseEntity<>(save, HttpStatus.OK);
    }
    @GetMapping("/get-all")
    public ResponseEntity<List<ServiceResponseDto>> getAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size
    ){
       return new ResponseEntity<>(service.getAll(page,size),HttpStatus.OK);
    }
}
