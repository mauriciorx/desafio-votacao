package com.mauriciorx.votacao.exception;

import com.mauriciorx.votacao.client.exception.InvalidCpfException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({AgendaNotFoundException.class})
    public ResponseEntity<Object> handleAgendaNotFoundException(AgendaNotFoundException exception) {
        logError(exception);
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler({AlreadyVotedException.class})
    public ResponseEntity<Object> handleAlreadyVotedException(AlreadyVotedException exception) {
        logError(exception);
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler({AssociateNotFoundException.class})
    public ResponseEntity<Object> handleAssociateNotFoundException(AssociateNotFoundException exception) {
        logError(exception);
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler({ExistentCpfException.class})
    public ResponseEntity<Object> handleExistentCpfException(ExistentCpfException exception) {
        logError(exception);
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler({SessionClosedException.class})
    public ResponseEntity<Object> handleSessionClosedException(SessionClosedException exception) {
        logError(exception);
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler({SessionInProgressException.class})
    public ResponseEntity<Object> handleSessionInProgressException(SessionInProgressException exception) {
        logError(exception);
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler({SessionNotFoundException.class})
    public ResponseEntity<Object> handleSessionNotFoundException(SessionNotFoundException exception) {
        logError(exception);
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler({VoteNotFoundException.class})
    public ResponseEntity<Object> handleVoteNotFoundException(VoteNotFoundException exception) {
        logError(exception);
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler({InvalidCpfException.class})
    public ResponseEntity<Object> handleInvalidCpfException(InvalidCpfException exception) {
        logError(exception);
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler({FeignException.class})
    public ResponseEntity<Object> handleFeignException(FeignException exception) {
        logError(exception);
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        logError(exception);
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    private void logError(RuntimeException exception) {
        log.error("{}", exception.getMessage(), exception);
    }
}
