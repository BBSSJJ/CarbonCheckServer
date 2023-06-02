package kr.co.carboncheck.spring.carboncheckserver.service.usage;

import kr.co.carboncheck.spring.carboncheckserver.dto.usage.GetUsageResponse;

import java.util.List;

public interface UsageService<T> {
    boolean insertUsage(T usage);

    List<GetUsageResponse> getTodayUserUsage(String userId);

    List<GetUsageResponse> getTodayGroupUsage(String homeServerId);
}