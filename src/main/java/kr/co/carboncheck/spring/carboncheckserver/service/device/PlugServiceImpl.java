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
    public RegisterPlugResponse registerPlug(Plug plug) {
        //TODO: 이미 등록된 플러그인지 확인

        RegisterPlugResponse registerPlugResponse = new RegisterPlugResponse();
        if (plugRepository.savePlug(plug)) {
            registerPlugResponse.setSuccess(true);
            registerPlugResponse.setMessage("플러그가 등록되었습니다.");
        } else {
            registerPlugResponse.setSuccess(false);
            registerPlugResponse.setMessage("플러그 등록에 실패했습니다.");
        }

        return registerPlugResponse;
    }
}
