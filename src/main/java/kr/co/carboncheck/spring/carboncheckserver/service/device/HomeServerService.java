package kr.co.carboncheck.spring.carboncheckserver.service.device;

import kr.co.carboncheck.spring.carboncheckserver.dto.device.JoinHomeServerResponse;
import kr.co.carboncheck.spring.carboncheckserver.dto.device.RegisterHomeServerResponse;

public interface HomeServerService {
    RegisterHomeServerResponse createHomeServer(String homeServerId, String email);

    JoinHomeServerResponse joinHomeServer(String homeServerId, String email);
}
