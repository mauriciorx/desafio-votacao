package com.mauriciorx.votacao.client.service;

import com.mauriciorx.votacao.api.v1.service.IAssociateService;
import com.mauriciorx.votacao.client.dto.CpfValidatorDTO;
import com.mauriciorx.votacao.client.enums.CpfValidatorEnum;
import com.mauriciorx.votacao.client.exception.InvalidCpfException;
import com.mauriciorx.votacao.util.CpfUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CpfValidatorService {

    private IAssociateService associateService;

    public ResponseEntity<Map<String, String>> generateCpf() {
        Map<String, String> response = Map.of("cpf", CpfUtil.generateCpf());

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Map<String, String>> validateCpf(String cpf) {
        if(!CpfUtil.isCpfValid(cpf)) throw new InvalidCpfException();

        CpfValidatorDTO result = associateService.findByCpf(cpf) != null && CpfUtil.isCpfAble() ? new CpfValidatorDTO(CpfValidatorEnum.ABLE_TO_VOTE) :
                                                                                                new CpfValidatorDTO(CpfValidatorEnum.UNABLE_TO_VOTE);

        Map<String, String> responseBody = Map.of("status", result.getCpfValidatorEnum().name());

        if( result.getCpfValidatorEnum() == CpfValidatorEnum.UNABLE_TO_VOTE ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
        }

        return ResponseEntity.ok().body(responseBody);
    }

}
