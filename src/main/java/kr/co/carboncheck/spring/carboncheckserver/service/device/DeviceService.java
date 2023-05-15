package kr.co.carboncheck.spring.carboncheckserver.service.device;

import kr.co.carboncheck.spring.carboncheckserver.dto.device.JoinHomeServerResponse;
import kr.co.carboncheck.spring.carboncheckserver.dto.device.RegisterHomeServerResponse;

public interface DeviceService {
    RegisterHomeServerResponse createHomeServer(String homeServerId, String email);

    JoinHomeServerResponse joinHomeServer(String homeServerId, String email);
}
