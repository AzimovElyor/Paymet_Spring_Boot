package uz.pdp.springboot.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.springboot.entity.BaseEntity;
import uz.pdp.springboot.validator.AbstractValidator;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
@Setter
public abstract class BaseService<
        ENTITY extends BaseEntity,
        ID,
        REP extends JpaRepository<ENTITY,ID>,
        REQ, RES,
        VALID extends AbstractValidator<REQ,ENTITY,REP>
        >
{
    protected final REP repository;
    protected final ModelMapper modelMapper;
    protected final VALID validator;

    public RES save(REQ req){
         validator.save(req);
        ENTITY entity = reqToEntity(req);
        repository.save(entity);
       return entityToResponse(entity);
    }
    public List<RES> getAll(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<ENTITY> all = validator.getAll(pageable);
        return all.get().map( this::entityToResponse).collect(Collectors.toList());
    }
    public RES findById(ID id){
        ENTITY byId = validator.findById((UUID) id);
        return entityToResponse(byId);
    }
    protected abstract ENTITY reqToEntity(REQ req);
    protected abstract RES entityToResponse(ENTITY entity);

}
