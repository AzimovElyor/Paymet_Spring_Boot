package uz.pdp.springboot.validator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.pdp.springboot.entity.BaseEntity;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Getter
@Setter
public abstract class AbstractValidator<REQ,ENTITY extends BaseEntity,REP> {
    protected final REP repository;
    public void save(REQ req){
        log.info("save validator" + req.getClass().getName() );

        log.info("save validator" + req.getClass().getName() );
    }
    public abstract Page<ENTITY> getAll(Pageable pageable);
    public abstract ENTITY findById(UUID id);
}
