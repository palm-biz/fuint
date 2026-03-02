package cloud.palmbiz.application.address.service;

import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.infrastructure.model.MtAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AddressQueryService {

    private final AddressService addressService;

    public MtAddress detail(Integer id) {
        return addressService.detail(id);
    }

    public List<MtAddress> queryListByParams(Map<String, Object> params) throws BusinessCheckException {
        return addressService.queryListByParams(params);
    }
}
