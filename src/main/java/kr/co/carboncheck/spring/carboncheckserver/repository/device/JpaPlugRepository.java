package kr.co.carboncheck.spring.carboncheckserver.repository.device;

import kr.co.carboncheck.spring.carboncheckserver.domain.Plug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class JpaPlugRepository implements PlugRepository {
    private final EntityManager em;

    @Autowired
    public JpaPlugRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean savePlug(Plug plug) {
        em.persist(plug);
        return true;
    }

    @Override
    public Optional<Plug> findByPlugId(String plugId) {
        Plug plug = em.find(Plug.class, plugId);
        return Optional.ofNullable(plug);
    }

    @Override
    public boolean deletePlugById(String plugId) {
        Optional<Plug> optionalPlug = findByPlugId(plugId);
        if(!optionalPlug.isPresent()) return false;
        else em.remove(optionalPlug.get());
        return true;
    }
}
