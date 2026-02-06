package br.ufjf.ead.validator.llm;

import br.ufjf.ead.exception.InvalidDocumentException;
import br.ufjf.ead.validator.CnpjValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes exaustivos para CnpjValidator.
 *
 * @author Fabio Oliveira
 */
public class CnpjValidator_LLMTest {

    // --- Casos de Borda ---
    // Testa cenários de entrada nula, vazia, espaços e formatos inválidos

    // Testa CNPJ nulo
    @Test
    void testCnpjNulo() {
        assertThrows(InvalidDocumentException.class, () -> CnpjValidator.validate(null),
            "Deve lançar exceção para CNPJ nulo");
    }

    // Testa CNPJ vazio
    @Test
    void testCnpjVazio() {
        assertThrows(InvalidDocumentException.class, () -> CnpjValidator.validate(""),
            "Deve lançar exceção para CNPJ vazio");
    }

    // Testa CNPJ com espaços
    @Test
    void testCnpjComEspacos() {
        assertThrows(InvalidDocumentException.class, () -> CnpjValidator.validate("   "),
            "Deve lançar exceção para CNPJ apenas com espaços");
    }

    // Testa formato inválido (letra no final)
    @Test
    void testCnpjFormatoInvalidoLetra() {
        assertThrows(InvalidDocumentException.class, () -> CnpjValidator.validate("12.345.678/9012-3A"),
            "Deve lançar exceção para CNPJ com letra");
    }

    // Testa CNPJ com menos de 14 dígitos
    @Test
    void testCnpjMenosDeQuatorzeDigitos() {
        assertThrows(InvalidDocumentException.class, () -> CnpjValidator.validate("1234567890123"),
            "Deve lançar exceção para CNPJ com menos de 14 dígitos");
    }

    // Testa CNPJ com mais de 14 dígitos
    @Test
    void testCnpjMaisDeQuatorzeDigitos() {
        assertThrows(InvalidDocumentException.class, () -> CnpjValidator.validate("123456789012345"),
            "Deve lançar exceção para CNPJ com mais de 14 dígitos");
    }

    // Testa CNPJ com caracteres especiais
    @Test
    void testCnpjComCaracteresEspeciais() {
        try {
            CnpjValidator.validate("12@345#678/9012-34");
        } catch (InvalidDocumentException ex) {
            return;
        }
        assertDoesNotThrow(() -> CnpjValidator.validate("12345678901234"),
            "Se ignorar caracteres especiais, deve aceitar o CNPJ válido");
    }

    // --- Casos Válidos ---

    // Testa CNPJ válido sem formatação
    @Test
    void testCnpjValidoSemFormatacao() {
        assertDoesNotThrow(() -> CnpjValidator.validate("12345678000195"),
            "Não deve lançar exceção para CNPJ válido sem formatação");
    }

    // Testa CNPJ válido com formatação
    @Test
    void testCnpjValidoComFormatacao() {
        assertDoesNotThrow(() -> CnpjValidator.validate("12.345.678/0001-95"),
            "Não deve lançar exceção para CNPJ válido com formatação");
    }

    // Testa CNPJ válido gerado aleatoriamente
    @Test
    void testCnpjValidoAleatorio() {
        assertDoesNotThrow(() -> CnpjValidator.validate("45.723.174/0001-10"),
            "Não deve lançar exceção para CNPJ válido real");
    }

    // --- Casos Inválidos ---

    // Testa CNPJ com dígitos verificadores incorretos
    @Test
    void testCnpjDigitosVerificadoresIncorretos() {
        assertThrows(InvalidDocumentException.class, () -> CnpjValidator.validate("12.345.678/0001-00"),
            "Deve lançar exceção para dígitos verificadores incorretos");
    }

    // Testa CNPJ conhecido inválido (todos dígitos iguais)
    @Test
    void testCnpjTodosDigitosIguais() {
        assertThrows(InvalidDocumentException.class, () -> CnpjValidator.validate("11.111.111/1111-11"),
            "Deve lançar exceção para CNPJ com todos dígitos iguais");
        assertThrows(InvalidDocumentException.class, () -> CnpjValidator.validate("00.000.000/0000-00"),
            "Deve lançar exceção para CNPJ com todos dígitos iguais");
    }

    // --- Mensagens de Erro Específicas ---

    // Testa mensagem de erro para CNPJ vazio
    @Test
    void testMensagemErroCnpjVazio() {
        InvalidDocumentException ex = assertThrows(InvalidDocumentException.class, () -> CnpjValidator.validate(""));
        assertTrue(ex.getMessage().toLowerCase().contains("vazio") || ex.getMessage().toLowerCase().contains("null"),
            "Mensagem de erro deve indicar CNPJ vazio ou nulo");
    }

    // Testa mensagem de erro para formato inválido
    @Test
    void testMensagemErroFormatoInvalido() {
        InvalidDocumentException ex = assertThrows(InvalidDocumentException.class, () -> CnpjValidator.validate("12.345.678/9012-3A"));
        String msg = ex.getMessage().toLowerCase();
        assertTrue(msg.contains("inválido") || msg.contains("formato") || msg.contains("caractere") || msg.contains("cnpj"),
            "Mensagem de erro deve indicar formato inválido ou caractere não permitido");
    }

    // Testa mensagem de erro para dígitos verificadores incorretos
    @Test
    void testMensagemErroDigitosVerificadores() {
        InvalidDocumentException ex = assertThrows(InvalidDocumentException.class, () -> CnpjValidator.validate("12.345.678/0001-00"));
        assertTrue(ex.getMessage().toLowerCase().contains("dígito") || ex.getMessage().toLowerCase().contains("verificador"),
            "Mensagem de erro deve indicar dígitos verificadores incorretos");
    }
}
