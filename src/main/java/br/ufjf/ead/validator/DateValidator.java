package br.ufjf.ead.validator;

import br.ufjf.ead.exception.InvalidDocumentException;

/**
 * Validador de datas com suporte a anos bissextos e múltiplos formatos.
 * 
 * Implementa validação robusta de datas, incluindo verificação de anos bissextos,
 * formatos de data comuns e tratamento de casos de borda.
 * 
 * @author Fabio Oliveira
 */
public class DateValidator {
    
    // Formatos de data suportados
    private static final String[] SUPPORTED_FORMATS = {
        "dd/MM/yyyy", "dd-MM-yyyy", "dd.MM.yyyy",
        "yyyy/MM/dd", "yyyy-MM-dd", "yyyy.MM.dd",
        "MM/dd/yyyy", "MM-dd-yyyy", "MM.dd.yyyy"
    };
    
    // Dias em cada mês (não bissexto)
    private static final int[] DAYS_IN_MONTH = {
        31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
    };
    
    // Ano mínimo e máximo permitido
    private static final int MIN_YEAR = 1900;
    private static final int MAX_YEAR = 2100;
    
    /**
     * Valida uma data.
     * 
     * @param date Data a ser validada
     * @return true se a data é válida, false caso contrário
     */
    public static boolean isValid(String date) {
        try {
            validate(date);
            return true;
        } catch (InvalidDocumentException e) {
            return false;
        }
    }
    
    /**
     * Valida uma data e lança exceção se inválida.
     * 
     * @param date Data a ser validada
     * @throws InvalidDocumentException se a data for inválida
     */
    public static void validate(String date) throws InvalidDocumentException {
        // Verifica se é nulo ou vazio
        if (date == null || date.trim().isEmpty()) {
            throw new InvalidDocumentException("Data não pode ser nula ou vazia");
        }
        
        // Remove espaços em branco
        String cleanDate = date.trim();
        
        // Tenta validar com cada formato suportado
        boolean valid = false;
        for (String format : SUPPORTED_FORMATS) {
            if (validateFormat(cleanDate, format)) {
                valid = true;
                break;
            }
        }
        
        if (!valid) {
            throw new InvalidDocumentException("Formato de data inválido. Use um dos formatos suportados: " + 
                String.join(", ", SUPPORTED_FORMATS));
        }
    }
    
