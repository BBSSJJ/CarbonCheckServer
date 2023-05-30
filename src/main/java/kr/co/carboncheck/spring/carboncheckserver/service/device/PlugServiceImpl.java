package kr.co.carboncheck.spring.carboncheckserver.service.device;

import kr.co.carboncheck.spring.carboncheckserver.domain.Plug;
import kr.co.carboncheck.spring.carboncheckserver.dto.device.RegisterPlugResponse;
import kr.co.carboncheck.spring.carboncheckserver.repository.device.PlugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class PlugServiceImpl implements PlugService {
    private PlugRepository plugRepository;

    @Autowired
    public PlugServiceImpl(PlugRepository plugRepository) {
        this.plugRepository = plugRepository;
    }

    @Override
    public RegisterPlugResponse registerPlug(String plugId, String userId) {
        //TODO: 이미 등록된 플러그인지 확인
        if (plugRepository.findByPlugId(plugId).isPresent()) {
            return new RegisterPlugResponse(false, "이미 등록된 플러그입니다.");
        } else {
            Plug plug = new Plug(plugId, userId);
            if (plugRepository.savePlug(plug)) {
                return new RegisterPlugResponse(true, "플러그가 등록되었습니다.");
            } else {
                return new RegisterPlugResponse(false, "플러그가 등록에 실패하였습니다.");
            }
        }
    }
}
