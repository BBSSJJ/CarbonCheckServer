package kr.co.carboncheck.spring.carboncheckserver.repository.usage;

import kr.co.carboncheck.spring.carboncheckserver.domain.ElectricityUsage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface UsageRepository<T> {
    boolean insert(T usage);

    public boolean updateUsage(T usage);

    Map<String, Float> findTodayUserUsage(String userId);

    Map<String, Float> findTodayGroupUsage(String homeServerId);

    List<ElectricityUsage> findByPlugIdAndDate(String plugId, LocalDateTime date);
}
