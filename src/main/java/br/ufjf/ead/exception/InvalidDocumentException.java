package br.ufjf.ead.exception;

/**
 * Exceção para indicar documento (CPF ou CNPJ) inválido.
 *
 * @author Fabio Oliveira
 */
public class InvalidDocumentException extends Exception {
    
    public InvalidDocumentException(String message) {
        super(message);
    }
    
    public InvalidDocumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
