package kr.co.carboncheck.spring.carboncheckserver.repository.usage;

import kr.co.carboncheck.spring.carboncheckserver.domain.ElectricityUsage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class JpaElectricityUsageRepository implements UsageRepository<ElectricityUsage> {
    private final EntityManager em;

    @Autowired
    public JpaElectricityUsageRepository(EntityManager em) {
        this.em = em;
    }


    @Override
    public boolean insert(ElectricityUsage usage) {
        em.persist(usage);
        return true;
    }
}
