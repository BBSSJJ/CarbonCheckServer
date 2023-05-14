package kr.co.carboncheck.spring.carboncheckserver.service.device;

import kr.co.carboncheck.spring.carboncheckserver.dto.device.RegisterHomeServerResponse;

public interface DeviceService {
    RegisterHomeServerResponse createHomeServer(String homeServerId, String email);
}
