package kr.co.carboncheck.spring.carboncheckserver.repository.usage;

import kr.co.carboncheck.spring.carboncheckserver.domain.ElectricityUsage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        int userIdInt = Integer.parseInt(userId);
        List<Object[]> results = em.createQuery("select p.plugId, COALESCE(SUM(e.amount), 0) " +
                        "from User u join Plug p on u.userId = p.userId " +
                        "left join ElectricityUsage e on p.plugId = e.plugId " +
                        "where u.userId = :userId " +
                        "and DATE(e.date) = CURDATE()", Object[].class)
                .setParameter("userId", userIdInt)
                .getResultList();
        Map<String, Float> map = new HashMap<>();
        for (Object[] result : results) {
            String name = (String) result[0];
            Float amount = (Float) result[1];
            map.put(name, amount);
        }
        return map;
    }

    public Map<String, Float> findTodayGroupUsage(String homeServerId) {
//        SELECT u.name, COALESCE(SUM(e.amount), 0) AS total_usage
//        FROM user u
//        LEFT JOIN plug p ON u.user_id = p.user_id
//        LEFT JOIN electricity_usage e ON p.plug_id = e.plug_id AND DATE(e.date) = CURDATE()
//        WHERE u.home_server_id = 'carboncheck_admin'
//        GROUP BY u.user_id;
        List<Object[]> results = em.createQuery("select u.name, COALESCE(SUM(e.amount), 0) " +
                        "from User u left join Plug p on u.userId = p.userId " +
                        "left join ElectricityUsage e on p.plugId = e.plugId " +
                        "and DATE(e.date) = CURDATE() " +
                        "where u.homeServerId = :homeServerId " +
                        "group by u.userId", Object[].class)
                .setParameter("homeServerId", homeServerId)
                .getResultList();
        Map<String, Float> map = new HashMap<>();
        for (Object[] result : results) {
            String name = (String) result[0];
            Float amount = (Float) result[1];
            map.put(name, amount);
        }
        return map;
    }

    @Override
    public Optional<ElectricityUsage> findTodayUsageByPlugId(String plugId) {
        ElectricityUsage result = em.createQuery("select e from ElectricityUsage e " +
                        "where e.plugId = :plugId " +
                        "and DATE(e.date) = CURDATE() ", ElectricityUsage.class)
                .setParameter("plugId", plugId)
                .getSingleResult();
        return Optional.ofNullable(result);
    }

    @Override
    public int findSumByPlugId(String plugId) {
        //검증이 필요함
        Long usage = em.createQuery("SELECT SUM(e.amount) FROM ElectricityUsage e WHERE e.plugId = :plugId " +
                        "AND e.date >= FUNCTION('DATE_FORMAT', FUNCTION('DATE_SUB', CURRENT_DATE(), 1), '%Y-%m-01') " +
                        "AND e.date < CURRENT_DATE()", Long.class)
                .setParameter("plugId", plugId)
                .getSingleResult();
        return (usage != null) ? usage.intValue() : 0;
    }
}
