package kr.co.carboncheck.spring.carboncheckserver.repository.usage;

import kr.co.carboncheck.spring.carboncheckserver.domain.ElectricityUsage;

import java.util.Map;
import java.util.Optional;

public interface UsageRepository<T> {
    boolean insert(T usage);

    public boolean updateUsage(T usage);

    Map<String, Float> findTodayUserUsage(String userId);

    Map<String, Float> findTodayGroupUsage(String homeServerId);

    Optional<ElectricityUsage> findTodayUsageByPlugId(String plugId);

    Optional<ElectricityUsage> findBeforeUsageByPlugId(String plugId);

}
