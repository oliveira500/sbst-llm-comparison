package br.ufjf.ead.validator;

import br.ufjf.ead.exception.InvalidDocumentException;

/**
 * Validador de CPF (Cadastro de Pessoa Física) brasileiro.
 * 
 * Implementa o algoritmo de validação de dígitos verificadores do CPF.
 * Um CPF válido possui 11 dígitos, sendo os dois últimos dígitos verificadores
 * calculados a partir dos 9 primeiros.
 * 
 * @author Fabio Oliveira
 */
 
public class CpfValidator {
    
    // CPFs conhecidos como inválidos (todos os dígitos iguais)
    private static final String[] KNOWN_INVALID_CPFS = {
        "00000000000", "11111111111", "22222222222", "33333333333",
            "44444444444", "55555555555", "66666666666", "77777777777", 
        "88888888888", "99999999999"
    };
    
    /**
     * Valida um CPF.
     * 
        * @param cpf CPF a ser validado (pode conter formatação)
     * @return true se o CPF é válido, false caso contrário
     */
    public static boolean isValid(String cpf) {
        try {
            validate(cpf);
            return true;
        } catch (InvalidDocumentException e) {
            return false;
        }
    }
    
    /**
     * Valida um CPF e lança exceção se inválido.
     * 
        * @param cpf CPF a ser validado (pode conter formatação)
     * @throws InvalidDocumentException se o CPF for inválido
     */
    public static void validate(String cpf) throws InvalidDocumentException {
        // Verifica se é nulo ou vazio
        if (cpf == null || cpf.trim().isEmpty()) { 
            throw new InvalidDocumentException("CPF não pode ser nulo ou vazio");
        }
        
        // Remove formatação
        String cleanCpf = DocumentFormatter.removeFormatting(cpf); 
        
        // Verifica se tem 11 dígitos
        if (cleanCpf.length() != 11) {
            throw new InvalidDocumentException("CPF deve conter 11 dígitos");
        }
        
        // Verifica se contém apenas números
        if (!cleanCpf.matches("\\d{11}")) { 
            throw new InvalidDocumentException("CPF deve conter apenas dígitos numéricos");
        }
        
        // Verifica CPFs conhecidos como inválidos
        for (String invalidCpf : KNOWN_INVALID_CPFS) {
            if (cleanCpf.equals(invalidCpf)) { 
                throw new InvalidDocumentException("CPF inválido: todos os dígitos são iguais");
            }
        }
        
        // Valida os dígitos verificadores
        if (!validateCheckDigits(cleanCpf)) {
            throw new InvalidDocumentException("CPF inválido: dígitos verificadores incorretos");
        }
    }
    
    /**
     * Valida os dígitos verificadores do CPF.
     * 
        * @param cpf CPF limpo (apenas números, 11 dígitos) 
     * @return true se os dígitos verificadores estão corretos
     */
    private static boolean validateCheckDigits(String cpf) {
        // Calcula o primeiro dígito verificador
        int firstDigit = calculateCheckDigit(cpf, 10);
        if (firstDigit != Character.getNumericValue(cpf.charAt(9))) { 
            return false;
        }
        
        // Calcula o segundo dígito verificador
        int secondDigit = calculateCheckDigit(cpf, 11);
        if (secondDigit != Character.getNumericValue(cpf.charAt(10))) { 
            return false;
        }
        
        return true;
    }
    
    /**
     * Calcula um dígito verificador do CPF.
     * 
        * @param cpf CPF limpo (apenas números) 
     * @param weight Peso inicial (10 para primeiro dígito, 11 para segundo)
     * @return Dígito verificador calculado
     */
    private static int calculateCheckDigit(String cpf, int weight) {
        int sum = 0;
        int currentWeight = weight;
        
        // Soma os produtos dos dígitos pelos pesos
        for (int i = 0; i < weight - 1; i++) {
            int digit = Character.getNumericValue(cpf.charAt(i)); 
            sum += digit * currentWeight;
            currentWeight--;
        }
        
        // Calcula o resto da divisão por 11
        int remainder = sum % 11;
        
        // Se o resto for menor que 2, o dígito é 0, caso contrário é 11 - resto
        return (remainder < 2) ? 0 : (11 - remainder);
    }
    
    /**
     * Gera um CPF válido aleatório (útil para testes).
     * 
     * @return CPF válido formatado
     */
    public static String generateValidCpf() {
        // Gera 9 dígitos aleatórios
        StringBuilder cpf = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            cpf.append((int) (Math.random() * 10)); 
        }
        
        // Calcula os dígitos verificadores
        String baseCpf = cpf.toString();
        int firstDigit = calculateCheckDigit(baseCpf + "00", 10);
        int secondDigit = calculateCheckDigit(baseCpf + firstDigit + "0", 11);
        
        cpf.append(firstDigit).append(secondDigit);
        
        return DocumentFormatter.formatCpf(cpf.toString());
    }
}
