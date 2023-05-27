package kr.co.carboncheck.spring.carboncheckserver.repository.device;


import kr.co.carboncheck.spring.carboncheckserver.domain.Plug;

public interface PlugRepository {
    boolean savePlug(Plug plug);
}
