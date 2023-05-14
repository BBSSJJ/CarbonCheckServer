package kr.co.carboncheck.spring.carboncheckserver.service.device;

import kr.co.carboncheck.spring.carboncheckserver.domain.HomeServer;
import kr.co.carboncheck.spring.carboncheckserver.domain.User;
import kr.co.carboncheck.spring.carboncheckserver.dto.device.RegisterHomeServerResponse;
import kr.co.carboncheck.spring.carboncheckserver.repository.device.HomeServerRepository;
import kr.co.carboncheck.spring.carboncheckserver.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Service
public class HomeServerDeviceServiceImpl implements DeviceService {

    private UserRepository userRepository;
    private HomeServerRepository homeServerRepository;

    @Autowired
    HomeServerDeviceServiceImpl(HomeServerRepository homeServerRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.homeServerRepository = homeServerRepository;
    }

    @Override
    public RegisterHomeServerResponse createHomeServer(String homeServerId, String email) {
        // email을 이용해서 UserRepository에서 user id 를 가져온닷
        System.out.println("홈서버 등록 유저 이메일: " + email + "홈서버 아이디: " + "homeserverId");
        Optional<User> userOptional = userRepository.findByEmail(email);
        Optional<HomeServer> homeServerOptional = homeServerRepository.findByHomeServerId(homeServerId);
        if (!userOptional.isPresent()) {
            return new RegisterHomeServerResponse(false, "잘못된 유저 정보 입니다.");
        }
        if (homeServerOptional.isPresent()) {
            return new RegisterHomeServerResponse(false, "이미 등록된 기기 입니다.");
        }
        User user = userOptional.get();

        System.out.println("여기까지1");
        HomeServer homeServer = new HomeServer();
        homeServer.setHomeServerId(homeServerId);
        homeServerRepository.saveHomeServer(homeServer);
        System.out.println("여기까지2");

        userRepository.updateHomeServerId(user, homeServerId);
        System.out.println("여기까지3");


        return new RegisterHomeServerResponse(true, "기기가 성공적으로 등록되었습니다");
    }
}
