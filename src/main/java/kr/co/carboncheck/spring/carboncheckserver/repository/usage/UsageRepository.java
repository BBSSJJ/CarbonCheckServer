package kr.co.carboncheck.spring.carboncheckserver.repository.usage;

import kr.co.carboncheck.spring.carboncheckserver.domain.ElectricityUsage;
import kr.co.carboncheck.spring.carboncheckserver.dto.usage.GetUsageResponse;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UsageRepository<T> {
    boolean insert(T usage);

    public boolean updateUsage(T usage);

    List<GetUsageResponse> findTodayUserUsage(String userId);

    List<GetUsageResponse> findTodayGroupUsage(String homeServerId);

    Optional<ElectricityUsage> findTodayUsageByPlugId(String plugId);

    Optional<ElectricityUsage> findBeforeUsageByPlugId(String plugId);

}
