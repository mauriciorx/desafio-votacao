package com.mauriciorx.votacao.api.v1.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(name = "cpfUtilFacade", url = "${votacao.url}")
public interface CpfUtilFacade {

    @PostMapping("/client/cpf/generate")
    Map<String, String> generateCpf();

    @PostMapping("/client/cpf/validate/{cpf}")
    Map<String, String> validateCpf(@PathVariable String cpf);
}
