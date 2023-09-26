package uz.pdp.springboot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.springboot.dto.PaymentRequestDto;
import uz.pdp.springboot.dto.PaymentResponseDto;
import uz.pdp.springboot.service.PaymentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;
    @GetMapping("/create")
    public ResponseEntity<PaymentResponseDto> create(@Valid @RequestBody PaymentRequestDto requestDto){
     return new ResponseEntity<>(paymentService.save(requestDto), HttpStatus.OK);
    }
}
