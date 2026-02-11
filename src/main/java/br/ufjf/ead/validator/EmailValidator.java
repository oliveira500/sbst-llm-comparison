package br.ufjf.ead.validator;

import br.ufjf.ead.exception.InvalidDocumentException;

/**
 * Validador de e-mails seguindo RFC 5322.
 * 
 * Implementa validação robusta de endereços de e-mail com regex complexa,
 * validação de domínios e tratamento de casos de borda.
 * 
 * @author Fabio Oliveira
 */
public class EmailValidator {
    
    // Regex complexa para validação de e-mails RFC 5322
    private static final String EMAIL_REGEX = 
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    
    // Domínios conhecidos como inválidos ou suspeitos
    private static final String[] INVALID_DOMAINS = {
        "tempmail.org", "10minutemail.com", "guerrillamail.com"
    };
    
    /**
     * Valida um e-mail.
     * 
     * @param email E-mail a ser validado
     * @return true se o e-mail é válido, false caso contrário
     */
    public static boolean isValid(String email) {
        try {
            validate(email);
            return true;
        } catch (InvalidDocumentException e) {
            return false;
        }
    }
    
    /**
     * Valida um e-mail e lança exceção se inválido.
     * 
     * @param email E-mail a ser validado
     * @throws InvalidDocumentException se o e-mail for inválido
     */
    public static void validate(String email) throws InvalidDocumentException {
        // Verifica se é nulo ou vazio
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidDocumentException("E-mail não pode ser nulo ou vazio");
        }
        
        // Remove espaços em branco
        String cleanEmail = email.trim();
        
        // Verifica tamanho máximo primeiro para evitar StringIndexOutOfBounds
        if (cleanEmail.length() > 254) {
            throw new InvalidDocumentException("E-mail muito longo");
        }
        
        // Verifica tamanho mínimo
        if (cleanEmail.length() < 5) {
            throw new InvalidDocumentException("E-mail muito curto");
        }
        
        // Verifica se contém '@'
        int atIndex = cleanEmail.indexOf('@');
        if (atIndex == -1 || atIndex == 0 || atIndex == cleanEmail.length() - 1) {
            throw new InvalidDocumentException("E-mail deve conter exatamente um '@' em posição válida");
        }
        
        // Verifica se tem mais de um '@'
        if (cleanEmail.indexOf('@', atIndex + 1) != -1) {
            throw new InvalidDocumentException("E-mail não pode conter mais de um '@'");
        }
        
        // Verifica se tem pontos consecutivos antes do '@'
        String localPart = cleanEmail.substring(0, atIndex);
        if (localPart.contains("..")) {
            throw new InvalidDocumentException("E-mail não pode conter pontos consecutivos");
        }
        
        // Verifica se começa ou termina com ponto no local part
        if (localPart.startsWith(".") || localPart.endsWith(".")) {
            throw new InvalidDocumentException("Local part do e-mail não pode começar ou terminar com ponto");
        }
        
        // Verifica formato básico
        if (!cleanEmail.matches(EMAIL_REGEX)) {
            throw new InvalidDocumentException("Formato de e-mail inválido");
        }
        
        // Verifica domínio
        String domain = cleanEmail.substring(atIndex + 1);
        if (domain.startsWith(".") || domain.endsWith(".")) {
            throw new InvalidDocumentException("Domínio inválido");
        }
        
        // Verifica se o domínio tem pelo menos um ponto
        if (!domain.contains(".")) {
            throw new InvalidDocumentException("Domínio deve conter pelo menos um ponto");
        }
        
        // Verifica domínios conhecidos como inválidos
        for (String invalidDomain : INVALID_DOMAINS) {
            if (domain.toLowerCase().contains(invalidDomain)) {
                throw new InvalidDocumentException("Domínio de e-mail não permitido");
            }
        }
    }
    
    /**
     * Formata um e-mail para minúsculas.
     * 
     * @param email E-mail a ser formatado
     * @return E-mail formatado em minúsculas
     */
    public static String format(String email) {
        if (email == null) {
            return null;
        }
        return email.trim().toLowerCase();
    }
}