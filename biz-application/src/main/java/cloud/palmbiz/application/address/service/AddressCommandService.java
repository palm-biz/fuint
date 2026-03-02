package cloud.palmbiz.application.address.service;

import cloud.palmbiz.common.service.AddressService;
import cloud.palmbiz.framework.exception.BusinessCheckException;
import cloud.palmbiz.infrastructure.model.MtAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressCommandService {

    private final AddressService addressService;

    @Transactional(rollbackFor = Exception.class)
    public MtAddress saveAddress(MtAddress mtAddress) throws BusinessCheckException {
        return addressService.saveAddress(mtAddress);
    }
}
