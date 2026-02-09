package br.ufjf.ead.validator.copilot;

import br.ufjf.ead.exception.InvalidDocumentException;
import br.ufjf.ead.validator.PhoneNumberValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes gerados por GitHub Copilot para PhoneNumberValidator.
 * 
 * Demonstração da abordagem LLM (Large Language Models) para geração de testes.
 * 
 * @author GitHub Copilot
 */
public class PhoneNumberValidator_CopilotTest {

    // --- Casos Válidos ---
    @Test
    void testTelefoneValidoFormatoBrasileiro() {
        assertTrue(PhoneNumberValidator.isValid("(21) 98765-4321"));
    }

    @Test
    void testTelefoneValidoFormatoInternacional() {
        assertTrue(PhoneNumberValidator.isValid("+55 21 98765-4321"));
    }

    @Test
    void testTelefoneValidoFormatoSimplificado() {
        assertTrue(PhoneNumberValidator.isValid("21987654321"));
    }

    @Test
    void testTelefoneValidoComDDD() {
        assertTrue(PhoneNumberValidator.isValid("(11) 98765-4321"));
    }

    @Test
    void testTelefoneValidoSemDDD() {
        assertTrue(PhoneNumberValidator.isValid("98765-4321"));
    }

    @Test
    void testTelefoneValidoFormatoAmericano() {
        assertTrue(PhoneNumberValidator.isValid("(555) 123-4567"));
    }

    // --- Casos Inválidos ---
    @Test
    void testTelefoneNulo() {
        assertFalse(PhoneNumberValidator.isValid(null));
    }

    @Test
    void testTelefoneVazio() {
        assertFalse(PhoneNumberValidator.isValid(""));
    }

    @Test
    void testTelefoneComLetras() {
        assertFalse(PhoneNumberValidator.isValid("(21) 98765-432A"));
    }

    @Test
    void testTelefoneMuitoCurto() {
        assertFalse(PhoneNumberValidator.isValid("12345678"));
    }

    @Test
    void testTelefoneMuitoLongo() {
        assertFalse(PhoneNumberValidator.isValid("12345678901234567890"));
    }

    @Test
    void testTelefoneSemFormato() {
        assertFalse(PhoneNumberValidator.isValid("telefone invalido"));
    }

    @Test
    void testTelefoneComCaracteresEspeciais() {
        assertFalse(PhoneNumberValidator.isValid("(21) 98765-432!"));
    }

    @Test
    void testTelefoneComEspacosExcessivos() {
        assertFalse(PhoneNumberValidator.isValid("(21)  98765-  4321"));
    }

    // --- Casos de Borda ---
    @Test
    void testTelefoneComEspacos() {
        assertTrue(PhoneNumberValidator.isValid(" (21) 98765-4321 "));
    }

    @Test
    void testTelefoneFormatoVariado() {
        assertTrue(PhoneNumberValidator.isValid("21 98765-4321"));
    }

    @Test
    void testTelefoneComZero() {
        assertTrue(PhoneNumberValidator.isValid("(21) 90000-0000"));
    }

    @Test
    void testTelefoneComNove() {
        assertTrue(PhoneNumberValidator.isValid("(21) 99999-9999"));
    }

    // --- Testes de Formatação ---
    @Test
    void testFormatarTelefone() {
        assertEquals("(21) 98765-4321", PhoneNumberValidator.format("(21) 98765-4321"));
    }

    @Test
    void testFormatarTelefoneNulo() {
        assertNull(PhoneNumberValidator.format(null));
    }

    // --- Testes de Geração de Telefone ---
    @Test
    void testGerarTelefoneValido() {
        String telefone = PhoneNumberValidator.generateRandomPhoneNumber(true, true);
        assertTrue(PhoneNumberValidator.isValid(telefone));
    }

    @Test
    void testGerarTelefoneComDDD() {
        String telefone = PhoneNumberValidator.generateRandomPhoneNumber(true, true);
        assertTrue(PhoneNumberValidator.isValid(telefone));
    }

    @Test
    void testGerarTelefoneNulo() {
        String telefone = PhoneNumberValidator.generateRandomPhoneNumber(true, true);
        assertTrue(PhoneNumberValidator.isValid(telefone));
    }

    // --- Testes de Exceção ---
    @Test
    void testValidateTelefoneNuloLancaExcecao() {
        assertThrows(InvalidDocumentException.class, () -> {
            PhoneNumberValidator.validate(null);
        });
    }

    @Test
    void testValidateTelefoneVazioLancaExcecao() {
        assertThrows(InvalidDocumentException.class, () -> {
            PhoneNumberValidator.validate("");
        });
    }

    @Test
    void testValidateTelefoneInvalidoLancaExcecao() {
        assertThrows(InvalidDocumentException.class, () -> {
            PhoneNumberValidator.validate("telefone invalido");
        });
    }
}