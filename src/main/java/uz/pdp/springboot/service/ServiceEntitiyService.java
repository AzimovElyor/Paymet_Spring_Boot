package uz.pdp.springboot.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uz.pdp.springboot.dto.ServiceRequestDto;
import uz.pdp.springboot.dto.ServiceResponseDto;
import uz.pdp.springboot.entity.ServiceEntity;
import uz.pdp.springboot.repository.ServiceRepository;
import uz.pdp.springboot.validator.ServiceEntityValidator;

import java.util.UUID;
@Service
public class ServiceEntitiyService extends BaseService
        <ServiceEntity,
                UUID,
                ServiceRepository,
                ServiceRequestDto,
                ServiceResponseDto, 
                ServiceEntityValidator>

{
    public ServiceEntitiyService(ServiceRepository repository, ModelMapper modelMapper, ServiceEntityValidator validator) {
        super(repository, modelMapper, validator);
    }

    @Override
    protected ServiceEntity reqToEntity(ServiceRequestDto serviceRequestDto) {
      return   modelMapper.map(serviceRequestDto,ServiceEntity.class);
    }

    @Override
    protected ServiceResponseDto entityToResponse(ServiceEntity entity) {
        return modelMapper.map(entity,ServiceResponseDto.class);
    }
}
