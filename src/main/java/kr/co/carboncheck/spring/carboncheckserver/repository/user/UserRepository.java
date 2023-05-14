package kr.co.carboncheck.spring.carboncheckserver.repository.user;

import kr.co.carboncheck.spring.carboncheckserver.domain.User;

import java.util.Optional;

public interface UserRepository {
    boolean saveUser(User user);

    boolean updateHomeServerId(User user, String homeServerId);

    Optional<User> findByUserId(Long userId);

    Optional<User> findByEmail(String email);
}
