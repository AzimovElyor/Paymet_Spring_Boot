package uz.pdp.springboot.validator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import uz.pdp.springboot.dto.LoginDto;
import uz.pdp.springboot.dto.UserRequestDto;
import uz.pdp.springboot.entity.User;
import uz.pdp.springboot.exception.LoginException;
import uz.pdp.springboot.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserValidator extends AbstractValidator<UserRequestDto,User, UserRepository>{

    public UserValidator(UserRepository repository) {
        super(repository);
    }

    @Override
    public void save(UserRequestDto userRequestDto) {
        Optional<User> byUsername = repository.findByUsername(userRequestDto.getUsername());
        if (byUsername.isPresent()) {
            throw new RuntimeException("Bunday username %s allaqachon mavjud".formatted(userRequestDto.getUsername()));
        }
    }
    public User loginValidator(LoginDto loginDto){
        Optional<User> byUsername = repository.findByUsername(loginDto.getUsername());
        byUsername.orElseThrow(() -> new LoginException("Username yoki parol xato"));
        if(byUsername.get().isActive()){
            throw new RuntimeException("Username yoki parol xato");
        }
        if(!byUsername.get().getPassword().equals(loginDto.getPassword())){
            throw new LoginException("Username yoki parol xato");
    }
        return byUsername.get();
}
    public Page<User> getAll(Pageable pageable){
         return repository.findByIsActiveFalse(pageable);
    }

    @Override
    public User findById(UUID id) {
        Optional<User> byIdAndIsActiveFalse = repository.findByIdAndIsActiveFalse(id);
        return byIdAndIsActiveFalse.orElseThrow(() -> new RuntimeException("User not found this id: %s".formatted(id)));
    }
}
