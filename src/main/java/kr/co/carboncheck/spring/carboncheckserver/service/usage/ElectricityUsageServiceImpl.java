package kr.co.carboncheck.spring.carboncheckserver.service.usage;

import kr.co.carboncheck.spring.carboncheckserver.domain.ElectricityUsage;
import kr.co.carboncheck.spring.carboncheckserver.dto.usage.GetUsageResponse;
import kr.co.carboncheck.spring.carboncheckserver.repository.device.PlugRepository;
import kr.co.carboncheck.spring.carboncheckserver.repository.usage.UsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

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
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        Optional<ElectricityUsage> optionalUsage = usageRepository.findTodayUsageByPlugId(plugId);

        if (!plugRepository.findByPlugId(usage.getPlugId()).isPresent()) {
            System.out.println("등록되지 않은 플러그");
            return false;
        }

        //오늘이 1일인 경우
        if (date.getDayOfMonth() == 1) {
            //그냥 넣으면 됨. 대신 오늘 등록된 값 있으면 업데이트
            if (!optionalUsage.isPresent()) {
                usageRepository.insert(usage);
            } else {
                ElectricityUsage pre_usage = optionalUsage.get();
                pre_usage.setDate(usage.getDate());
                pre_usage.setAmount(usage.getAmount());
                usageRepository.updateUsage(pre_usage);
            }
            return true;
        }

        //오늘이 1일이 아닌 경우
        //이전 사용량의 합 가져와서 뺀 후 넣는다.
        int beforeUsage = usageRepository.findSumByPlugId(plugId);

        if (!optionalUsage.isPresent()) {
            usage.setAmount(usage.getAmount() - beforeUsage);
            usageRepository.insert(usage);
        } else {
            ElectricityUsage pre_usage = optionalUsage.get();
            pre_usage.setDate(usage.getDate());
            pre_usage.setAmount(usage.getAmount() - beforeUsage);
            usageRepository.updateUsage(pre_usage);
        }
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
