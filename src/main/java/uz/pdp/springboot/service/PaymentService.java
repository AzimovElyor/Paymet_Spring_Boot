package uz.pdp.springboot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.springboot.dto.PaymentRequestDto;
import uz.pdp.springboot.dto.PaymentResponseDto;
import uz.pdp.springboot.entity.Card;
import uz.pdp.springboot.entity.PaymentEntity;
import uz.pdp.springboot.entity.ServiceEntity;
import uz.pdp.springboot.entity.User;
import uz.pdp.springboot.repository.CardRepository;
import uz.pdp.springboot.repository.PaymentRepository;
import uz.pdp.springboot.repository.ServiceRepository;
import uz.pdp.springboot.repository.UserRepository;
import uz.pdp.springboot.validator.PaymentValidator;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class PaymentService extends BaseService<
        PaymentEntity,
        UUID,
        PaymentRepository,
        PaymentRequestDto,
        PaymentResponseDto,
        PaymentValidator>{
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final ServiceRepository serviceRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    public PaymentService(PaymentRepository repository, ModelMapper modelMapper,
                          PaymentValidator validator, CardRepository cardRepository, UserRepository userRepository, ServiceRepository serviceRepository) {
        super(repository, modelMapper, validator);
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.serviceRepository = serviceRepository;
    }


    @Override
    protected PaymentEntity reqToEntity(PaymentRequestDto paymentRequestDto) {
        User user = userRepository.findByIdAndIsActiveFalse(paymentRequestDto.getOwnerId()).get();
        Card card = cardRepository.findByIdAndIsActiveFalse(paymentRequestDto.getSenderCardId()).get();
        ServiceEntity service = serviceRepository.findByIdAndIsActiveFalse(paymentRequestDto.getServiceEntityId()).get();

        BigDecimal commission = paymentRequestDto.getAmount().divide(BigDecimal.valueOf(100)).multiply(service.getCommission());
        System.out.println(commission);
        if (card.getBalance().compareTo(commission.add(paymentRequestDto.getAmount())) < 0){
            throw new RuntimeException("Cartada yetarlichi mablag yoq");
        }
        card.setBalance(card.getBalance().subtract(paymentRequestDto.getAmount().add(commission)));
        cardRepository.save(card);

        String saveField;
        try {
            saveField = objectMapper.writeValueAsString(paymentRequestDto.getField());
        }catch (JsonProcessingException e){
            e.printStackTrace();
            throw new RuntimeException("Mapni parse qilishda muammo roy berdi");
        }
       return new PaymentEntity(user,card,service,saveField,paymentRequestDto.getAmount(),commission);
    }

    @Override
    protected PaymentResponseDto entityToResponse(PaymentEntity entity) {
      return new PaymentResponseDto(entity);
    }

}