    /**
     * Valida uma data em um formato específico.
     * 
     * @param date Data a ser validada
     * @param format Formato esperado
     * @return true se a data é válida no formato especificado
     */
    private static boolean validateFormat(String date, String format) {
        try {
            // Remove caracteres não numéricos para extrair os componentes
            String[] parts = date.split("[/\\-.]");
            
            if (parts.length != 3) {
                return false;
            }
            
            int day, month, year;
            
            // Parseia os componentes baseado no formato
            switch (format) {
                case "dd/MM/yyyy":
                case "dd-MM-yyyy":
                case "dd.MM.yyyy":
                    day = Integer.parseInt(parts[0]);
                    month = Integer.parseInt(parts[1]);
                    year = Integer.parseInt(parts[2]);
                    break;
                    
                case "yyyy/MM/dd":
                case "yyyy-MM-dd":
                case "yyyy.MM.dd":
                    year = Integer.parseInt(parts[0]);
                    month = Integer.parseInt(parts[1]);
                    day = Integer.parseInt(parts[2]);
                    break;
                    
                case "MM/dd/yyyy":
                case "MM-dd-yyyy":
                case "MM.dd.yyyy":
                    month = Integer.parseInt(parts[0]);
                    day = Integer.parseInt(parts[1]);
                    year = Integer.parseInt(parts[2]);
                    break;
                    
                default:
                    return false;
            }
            
            return validateDateComponents(day, month, year);
            
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }
    
    /**
     * Valida os componentes de uma data (dia, mês, ano).
     * 
     * @param day Dia
     * @param month Mês
     * @param year Ano
     * @return true se os componentes são válidos
     */
    private static boolean validateDateComponents(int day, int month, int year) {
        // Verifica ano
        if (year < MIN_YEAR || year > MAX_YEAR) {
            return false;
        }
        
        // Verifica mês
        if (month < 1 || month > 12) {
            return false;
        }
        
        // Verifica dia
        if (day < 1) {
            return false;
        }
        
        // Verifica dia máximo para o mês
        int maxDay = DAYS_IN_MONTH[month - 1];
        
        // Ajusta fevereiro para anos bissextos
        if (month == 2 && isLeapYear(year)) {
            maxDay = 29;
        }
        
        return day <= maxDay;
    }
    
    /**
     * Verifica se um ano é bissexto.
     * 
     * @param year Ano a ser verificado
     * @return true se o ano é bissexto, false caso contrário
     */
    public static boolean isLeapYear(int year) {
        // Ano bissexto: divisível por 4, mas não por 100, exceto se for divisível por 400
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
    
    /**
     * Formata uma data para o padrão brasileiro.
     * 
     * @param date Data a ser formatada
     * @return Data formatada no padrão dd/MM/yyyy
     */
    public static String formatBrazilian(String date) {
        if (date == null) {
            return null;
        }
        
        // Remove caracteres não numéricos
        String cleanDate = date.replaceAll("[^0-9]", "");
        
        if (cleanDate.length() != 8) {
            return date; // Retorna original se não puder formatar
        }
        
        // Extrai componentes
        String day = cleanDate.substring(0, 2);
        String month = cleanDate.substring(2, 4);
        String year = cleanDate.substring(4, 8);
        
        return day + "/" + month + "/" + year;
    }
    
    /**
     * Formata uma data para o padrão ISO (yyyy-MM-dd).
     * 
     * @param date Data a ser formatada
     * @return Data formatada no padrão yyyy-MM-dd
     */
    public static String formatISO(String date) {
        if (date == null) {
            return null;
        }
        
        // Remove caracteres não numéricos
        String cleanDate = date.replaceAll("[^0-9]", "");
        
        if (cleanDate.length() != 8) {
            return date; // Retorna original se não puder formatar
        }
        
        // Extrai componentes
        String year = cleanDate.substring(0, 4);
        String month = cleanDate.substring(4, 6);
        String day = cleanDate.substring(6, 8);
        
        return year + "-" + month + "-" + day;
    }
    
    /**
     * Calcula a idade a partir de uma data de nascimento.
     * 
     * @param birthDate Data de nascimento
     * @return Idade em anos, ou 0 se a data for inválida
     */
    public static int calculateAge(String birthDate) {
        try {
            // Extrai componentes da data de nascimento
            String[] parts = birthDate.split("[/\\-.]");
            int birthYear, birthMonth, birthDay;
            
            // Assume formato dd/MM/yyyy para cálculo
            birthDay = Integer.parseInt(parts[0]);
            birthMonth = Integer.parseInt(parts[1]);
            birthYear = Integer.parseInt(parts[2]);
            
            // Data atual (simulada - em um sistema real usaria java.time)
            int currentYear = 2026;
            int currentMonth = 2;
            int currentDay = 8;
            
            int age = currentYear - birthYear;
            
            // Ajusta se o aniversário ainda não chegou este ano
            if (currentMonth < birthMonth || 
                (currentMonth == birthMonth && currentDay < birthDay)) {
                age--;
            }
            
            return age;
            
        } catch (Exception e) {
            return 0; // Retorna 0 para datas inválidas
        }
    }
    
    /**
     * Verifica se uma data está em um intervalo válido.
     * 
     * @param date Data a ser verificada
     * @param startDate Data de início do intervalo
     * @param endDate Data de fim do intervalo
     * @return true se a data está no intervalo, false caso contrário
     */
    public static boolean isInRange(String date, String startDate, String endDate) {
        try {
            int dateValue = convertToComparableValue(date);
            int startValue = convertToComparableValue(startDate);
            int endValue = convertToComparableValue(endDate);
            
            return dateValue >= startValue && dateValue <= endValue;
            
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Converte uma data para um valor comparável (yyyymmdd).
     * 
     * @param date Data a ser convertida
     * @return Valor numérico comparável
     */
    private static int convertToComparableValue(String date) {
        String[] parts = date.split("[/\\-.]");
        int year, month, day;
        
        // Assume formato dd/MM/yyyy
        day = Integer.parseInt(parts[0]);
        month = Integer.parseInt(parts[1]);
        year = Integer.parseInt(parts[2]);
        
        return year * 10000 + month * 100 + day;
    }
    
    /**
     * Gera uma data válida aleatória.
     * 
     * @param startYear Ano inicial
     * @param endYear Ano final
     * @return Data válida no formato dd/MM/yyyy
     */
    public static String generateRandomDate(int startYear, int endYear) {
        int year = startYear + (int) (Math.random() * (endYear - startYear + 1));
        int month = 1 + (int) (Math.random() * 12);
        
        int maxDay = DAYS_IN_MONTH[month - 1];
        if (month == 2 && isLeapYear(year)) {
            maxDay = 29;
        }
        
        int day = 1 + (int) (Math.random() * maxDay);
        
        return String.format("%02d/%02d/%04d", day, month, year);
    }
}