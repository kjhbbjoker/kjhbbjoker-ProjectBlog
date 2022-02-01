package com.example.projectblog.service;

import com.example.projectblog.dto.SignDto;
import com.example.projectblog.model.User;
import com.example.projectblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;



@Service
@Valid
@Transactional
public class UserService {
    private final PasswordEncoder passwordEncoder; //인코딩
    private final UserRepository userRepository;  //유저


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(SignDto requestDto, BindingResult bindingResult) {
        // 회원 ID 중복 확인
        String username = requestDto.getUsername();


       Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            FieldError fieldError = new FieldError("requestDto","username","이미 있습니다.");
            bindingResult.addError(fieldError);
        }


// 패스워드 암호화
        String password = passwordEncoder.encode(requestDto.getPassword()); //비밀번호
        String passwordCheck = passwordEncoder.encode(requestDto.getPasswordCheck()); //비밀번호 확인
        String email = requestDto.getEmail(); //이메일 받기**/

        //패스워드와 패스워드 확인 같은지 확인
       if(!Objects.equals(requestDto.getPassword(), requestDto.getPasswordCheck())){ //비밀번호 비밀번호 확인
            FieldError fieldError = new FieldError("requestDto","password","비밀번호와 비밀번호 확인이 다릅니다.");
            bindingResult.addError(fieldError);
        }


        if(Objects.equals(requestDto.getUsername(), requestDto.getPassword())){ //닉네임 비밀번호 확인
            FieldError fieldError = new FieldError("requestDto","passwordCheck","닉네임와 비밀번호가 같으면 안됩니다");
            bindingResult.addError(fieldError);
        }


        User user = new User(username, password, passwordCheck ,email);
        userRepository.save(user);





    }
}