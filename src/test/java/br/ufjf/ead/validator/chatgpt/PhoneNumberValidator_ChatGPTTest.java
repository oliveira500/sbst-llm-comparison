package br.ufjf.ead.validator.chatgpt;

import br.ufjf.ead.exception.InvalidDocumentException;
import br.ufjf.ead.validator.PhoneNumberValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes gerados por ChatGPT para PhoneNumberValidator.
 * 
 * Demonstração da abordagem LLM (Large Language Models) para geração de testes.
 * 
 * @author ChatGPT
 */
public class PhoneNumberValidator_ChatGPTTest {

    // --- Testes de Validação Básica ---
    @Test
    void testTelefoneValidoBasico() {
        assertTrue(PhoneNumberValidator.isValid("(11) 98765-4321"));
    }

    @Test
    void testTelefoneValidoFormatoInternacional() {
        assertTrue(PhoneNumberValidator.isValid("+55 11 98765-4321"));
    }

    @Test
    void testTelefoneValidoFormatoSimples() {
        assertTrue(PhoneNumberValidator.isValid("11987654321"));
    }

    // --- Testes de Validação de DDD ---
    @Test
    void testTelefoneValidoDDDValido() {
        assertTrue(PhoneNumberValidator.isValid("(11) 98765-4321"));
    }

    @Test
    void testTelefoneInvalidoDDDMuitoAlto() {
        assertFalse(PhoneNumberValidator.isValid("(99) 98765-4321"));
    }

    @Test
    void testTelefoneInvalidoDDDMuitoBaixo() {
        assertFalse(PhoneNumberValidator.isValid("(00) 98765-4321"));
    }

    // --- Testes de Validação de Operadora ---
    @Test
    void testTelefoneValidoOperadoraValida() {
        assertTrue(PhoneNumberValidator.isValid("(11) 98765-4321"));
    }

    @Test
    void testTelefoneInvalidoOperadoraInvalida() {
        assertFalse(PhoneNumberValidator.isValid("(11) 88765-4321"));
    }

    // --- Testes de Validação de Exceção ---
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

    // --- Testes de Formatação ---
    @Test
    void testFormatarTelefone() {
        assertEquals("(11) 98765-4321", PhoneNumberValidator.format("11987654321"));
    }

    @Test
    void testFormatarTelefoneNulo() {
        assertNull(PhoneNumberValidator.format(null));
    }

}