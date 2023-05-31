package kr.co.carboncheck.spring.carboncheckserver.service.usage;

import kr.co.carboncheck.spring.carboncheckserver.domain.WaterUsage;
import kr.co.carboncheck.spring.carboncheckserver.dto.usage.GetUsageResponse;
import kr.co.carboncheck.spring.carboncheckserver.repository.usage.UsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class WaterUsageServiceImpl implements UsageService<WaterUsage> {

    private final UsageRepository<WaterUsage> usageRepository;

    @Autowired
    public WaterUsageServiceImpl(UsageRepository<WaterUsage> usageRepository) {
        this.usageRepository = usageRepository;
    }

    @Override
    public boolean insertUsage(WaterUsage usage) {
        usageRepository.insert(usage);
        return true;
    }

    @Override
    public GetUsageResponse getTodayUserUsage(String userId) {
        GetUsageResponse userUsage = new GetUsageResponse(usageRepository.findTodayUserUsage(userId));
        return userUsage;
    }

    @Override
    public GetUsageResponse getTodayGroupUsage(String homeServerId) {
        GetUsageResponse groupUsage = new GetUsageResponse(usageRepository.findTodayGroupUsage(homeServerId));
        return groupUsage;
    }
}
