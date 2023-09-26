package uz.pdp.springboot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springboot.dto.CardRequestDto;
import uz.pdp.springboot.dto.CardResponseDto;
import uz.pdp.springboot.service.CardService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/card")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;
    @GetMapping("/user-cards/{id}")
    public ResponseEntity<List<CardResponseDto>> getUserCards(
            @PathVariable UUID id,
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false,defaultValue = "3") int size
    ){
      return new ResponseEntity<>(cardService.getUserCards(id, page,size), HttpStatus.OK);
    }
    @GetMapping("/get-all")
    public ResponseEntity<List<CardResponseDto>> getAll(
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false,defaultValue = "3") int size
    ){
        return  new ResponseEntity<>(cardService.getAll(page,size),HttpStatus.OK);
    }
    @GetMapping("/find-card/{id}")
    public ResponseEntity<CardResponseDto> findById(
            @PathVariable UUID id){
      return  new ResponseEntity<>(cardService.findByCardId(id),HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<CardResponseDto> create( @Valid @RequestBody CardRequestDto cardRequestDto){
    return new ResponseEntity<>(cardService.create(cardRequestDto),HttpStatus.CREATED);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<CardResponseDto> updateCardById(
            @PathVariable UUID id,
             @RequestBody CardRequestDto requestDto ){
       return new ResponseEntity<>(cardService.updateCard(id,requestDto),HttpStatus.OK);
    }

    @DeleteMapping("/delete-card/{id}")
    public ResponseEntity<String> deleteCardById(@PathVariable UUID id){
      return new ResponseEntity<>(cardService.deleteCardById(id), HttpStatus.OK);
    }
}
