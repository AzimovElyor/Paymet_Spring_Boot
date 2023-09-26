package uz.pdp.springboot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.springboot.dto.UserRequestDto;
import uz.pdp.springboot.dto.UserResponseDto;
import uz.pdp.springboot.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/get-all")
    public ResponseEntity<List<UserResponseDto>> getAll(
            @RequestParam(required = false , defaultValue = "5") int size,
            @RequestParam(required = false,defaultValue = "0") int page
         /*       @RequestParam(required = false, defaultValue = "A") String name*/

    ){
      return new ResponseEntity<>(userService.getAll(page,size), HttpStatus.OK);
    }
    @GetMapping("/find-by/{id}")
    public ResponseEntity<UserResponseDto> findById(@PathVariable UUID id){
        return new ResponseEntity<>(userService.findById(id),HttpStatus.OK);
    }
   @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable UUID id){
      return ResponseEntity.ok(userService.deleteById(id));
   }
       @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDto> updateById(@Valid @RequestBody UserRequestDto updateUser,@PathVariable UUID id ){
      return new ResponseEntity<>(userService.updateUser(updateUser,id),HttpStatus.OK);
   }
}
