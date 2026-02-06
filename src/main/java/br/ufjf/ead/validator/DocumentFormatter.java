package br.ufjf.ead.validator;

/**
 * Classe utilitária para formatação e limpeza de documentos (CPF/CNPJ).
 * 
 * @author Fabio Oliveira
 */
public class DocumentFormatter {
        /**
         * Remove a formatação de um CPF, retornando apenas os dígitos.
         * @param cpf CPF formatado ou não
         * @return String contendo apenas os dígitos do CPF, ou null se entrada for null
         */
        public static String unformatCpf(String cpf) {
            if (cpf == null) return null;
            return cpf.replaceAll("[^0-9]", "");
        }

        /**
         * Remove a formatação de um CNPJ, retornando apenas os dígitos.
         * @param cnpj CNPJ formatado ou não
         * @return String contendo apenas os dígitos do CNPJ, ou null se entrada for null
         */
        public static String unformatCnpj(String cnpj) {
            if (cnpj == null) return null;
            return cnpj.replaceAll("[^0-9]", "");
        }
    
    /**
     * Remove caracteres não numéricos de uma string de documento.
     * @param document String contendo o documento
     * @return String contendo apenas dígitos numéricos, ou null se entrada for null
     */
    public static String removeFormatting(String document) {
        if (document == null) {
            return null;
        }
        return document.replaceAll("[^0-9]", "");
    }
    
    /**
     * Formata um CPF no padrão XXX.XXX.XXX-XX.
     * @param cpf String contendo 11 dígitos do CPF (ou formatado)
     * @return CPF formatado, string vazia se entrada vazia, null se entrada null, ou valor original se não tiver 11 dígitos
     */
    public static String formatCpf(String cpf) {
        if (cpf == null) return null;
        if (cpf.isEmpty()) return "";
        String digits = unformatCpf(cpf);
        if (digits.length() != 11) return cpf;
        return String.format("%s.%s.%s-%s",
                digits.substring(0, 3),
                digits.substring(3, 6),
                digits.substring(6, 9),
                digits.substring(9, 11));
    }
    
    /**
     * Formata um CNPJ no padrão XX.XXX.XXX/XXXX-XX.
     * @param cnpj String contendo 14 dígitos do CNPJ (ou formatado)
     * @return CNPJ formatado, string vazia se entrada vazia, null se entrada null, ou valor original se não tiver 14 dígitos
     */
    public static String formatCnpj(String cnpj) {
        if (cnpj == null) return null;
        if (cnpj.isEmpty()) return "";
        String digits = unformatCnpj(cnpj);
        if (digits.length() != 14) return cnpj;
        return String.format("%s.%s.%s/%s-%s",
                digits.substring(0, 2),
                digits.substring(2, 5),
                digits.substring(5, 8),
                digits.substring(8, 12),
                digits.substring(12, 14));
    }
}
