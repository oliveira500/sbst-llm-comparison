package br.ufjf.ead.validator;

import br.ufjf.ead.exception.InvalidDocumentException;

/**
 * Validador de cartões de crédito utilizando o algoritmo de Luhn.
 * 
 * Implementa validação robusta de números de cartão de crédito,
 * identificação de bandeiras e tratamento de casos de borda.
 * 
 * @author Fabio Oliveira
 */
public class CreditCardValidator {
    
    // Bandeiras de cartão conhecidas e seus padrões
    private static final String[] VISA_PREFIXES = {"4"};
    private static final int[] VISA_LENGTHS = {13, 16, 19};
    
    private static final String[] MASTERCARD_PREFIXES = {
        "51", "52", "53", "54", "55",
        "2221", "2222", "2223", "2224", "2225", "2226", "2227", "2228", "2229",
        "223", "224", "225", "226", "227", "228", "229",
        "23", "24", "25", "26", "27"
    };
    private static final int[] MASTERCARD_LENGTHS = {16};
    
    private static final String[] AMEX_PREFIXES = {"34", "37"};
    private static final int[] AMEX_LENGTHS = {15};
    
    private static final String[] DINERS_PREFIXES = {"300", "301", "302", "303", "304", "305", "309", "36", "38", "39"};
    private static final int[] DINERS_LENGTHS = {14, 16, 19};
    
    private static final String[] DISCOVER_PREFIXES = {"6011", "622126", "622127", "622128", "622129", 
                                                       "62218", "62219", "6222", "6223", "6224", "6225", 
                                                       "6226", "6227", "6228", "62290", "62291", "622920", 
                                                       "622921", "622922", "622923", "622924", "622925", 
                                                       "644", "645", "646", "647", "648", "649", "65"};
    private static final int[] DISCOVER_LENGTHS = {16, 19};
    
    private static final String[] JCB_PREFIXES = {"3528", "3529", "353", "354", "355", "356", "357", "358"};
    private static final int[] JCB_LENGTHS = {15, 16};
    
    /**
     * Valida um número de cartão de crédito.
     * 
     * @param cardNumber Número do cartão a ser validado
     * @return true se o cartão é válido, false caso contrário
     */
    public static boolean isValid(String cardNumber) {
        try {
            validate(cardNumber);
            return true;
        } catch (InvalidDocumentException e) {
            return false;
        }
    }
    
    /**
     * Valida um número de cartão de crédito e lança exceção se inválido.
     * 
     * @param cardNumber Número do cartão a ser validado
     * @throws InvalidDocumentException se o cartão for inválido
     */
    public static void validate(String cardNumber) throws InvalidDocumentException {
        // Verifica se é nulo ou vazio
        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            throw new InvalidDocumentException("Número do cartão não pode ser nulo ou vazio");
        }
        
        // Remove espaços e caracteres não numéricos
        String cleanNumber = cardNumber.replaceAll("[^0-9]", "");
        
        // Verifica se contém apenas dígitos
        if (!cleanNumber.matches("\\d+")) {
            throw new InvalidDocumentException("Número do cartão deve conter apenas dígitos");
        }
        
        // Verifica tamanho mínimo
        if (cleanNumber.length() < 12) {
            throw new InvalidDocumentException("Número do cartão muito curto");
        }
        
        // Verifica tamanho máximo
        if (cleanNumber.length() > 19) {
            throw new InvalidDocumentException("Número do cartão muito longo");
        }
        
        // Verifica algoritmo de Luhn
        if (!validateLuhn(cleanNumber)) {
            throw new InvalidDocumentException("Número do cartão inválido (algoritmo de Luhn)");
        }
        
