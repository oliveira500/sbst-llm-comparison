package br.ufjf.ead.validator;

import br.ufjf.ead.exception.InvalidDocumentException;

/**
 * Validador de números de telefone com suporte a formatos brasileiros.
 * 
 * Implementa validação robusta de números de telefone, incluindo DDDs,
 * formatos com e sem código do país e tratamento de casos de borda.
 * 
 * @author Fabio Oliveira
 */
public class PhoneNumberValidator {
    
    // DDDs válidos do Brasil
    private static final String[] VALID_DDDS = {
        "11", "12", "13", "14", "15", "16", "17", "18", "19", // SP
        "21", "22", "24", // RJ
        "31", "32", "33", "34", "35", "37", "38", // MG
        "41", "42", "43", "44", "45", "46", // PR
        "51", "53", "54", "55", // RS
        "61", "62", "63", "64", "65", "66", "67", "68", "69", // DF, GO, TO, MT, MS
        "71", "73", "74", "75", "77", // BA
        "81", "82", "83", "84", "85", "86", "87", "88", "89", // PE, AL, PB, RN, CE, PI, MA
        "91", "92", "93", "94", "95", "96", "97", "98", "99"  // PA, AM, AP, RR, AC, RO, MA
    };
    
    // Formatos de telefone suportados
    private static final String[] SUPPORTED_FORMATS = {
        "(XX) XXXXX-XXXX", "(XX) XXXX-XXXX", "XX XXXXX-XXXX", "XX XXXX-XXXX",
        "+55 XX XXXXX-XXXX", "+55 XX XXXX-XXXX", "55 XX XXXXX-XXXX", "55 XX XXXX-XXXX"
    };
    
    /**
     * Valida um número de telefone.
     * 
     * @param phoneNumber Número de telefone a ser validado
     * @return true se o telefone é válido, false caso contrário
     */
    public static boolean isValid(String phoneNumber) {
        try {
            validate(phoneNumber);
            return true;
        } catch (InvalidDocumentException e) {
            return false;
        }
    }
    
    /**
     * Valida um número de telefone e lança exceção se inválido.
     * 
     * @param phoneNumber Número de telefone a ser validado
     * @throws InvalidDocumentException se o telefone for inválido
     */
    public static void validate(String phoneNumber) throws InvalidDocumentException {
        // Verifica se é nulo ou vazio
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new InvalidDocumentException("Número de telefone não pode ser nulo ou vazio");
        }
        
        // Remove espaços e caracteres não numéricos para validação
        String cleanNumber = phoneNumber.replaceAll("[^0-9]", "");
        
        // Verifica tamanho mínimo (8 dígitos sem DDD)
        if (cleanNumber.length() < 8) {
            throw new InvalidDocumentException("Número de telefone muito curto");
        }
        
        // Verifica tamanho máximo (13 dígitos com código do país)
        if (cleanNumber.length() > 13) {
            throw new InvalidDocumentException("Número de telefone muito longo");
        }
        
        // Verifica DDD se houver
        if (cleanNumber.length() >= 10) {
            String ddd = cleanNumber.substring(0, 2);
            if (!isValidDDD(ddd)) {
                throw new InvalidDocumentException("DDD inválido: " + ddd);
            }
        }
        
