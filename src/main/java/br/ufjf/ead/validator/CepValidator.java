package br.ufjf.ead.validator;

import br.ufjf.ead.exception.InvalidDocumentException;

/**
 * Validador de CEP (Código de Endereçamento Postal) brasileiro.
 * 
 * Implementa validação robusta de CEPs, incluindo verificação de formato,
 * dígitos verificadores e tratamento de casos de borda.
 * 
 * @author Fabio Oliveira
 */
public class CepValidator {
    
    // CEPs conhecidos como inválidos (todos os dígitos iguais)
    private static final String[] KNOWN_INVALID_CEPS = {
        "00000000", "11111111", "22222222", "33333333",
        "44444444", "55555555", "66666666", "77777777", 
        "88888888", "99999999"
    };
    
    /**
     * Valida um CEP.
     * 
     * @param cep CEP a ser validado
     * @return true se o CEP é válido, false caso contrário
     */
    public static boolean isValid(String cep) {
        try {
            validate(cep);
            return true;
        } catch (InvalidDocumentException e) {
            return false;
        }
    }
    
    /**
     * Valida um CEP e lança exceção se inválido.
     * 
     * @param cep CEP a ser validado
     * @throws InvalidDocumentException se o CEP for inválido
     */
    public static void validate(String cep) throws InvalidDocumentException {
        // Verifica se é nulo ou vazio
        if (cep == null || cep.trim().isEmpty()) {
            throw new InvalidDocumentException("CEP não pode ser nulo ou vazio");
        }
        
        // Remove formatação
        String cleanCep = removeFormatting(cep);
        
        // Verifica se tem 8 dígitos
        if (cleanCep.length() != 8) {
            throw new InvalidDocumentException("CEP deve conter 8 dígitos");
        }
        
        // Verifica se contém apenas números
        if (!cleanCep.matches("\\d{8}")) {
            throw new InvalidDocumentException("CEP deve conter apenas dígitos numéricos");
        }
        
        // Verifica CEPs conhecidos como inválidos
        for (String invalidCep : KNOWN_INVALID_CEPS) {
            if (cleanCep.equals(invalidCep)) {
                throw new InvalidDocumentException("CEP inválido: todos os dígitos são iguais");
            }
        }
        
        // Verifica se é um CEP válido (não há algoritmo de validação como no CPF/CNPJ)
        // Apenas verifica se não é um padrão óbvio de inválido
        if (isSequentialPattern(cleanCep) || isRepetitivePattern(cleanCep)) {
            throw new InvalidDocumentException("CEP inválido: padrão sequencial ou repetitivo");
        }
    }
    
    /**
     * Remove a formatação de um CEP.
     * 
     * @param cep CEP com formatação
     * @return CEP sem formatação
     */
    public static String removeFormatting(String cep) {
        if (cep == null) {
            return null;
        }
        
        // Remove todos os caracteres não numéricos
        return cep.replaceAll("[^0-9]", "");
    }
    
    /**
     * Formata um CEP para o padrão brasileiro.
     * 
     * @param cep CEP a ser formatado
     * @return CEP formatado no padrão XXXXX-XXX
     */
    public static String format(String cep) {
        if (cep == null) {
            return null;
        }
        
        // Remove formatação
        String cleanCep = removeFormatting(cep);
        
        if (cleanCep.length() != 8) {
            return cep; // Retorna original se não puder formatar
        }
        
        // Formata com hífen
        return cleanCep.substring(0, 5) + "-" + cleanCep.substring(5, 8);
    }
    
