package uz.pdp.springboot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springboot.dto.TransactionRequestDto;
import uz.pdp.springboot.dto.TransactionResponseDto;
import uz.pdp.springboot.dto.UserTransactions;
import uz.pdp.springboot.service.TransactionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/create")
    public String create(@Valid @RequestBody TransactionRequestDto requestDto) {
        return transactionService.create(requestDto);
    }

    @PostMapping("/user-transactions/{id}")
    public ResponseEntity<List<TransactionResponseDto>> getUserTransactions(
            @PathVariable UUID id,
            @RequestBody UserTransactions transactions
    ) {
        return new ResponseEntity<>(transactionService.getUserTransaction(id, transactions), HttpStatus.OK);
    }
    @PostMapping("/get-all")
    public ResponseEntity<List<TransactionResponseDto>> getAll(@RequestBody UserTransactions transactions){
        List<TransactionResponseDto> allTransaction = transactionService.getAllTransaction(transactions);
        return ResponseEntity.ok(allTransaction);
    }

}
