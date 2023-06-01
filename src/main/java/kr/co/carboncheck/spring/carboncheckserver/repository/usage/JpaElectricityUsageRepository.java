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
        System.out.println("in findTodayUserUsage");
        int userIdInt = Integer.parseInt(userId);
        List<Object[]> results = em.createQuery("select p.plugId, COALESCE(SUM(e.amount), 0) " +
                        "from User u join Plug p on u.userId = p.userId " +
                        "left join ElectricityUsage e on p.plugId = e.plugId " +
                        "where u.userId = :userId " +
                        "and DATE(e.date) = CURDATE()" +
                        "group by p.plugId", Object[].class)
                .setParameter("userId", userIdInt)
                .getResultList();
        Map<String, Float> map = new HashMap<>();
        //TODO: 원래는 Long 타입이어야 한다!!!!
        for (Object[] result : results) {
            String plugId = (String) result[0];
            Number amount = (Number) result[1];
            Float convertedAmount = amount.floatValue(); // 형변환
            map.put(plugId, convertedAmount);
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
        System.out.println("in findTodayGroupUsage");
        List<Object[]> results = em.createQuery("select u.name, COALESCE(SUM(e.amount), 0) " +
                        "from User u left join Plug p on u.userId = p.userId " +
                        "left join ElectricityUsage e on p.plugId = e.plugId " +
                        "and DATE(e.date) = CURDATE() " +
                        "where u.homeServerId = :homeServerId " +
                        "group by u.userId", Object[].class)
                .setParameter("homeServerId", homeServerId)
                .getResultList();
        Map<String, Float> map = new HashMap<>();
        //TODO: 원래는 Long 타입이어야 한다!!!!
        for (Object[] result : results) {
            String name = (String) result[0];
            Number amount = (Number) result[1];
            Float convertedAmount = amount.floatValue(); // 형변환
            map.put(name, convertedAmount);
        }
        return map;
    }

    @Override
    public Optional<ElectricityUsage> findTodayUsageByPlugId(String plugId) {
        System.out.println("in findTodayUsageByPlugId");
        List<ElectricityUsage> result = em.createQuery("select e from ElectricityUsage e " +
                        "where e.plugId = :plugId " +
                        "and DATE(e.date) = CURDATE() ", ElectricityUsage.class)
                .setParameter("plugId", plugId)
                .getResultList();
        if (result.isEmpty()) return Optional.ofNullable(null);
        else return Optional.ofNullable(result.get(0));
    }

    @Override
    public Optional<ElectricityUsage> findBeforeUsageByPlugId(String plugId) {
//        SELECT *
//                FROM electricity_usage
//        WHERE plug_id = 'ebe5307157b8c5a9a9ecwi' AND date < CURDATE()
//        ORDER BY date DESC
//        LIMIT 1;
        System.out.println("in findBeforeUsageByPlugId");
        List<ElectricityUsage> result = em.createQuery("select e from ElectricityUsage e " +
                        "where e.plugId = :plugId " +
                        "and DATE(e.date) < CURDATE() " +
                        "ORDER BY e.date DESC ", ElectricityUsage.class)
                .setParameter("plugId", plugId)
                .setMaxResults(1)
                .getResultList();
        if (result.isEmpty()) return Optional.ofNullable(null);
        else return Optional.ofNullable(result.get(0));
    }

//    @Override
//    public int findSumByPlugId(String plugId) {
//        //검증이 필요함
//        Long usage = em.createQuery("SELECT SUM(e.amount) FROM ElectricityUsage e WHERE e.plugId = :plugId " +
//                        "AND e.date >= FUNCTION('DATE_FORMAT', FUNCTION('DATE_SUB', CURRENT_DATE(), 1), '%Y-%m-01') " +
//                        "AND e.date < CURRENT_DATE()", Long.class)
//                .setParameter("plugId", plugId)
//                .getSingleResult();
//        return (usage != null) ? usage.intValue() : 0;
//    }
}
