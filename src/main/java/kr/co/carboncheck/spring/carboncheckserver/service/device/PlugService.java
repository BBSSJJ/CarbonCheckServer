package kr.co.carboncheck.spring.carboncheckserver.service.device;

import kr.co.carboncheck.spring.carboncheckserver.domain.Plug;
import kr.co.carboncheck.spring.carboncheckserver.dto.device.RegisterPlugResponse;

public interface PlugService {
    RegisterPlugResponse registerPlug(String plugId, String userId);
}
