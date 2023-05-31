package kr.co.carboncheck.spring.carboncheckserver.service.device;

import kr.co.carboncheck.spring.carboncheckserver.domain.HomeServer;
import kr.co.carboncheck.spring.carboncheckserver.domain.User;
import kr.co.carboncheck.spring.carboncheckserver.dto.device.JoinHomeServerResponse;
import kr.co.carboncheck.spring.carboncheckserver.dto.device.RegisterHomeServerResponse;
import kr.co.carboncheck.spring.carboncheckserver.repository.device.HomeServerRepository;
import kr.co.carboncheck.spring.carboncheckserver.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class HomeServerServiceImpl implements HomeServerService {

    private final UserRepository userRepository;
    private final HomeServerRepository homeServerRepository;

    @Autowired
    HomeServerServiceImpl(HomeServerRepository homeServerRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.homeServerRepository = homeServerRepository;
    }

    @Override
    public RegisterHomeServerResponse createHomeServer(String homeServerId, String email) {
        // email을 이용해서 UserRepository에서 user id 를 가져온닷
        System.out.println("홈서버 등록 유저 이메일: " + email + "홈서버 아이디: " + homeServerId);
        Optional<User> userOptional = userRepository.findByEmail(email);
        Optional<HomeServer> homeServerOptional = homeServerRepository.findByHomeServerId(homeServerId);
        if (!userOptional.isPresent()) {
            return new RegisterHomeServerResponse(false, "잘못된 유저 정보 입니다.");
        }
        if (homeServerOptional.isPresent()) {
            return new RegisterHomeServerResponse(false, "이미 등록된 기기 입니다.");
        }
        User user = userOptional.get();
        if(user.getHomeServerId() != null){
            return new RegisterHomeServerResponse(false, "이미 홈서버에 가입되어 있습니다.");
        }

        HomeServer homeServer = new HomeServer();
        homeServer.setHomeServerId(homeServerId);
        homeServerRepository.saveHomeServer(homeServer);

        userRepository.updateHomeServerId(user, homeServerId);


        return new RegisterHomeServerResponse(true, "기기가 성공적으로 등록되었습니다.");
    }

    @Override
    public JoinHomeServerResponse joinHomeServer(String homeServerId, String email) {
        System.out.println("홈서버 참여 유저 이메일: " + email + "홈서버 아이디: " + homeServerId);
        Optional<User> userOptional = userRepository.findByEmail(email);
        Optional<HomeServer> homeServerOptional = homeServerRepository.findByHomeServerId(homeServerId);
        if (!userOptional.isPresent()) {
            return new JoinHomeServerResponse(false, "잘못된 유저 정보 입니다.");
        }
        if (!homeServerOptional.isPresent()) {
            return new JoinHomeServerResponse(false, "등록되지 않은 기기입니다.");
        }
        User user = userOptional.get();

        if(user.getHomeServerId() != null){
            return new JoinHomeServerResponse(false, "이미 홈서버에 가입되어 있습니다.");
        }

        userRepository.updateHomeServerId(user, homeServerId);

        return new JoinHomeServerResponse(true, "정상적으로 참여하셨습니다.");
    }
}
