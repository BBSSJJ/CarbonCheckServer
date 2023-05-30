package kr.co.carboncheck.spring.carboncheckserver.repository.device;


import kr.co.carboncheck.spring.carboncheckserver.domain.Plug;

import java.util.Optional;

public interface PlugRepository {
    boolean savePlug(Plug plug);
    Optional<Plug> findByPlugId(String plugId);
}
