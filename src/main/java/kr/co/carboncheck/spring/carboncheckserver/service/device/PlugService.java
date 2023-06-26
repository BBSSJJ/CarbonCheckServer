package kr.co.carboncheck.spring.carboncheckserver.service.device;

import kr.co.carboncheck.spring.carboncheckserver.domain.Plug;
import kr.co.carboncheck.spring.carboncheckserver.domain.User;
import kr.co.carboncheck.spring.carboncheckserver.dto.device.RegisterPlugResponse;

import java.util.Optional;

public interface PlugService {
    RegisterPlugResponse registerPlug(String plugId, String userId);

    Optional<User> findUserByPlugId(String plugId);

    Boolean removePlug(String plugId);
}