        // Verifica bandeira
        String brand = getCardBrand(cleanNumber);
        if (brand == null) {
            throw new InvalidDocumentException("Bandeira do cartão não reconhecida");
        }
    }
    
    /**
     * Valida o algoritmo de Luhn para um número de cartão.
     * 
     * @param cardNumber Número do cartão (apenas dígitos)
     * @return true se o algoritmo de Luhn é válido, false caso contrário
     */
    private static boolean validateLuhn(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        
        // Processa da direita para a esquerda
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            
            sum += n;
            alternate = !alternate;
        }
        
        return (sum % 10 == 0);
    }
    
    /**
     * Identifica a bandeira do cartão.
     * 
     * @param cardNumber Número do cartão (apenas dígitos)
     * @return Nome da bandeira do cartão, ou null se não reconhecida
     */
    public static String getCardBrand(String cardNumber) {
        // Remove caracteres não numéricos
        String cleanNumber = cardNumber.replaceAll("[^0-9]", "");
        
        // Verifica cada bandeira
        if (matchesBrand(cleanNumber, VISA_PREFIXES, VISA_LENGTHS)) {
            return "Visa";
        } else if (matchesBrand(cleanNumber, MASTERCARD_PREFIXES, MASTERCARD_LENGTHS)) {
            return "MasterCard";
        } else if (matchesBrand(cleanNumber, AMEX_PREFIXES, AMEX_LENGTHS)) {
            return "American Express";
        } else if (matchesBrand(cleanNumber, DINERS_PREFIXES, DINERS_LENGTHS)) {
            return "Diners Club";
        } else if (matchesBrand(cleanNumber, DISCOVER_PREFIXES, DISCOVER_LENGTHS)) {
            return "Discover";
        } else if (matchesBrand(cleanNumber, JCB_PREFIXES, JCB_LENGTHS)) {
            return "JCB";
        }
        
        return null;
    }
    
    /**
     * Verifica se o número do cartão corresponde a uma bandeira específica.
     * 
     * @param cardNumber Número do cartão
     * @param prefixes Prefixos válidos para a bandeira
     * @param lengths Comprimentos válidos para a bandeira
     * @return true se corresponde, false caso contrário
     */
    private static boolean matchesBrand(String cardNumber, String[] prefixes, int[] lengths) {
        // Verifica comprimento
        boolean validLength = false;
        for (int length : lengths) {
            if (cardNumber.length() == length) {
                validLength = true;
                break;
            }
        }
        
        if (!validLength) {
            return false;
        }
        
        // Verifica prefixos
        for (String prefix : prefixes) {
            if (cardNumber.startsWith(prefix)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Formata o número do cartão para exibição.
     * 
     * @param cardNumber Número do cartão
     * @return Número do cartão formatado (ex: 1234 5678 9012 3456)
     */
    public static String format(String cardNumber) {
        if (cardNumber == null) {
            return null;
        }
        
        // Remove caracteres não numéricos
        String cleanNumber = cardNumber.replaceAll("[^0-9]", "");
        
        // Formata em grupos de 4 dígitos
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < cleanNumber.length(); i++) {
            formatted.append(cleanNumber.charAt(i));
            if ((i + 1) % 4 == 0 && i != cleanNumber.length() - 1) {
                formatted.append(" ");
            }
        }
        
        return formatted.toString();
    }
    
    /**
     * Mascara o número do cartão para exibição segura.
     * 
     * @param cardNumber Número do cartão
     * @return Número do cartão mascarado (ex: 1234 **** **** 3456)
     */
    public static String mask(String cardNumber) {
        if (cardNumber == null) {
            return null;
        }
        
        // Remove caracteres não numéricos
        String cleanNumber = cardNumber.replaceAll("[^0-9]", "");
        
        if (cleanNumber.length() < 8) {
            return "**** **** **** ****";
        }
        
        // Mantém os 6 primeiros e 4 últimos dígitos
        String firstSix = cleanNumber.substring(0, 6);
        String lastFour = cleanNumber.substring(cleanNumber.length() - 4);
        
        // Gera os asteriscos para o meio
        int maskLength = cleanNumber.length() - 10;
        StringBuilder mask = new StringBuilder();
        for (int i = 0; i < maskLength; i++) {
            mask.append("*");
        }
        
        // Formata com espaços
        return firstSix + " " + mask.toString() + " " + lastFour;
    }
    
    /**
     * Gera um número de cartão de crédito válido para testes.
     * 
     * @param brand Bandeira do cartão (Visa, MasterCard, etc.)
     * @return Número de cartão válido para a bandeira especificada
     */
    public static String generateTestCard(String brand) {
        String prefix = "";
        int length = 16;
        
        switch (brand.toLowerCase()) {
            case "visa":
                prefix = "4";
                length = 16;
                break;
            case "mastercard":
                prefix = "55";
                length = 16;
                break;
            case "amex":
                prefix = "34";
                length = 15;
                break;
            case "discover":
                prefix = "6011";
                length = 16;
                break;
            default:
                prefix = "4";
                length = 16;
        }
        
        // Gera número aleatório
        StringBuilder cardNumber = new StringBuilder(prefix);
        while (cardNumber.length() < length - 1) {
            cardNumber.append((int) (Math.random() * 10));
        }
        
        // Calcula dígito verificador
        String checkDigit = calculateLuhnCheckDigit(cardNumber.toString());
        cardNumber.append(checkDigit);
        
        return cardNumber.toString();
    }
    
    /**
     * Calcula o dígito verificador usando o algoritmo de Luhn.
     * 
     * @param partialNumber Número parcial do cartão
     * @return Dígito verificador
     */
    private static String calculateLuhnCheckDigit(String partialNumber) {
        int sum = 0;
        boolean alternate = false;
        
        // Processa da direita para a esquerda
        for (int i = partialNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(partialNumber.substring(i, i + 1));
            
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            
            sum += n;
            alternate = !alternate;
        }
        
        int checkDigit = (10 - (sum % 10)) % 10;
        return String.valueOf(checkDigit);
    }
}