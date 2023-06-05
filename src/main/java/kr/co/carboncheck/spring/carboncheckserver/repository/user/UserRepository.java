package kr.co.carboncheck.spring.carboncheckserver.repository.user;

import kr.co.carboncheck.spring.carboncheckserver.domain.User;
import kr.co.carboncheck.spring.carboncheckserver.dto.user.GetGroupTargetAmountResponse;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    boolean saveUser(User user);

    boolean updateHomeServerId(User user, String homeServerId);

    Optional<User> findByUserId(int userId);

    Optional<User> findByEmail(String email);

    List<GetGroupTargetAmountResponse> findGroupTargetAmount(String homeServerId);

    boolean updateTargetAmount(User user);
}
