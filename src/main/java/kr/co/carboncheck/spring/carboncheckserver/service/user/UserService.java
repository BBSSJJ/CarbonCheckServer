package kr.co.carboncheck.spring.carboncheckserver.service.user;

import kr.co.carboncheck.spring.carboncheckserver.domain.User;
import kr.co.carboncheck.spring.carboncheckserver.dto.user.GetGroupTargetAmountResponse;
import kr.co.carboncheck.spring.carboncheckserver.dto.user.JoinResponse;
import kr.co.carboncheck.spring.carboncheckserver.dto.user.LoginResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
    JoinResponse createUser(User user);

    LoginResponse authenticateUser(String email, String password);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserById(int userId);

    List<GetGroupTargetAmountResponse> getGroupTargetAmount(String homeServerId);

    boolean updateUserTargetAmount(int userId, int targetAmount);
}
