package uz.pdp.springboot.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uz.pdp.springboot.dto.LoginDto;
import uz.pdp.springboot.dto.StatisticsDto;
import uz.pdp.springboot.dto.UserRequestDto;
import uz.pdp.springboot.dto.UserResponseDto;
import uz.pdp.springboot.entity.User;

import uz.pdp.springboot.repository.CardRepository;
import uz.pdp.springboot.repository.TransactionRepository;
import uz.pdp.springboot.repository.UserRepository;
import uz.pdp.springboot.validator.UserValidator;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserService extends
        BaseService<User,
                UUID,
                UserRepository,
                UserRequestDto,
                UserResponseDto,
                UserValidator>
{
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CardRepository cardRepository;
    public UserService(UserRepository userRepository,ModelMapper modelMapper,UserValidator validator){
        super(userRepository,modelMapper,validator);
    }
    public UserResponseDto login(LoginDto loginDto){
        User user = validator.loginValidator(loginDto);
        return entityToResponse(user);
    }

    @Override
    protected User reqToEntity(UserRequestDto userRequestDto) {
       return modelMapper.map(userRequestDto,User.class);
    }

    @Override
    protected UserResponseDto entityToResponse(User entity) {
       return modelMapper.map(entity,UserResponseDto.class);
    }





    /*private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserResponseDto save(UserRequestDto userRequestDto){
        User user = modelMapper.map(userRequestDto, User.class);
         return modelMapper.map(userRepository.save(user),UserResponseDto.class);
    }
    public UserResponseDto findById(UUID userId){
        return modelMapper
                .map(userRepository.findByIdAndIsActiveFalse(userId).orElseThrow(() -> new RuntimeException("User not found this id: %s".formatted(userId))),
                        UserResponseDto.class);
    }


    public List<UserResponseDto> getAll(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<User> allUsers = userRepository.findByIsActiveFalse(pageable);
        System.out.println(allUsers);
        return modelMapper.map(allUsers.get().collect(Collectors.toList()), new TypeToken<List<UserResponseDto>>(){}.getType());
    }
    */

    @Transactional
    public String deleteById(UUID userId){
        int res = repository.deleteUser(userId);
        if(res == 1){
            return "Successfully deleted";
        }
        throw new RuntimeException("User not found this %s id".formatted(userId));
    }
    @Transactional
    public UserResponseDto updateUser(UserRequestDto updateDto, UUID userId){
        Optional<User> findUser = repository.findByIdAndIsActiveFalse(userId);
        if(findUser.isEmpty()){
            throw new RuntimeException("User not found this %s id".formatted(userId));
        }
        User user = findUser.get();
        boolean isUsername= updateDto.getUsername().equals(user.getUsername());
      /*  if(updateDto.getUsername().equals(user.getUsername())) {
            user.setName(updateDto.getName());
            user.setPassword(updateDto.getPassword());
        }*/
        Optional<User> byUsername = repository.findByUsername(updateDto.getUsername());
        if (byUsername.isPresent() && !isUsername) {
           throw new RuntimeException("Bunday username %s allaqachon mavjud".formatted(updateDto.getUsername()));
        }
        user.setName(user.getName());
        user.setPassword(updateDto.getPassword());
        user.setUsername(updateDto.getUsername());
        repository.updateUser(user.getName(),user.getUsername(),user.getPassword(),user.getId());
        return modelMapper.map(user,UserResponseDto.class);

    }
    public StatisticsDto getStatisticsData(){
        int allUsers = repository.countAllBy();
        int deleteUsers = repository.countAllByIsActiveTrue();
        long cardsCount = cardRepository.count();
        long transactionCount = transactionRepository.count();
        BigDecimal sumAmount = transactionRepository.sumBalance();
        BigDecimal sumCommission = transactionRepository.sumCommission();
        return new StatisticsDto(allUsers,deleteUsers,cardsCount,sumAmount,sumCommission,transactionCount);
    }

}
