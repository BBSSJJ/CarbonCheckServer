package kr.co.carboncheck.spring.carboncheckserver.service.usage;

import kr.co.carboncheck.spring.carboncheckserver.dto.usage.GetUsageResponse;

import java.util.Map;

public interface UsageService<T> {
    boolean insertUsage(T usage);

    GetUsageResponse getTodayUserUsage(String userId);

    GetUsageResponse getTodayGroupUsage(String homeServerId);
}