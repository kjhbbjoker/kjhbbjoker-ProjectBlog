package com.example.projectblog.service;

import com.example.projectblog.Security.UserDetailsImpl;
import com.example.projectblog.dto.KakaoUserInfoDto;
import com.example.projectblog.dto.SignDto;
import com.example.projectblog.model.User;
import com.example.projectblog.model.UserRoleEnum;
import com.example.projectblog.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;


@Service
@Valid
@Transactional
public class UserService {
    private final PasswordEncoder passwordEncoder; //인코딩
    private final UserRepository userRepository;  //유저
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";



    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public void registerUser(SignDto requestDto, BindingResult bindingResult) {

        // 회원 ID 중복 확인
        String username = requestDto.getUsername();
        String email = requestDto.getEmail(); //이메일 받기**/
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            FieldError fieldError = new FieldError("requestDto", "username", "이미 있습니다.");
            bindingResult.addError(fieldError);
        }


// 패스워드 암호화
        String password = passwordEncoder.encode(requestDto.getPassword()); //비밀번호
        String passwordCheck = passwordEncoder.encode(requestDto.getPasswordCheck()); //비밀번호 확인


        //패스워드와 패스워드 확인 같은지 확인
        if (!requestDto.getPassword().equals((requestDto.getPasswordCheck()))) { //비밀번호 비밀번호 확인
            FieldError fieldError = new FieldError("requestDto", "passwordCheck", "비밀번호와 비밀번호 확인이 다릅니다.");
            bindingResult.addError(fieldError);
        }


        if (requestDto.getUsername().equals(requestDto.getPassword())) { //닉네임 비밀번호 확인
            FieldError fieldError = new FieldError("requestDto", "password", "닉네임와 비밀번호가 같으면 안됩니다");
            bindingResult.addError(fieldError);
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, email,passwordCheck, role);
        userRepository.save(user);


    }

    public void kakaoLogin(String code) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getAccessToken(code);

        // 2. 토큰으로 카카오 API 호출
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);

        // DB 에 중복된 Kakao Id 가 있는지 확인
        Long kakaoId = kakaoUserInfo.getId();
        User kakaoUser = userRepository.findByKakaoId(kakaoId)
                .orElse(null);
        if (kakaoUser == null) {
            // 회원가입
            // username: kakao nickname
            String nickname = kakaoUserInfo.getNickname();

            // password: random UUID
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);

            // email: kakao email
            String email = kakaoUserInfo.getEmail();

            // role: 일반 사용자
            UserRoleEnum role = UserRoleEnum.USER;


            kakaoUser = new User(nickname, encodedPassword, email, role, kakaoId);
            userRepository.save(kakaoUser);
        }

        // 4. 강제 로그인 처리
        UserDetails userDetails = new UserDetailsImpl(kakaoUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);


    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {

        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();

        System.out.println("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);
        return new KakaoUserInfoDto(id, nickname, email);
    }

    private String getAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

// HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "e353dbf7d70dc9e901ce2dc7cc2516b8");
        body.add("redirect_uri", "http://localhost:8080/user/kakao/callback");
        body.add("code", code);

// HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

// HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();

    }


}


