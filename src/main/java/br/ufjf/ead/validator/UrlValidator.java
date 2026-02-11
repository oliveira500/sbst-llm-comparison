package br.ufjf.ead.validator;

import br.ufjf.ead.exception.InvalidDocumentException;

/**
 * Validador de URLs com suporte a protocolos, domínios e paths.
 * 
 * Implementa validação robusta de URLs, incluindo verificação de protocolos,
 * domínios válidos, paths e tratamento de casos de borda.
 * 
 * @author Fabio Oliveira
 */
public class UrlValidator {
    
    // Protocolos válidos
    private static final String[] VALID_PROTOCOLS = {
        "http", "https", "ftp", "ftps"
    };
    
    // Domínios de alto nível válidos
    private static final String[] VALID_TLDS = {
        "com", "org", "net", "edu", "gov", "mil", "int",
        "br", "pt", "es", "fr", "de", "it", "uk", "us"
    };
    
    // Caracteres válidos em domínios
    private static final String DOMAIN_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-";
    
    // Caracteres válidos em paths
    private static final String PATH_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.~:/?#[]@!$&'()*+,;=%";
    
    /**
     * Valida uma URL.
     * 
     * @param url URL a ser validada
     * @return true se a URL é válida, false caso contrário
     */
    public static boolean isValid(String url) {
        try {
            validate(url);
            return true;
        } catch (InvalidDocumentException e) {
            return false;
        }
    }
    
    /**
     * Valida uma URL e lança exceção se inválida.
     * 
     * @param url URL a ser validada
     * @throws InvalidDocumentException se a URL for inválida
     */
    public static void validate(String url) throws InvalidDocumentException {
        // Verifica se é nulo ou vazio
        if (url == null || url.trim().isEmpty()) {
            throw new InvalidDocumentException("URL não pode ser nula ou vazia");
        }
        
        // Remove espaços em branco
        String cleanUrl = url.trim();
        
        // Verifica se contém protocolo
        if (!cleanUrl.contains("://")) {
            throw new InvalidDocumentException("URL deve conter protocolo (ex: http://)");
        }
        
        // Divide a URL em partes
        String[] parts = cleanUrl.split("://");
        if (parts.length != 2) {
            throw new InvalidDocumentException("Formato de URL inválido");
        }
        
        String protocol = parts[0].toLowerCase();
        String remaining = parts[1];
        
        // Verifica protocolo
        if (!isValidProtocol(protocol)) {
            throw new InvalidDocumentException("Protocolo inválido: " + protocol);
        }
        
        // Verifica domínio
        if (!remaining.contains("/")) {
            // URL sem path
            if (!isValidDomain(remaining)) {
                throw new InvalidDocumentException("Domínio inválido");
            }
        } else {
            // URL com path
            String[] domainPath = remaining.split("/", 2);
            if (domainPath.length < 2) {
                throw new InvalidDocumentException("Formato de URL com path inválido");
            }
            
            String domain = domainPath[0];
            String path = "/" + domainPath[1];
            
            if (!isValidDomain(domain)) {
                throw new InvalidDocumentException("Domínio inválido");
            }
            
            if (!isValidPath(path)) {
                throw new InvalidDocumentException("Path inválido");
            }
        }
    }
    
