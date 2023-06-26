package kr.co.carboncheck.spring.carboncheckserver.service.usage;

import kr.co.carboncheck.spring.carboncheckserver.dto.usage.GetUsageResponse;

import java.util.List;
import java.util.Optional;

public interface UsageService<T> {
    Optional<T> insertUsage(T usage);

    List<GetUsageResponse> getTodayUserUsage(String userId);

    List<GetUsageResponse> getTodayGroupUsage(String homeServerId);
}