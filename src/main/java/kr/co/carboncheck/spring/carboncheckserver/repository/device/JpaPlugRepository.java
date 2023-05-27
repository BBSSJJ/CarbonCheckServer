package kr.co.carboncheck.spring.carboncheckserver.repository.device;

import kr.co.carboncheck.spring.carboncheckserver.domain.Plug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class JpaPlugRepository implements PlugRepository {
    private final EntityManager em;

    @Autowired
    public JpaPlugRepository(EntityManager em){
        this.em = em;
    }

    @Override
    public boolean savePlug(Plug plug) {
        em.persist(plug);
        return true;
    }
}