    /**
     * Verifica se um protocolo é válido.
     * 
     * @param protocol Protocolo a ser verificado
     * @return true se o protocolo é válido, false caso contrário
     */
    private static boolean isValidProtocol(String protocol) {
        for (String validProtocol : VALID_PROTOCOLS) {
            if (validProtocol.equals(protocol)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Verifica se um domínio é válido.
     * 
     * @param domain Domínio a ser verificado
     * @return true se o domínio é válido, false caso contrário
     */
    private static boolean isValidDomain(String domain) {
        // Verifica se contém ponto
        if (!domain.contains(".")) {
            return false;
        }
        
        // Divide em partes
        String[] parts = domain.split("\\.");
        
        // Verifica se tem pelo menos domínio e TLD
        if (parts.length < 2) {
            return false;
        }
        
        // Verifica TLD
        String tld = parts[parts.length - 1].toLowerCase();
        boolean validTld = false;
        for (String validTldStr : VALID_TLDS) {
            if (validTldStr.equals(tld)) {
                validTld = true;
                break;
            }
        }
        
        if (!validTld) {
            return false;
        }
        
        // Verifica cada parte do domínio
        for (String part : parts) {
            if (!isValidDomainPart(part)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Verifica se uma parte do domínio é válida.
     * 
     * @param part Parte do domínio
     * @return true se a parte é válida, false caso contrário
     */
    private static boolean isValidDomainPart(String part) {
        // Não pode estar vazio
        if (part.isEmpty()) {
            return false;
        }
        
        // Não pode começar ou terminar com hífen
        if (part.startsWith("-") || part.endsWith("-")) {
            return false;
        }
        
        // Verifica caracteres
        for (char c : part.toCharArray()) {
            if (DOMAIN_CHARS.indexOf(c) == -1) {
                return false;
            }
        }
        
        // Verifica tamanho
        if (part.length() > 63) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Verifica se um path é válido.
     * 
     * @param path Path a ser verificado
     * @return true se o path é válido, false caso contrário
     */
    private static boolean isValidPath(String path) {
        // Verifica caracteres
        for (char c : path.toCharArray()) {
            if (PATH_CHARS.indexOf(c) == -1) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Obtém o protocolo de uma URL.
     * 
     * @param url URL
     * @return Protocolo da URL
     */
    public static String getProtocol(String url) {
        if (url == null) {
            return null;
        }
        
        if (url.contains("://")) {
            return url.split("://")[0].toLowerCase();
        }
        
        return null;
    }
    
    /**
     * Obtém o domínio de uma URL.
     * 
     * @param url URL
     * @return Domínio da URL
     */
    public static String getDomain(String url) {
        if (url == null) {
            return null;
        }
        
        // Remove protocolo
        String cleanUrl = url;
        if (cleanUrl.contains("://")) {
            cleanUrl = cleanUrl.split("://")[1];
        }
        
        // Remove path
        if (cleanUrl.contains("/")) {
            cleanUrl = cleanUrl.split("/")[0];
        }
        
        return cleanUrl;
    }
    
    /**
     * Obtém o path de uma URL.
     * 
     * @param url URL
     * @return Path da URL, ou "/" se não houver
     */
    public static String getPath(String url) {
        if (url == null) {
            return null;
        }
        
        if (url.contains("/")) {
            String[] parts = url.split("/", 2);
            if (parts.length > 1) {
                return "/" + parts[1];
            }
        }
        
        return "/";
    }
    
    /**
     * Formata uma URL para o padrão correto.
     * 
     * @param url URL a ser formatada
     * @return URL formatada
     */
    public static String format(String url) {
        if (url == null) {
            return null;
        }
        
        // Remove espaços em branco
        String cleanUrl = url.trim();
        
        // Adiciona protocolo se não tiver
        if (!cleanUrl.contains("://")) {
            cleanUrl = "http://" + cleanUrl;
        }
        
        // Remove barras duplas no protocolo
        cleanUrl = cleanUrl.replace(":///", "://");
        
        // Remove barras no final se não houver path
        int protocolIndex = cleanUrl.indexOf("://");
        if (protocolIndex != -1) {
            String afterProtocol = cleanUrl.substring(protocolIndex + 3);
            if (!afterProtocol.contains("/")) {
                cleanUrl = cleanUrl.replaceAll("/+$", "");
            }
        }
        
        return cleanUrl;
    }
    
    /**
     * Verifica se uma URL é segura (HTTPS).
     * 
     * @param url URL
     * @return true se for HTTPS, false caso contrário
     */
    public static boolean isSecure(String url) {
        String protocol = getProtocol(url);
        return protocol != null && protocol.equals("https");
    }
    
    /**
     * Gera uma URL válida aleatória.
     * 
     * @param withPath Incluir path
     * @return URL válida
     */
    public static String generateRandomUrl(boolean withPath) {
        String protocol = VALID_PROTOCOLS[(int) (Math.random() * VALID_PROTOCOLS.length)];
        String domain = generateRandomDomain();
        String path = withPath ? generateRandomPath() : "";
        
        return protocol + "://" + domain + path;
    }
    
    /**
     * Gera um domínio válido aleatório.
     * 
     * @return Domínio válido
     */
    private static String generateRandomDomain() {
        String tld = VALID_TLDS[(int) (Math.random() * VALID_TLDS.length)];
        String subdomain = generateRandomString(5, DOMAIN_CHARS);
        String domain = generateRandomString(8, DOMAIN_CHARS);
        
        return subdomain + "." + domain + "." + tld;
    }
    
    /**
     * Gera um path válido aleatório.
     * 
     * @return Path válido
     */
    private static String generateRandomPath() {
        StringBuilder path = new StringBuilder();
        int pathLength = 2 + (int) (Math.random() * 4); // 2-5 partes
        
        for (int i = 0; i < pathLength; i++) {
            String part = generateRandomString(4, PATH_CHARS.replace("/", ""));
            path.append("/").append(part);
        }
        
        return path.toString();
    }
    
    /**
     * Gera uma string aleatória com caracteres válidos.
     * 
     * @param length Tamanho da string
     * @param validChars Caracteres válidos
     * @return String aleatória
     */
    private static String generateRandomString(int length, String validChars) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * validChars.length());
            sb.append(validChars.charAt(index));
        }
        return sb.toString();
    }
}