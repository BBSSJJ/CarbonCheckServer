package kr.co.carboncheck.spring.carboncheckserver.service.usage;

import kr.co.carboncheck.spring.carboncheckserver.domain.ElectricityUsage;
import kr.co.carboncheck.spring.carboncheckserver.dto.usage.GetUsageResponse;
import kr.co.carboncheck.spring.carboncheckserver.repository.device.PlugRepository;
import kr.co.carboncheck.spring.carboncheckserver.repository.usage.UsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class ElectricityUsageServiceImpl implements UsageService<ElectricityUsage> {

    private UsageRepository<ElectricityUsage> usageRepository;
    private PlugRepository plugRepository;

    @Autowired
    public ElectricityUsageServiceImpl(UsageRepository<ElectricityUsage> usageRepository, PlugRepository plugRepository) {
        this.usageRepository = usageRepository;
        this.plugRepository = plugRepository;
    }

    @Override
    public boolean insertUsage(ElectricityUsage usage) {
        //TODO 전기사용량은 지금 날짜로 등록된거 가져와서 수정한 뒤 넣어야 함!!!
        String plugId = usage.getPlugId();
        LocalDateTime date = usage.getDate();

        if(!plugRepository.findByPlugId(usage.getPlugId()).isPresent()){
            System.out.println("등록되지 않은 플러그");
            return false;
        }

        List<ElectricityUsage> list = usageRepository.findByPlugIdAndDate(plugId, date);
        if (list.isEmpty()) {
            System.out.println("today's electricity usage is empty");
            usageRepository.insert(usage);
        } else if (list.size() > 1) {
            //오류
        } else {
            ElectricityUsage pre_usage = list.get(0);
            pre_usage.setDate(usage.getDate());
            pre_usage.setAmount(usage.getAmount());
            usageRepository.updateUsage(pre_usage);
        }
        return true;
    }

    @Override
    public GetUsageResponse getTodayUserUsage(String userId) {
        return null;
    }

    @Override
    public GetUsageResponse getTodayGroupUsage(String homeServerId) {
        return null;
    }


}
