package uz.pdp.springboot.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import uz.pdp.springboot.dto.PaymentRequestDto;
import uz.pdp.springboot.entity.Card;
import uz.pdp.springboot.entity.PaymentEntity;
import uz.pdp.springboot.entity.ServiceEntity;
import uz.pdp.springboot.entity.User;
import uz.pdp.springboot.repository.CardRepository;
import uz.pdp.springboot.repository.PaymentRepository;
import uz.pdp.springboot.repository.ServiceRepository;
import uz.pdp.springboot.repository.UserRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
@Component
public class PaymentValidator extends AbstractValidator<PaymentRequestDto, PaymentEntity, PaymentRepository> {
    @Autowired
    private  UserRepository userRepository ;
   @Autowired
    private  CardRepository cardRepository;
   @Autowired
   private  ServiceRepository serviceRepository;
    public PaymentValidator(PaymentRepository repository) {
        super(repository);
    }

    @Override
    public void save(PaymentRequestDto paymentRequestDto) {
        Optional<User> owner = userRepository.findByIdAndIsActiveFalse(paymentRequestDto.getOwnerId());
        if (owner.isEmpty()) {
           throw new RuntimeException("Owner %s bu id bilan topilmadi".formatted(paymentRequestDto.getOwnerId()));
        }
        Optional<Card> card = cardRepository.findByIdAndIsActiveFalse(paymentRequestDto.getSenderCardId());
        if (card.isEmpty()) {
            throw new RuntimeException("Card %s bu id bilan topilmadi".formatted(paymentRequestDto.getSenderCardId()));
        }
        Optional<ServiceEntity> service = serviceRepository.findById(paymentRequestDto.getServiceEntityId());
        if (service.isEmpty()) {
            throw new RuntimeException("Service %s bu id bilan topilmadi".formatted(paymentRequestDto.getServiceEntityId()));
        }


    }

    @Override
    public Page<PaymentEntity> getAll(Pageable pageable) {
       return repository.findAll(pageable);
    }

    @Override
    public PaymentEntity findById(UUID id) {
       return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bu id %s bilan payment tranzaktsiyalar topilmadi".formatted(id)));
    }
}
