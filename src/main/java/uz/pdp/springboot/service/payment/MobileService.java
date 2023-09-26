package uz.pdp.springboot.service.payment;

import org.springframework.stereotype.Service;

@Service
public class MobileService implements PaymentService {
    @Override
    public PaymentService create() {
        return new MobileService();
    }
}
