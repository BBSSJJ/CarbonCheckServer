package kr.co.carboncheck.spring.carboncheckserver.repository.device;

import kr.co.carboncheck.spring.carboncheckserver.domain.HomeServer;

import java.util.Optional;

public interface HomeServerRepository {
    Boolean saveHomeServer(HomeServer homeServer);

    Optional<HomeServer> findByHomeServerId(String homeServerId);
}
