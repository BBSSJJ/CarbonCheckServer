package kr.co.carboncheck.spring.carboncheckserver.repository.usage;

import kr.co.carboncheck.spring.carboncheckserver.domain.ElectricityUsage;
import kr.co.carboncheck.spring.carboncheckserver.domain.WaterUsage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JpaWaterUsageRepository implements UsageRepository<WaterUsage> {

    private final EntityManager em;

    @Autowired
    public JpaWaterUsageRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean insert(WaterUsage usage) {
        em.persist(usage);
        return true;
    }

    //No Use
    @Override
    public List<ElectricityUsage> findByPlugIdAndDate(String plugId, LocalDateTime date) {
        return null;
    }

    @Override
    public boolean updateUsage(WaterUsage usage) {
        return false;
    }
}
