package kr.co.carboncheck.spring.carboncheckserver.repository.user;

import kr.co.carboncheck.spring.carboncheckserver.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JpaUserRepository implements UserRepository {
    private final EntityManager em;

    @Autowired
    public JpaUserRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean saveUser(User user) {
        System.out.println("email is " + user.getEmail());
        em.persist(user);
        return true;
    }

    @Override
    public boolean updateHomeServerId(User user, String homeServerId) {
        user.setHomeServerId(homeServerId);
        em.merge(user);
        return true;
    }

    @Override
    public Optional<User> findByUserId(Long userId) {
        User user = em.find(User.class, userId);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        List<User> result = em.createQuery("select m from User m where m.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public Map<String, Integer> findGroupTargetAmount(String homeServerId) {
        List<Object[]> results = em.createQuery("select u.name, u.targetAmount " +
                "from User u " +
                "where u.homeServerId = :homeServerId ", Object[].class)
                .setParameter("homeServerId", homeServerId)
                .getResultList();
        Map<String, Integer> map = new HashMap<>();
        //TODO: 원래는 Long 타입이어야 한다!!!!
        for (Object[] result : results) {
            String name = (String) result[0];
            Integer amount = (Integer) result[1];
            map.put(name, amount);
        }
        return map;
    }
}
