package br.ufjf.ead.validator.copilot;

import br.ufjf.ead.exception.InvalidDocumentException;
import br.ufjf.ead.validator.CreditCardValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes gerados por GitHub Copilot para CreditCardValidator.
 * 
 * Demonstração da abordagem LLM (Large Language Models) para geração de testes.
 * 
 * @author GitHub Copilot
 */
public class CreditCardValidator_CopilotTest {

    // --- Casos Válidos ---
    @Test
    void testCartaoVisaValido() {
        assertTrue(CreditCardValidator.isValid("4532015112830366"));
    }

    @Test
    void testCartaoMastercardValido() {
        assertTrue(CreditCardValidator.isValid("5555555555554444"));
    }

    @Test
    void testCartaoAmexValido() {
        assertTrue(CreditCardValidator.isValid("378282246310005"));
    }

    @Test
    void testCartaoDiscoverValido() {
        assertTrue(CreditCardValidator.isValid("6011111111111117"));
    }

    @Test
    void testCartaoDinersValido() {
        assertTrue(CreditCardValidator.isValid("30569309025904"));
    }

    @Test
    void testCartaoJCBValido() {
        assertTrue(CreditCardValidator.isValid("3530111333300000"));
    }

    // --- Casos Inválidos ---
    @Test
    void testCartaoNulo() {
        assertFalse(CreditCardValidator.isValid(null));
    }

    @Test
    void testCartaoVazio() {
        assertFalse(CreditCardValidator.isValid(""));
    }

    @Test
    void testCartaoComLetras() {
        assertFalse(CreditCardValidator.isValid("453201511283036A"));
    }

    @Test
    void testCartaoComEspacos() {
        assertFalse(CreditCardValidator.isValid("4532 0151 1283 0366"));
    }

    @Test
    void testCartaoMuitoCurto() {
        assertFalse(CreditCardValidator.isValid("123456789012"));
    }

    @Test
    void testCartaoMuitoLongo() {
        assertFalse(CreditCardValidator.isValid("123456789012345678901234567890"));
    }

    @Test
    void testCartaoInvalidoLuhn() {
        assertFalse(CreditCardValidator.isValid("4532015112830367"));
    }

    @Test
    void testCartaoBandeiraDesconhecida() {
        assertFalse(CreditCardValidator.isValid("9999999999999999"));
    }

    // --- Casos de Borda ---
    @Test
    void testCartaoVisa13Digitos() {
        assertTrue(CreditCardValidator.isValid("4532015112830"));
    }

    @Test
    void testCartaoVisa16Digitos() {
        assertTrue(CreditCardValidator.isValid("4532015112830366"));
    }

    @Test
    void testCartaoVisa19Digitos() {
        assertTrue(CreditCardValidator.isValid("4532015112830366123"));
    }

    @Test
    void testCartaoMastercard16Digitos() {
        assertTrue(CreditCardValidator.isValid("5555555555554444"));
    }

    @Test
    void testCartaoAmex15Digitos() {
        assertTrue(CreditCardValidator.isValid("378282246310005"));
    }

    // --- Testes de Identificação de Bandeira ---
    @Test
    void testIdentificarBandeiraVisa() {
        assertEquals("Visa", CreditCardValidator.getCardBrand("4532015112830366"));
    }

    @Test
    void testIdentificarBandeiraMastercard() {
        assertEquals("MasterCard", CreditCardValidator.getCardBrand("5555555555554444"));
    }

    @Test
    void testIdentificarBandeiraAmex() {
        assertEquals("American Express", CreditCardValidator.getCardBrand("378282246310005"));
    }

    @Test
    void testIdentificarBandeiraDiscover() {
        assertEquals("Discover", CreditCardValidator.getCardBrand("6011111111111117"));
    }

    @Test
    void testIdentificarBandeiraDiners() {
        assertEquals("Diners Club", CreditCardValidator.getCardBrand("30569309025904"));
    }

    @Test
    void testIdentificarBandeiraJCB() {
        assertEquals("JCB", CreditCardValidator.getCardBrand("3530111333300000"));
    }

    @Test
    void testIdentificarBandeiraDesconhecida() {
        assertNull(CreditCardValidator.getCardBrand("9999999999999999"));
    }

    // --- Testes de Formatação ---
    @Test
    void testFormatarCartao() {
        assertEquals("4532 0151 1283 0366", CreditCardValidator.format("4532015112830366"));
    }

    @Test
    void testFormatarCartaoComEspacos() {
        assertEquals("4532 0151 1283 0366", CreditCardValidator.format("4532 0151 1283 0366"));
    }

    @Test
    void testFormatarCartaoNulo() {
        assertNull(CreditCardValidator.format(null));
    }

    // --- Testes de Mascaramento ---
    @Test
    void testMascararCartao() {
        assertEquals("4532 **** **** 0366", CreditCardValidator.mask("4532015112830366"));
    }

    @Test
    void testMascararCartaoCurto() {
        assertEquals("**** **** **** ****", CreditCardValidator.mask("123456789012"));
    }

    @Test
    void testMascararCartaoNulo() {
        assertNull(CreditCardValidator.mask(null));
    }

    // --- Testes de Exceção ---
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

    // --- Testes de Geração de Cartão ---
    @Test
    void testGerarCartaoVisa() {
        String cartao = CreditCardValidator.generateTestCard("visa");
        assertTrue(CreditCardValidator.isValid(cartao));
        assertEquals("Visa", CreditCardValidator.getCardBrand(cartao));
    }

    @Test
    void testGerarCartaoMastercard() {
        String cartao = CreditCardValidator.generateTestCard("mastercard");
        assertTrue(CreditCardValidator.isValid(cartao));
        assertEquals("MasterCard", CreditCardValidator.getCardBrand(cartao));
    }

    @Test
    void testGerarCartaoAmex() {
        String cartao = CreditCardValidator.generateTestCard("amex");
        assertTrue(CreditCardValidator.isValid(cartao));
        assertEquals("American Express", CreditCardValidator.getCardBrand(cartao));
    }

    @Test
    void testGerarCartaoDesconhecido() {
        String cartao = CreditCardValidator.generateTestCard("desconhecido");
        assertTrue(CreditCardValidator.isValid(cartao));
        assertEquals("Visa", CreditCardValidator.getCardBrand(cartao)); // Deve usar Visa como padrão
    }
}