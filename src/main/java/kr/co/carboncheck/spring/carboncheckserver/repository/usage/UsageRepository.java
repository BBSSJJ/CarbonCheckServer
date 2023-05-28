package kr.co.carboncheck.spring.carboncheckserver.repository.usage;

import kr.co.carboncheck.spring.carboncheckserver.domain.ElectricityUsage;

import java.time.LocalDateTime;
import java.util.List;

public interface UsageRepository<T> {
    boolean insert(T usage);

    public boolean updateUsage(T usage);

    List<ElectricityUsage> findByPlugIdAndDate(String plugId, LocalDateTime date);
}
