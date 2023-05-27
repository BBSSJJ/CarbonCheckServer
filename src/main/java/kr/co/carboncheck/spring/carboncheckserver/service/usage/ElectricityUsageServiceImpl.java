package kr.co.carboncheck.spring.carboncheckserver.service.usage;

import kr.co.carboncheck.spring.carboncheckserver.domain.ElectricityUsage;
import kr.co.carboncheck.spring.carboncheckserver.repository.usage.UsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElectricityUsageServiceImpl implements UsageService<ElectricityUsage> {

    private UsageRepository<ElectricityUsage> usageRepository;

    @Autowired
    public ElectricityUsageServiceImpl(UsageRepository<ElectricityUsage> usageRepository){
        this.usageRepository = usageRepository;
    }

    @Override
    public boolean insertUsage(ElectricityUsage usage) {
        usageRepository.insert(usage);
        return true;
    }
}
