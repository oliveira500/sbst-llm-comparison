package br.ufjf.ead.validator;

import br.ufjf.ead.exception.InvalidDocumentException;

/**
 * Validador de senhas com regras múltiplas para força de senha.
 * 
 * Implementa validação robusta de senhas com requisitos de complexidade,
 * força da senha e tratamento de casos de borda.
 * 
 * @author Fabio Oliveira
 */
public class PasswordValidator {
    
    // Requisitos mínimos para a senha
    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 128;
    
    // Critérios de força da senha
    private static final int MIN_UPPERCASE = 1;
    private static final int MIN_LOWERCASE = 1;
    private static final int MIN_DIGITS = 1;
    private static final int MIN_SPECIAL_CHARS = 1;
    
    // Caracteres especiais permitidos
    private static final String SPECIAL_CHARS = "!@#$%^&*()_+-=[]{}|;:,.<>?";
    
    // Senhas comuns conhecidas (não permitidas)
    private static final String[] COMMON_PASSWORDS = {
        "123456", "password", "123456789", "12345678", "12345", "1234567",
        "password123", "admin", "qwerty", "abc123", "Password1", "welcome"
    };
    
    /**
     * Valida uma senha.
     * 
     * @param password Senha a ser validada
     * @return true se a senha é válida, false caso contrário
     */
    public static boolean isValid(String password) {
        try {
            validate(password);
            return true;
        } catch (InvalidDocumentException e) {
            return false;
        }
    }
    
    /**
     * Valida uma senha e lança exceção se inválida.
     * 
     * @param password Senha a ser validada
     * @throws InvalidDocumentException se a senha for inválida
     */
    public static void validate(String password) throws InvalidDocumentException {
        // Verifica se é nulo ou vazio
        if (password == null || password.trim().isEmpty()) {
            throw new InvalidDocumentException("Senha não pode ser nula ou vazia");
        }
        
        // Remove espaços em branco no início e fim
        String cleanPassword = password.trim();
        
        // Verifica tamanho mínimo
        if (cleanPassword.length() < MIN_LENGTH) {
            throw new InvalidDocumentException("Senha deve ter pelo menos " + MIN_LENGTH + " caracteres");
        }
        
        // Verifica tamanho máximo
        if (cleanPassword.length() > MAX_LENGTH) {
            throw new InvalidDocumentException("Senha não pode ter mais de " + MAX_LENGTH + " caracteres");
        }
        
        // Verifica se contém apenas caracteres ASCII imprimíveis
        if (!cleanPassword.matches("[\\x20-\\x7E]+")) {
            throw new InvalidDocumentException("Senha deve conter apenas caracteres ASCII imprimíveis");
        }
        
        // Verifica se é uma senha comum conhecida
        for (String commonPassword : COMMON_PASSWORDS) {
            if (cleanPassword.equalsIgnoreCase(commonPassword)) {
                throw new InvalidDocumentException("Senha muito comum, escolha outra");
            }
        }
        
        // Verifica requisitos de complexidade
        validateComplexity(cleanPassword);
    }
    
