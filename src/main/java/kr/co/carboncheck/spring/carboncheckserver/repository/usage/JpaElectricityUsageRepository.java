package kr.co.carboncheck.spring.carboncheckserver.repository.usage;

import kr.co.carboncheck.spring.carboncheckserver.domain.ElectricityUsage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Map;

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

    @Override
    public boolean updateUsage(ElectricityUsage usage) {
        em.merge(usage);
        return true;
    }

    public Map<String, Float> findTodayUserUsage(String userId) {
        return null;
    }

    public Map<String, Float> findTodayGroupUsage(String homeServerId) {
        return null;
    }

    public List<ElectricityUsage> findByPlugIdAndDate(String plugId, LocalDateTime date) {
        int year = date.getYear();
        int month = date.getMonth().getValue();
        int day = date.getDayOfMonth();
        List<ElectricityUsage> results = em.createQuery("select e from ElectricityUsage e " +
                        "where e.plugId = :plugId " +
                        "and YEAR(e.date) = :year " +
                        "and MONTH(e.date) =: month " +
                        "and DAY(e.date) =: day", ElectricityUsage.class)
                .setParameter("plugId", plugId)
                .setParameter("year", year)
                .setParameter("month", month)
                .setParameter("day", day).getResultList();
        return results;
    }
}
