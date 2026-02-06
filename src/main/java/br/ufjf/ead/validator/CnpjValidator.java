package br.ufjf.ead.validator;

import br.ufjf.ead.exception.InvalidDocumentException;

/**
 * Validador de CNPJ (Cadastro Nacional de Pessoa Jurídica) brasileiro.
 * 
 * Implementa o algoritmo de validação de dígitos verificadores do CNPJ.
 * Um CNPJ válido possui 14 dígitos, sendo os dois últimos dígitos verificadores
 * calculados a partir dos 12 primeiros.
 * 
 * @author Fabio Oliveira
 */
public class CnpjValidator {
    
    // CNPJs conhecidos como inválidos (todos os dígitos iguais)
    private static final String[] KNOWN_INVALID_CNPJS = {
        "00000000000000", "11111111111111", "22222222222222", "33333333333333",
        "44444444444444", "55555555555555", "66666666666666", "77777777777777",
        "88888888888888", "99999999999999"
    };
    
    /**
     * Valida um CNPJ.
     * 
     * @param cnpj String contendo o CNPJ (pode conter formatação)
     * @return true se o CNPJ é válido, false caso contrário
     */
    public static boolean isValid(String cnpj) {
        try {
            validate(cnpj);
            return true;
        } catch (InvalidDocumentException e) {
            return false;
        }
    }
    
    /**
     * Valida um CNPJ e lança exceção se inválido.
     * 
     * @param cnpj String contendo o CNPJ (pode conter formatação)
     * @throws InvalidDocumentException se o CNPJ for inválido
     */
    public static void validate(String cnpj) throws InvalidDocumentException {
        // Verifica se é nulo ou vazio
        if (cnpj == null || cnpj.trim().isEmpty()) {
            throw new InvalidDocumentException("CNPJ não pode ser nulo ou vazio");
        }
        
        // Remove formatação
        String cleanCnpj = DocumentFormatter.removeFormatting(cnpj);
        
        // Verifica se tem 14 dígitos
        if (cleanCnpj.length() != 14) {
            throw new InvalidDocumentException("CNPJ deve conter 14 dígitos");
        }
        
        // Verifica se contém apenas números
        if (!cleanCnpj.matches("\\d{14}")) {
            throw new InvalidDocumentException("CNPJ deve conter apenas dígitos numéricos");
        }
        
        // Verifica CNPJs conhecidos como inválidos
        for (String invalidCnpj : KNOWN_INVALID_CNPJS) {
            if (cleanCnpj.equals(invalidCnpj)) {
                throw new InvalidDocumentException("CNPJ inválido: todos os dígitos são iguais");
            }
        }
        
        // Valida os dígitos verificadores
        if (!validateCheckDigits(cleanCnpj)) {
            throw new InvalidDocumentException("CNPJ inválido: dígitos verificadores incorretos");
        }
    }
    
    /**
     * Valida os dígitos verificadores do CNPJ.
     * 
     * @param cnpj CNPJ limpo (apenas números, 14 dígitos)
     * @return true se os dígitos verificadores estão corretos
     */
    private static boolean validateCheckDigits(String cnpj) {
        // Calcula o primeiro dígito verificador
        int firstDigit = calculateFirstCheckDigit(cnpj);
        if (firstDigit != Character.getNumericValue(cnpj.charAt(12))) {
            return false;
        }
        
        // Calcula o segundo dígito verificador
        int secondDigit = calculateSecondCheckDigit(cnpj);
        if (secondDigit != Character.getNumericValue(cnpj.charAt(13))) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Calcula o primeiro dígito verificador do CNPJ.
     * 
     * @param cnpj CNPJ limpo (apenas números)
     * @return Primeiro dígito verificador calculado
     */
    private static int calculateFirstCheckDigit(String cnpj) {
        // Pesos para o primeiro dígito: 5,4,3,2,9,8,7,6,5,4,3,2
        int[] weights = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int sum = 0;
        
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(cnpj.charAt(i));
            sum += digit * weights[i];
        }
        
        int remainder = sum % 11;
        return (remainder < 2) ? 0 : (11 - remainder);
    }
    
    /**
     * Calcula o segundo dígito verificador do CNPJ.
     * 
     * @param cnpj CNPJ limpo (apenas números)
     * @return Segundo dígito verificador calculado
     */
    private static int calculateSecondCheckDigit(String cnpj) {
        // Pesos para o segundo dígito: 6,5,4,3,2,9,8,7,6,5,4,3,2
        int[] weights = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int sum = 0;
        
        for (int i = 0; i < 13; i++) {
            int digit = Character.getNumericValue(cnpj.charAt(i));
            sum += digit * weights[i];
        }
        
        int remainder = sum % 11;
        return (remainder < 2) ? 0 : (11 - remainder);
    }
    
    /**
     * Gera um CNPJ válido aleatório (útil para testes).
     * 
     * @return CNPJ válido formatado
     */
    public static String generateValidCnpj() {
        // Gera 12 dígitos aleatórios
        StringBuilder cnpj = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            cnpj.append((int) (Math.random() * 10));
        }
        
        // Calcula os dígitos verificadores
        String baseCnpj = cnpj.toString();
        int firstDigit = calculateFirstCheckDigit(baseCnpj + "00");
        int secondDigit = calculateSecondCheckDigit(baseCnpj + firstDigit + "0");
        
        cnpj.append(firstDigit).append(secondDigit);
        
        return DocumentFormatter.formatCnpj(cnpj.toString());
    }
}
