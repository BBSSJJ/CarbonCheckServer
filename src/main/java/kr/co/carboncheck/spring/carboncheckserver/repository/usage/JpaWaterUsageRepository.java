package kr.co.carboncheck.spring.carboncheckserver.repository.usage;

import kr.co.carboncheck.spring.carboncheckserver.domain.ElectricityUsage;
import kr.co.carboncheck.spring.carboncheckserver.domain.WaterUsage;
import kr.co.carboncheck.spring.carboncheckserver.dto.usage.GetUsageResponse;
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
    public List<GetUsageResponse> findTodayUserUsage(String userId) {
        System.out.println("find TodayUserUsage");
        int userIdInt = Integer.parseInt(userId);

        List<Object[]> results = em.createQuery("select w.place, SUM(w.amount) from WaterUsage w " +
                        "where w.userId = :userId " +
                        "and DATE(w.endTime) = CURDATE() " +
                        "GROUP BY w.place", Object[].class)
                .setParameter("userId", userIdInt)
                .getResultList();
        List<GetUsageResponse> list = new ArrayList<>();
        //TODO: 원래는 DOUBLE형이어야 한다!!!!!!!
        for (Object[] result : results) {
            String place = (String) result[0];
            float amount = (result[1] == null) ? 0f : ((Double) result[1]).floatValue();
            list.add(new GetUsageResponse(place, amount));
        }
        return list;
    }

    @Override
    public List<GetUsageResponse> findTodayGroupUsage(String homeServerId) {
        System.out.println("in findTodayGroupUsage");

        List<Object[]> results = em.createQuery("select u.name, SUM(w.amount) " +
                        "from User u left join WaterUsage w " +
                        "on u.userId = w.userId " +
                        "and DATE(w.endTime) = CURDATE() " +
                        "where u.homeServerId = :homeServerId " +
                        "group by u.name", Object[].class)
                .setParameter("homeServerId", homeServerId)
                .getResultList();
        List<GetUsageResponse> list = new ArrayList<>();
        //TODO: 원래는 DOUBLE형이어야 한다!!!!!!!
        for (Object[] result : results) {
            String name = (String) result[0];
            float amount = (result[1] == null) ? 0f : ((Double) result[1]).floatValue();
            list.add(new GetUsageResponse(name, amount));
        }
        return list;
    }


    //No Use
    @Override
    public Optional<ElectricityUsage> findTodayUsageByPlugId(String plugId) {
        return Optional.empty();
    }

    @Override
    public Optional<ElectricityUsage> findBeforeUsageByPlugId(String plugId) {
        return Optional.empty();
    }


    @Override
    public boolean updateUsage(WaterUsage usage) {
        return false;
    }


}
