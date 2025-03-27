package com.mauriciorx.votacao.api.v1.service;

import com.mauriciorx.votacao.api.v1.dto.request.AssociateRequestDTO;
import com.mauriciorx.votacao.api.v1.dto.response.AssociateResponseDTO;
import com.mauriciorx.votacao.api.v1.entity.Associate;
import com.mauriciorx.votacao.api.v1.repository.AssociateRepository;
import com.mauriciorx.votacao.api.v1.service.impl.AssociateService;
import com.mauriciorx.votacao.client.exception.InvalidCpfException;
import com.mauriciorx.votacao.exception.AssociateNotFoundException;
import com.mauriciorx.votacao.exception.ExistentCpfException;
import com.mauriciorx.votacao.util.CpfUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssociateServiceTest {

    @InjectMocks
    private AssociateService associateService;

    @Mock
    private AssociateRepository associateRepository;

    private MockedStatic<CpfUtil> mockedStatic;

    private Associate associate;
    private final String validCpf = "241.690.100-19";
    private final String invalidCpf = "241.690.100-00";

    @BeforeEach
    void setUp() {
        mockedStatic = mockStatic(CpfUtil.class);

        associate = Associate.builder()
                .id(1L)
                .name("João Silva")
                .cpf(validCpf)
                .build();
    }

    @AfterEach
    void tearDown() {
        mockedStatic.close();
    }

    @Test
    void shouldCreateAssociateSuccessfully() {
        mockedStatic.when(() -> CpfUtil.isCpfValid(validCpf)).thenReturn(true);

        when(associateRepository.findByCpf(validCpf)).thenReturn(Optional.empty());
        when(associateRepository.save(any(Associate.class))).thenReturn(associate);

        AssociateRequestDTO requestDTO = new AssociateRequestDTO("João Silva", validCpf);
        AssociateResponseDTO responseDTO = associateService.create(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(associate.getId(), responseDTO.getId());
        assertEquals(associate.getName(), responseDTO.getName());
        assertEquals(associate.getCpf(), responseDTO.getCpf());

        verify(associateRepository, times(1)).findByCpf(validCpf);
        verify(associateRepository, times(1)).save(any(Associate.class));
    }

    @Test
    void shouldThrowExceptionWhenCpfAlreadyExists() {
        when(associateRepository.findByCpf(validCpf)).thenReturn(Optional.of(associate));

        AssociateRequestDTO requestDTO = new AssociateRequestDTO("João Silva", validCpf);

        assertThrows(ExistentCpfException.class, () -> associateService.create(requestDTO));
        verify(associateRepository, never()).save(any(Associate.class));
    }

    @Test
    void shouldThrowExceptionWhenCpfIsInvalid() {
        mockedStatic.when(() -> CpfUtil.isCpfValid(invalidCpf)).thenReturn(false);

        AssociateRequestDTO requestDTO = new AssociateRequestDTO("Maria Souza", invalidCpf);

        assertThrows(InvalidCpfException.class, () -> associateService.create(requestDTO));
        verify(associateRepository, never()).save(any(Associate.class));
    }

    @Test
    void shouldFindAssociateByIdSuccessfully() {
        when(associateRepository.findById(1L)).thenReturn(Optional.of(associate));

        AssociateResponseDTO responseDTO = associateService.findById(1L);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
    }

    @Test
    void shouldThrowExceptionWhenAssociateNotFoundById() {
        when(associateRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(AssociateNotFoundException.class, () -> associateService.findById(2L));
    }

    @Test
    void shouldFindAssociateByCpfSuccessfully() {
        mockedStatic.when(() -> CpfUtil.isCpfValid(validCpf)).thenReturn(true);
        when(associateRepository.findByCpf(validCpf)).thenReturn(Optional.of(associate));

        AssociateResponseDTO responseDTO = associateService.findByCpf(validCpf);

        assertNotNull(responseDTO);
        assertEquals(validCpf, responseDTO.getCpf());
    }

    @Test
    void shouldThrowExceptionWhenAssociateNotFoundByCpf() {
        mockedStatic.when(() -> CpfUtil.isCpfValid(validCpf)).thenReturn(true);
        when(associateRepository.findByCpf(validCpf)).thenReturn(Optional.empty());

        assertThrows(AssociateNotFoundException.class, () -> associateService.findByCpf(validCpf));
    }

    @Test
    void shouldFindAllAssociatesSuccessfully() {
        when(associateRepository.findAll()).thenReturn(List.of(associate));

        List<AssociateResponseDTO> responseList = associateService.findAll();

        assertEquals(1, responseList.size());
        verify(associateRepository, times(1)).findAll();
    }
}