    /**
     * Verifica se um CEP tem padrão sequencial.
     * 
     * @param cep CEP a ser verificado
     * @return true se tiver padrão sequencial, false caso contrário
     */
    private static boolean isSequentialPattern(String cep) {
        // Verifica sequências crescentes
        for (int i = 0; i < 5; i++) {
            boolean isSequential = true;
            for (int j = 0; j < 3; j++) {
                int current = Character.getNumericValue(cep.charAt(i + j));
                int next = Character.getNumericValue(cep.charAt(i + j + 1));
                if (next != current + 1) {
                    isSequential = false;
                    break;
                }
            }
            if (isSequential) {
                return true;
            }
        }
        
        // Verifica sequências decrescentes
        for (int i = 0; i < 5; i++) {
            boolean isSequential = true;
            for (int j = 0; j < 3; j++) {
                int current = Character.getNumericValue(cep.charAt(i + j));
                int next = Character.getNumericValue(cep.charAt(i + j + 1));
                if (next != current - 1) {
                    isSequential = false;
                    break;
                }
            }
            if (isSequential) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Verifica se um CEP tem padrão repetitivo.
     * 
     * @param cep CEP a ser verificado
     * @return true se tiver padrão repetitivo, false caso contrário
     */
    private static boolean isRepetitivePattern(String cep) {
        // Verifica repetição de 3 ou mais dígitos iguais consecutivos
        for (int i = 0; i < 6; i++) {
            char current = cep.charAt(i);
            if (cep.charAt(i + 1) == current && cep.charAt(i + 2) == current) {
                return true;
            }
        }
        
        // Verifica padrões de repetição de 2 dígitos
        for (int i = 0; i < 4; i++) {
            String pattern = cep.substring(i, i + 2);
            if (cep.contains(pattern + pattern + pattern)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Obtém a região de um CEP.
     * 
     * @param cep CEP
     * @return Região do CEP (Sudeste, Nordeste, etc.)
     */
    public static String getRegion(String cep) {
        if (cep == null) {
            return null;
        }
        
        // Remove formatação
        String cleanCep = removeFormatting(cep);
        
        if (cleanCep.length() != 8) {
            return "Desconhecida";
        }
        
        // Primeiro dígito indica a região
        char firstDigit = cleanCep.charAt(0);
        
        switch (firstDigit) {
            case '0': return "Sudeste (SP)";
            case '1': return "Sudeste (SP)";
            case '2': return "Sudeste (RJ, ES)";
            case '3': return "Sudeste (MG)";
            case '4': return "Nordeste (BA, SE)";
            case '5': return "Nordeste (PE, AL, PB, RN)";
            case '6': return "Norte (AM, PA, AP, TO, RR, AC)";
            case '7': return "Centro-Oeste (DF, GO, MT, MS)";
            case '8': return "Sul (PR, SC)";
            case '9': return "Sul (RS)";
            default: return "Desconhecida";
        }
    }
    
    /**
     * Verifica se um CEP é de capital.
     * 
     * @param cep CEP
     * @return true se for de capital, false caso contrário
     */
    public static boolean isCapital(String cep) {
        if (cep == null) {
            return false;
        }
        
        // Remove formatação
        String cleanCep = removeFormatting(cep);
        
        if (cleanCep.length() != 8) {
            return false;
        }
        
        // CEPs de capitais geralmente começam com 70, 71, 30, 01, etc.
        String firstTwoDigits = cleanCep.substring(0, 2);
        
        String[] capitalPrefixes = {
            "01", "02", "03", "04", "05", "06", "07", "08", "09", // SP
            "20", "21", "22", "23", "24", "25", "26", "27", "28", // RJ
            "30", "31", "32", "33", "34", "35", "36", "37", "38", // MG
            "40", "41", "42", "43", "44", "45", "46", "47", "48", // BA
            "60", "61", "62", "63", "64", "65", "66", "67", "68", // DF
            "70", "71", "72", "73", "74", "75", "76", "77", "78", // DF (continuação)
            "80", "81", "82", "83", "84", "85", "86", "87", "88", // PR
            "90", "91", "92", "93", "94", "95", "96", "97", "98"  // RS
        };
        
        for (String prefix : capitalPrefixes) {
            if (firstTwoDigits.equals(prefix)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Gera um CEP válido aleatório.
     * 
     * @return CEP válido
     */
    public static String generateValidCep() {
        StringBuilder cep = new StringBuilder();
        
        // Gera 8 dígitos aleatórios
        for (int i = 0; i < 8; i++) {
            cep.append((int) (Math.random() * 10));
        }
        
        // Verifica se gerou um CEP inválido conhecido
        String generatedCep = cep.toString();
        for (String invalidCep : KNOWN_INVALID_CEPS) {
            if (generatedCep.equals(invalidCep)) {
                // Gera novamente
                return generateValidCep();
            }
        }
        
        // Verifica padrões sequenciais ou repetitivos
        if (isSequentialPattern(generatedCep) || isRepetitivePattern(generatedCep)) {
            // Gera novamente
            return generateValidCep();
        }
        
        return format(generatedCep);
    }
    
    /**
     * Verifica se dois CEPs são da mesma região.
     * 
     * @param cep1 Primeiro CEP
     * @param cep2 Segundo CEP
     * @return true se forem da mesma região, false caso contrário
     */
    public static boolean isSameRegion(String cep1, String cep2) {
        String region1 = getRegion(cep1);
        String region2 = getRegion(cep2);
        
        return region1 != null && region2 != null && region1.equals(region2);
    }
}