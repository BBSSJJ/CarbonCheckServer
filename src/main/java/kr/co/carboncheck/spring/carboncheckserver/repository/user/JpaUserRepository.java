package kr.co.carboncheck.spring.carboncheckserver.repository.user;

import kr.co.carboncheck.spring.carboncheckserver.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaUserRepository implements UserRepository {
    private final EntityManager em;


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
    public Optional<User> findByUserId(Long userId) {
        User user = em.find(User.class, userId);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        System.out.println("in find by email");
        List<User> result = em.createQuery("select m from User m where m.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();
        return result.stream().findAny();
    }
}