        // Verifica se o número tem formato válido
        if (!isValidPhoneNumberFormat(phoneNumber)) {
            throw new InvalidDocumentException("Formato de telefone inválido");
        }
    }
    
    /**
     * Verifica se um DDD é válido.
     * 
     * @param ddd DDD a ser verificado
     * @return true se o DDD é válido, false caso contrário
     */
    private static boolean isValidDDD(String ddd) {
        for (String validDDD : VALID_DDDS) {
            if (validDDD.equals(ddd)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Verifica se o formato do número de telefone é válido.
     * 
     * @param phoneNumber Número de telefone a ser verificado
     * @return true se o formato é válido, false caso contrário
     */
    private static boolean isValidPhoneNumberFormat(String phoneNumber) {
        // Remove espaços em branco
        String cleanNumber = phoneNumber.trim();
        
        // Formatos válidos
        String[] validPatterns = {
            "^\\(\\d{2}\\)\\s?\\d{4,5}-\\d{4}$",           // (XX) XXXX-XXXX ou (XX) XXXXX-XXXX
            "^\\d{2}\\s?\\d{4,5}-\\d{4}$",                  // XX XXXX-XXXX ou XX XXXXX-XXXX
            "^\\+55\\s?\\d{2}\\s?\\d{4,5}-\\d{4}$",         // +55 XX XXXX-XXXX ou +55 XX XXXXX-XXXX
            "^55\\s?\\d{2}\\s?\\d{4,5}-\\d{4}$"             // 55 XX XXXX-XXXX ou 55 XX XXXXX-XXXX
        };
        
        for (String pattern : validPatterns) {
            if (cleanNumber.matches(pattern)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Formata um número de telefone para o padrão brasileiro.
     * 
     * @param phoneNumber Número de telefone a ser formatado
     * @return Número de telefone formatado
     */
    public static String format(String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }
        
        // Remove caracteres não numéricos
        String cleanNumber = phoneNumber.replaceAll("[^0-9]", "");
        
        if (cleanNumber.length() < 8) {
            return phoneNumber; // Retorna original se não puder formatar
        }
        
        // Remove código do país se houver
        if (cleanNumber.startsWith("55") && cleanNumber.length() > 10) {
            cleanNumber = cleanNumber.substring(2);
        }
        
        // Formata com DDD
        if (cleanNumber.length() >= 10) {
            String ddd = cleanNumber.substring(0, 2);
            String prefix = cleanNumber.substring(2, 6);
            String suffix = cleanNumber.substring(6, 10);
            
            return "(" + ddd + ") " + prefix + "-" + suffix;
        } 
        // Formata sem DDD
        else {
            String prefix = cleanNumber.substring(0, 4);
            String suffix = cleanNumber.substring(4, 8);
            
            return prefix + "-" + suffix;
        }
    }
    
    /**
     * Formata um número de telefone para uso internacional.
     * 
     * @param phoneNumber Número de telefone a ser formatado
     * @return Número de telefone formatado para uso internacional
     */
    public static String formatInternational(String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }
        
        // Remove caracteres não numéricos
        String cleanNumber = phoneNumber.replaceAll("[^0-9]", "");
        
        if (cleanNumber.length() < 8) {
            return phoneNumber; // Retorna original se não puder formatar
        }
        
        // Adiciona código do país se não tiver
        if (!cleanNumber.startsWith("55")) {
            cleanNumber = "55" + cleanNumber;
        }
        
        // Formata com código do país
        String countryCode = cleanNumber.substring(0, 2);
        String ddd = cleanNumber.substring(2, 4);
        String prefix = cleanNumber.substring(4, 8);
        String suffix = cleanNumber.substring(8, 12);
        
        return "+" + countryCode + " " + ddd + " " + prefix + "-" + suffix;
    }
    
    /**
     * Obtém o DDD de um número de telefone.
     * 
     * @param phoneNumber Número de telefone
     * @return DDD do telefone, ou null se não houver
     */
    public static String getDDD(String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }
        
        // Remove caracteres não numéricos
        String cleanNumber = phoneNumber.replaceAll("[^0-9]", "");
        
        // Remove código do país se houver
        if (cleanNumber.startsWith("55") && cleanNumber.length() > 10) {
            cleanNumber = cleanNumber.substring(2);
        }
        
        // Retorna DDD se houver
        if (cleanNumber.length() >= 10) {
            return cleanNumber.substring(0, 2);
        }
        
        return null;
    }
    
    /**
     * Verifica se um número de telefone é móvel.
     * 
     * @param phoneNumber Número de telefone
     * @return true se for móvel, false se for fixo
     */
    public static boolean isMobile(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }
        
        // Remove caracteres não numéricos
        String cleanNumber = phoneNumber.replaceAll("[^0-9]", "");
        
        // Remove código do país se houver
        if (cleanNumber.startsWith("55") && cleanNumber.length() > 10) {
            cleanNumber = cleanNumber.substring(2);
        }
        
        // Verifica se tem 9 dígitos no número (indicativo de celular)
        if (cleanNumber.length() >= 11) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Gera um número de telefone válido aleatório.
     * 
     * @param withDDD Incluir DDD
     * @param isMobile Número móvel
     * @return Número de telefone válido
     */
    public static String generateRandomPhoneNumber(boolean withDDD, boolean isMobile) {
        StringBuilder phoneNumber = new StringBuilder();
        
        // Adiciona DDD
        if (withDDD) {
            String ddd = VALID_DDDS[(int) (Math.random() * VALID_DDDS.length)];
            phoneNumber.append(ddd);
        }
        
        // Adiciona número
        if (isMobile) {
            // Números móveis começam com 9
            phoneNumber.append("9");
            for (int i = 0; i < 8; i++) {
                phoneNumber.append((int) (Math.random() * 10));
            }
        } else {
            // Números fixos têm 8 dígitos
            for (int i = 0; i < 8; i++) {
                phoneNumber.append((int) (Math.random() * 10));
            }
        }
        
        return phoneNumber.toString();
    }
}