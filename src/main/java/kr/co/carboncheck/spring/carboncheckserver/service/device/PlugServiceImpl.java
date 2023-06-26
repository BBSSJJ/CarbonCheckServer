package kr.co.carboncheck.spring.carboncheckserver.service.device;

import kr.co.carboncheck.spring.carboncheckserver.domain.Plug;
import kr.co.carboncheck.spring.carboncheckserver.domain.User;
import kr.co.carboncheck.spring.carboncheckserver.dto.device.RegisterPlugResponse;
import kr.co.carboncheck.spring.carboncheckserver.repository.device.PlugRepository;
import kr.co.carboncheck.spring.carboncheckserver.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class PlugServiceImpl implements PlugService {
    private PlugRepository plugRepository;
    private UserRepository userRepository;

    @Autowired
    public PlugServiceImpl(PlugRepository plugRepository, UserRepository userRepository) {
        this.plugRepository = plugRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RegisterPlugResponse registerPlug(String plugId, String userId) {
        //TODO: 이미 등록된 플러그인지 확인
        if (plugRepository.findByPlugId(plugId).isPresent()) {
            return new RegisterPlugResponse(false, "이미 등록된 플러그입니다.");
        } else {
            int userIdInt = Integer.parseInt(userId);
            Plug plug = new Plug(plugId, userIdInt);
            if (plugRepository.savePlug(plug)) {
                return new RegisterPlugResponse(true, "플러그가 등록되었습니다.");
            } else {
                return new RegisterPlugResponse(false, "플러그가 등록에 실패하였습니다.");
            }
        }
    }

    @Override
    public Optional<User> findUserByPlugId(String plugId) {
        Optional<Plug> plugOptional = plugRepository.findByPlugId(plugId);
        if (!plugOptional.isPresent()) return Optional.ofNullable(null);
        Plug plug = plugOptional.get();

        return userRepository.findByUserId(plug.getUserId());
    }

    @Override
    public Boolean removePlug(String plugId) {
        return plugRepository.deletePlugById(plugId);
    }
}
