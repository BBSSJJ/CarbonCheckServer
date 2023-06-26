package kr.co.carboncheck.spring.carboncheckserver.service.usage;

import kr.co.carboncheck.spring.carboncheckserver.domain.WaterUsage;
import kr.co.carboncheck.spring.carboncheckserver.dto.usage.GetUsageResponse;
import kr.co.carboncheck.spring.carboncheckserver.repository.usage.UsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class WaterUsageServiceImpl implements UsageService<WaterUsage> {

    private final UsageRepository<WaterUsage> usageRepository;

    @Autowired
    public WaterUsageServiceImpl(UsageRepository<WaterUsage> usageRepository) {
        this.usageRepository = usageRepository;
    }

    @Override
    public Optional<WaterUsage> insertUsage(WaterUsage usage) {
        usageRepository.insert(usage);
        return Optional.ofNullable(usage);
    }

    @Override
    public List<GetUsageResponse> getTodayUserUsage(String userId) {
        return usageRepository.findTodayUserUsage(userId);
    }

    @Override
    public List<GetUsageResponse> getTodayGroupUsage(String homeServerId) {
        return usageRepository.findTodayGroupUsage((homeServerId));
    }
}
