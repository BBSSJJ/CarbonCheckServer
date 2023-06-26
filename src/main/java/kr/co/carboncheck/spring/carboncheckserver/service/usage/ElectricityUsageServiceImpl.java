package kr.co.carboncheck.spring.carboncheckserver.service.usage;

import kr.co.carboncheck.spring.carboncheckserver.domain.ElectricityUsage;
import kr.co.carboncheck.spring.carboncheckserver.dto.usage.GetUsageResponse;
import kr.co.carboncheck.spring.carboncheckserver.repository.device.PlugRepository;
import kr.co.carboncheck.spring.carboncheckserver.repository.usage.UsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    public Optional<ElectricityUsage> insertUsage(ElectricityUsage usage) {

        //TODO 전기사용량은 지금 날짜로 등록된거 가져와서 수정한 뒤 넣어야 함!!!
        String plugId = usage.getPlugId();

        Optional<ElectricityUsage> optionalTodayUsage = usageRepository.findTodayUsageByPlugId(plugId);
        Optional<ElectricityUsage> optionalBeforeUsage = usageRepository.findBeforeUsageByPlugId(plugId);

        //유효한 플러그가 아닐 경우
        if (!plugRepository.findByPlugId(usage.getPlugId()).isPresent()) {
            System.out.println("등록되지 않은 플러그");
            return Optional.empty();
        }

        // 이 전에 등록된 기록이 없을 경우
        if (!optionalBeforeUsage.isPresent()) {
            //오늘 기록된 데이터가 하나도 없을 경우에는 그냥 삽입
            if (!optionalTodayUsage.isPresent()) {
                usage.setAmount(usage.getCumulativeAmount());
                usageRepository.insert(usage);
                return Optional.ofNullable(usage);
            }
            //오늘 기록된 데이터가 있을 경우, 업데이트하여 삽입
            else {
                ElectricityUsage todayUsage = optionalTodayUsage.get();
                todayUsage.setAmount(usage.getCumulativeAmount());
                todayUsage.setCumulativeAmount(usage.getCumulativeAmount());
                todayUsage.setDate(usage.getDate());
                usageRepository.updateUsage(todayUsage);
                return Optional.ofNullable(todayUsage);
            }
        }
        // 이 전에 등록된 기록이 있을 경우
        else {
            //오늘 기록된 데이터가 하나도 없을 경우에는 그냥 삽입
            ElectricityUsage beforeUsage = optionalBeforeUsage.get();
            if (!optionalTodayUsage.isPresent()) {
                usage.setAmount(usage.getCumulativeAmount() - beforeUsage.getCumulativeAmount());
                usageRepository.insert(usage);
                return Optional.ofNullable(usage);
            }
            //오늘 기록된 데이터가 있을 경우, 업데이트하여 삽입
            else {
                ElectricityUsage todayUsage = optionalTodayUsage.get();
                todayUsage.setAmount(usage.getCumulativeAmount() - beforeUsage.getCumulativeAmount());
                todayUsage.setCumulativeAmount(usage.getCumulativeAmount());
                todayUsage.setDate(usage.getDate());
                usageRepository.updateUsage(todayUsage);
                return Optional.ofNullable(todayUsage);
            }
        }
    }

    @Override
    public List<GetUsageResponse> getTodayUserUsage(String userId) {
        System.out.println("in getTodayUserUsage");
        return usageRepository.findTodayUserUsage(userId);
    }

    @Override
    public List<GetUsageResponse> getTodayGroupUsage(String homeServerId) {
        return usageRepository.findTodayGroupUsage((homeServerId));
    }
}
