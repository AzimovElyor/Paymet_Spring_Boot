package uz.pdp.springboot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.springboot.dto.LoginDto;
import uz.pdp.springboot.dto.UserRequestDto;
import uz.pdp.springboot.dto.UserResponseDto;
import uz.pdp.springboot.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private  final UserService userService;
    @PostMapping("/sign-up")
    public ResponseEntity<UserResponseDto> signUp(@Valid @RequestBody UserRequestDto requestDto){
        return new ResponseEntity<>(userService.save(requestDto), HttpStatus.CREATED);
    }
    @PostMapping("/sign-in")
    public ResponseEntity<UserResponseDto> signIn(@Valid @RequestBody LoginDto loginDto){
        return new ResponseEntity<>(userService.login(loginDto),HttpStatus.OK);
    }
}
