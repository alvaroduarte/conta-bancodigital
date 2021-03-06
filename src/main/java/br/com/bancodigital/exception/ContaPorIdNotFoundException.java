package br.com.bancodigital.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Agencia e Conta não encontrada pelo id informado!")
public class ContaPorIdNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7227212838595641219L;
	
	public ContaPorIdNotFoundException() {
        super();
    }
    public ContaPorIdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public ContaPorIdNotFoundException(String message) {
        super(message);
    }
    public ContaPorIdNotFoundException(Throwable cause) {
        super(cause);
    }
}