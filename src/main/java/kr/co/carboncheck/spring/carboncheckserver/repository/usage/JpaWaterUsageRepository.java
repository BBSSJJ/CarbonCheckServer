package kr.co.carboncheck.spring.carboncheckserver.repository.usage;

import kr.co.carboncheck.spring.carboncheckserver.domain.ElectricityUsage;
import kr.co.carboncheck.spring.carboncheckserver.domain.WaterUsage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.*;

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

    @Override
    public Map<String, Float> findTodayUserUsage(String userId) {
        int userIdInt = Integer.parseInt(userId);
        int year = LocalDateTime.now().getYear();
        int month = LocalDateTime.now().getMonthValue();
        int day = LocalDateTime.now().getDayOfMonth();
        List<Object[]> results = em.createQuery("select w.place, SUM(w.amount) from WaterUsage w " +
                        "where w.userId =: userId " +
                        "and YEAR(w.endTime) =: year " +
                        "and MONTH(w.endTime) =: month " +
                        "and DAY(w.endTime) =: day " +
                        "GROUP BY w.place", Object[].class)
                .setParameter("userId", userIdInt)
                .setParameter("year", year)
                .setParameter("month", month)
                .setParameter("day", day).getResultList();
        Map<String, Float> map = new HashMap<>();
        for (Object[] result : results) {
            String place = (String) result[0];
            Float amount = (result[1] == null) ? 0f : (Float) result[1];
            map.put(place, amount);
            //TODO: 사용한 place만 뜨기 때문에 문제가 있다...
        }
        return map;
    }

    @Override
    public Map<String, Float> findTodayGroupUsage(String homeServerId) {
        int year = LocalDateTime.now().getYear();
        int month = LocalDateTime.now().getMonthValue();
        int day = LocalDateTime.now().getDayOfMonth();
        List<Object[]> results = em.createQuery("select u.name, SUM(w.amount) " +
                        "from User u left join WaterUsage w " +
                        "where u.homeServerId =: homeServerId " +
                        "and YEAR(w.endTime) =: year " +
                        "and MONTH(w.endTime) =: month " +
                        "and DAY(w.endTime) =: day " +
                        "GROUP BY w.place", Object[].class)
                .setParameter("homeServerId", homeServerId)
                .setParameter("year", year)
                .setParameter("month", month)
                .setParameter("day", day).getResultList();
        Map<String, Float> map = new HashMap<>();
        for (Object[] result : results) {
            String name = (String) result[0];
            Float amount = (result[1] == null) ? 0f : (Float) result[1];
            map.put(name, amount);
        }
        return map;
    }


    //No Use
    @Override
    public Optional<ElectricityUsage> findTodayUsageByPlugId(String plugId) {
        return null;
    }

    @Override
    public int findSumByPlugId(String plugId) {
        return 0;
    }

    @Override
    public boolean updateUsage(WaterUsage usage) {
        return false;
    }


}
