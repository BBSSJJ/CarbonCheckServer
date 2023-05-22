package kr.co.carboncheck.spring.carboncheckserver.service.usage;

import kr.co.carboncheck.spring.carboncheckserver.domain.ElectricityUsage;
import org.springframework.stereotype.Service;

@Service
public class ElectricityUsageServiceImpl implements UsageService<ElectricityUsage> {

    @Override
    public boolean insertUsage(ElectricityUsage usage) {
        return false;
    }
}
