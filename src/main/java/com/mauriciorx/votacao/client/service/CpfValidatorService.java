package com.mauriciorx.votacao.client.service;

import com.mauriciorx.votacao.api.v1.service.IAssociateService;
import com.mauriciorx.votacao.client.dto.CpfValidatorDTO;
import com.mauriciorx.votacao.client.enums.CpfValidatorEnum;
import com.mauriciorx.votacao.client.exception.InvalidCpfException;
import com.mauriciorx.votacao.util.CpfUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CpfValidatorService {

    private IAssociateService associateService;

    public String generateCpf(){
        return CpfUtil.generateCpf();
    }

    public CpfValidatorDTO validateCpf(String cpf){
        if(!CpfUtil.isCpfValid(cpf)) throw new InvalidCpfException();

        return associateService.findByCpf(cpf) != null && CpfUtil.isCpfAble() ? new CpfValidatorDTO(CpfValidatorEnum.ABLE_TO_VOTE) : new CpfValidatorDTO(CpfValidatorEnum.UNABLE_TO_VOTE);
    }

}
