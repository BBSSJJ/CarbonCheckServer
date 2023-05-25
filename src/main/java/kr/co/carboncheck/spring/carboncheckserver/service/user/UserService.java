package kr.co.carboncheck.spring.carboncheckserver.service.user;

import kr.co.carboncheck.spring.carboncheckserver.domain.User;
import kr.co.carboncheck.spring.carboncheckserver.dto.user.JoinResponse;
import kr.co.carboncheck.spring.carboncheckserver.dto.user.LoginResponse;

import java.util.Optional;

public interface UserService {
    public JoinResponse createUser(User user);

    public LoginResponse authenticateUser(String email, String password);

    public Optional<User> getUserByEmail(String email);
}
