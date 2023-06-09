package kr.co.carboncheck.spring.carboncheckserver.repository.device;


import kr.co.carboncheck.spring.carboncheckserver.domain.HomeServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class JpaHomeServerRepository implements HomeServerRepository {

    private final EntityManager em;

    @Autowired
    public JpaHomeServerRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Boolean saveHomeServer(HomeServer homeServer) {
        em.persist(homeServer);
        return true;
    }

    @Override
    public Optional<HomeServer> findByHomeServerId(String homeServerId) {
        HomeServer homeServer = em.find(HomeServer.class, homeServerId);
        return Optional.ofNullable(homeServer);
    }
}
