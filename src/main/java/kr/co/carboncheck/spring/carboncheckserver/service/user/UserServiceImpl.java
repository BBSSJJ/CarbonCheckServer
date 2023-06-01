package kr.co.carboncheck.spring.carboncheckserver.service.user;

import kr.co.carboncheck.spring.carboncheckserver.domain.User;
import kr.co.carboncheck.spring.carboncheckserver.dto.user.GetGroupTargetAmountResponse;
import kr.co.carboncheck.spring.carboncheckserver.dto.user.JoinResponse;
import kr.co.carboncheck.spring.carboncheckserver.dto.user.LoginResponse;
import kr.co.carboncheck.spring.carboncheckserver.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public JoinResponse createUser(User user) {
        String email = user.getEmail();
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            System.out.println("이미 존재하는 이메일");
            return new JoinResponse(false, "이미 존재하는 이메일입니다.");
        } else {
            System.out.println("회원가입 성공");
            userRepository.saveUser(user);
            return new JoinResponse(true, "회원가입 성공");
        }
    }

    @Override
    public LoginResponse authenticateUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            if (passwordEncoder.matches(password, user.getPassword())) {
                System.out.println("로그인 성공");
                return new LoginResponse(true, "로그인 성공!");
            } else {
                System.out.println("비밀번호 일치하지 않음");
                return new LoginResponse(false, "비밀번호가 일치하지 않습니다.");
            }
        } else {
            System.out.println("등록되지않은 이메일");
            return new LoginResponse(false, "등록되지 않는 이메일입니다.");
        }
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public GetGroupTargetAmountResponse getGroupTargetAmount(String homeServerId) {
        return new GetGroupTargetAmountResponse(userRepository.findGroupTargetAmount(homeServerId));
    }

}
