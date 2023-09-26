package uz.pdp.springboot.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.springboot.dto.CardRequestDto;
import uz.pdp.springboot.dto.CardResponseDto;
import uz.pdp.springboot.entity.Card;
import uz.pdp.springboot.entity.User;
import uz.pdp.springboot.repository.CardRepository;
import uz.pdp.springboot.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    public CardResponseDto create(CardRequestDto cardRequestDto){
        Optional<Card> byCardNumber = cardRepository.findByCardNumber(cardRequestDto.getCardNumber());
        if (byCardNumber.isPresent()) {
            throw new RuntimeException("Bunday card number %s allaqachon mavjud".formatted(cardRequestDto.getCardNumber()));
        }
        Optional<User> byId = userRepository.findById(cardRequestDto.getOwnerId());
        if(byId.isEmpty()){
            throw new RuntimeException("Cardni useri topilmadi");
        }
        Card card = modelMapper.map(cardRequestDto, Card.class);
        card.setOwner(byId.get());
        cardRepository.save(card);
        return modelMapper.map(card,CardResponseDto.class);
    }

    public List<CardResponseDto> getUserCards(UUID userId, int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Card> cards = cardRepository.findAllByIsActiveFalseAndOwnerId(userId,pageable );
       return modelMapper.map(cards.get().collect(Collectors.toList()), new TypeToken<List<CardResponseDto>>(){}.getType());
    }
    public CardResponseDto findByCardId(UUID cardId){
        return modelMapper
                .map(cardRepository.findByIdAndIsActiveFalse(cardId).
                        orElseThrow(() -> new RuntimeException("Card %s bu id bilan topilmadi".formatted(cardId)))
                        ,CardResponseDto.class);
    }
    @Transactional
    public String deleteCardById(UUID cardId){
        int res = cardRepository.deleteCard(cardId);
        if(res == 1) return "Successfully deleted card";
        throw new RuntimeException("Card bu %s bilan topilmadi".formatted(cardId));

    }

    public List<CardResponseDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Card> allCards = cardRepository.findAllByIsActiveFalse(pageable);
        return modelMapper.map(allCards.get().collect(Collectors.toList()), new TypeToken<List<CardResponseDto>>(){}.getType());
    }
    public CardResponseDto updateCard(UUID cardId,CardRequestDto cardRequestDto){

        Card card = cardRepository
                .findByIdAndIsActiveFalse(cardId).orElseThrow(() -> new RuntimeException("Carta %s bu id bilan topilmadi".formatted(cardId)));
        if(cardRequestDto.getCardNumber() != null && !card.getCardNumber().equals(cardRequestDto.getCardNumber())){
            if (cardRepository.findByCardNumber(cardRequestDto.getCardNumber()).isPresent()) {
                throw new RuntimeException("Carta %s raqami allaqachon mavjud".formatted(cardRequestDto.getCardNumber()));
            }
            modelMapper.map(cardRequestDto, card);
        }else {
            modelMapper.map(cardRequestDto, card);
        }
        cardRepository.save(card);
        return modelMapper.map(card,CardResponseDto.class);
    }
}
