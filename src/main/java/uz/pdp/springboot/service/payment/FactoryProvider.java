package uz.pdp.springboot.service.payment;

import uz.pdp.springboot.enums.Category;

public class FactoryProvider {
        public PaymentService getService(Category category){
            switch (category){
                case MOBILE -> {
                    return new MobileService();
                }case STUDY -> {
                    return new StudyService();
                }case COMMUNAL -> {
                    return new CommunalService();
                }
                default -> throw new RuntimeException("Biz boshqa categroyalarni support qilmaymiz");
            }
        }
}
