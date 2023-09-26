package uz.pdp.springboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.springboot.dto.TransactionRequestDto;
import uz.pdp.springboot.dto.TransactionResponseDto;
import uz.pdp.springboot.dto.UserTransactions;
import uz.pdp.springboot.entity.Card;
import uz.pdp.springboot.entity.Transaction;
import uz.pdp.springboot.entity.User;
import uz.pdp.springboot.enums.TransactionRole;
import uz.pdp.springboot.repository.CardRepository;
import uz.pdp.springboot.repository.TransactionRepository;
import uz.pdp.springboot.repository.UserRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    public String create(TransactionRequestDto createDto){
           Optional<User> owner = userRepository.findByIdAndIsActiveFalse(createDto.getOwnerId());
           if (owner.isEmpty()) {
               throw new RuntimeException("User bu id %s bilan topilmadi".formatted(createDto.getOwnerId()));
           }
           Optional<Card> senderCard = findByCardByNumber(createDto.getSenderCardNumber());
           Optional<Card> receiverCard = findByCardByNumber(createDto.getReceiverCardNumber());

           if (senderCard.isEmpty()) {
               throw new RuntimeException("Sender Card Raqami  %s topilmadi".formatted(createDto.getSenderCardNumber()));
           }
           if (receiverCard.isEmpty()) {
               throw new RuntimeException("Receiver Card Raqami  %s topilmadi".formatted(createDto.getSenderCardNumber()));
           }
           BigDecimal commission = createDto.getPrice().multiply(BigDecimal.valueOf(0.01));

           BigDecimal sendingAmount;
           BigDecimal totalAmount;
           if (createDto.getIsIn()) {
               sendingAmount = createDto.getPrice().subtract(commission);
               totalAmount = createDto.getPrice();
           } else {
               sendingAmount = createDto.getPrice();
               totalAmount = createDto.getPrice().add(commission);
           }
           Card cardSender = senderCard.get();
           Card cardReceiver = receiverCard.get();
           if(cardSender.getBalance().compareTo(totalAmount) < 0){
               throw new RuntimeException("Mablag yetarli emas");
           }
           cardSender.setBalance(cardSender.getBalance().subtract(totalAmount));
           cardReceiver.setBalance(cardReceiver.getBalance().add(sendingAmount));
           cardRepository.save(cardSender);
           cardRepository.save(cardReceiver);
           Transaction transaction = new Transaction(owner.get(), cardSender, cardReceiver, sendingAmount, commission);
           transactionRepository.save(transaction);
           return "Successfully";


    }
    public List<TransactionResponseDto> getUserTransaction(UUID userId, UserTransactions transactions) {
        System.out.println(transactions);
        if (userRepository.findByIdAndIsActiveFalse(userId).isEmpty()) {
            throw new RuntimeException("User bu %s id bilan topilmadi".formatted(userId));
        }
        Pageable pageable = PageRequest.of(transactions.getPage(), transactions.getSize());
        Page<Transaction> pages;
        if (transactions.getRole().equals(TransactionRole.ALL)) {
            pages = transactionRepository
                    .findAllBySenderCardOwnerIdOrReceiverCardOwnerIdAndCreatedDateBetween
                            (userId, userId, pageable,transactions.getStartDate(),transactions.getFinishDate());
        } else if (transactions.getRole().equals(TransactionRole.SENDER)) {
            pages = transactionRepository
                        .findAllBySenderCardOwnerIdAndCreatedDateBetween
                            (userId, pageable,transactions.getStartDate(),transactions.getFinishDate());
        } else {
            pages = transactionRepository
                    .findAllByReceiverCardOwnerIdAndCreatedDateBetween
                            (userId, pageable,transactions.getStartDate(),transactions.getFinishDate());
        }

      return   mapTransactionsToResponceDto(userId, pages);
    }
    public List<TransactionResponseDto> getAllTransaction(UserTransactions transactions){
        List<TransactionResponseDto> all = new ArrayList<>();
        Pageable pageable = PageRequest.of(transactions.getPage(),transactions.getSize());
        Page<Transaction> allTransactions = transactionRepository.getAllByCreatedDateBetween(pageable, transactions.getStartDate(), transactions.getFinishDate());
        for (Transaction transaction : allTransactions.toList()) {
            all.add(
                    new TransactionResponseDto(
                            transaction, null
                    )
            );
        }
        return all;
    }

    private  List<TransactionResponseDto> mapTransactionsToResponceDto(UUID userId, Page<Transaction> pages) {
        List<TransactionResponseDto> responseDtos = new ArrayList<>();
        for (Transaction transaction : pages.get().toList()) {
            if (transaction.getSenderCard().getOwner().getId().equals(userId)) {
                responseDtos.add(new TransactionResponseDto(transaction, TransactionRole.SENDER));
            } else {
                responseDtos.add(new TransactionResponseDto(transaction, TransactionRole.RECEIVER));
            }
        }
        return responseDtos;
    }

    private Optional<Card> findByCardByNumber(String cardNumber){
        return cardRepository.findByCardNumber(cardNumber);
    }
}
