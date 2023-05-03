package kr.co.carboncheck.spring.carboncheckserver.service;

import kr.co.carboncheck.spring.carboncheckserver.domain.User;
import kr.co.carboncheck.spring.carboncheckserver.dto.JoinRequestDTO;
import kr.co.carboncheck.spring.carboncheckserver.dto.JoinResponseDTO;
import kr.co.carboncheck.spring.carboncheckserver.dto.LoginResponseDTO;
import kr.co.carboncheck.spring.carboncheckserver.repository.UserRepository;
import org.hibernate.mapping.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Service
@Component
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public JoinResponseDTO join(User user) {
        String email = user.getEmail();
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isPresent()){
            System.out.println("이미 존재하는 이메일");
            return new JoinResponseDTO(false, "이미 존재하는 이메일입니다.");
        }
        else {
            System.out.println("회원가입 성공");
            userRepository.saveUser(user);
            return new JoinResponseDTO(true, "회원가입 성공");
        }
    }

    public LoginResponseDTO login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            if(user.getPassword().equals(password)){
                System.out.println("로그인 성공");
                return new LoginResponseDTO(true, "로그인 성공!");
            }
            else{
                System.out.println("비밀번호 일치하지 않음");
                return new LoginResponseDTO(false, "비밀번호가 일치하지 않습니다.");
            }
        }
        else{
            System.out.println("등록되지않은 이메일");
            return new LoginResponseDTO(false, "등록되지 않는 이메일입니다.");
        }
    }


}
