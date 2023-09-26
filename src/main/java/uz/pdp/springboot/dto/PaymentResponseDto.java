package uz.pdp.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.springboot.entity.PaymentEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentResponseDto {
    private UUID id;
    private String ownerName;
    private String senderCardNumber;
    private String serviceName;
    private BigDecimal amount;
    private String fields;
    private BigDecimal commission;
    private LocalDateTime createdDate;

    public PaymentResponseDto(PaymentEntity paymentEntity){
        this.id = paymentEntity.getId();
        this.ownerName = paymentEntity.getOwner().getName();
        this.senderCardNumber = paymentEntity.getSenderCard().getCardNumber();
        this.serviceName = paymentEntity.getService().getTitle();
        this.amount = paymentEntity.getAmount();
        this.fields = paymentEntity.getField();
        this.commission = paymentEntity.getCommission();
        this.createdDate = paymentEntity.getCreatedDate();
    }

}
