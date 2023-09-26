package uz.pdp.springboot.validator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import uz.pdp.springboot.dto.ServiceRequestDto;
import uz.pdp.springboot.entity.ServiceEntity;
import uz.pdp.springboot.repository.ServiceRepository;

import java.util.Optional;
import java.util.UUID;
@Component
public class ServiceEntityValidator extends AbstractValidator<ServiceRequestDto, ServiceEntity, ServiceRepository>{
    public ServiceEntityValidator(ServiceRepository repository) {
        super(repository);
    }

    @Override
    public void save(ServiceRequestDto serviceRequestDto) {
        Optional<ServiceEntity> byTitle = repository.findByTitleAndIsActiveFalse(serviceRequestDto.getTitle());
        if (byTitle.isPresent()) {
            throw new RuntimeException("Bunday title li %s servive Entity allqachon mavjud".formatted(serviceRequestDto.getTitle()));
        }
    }

    @Override
    public Page<ServiceEntity> getAll(Pageable pageable) {
        return repository.findAllByIsActiveFalse(pageable);
    }

    @Override
    public ServiceEntity findById(UUID id) {
        return repository
                .findByIdAndIsActiveFalse(id).orElseThrow(() -> new RuntimeException("Service Entity bu id %s bilan topilmadi".formatted(id)));
    }
}