    /**
     * Valida a complexidade da senha.
     * 
     * @param password Senha a ser validada
     * @throws InvalidDocumentException se a complexidade for insuficiente
     */
    private static void validateComplexity(String password) throws InvalidDocumentException {
        int uppercaseCount = 0;
        int lowercaseCount = 0;
        int digitCount = 0;
        int specialCharCount = 0;
        
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                uppercaseCount++;
            } else if (Character.isLowerCase(c)) {
                lowercaseCount++;
            } else if (Character.isDigit(c)) {
                digitCount++;
            } else if (SPECIAL_CHARS.indexOf(c) != -1) {
                specialCharCount++;
            }
        }
        
        // Verifica requisitos mínimos
        if (uppercaseCount < MIN_UPPERCASE) {
            throw new InvalidDocumentException("Senha deve conter pelo menos " + MIN_UPPERCASE + " letra maiúscula");
        }
        
        if (lowercaseCount < MIN_LOWERCASE) {
            throw new InvalidDocumentException("Senha deve conter pelo menos " + MIN_LOWERCASE + " letra minúscula");
        }
        
        if (digitCount < MIN_DIGITS) {
            throw new InvalidDocumentException("Senha deve conter pelo menos " + MIN_DIGITS + " número");
        }
        
        if (specialCharCount < MIN_SPECIAL_CHARS) {
            throw new InvalidDocumentException("Senha deve conter pelo menos " + MIN_SPECIAL_CHARS + " caractere especial");
        }
    }
    
    /**
     * Avalia a força da senha.
     * 
     * @param password Senha a ser avaliada
     * @return Força da senha (1-5, onde 5 é muito forte)
     */
    public static int evaluateStrength(String password) {
        if (password == null || password.trim().isEmpty()) {
            return 0;
        }
        
        String cleanPassword = password.trim();
        int score = 0;
        
        // Base score por tamanho
        if (cleanPassword.length() >= 8) score += 1;
        if (cleanPassword.length() >= 12) score += 1;
        if (cleanPassword.length() >= 16) score += 1;
        
        // Score por tipos de caracteres
        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;
        
        for (char c : cleanPassword.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (SPECIAL_CHARS.indexOf(c) != -1) hasSpecial = true;
        }
        
        if (hasUpper) score += 1;
        if (hasLower) score += 1;
        if (hasDigit) score += 1;
        if (hasSpecial) score += 1;
        
        // Penalidade por repetições
        if (hasRepetitions(cleanPassword)) score -= 1;
        
        // Penalidade por sequências
        if (hasSequences(cleanPassword)) score -= 1;
        
        return Math.max(0, Math.min(5, score));
    }
    
    /**
     * Verifica se a senha contém repetições de caracteres.
     * 
     * @param password Senha a ser verificada
     * @return true se houver repetições, false caso contrário
     */
    private static boolean hasRepetitions(String password) {
        for (int i = 0; i < password.length() - 2; i++) {
            if (password.charAt(i) == password.charAt(i + 1) && 
                password.charAt(i) == password.charAt(i + 2)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Verifica se a senha contém sequências de caracteres.
     * 
     * @param password Senha a ser verificada
     * @return true se houver sequências, false caso contrário
     */
    private static boolean hasSequences(String password) {
        String lowerPassword = password.toLowerCase();
        
        // Sequências comuns
        String[] sequences = {
            "abcdef", "bcdefg", "cdefgh", "defghi", "efghij", "fghijk",
            "123456", "234567", "345678", "456789", "567890",
            "qwerty", "asdfgh", "zxcvbn"
        };
        
        for (String seq : sequences) {
            if (lowerPassword.contains(seq)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Gera uma senha segura aleatória.
     * 
     * @param length Tamanho da senha (mínimo 8, máximo 128)
     * @return Senha segura gerada aleatoriamente
     */
    public static String generateSecurePassword(int length) {
        if (length < MIN_LENGTH) length = MIN_LENGTH;
        if (length > MAX_LENGTH) length = MAX_LENGTH;
        
        StringBuilder password = new StringBuilder();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789" + SPECIAL_CHARS;
        
        // Garante que a senha tenha todos os tipos de caracteres
        password.append(getRandomChar("ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        password.append(getRandomChar("abcdefghijklmnopqrstuvwxyz"));
        password.append(getRandomChar("0123456789"));
        password.append(getRandomChar(SPECIAL_CHARS));
        
        // Completa o restante da senha
        for (int i = 4; i < length; i++) {
            password.append(getRandomChar(chars));
        }
        
        // Embaralha a senha
        return shuffleString(password.toString());
    }
    
    /**
     * Obtém um caractere aleatório de uma string.
     * 
     * @param chars String contendo os caracteres possíveis
     * @return Caractere aleatório
     */
    private static char getRandomChar(String chars) {
        return chars.charAt((int) (Math.random() * chars.length()));
    }
    
    /**
     * Embaralha uma string.
     * 
     * @param str String a ser embaralhada
     * @return String embaralhada
     */
    private static String shuffleString(String str) {
        char[] chars = str.toCharArray();
        for (int i = chars.length - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        return new String(chars);
    }
}