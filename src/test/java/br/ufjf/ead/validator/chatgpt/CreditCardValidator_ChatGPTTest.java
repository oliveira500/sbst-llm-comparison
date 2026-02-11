package br.ufjf.ead.validator.chatgpt;

import br.ufjf.ead.exception.InvalidDocumentException;
import br.ufjf.ead.validator.CreditCardValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes gerados por ChatGPT para CreditCardValidator.
 * 
 * Demonstração da abordagem LLM (Large Language Models) para geração de testes.
 * 
 * @author ChatGPT
 */
public class CreditCardValidator_ChatGPTTest {

    // --- Testes de Validação Básica ---
    @Test
    void testCartaoValidoVisa() {
        assertTrue(CreditCardValidator.isValid("4532015112830366"));
    }

    @Test
    void testCartaoValidoMastercard() {
        assertTrue(CreditCardValidator.isValid("5555555555554444"));
    }

    @Test
    void testCartaoValidoAmericanExpress() {
        assertTrue(CreditCardValidator.isValid("378282246310005"));
    }

    @Test
    void testCartaoValidoDiscover() {
        assertTrue(CreditCardValidator.isValid("6011111111111117"));
    }

    // --- Testes de Validação de Tamanho ---
    @Test
    void testCartaoValidoTamanhoMinimo() {
        assertTrue(CreditCardValidator.isValid("4111111111111111"));
    }

    @Test
    void testCartaoValidoTamanhoMaximo() {
        assertTrue(CreditCardValidator.isValid("378282246310005"));
    }

    @Test
    void testCartaoInvalidoMuitoCurto() {
        assertFalse(CreditCardValidator.isValid("123456789012345"));
    }

    @Test
    void testCartaoInvalidoMuitoLongo() {
        assertFalse(CreditCardValidator.isValid("1234567890123456789012345"));
    }

    // --- Testes de Validação de Algoritmo de Luhn ---
    @Test
    void testCartaoInvalidoLuhn() {
        assertFalse(CreditCardValidator.isValid("4532015112830367"));
    }

    @Test
    void testCartaoValidoLuhn() {
        assertTrue(CreditCardValidator.isValid("4532015112830366"));
    }

    // --- Testes de Validação de Tipo de Cartão ---
    @Test
    void testIdentificarVisa() {
        assertEquals("Visa", CreditCardValidator.getCardBrand("4532015112830366"));
    }

    @Test
    void testIdentificarMastercard() {
        assertEquals("MasterCard", CreditCardValidator.getCardBrand("5555555555554444"));
    }

    @Test
    void testIdentificarAmericanExpress() {
        assertEquals("American Express", CreditCardValidator.getCardBrand("378282246310005"));
    }

    @Test
    void testIdentificarDiscover() {
        assertEquals("Discover", CreditCardValidator.getCardBrand("6011111111111117"));
    }

    @Test
    void testIdentificarTipoDesconhecido() {
        assertNull(CreditCardValidator.getCardBrand("1234567890123456"));
    }

    // --- Testes de Validação de Exceção ---
    @Test
    void testValidateCartaoNuloLancaExcecao() {
        assertThrows(InvalidDocumentException.class, () -> {
            CreditCardValidator.validate(null);
        });
    }

    @Test
    void testValidateCartaoVazioLancaExcecao() {
        assertThrows(InvalidDocumentException.class, () -> {
            CreditCardValidator.validate("");
        });
    }

    @Test
    void testValidateCartaoInvalidoLancaExcecao() {
        assertThrows(InvalidDocumentException.class, () -> {
            CreditCardValidator.validate("1234567890123456");
        });
    }

    // --- Testes de Formatação ---
    @Test
    void testFormatarCartao() {
        assertEquals("4532 0151 1283 0366", CreditCardValidator.format("4532015112830366"));
    }

    @Test
    void testFormatarCartaoNulo() {
        assertNull(CreditCardValidator.format(null));
    }

    // --- Testes de Geração de Números de Teste ---
    @Test
    void testGerarNumeroVisa() {
        String numero = CreditCardValidator.generateTestCard("Visa");
        assertTrue(CreditCardValidator.isValid(numero));
        assertEquals("Visa", CreditCardValidator.getCardBrand(numero));
    }

    @Test
    void testGerarNumeroMastercard() {
        String numero = CreditCardValidator.generateTestCard("MasterCard");
        assertTrue(CreditCardValidator.isValid(numero));
        assertEquals("MasterCard", CreditCardValidator.getCardBrand(numero));
    }

    // --- Testes de Casos Extremos ---
    @Test
    void testCartaoComEspacos() {
        assertTrue(CreditCardValidator.isValid("4532 0151 1283 0366"));
    }

    @Test
    void testCartaoComHifens() {
        assertTrue(CreditCardValidator.isValid("4532-0151-1283-0366"));
    }

    @Test
    void testCartaoComLetras() {
        assertFalse(CreditCardValidator.isValid("453201511283036A"));
    }

    @Test
    void testCartaoComCaracteresEspeciais() {
        assertFalse(CreditCardValidator.isValid("453201511283036!"));
    }

    // --- Testes de Validação de Dígitos ---
    @Test
    void testCartaoValido16Digitos() {
        assertTrue(CreditCardValidator.isValid("4532015112830366"));
    }

    @Test
    void testCartaoValido15Digitos() {
        assertTrue(CreditCardValidator.isValid("378282246310005"));
    }

    @Test
    void testCartaoValido13Digitos() {
        assertTrue(CreditCardValidator.isValid("402400713651268"));
    }

    @Test
    void testCartaoInvalido17Digitos() {
        assertFalse(CreditCardValidator.isValid("12345678901234567"));
    }

    // --- Testes de Validação de Prefixos ---
    @Test
    void testCartaoValidoPrefixoVisa() {
        assertTrue(CreditCardValidator.isValid("4111111111111111"));
    }

    @Test
    void testCartaoValidoPrefixoMastercard() {
        assertTrue(CreditCardValidator.isValid("5105105105105100"));
    }

    @Test
    void testCartaoValidoPrefixoAmericanExpress() {
        assertTrue(CreditCardValidator.isValid("341111111111111"));
    }

    @Test
    void testCartaoValidoPrefixoDiscover() {
        assertTrue(CreditCardValidator.isValid("6011000990139424"));
    }

    // --- Testes de Validação de Dígitos de Controle ---
    @Test
    void testCartaoValidoDigitoControle() {
        assertTrue(CreditCardValidator.isValid("4532015112830366"));
    }

    @Test
    void testCartaoInvalidoDigitoControle() {
        assertFalse(CreditCardValidator.isValid("4532015112830367"));
    }

    // --- Testes de Validação de Números Conhecidos ---
    @Test
    void testCartaoTesteVisa() {
        assertTrue(CreditCardValidator.isValid("4111111111111111"));
    }

    @Test
    void testCartaoTesteMastercard() {
        assertTrue(CreditCardValidator.isValid("5555555555554444"));
    }

    @Test
    void testCartaoTesteAmericanExpress() {
        assertTrue(CreditCardValidator.isValid("378282246310005"));
    }

    @Test
    void testCartaoTesteDiscover() {
        assertTrue(CreditCardValidator.isValid("6011111111111117"));
    }
}