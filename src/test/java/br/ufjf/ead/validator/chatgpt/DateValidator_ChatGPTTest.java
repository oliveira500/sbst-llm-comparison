package br.ufjf.ead.validator.chatgpt;

import br.ufjf.ead.exception.InvalidDocumentException;
import br.ufjf.ead.validator.DateValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes gerados por ChatGPT para DateValidator.
 * 
 * Demonstração da abordagem LLM (Large Language Models) para geração de testes.
 * 
 * @author ChatGPT
 */
public class DateValidator_ChatGPTTest {

    // --- Testes de Validação Básica ---
    @Test
    void testDataValidaBasica() {
        assertTrue(DateValidator.isValid("01/01/2023"));
    }

    @Test
    void testDataValidaFormatoAno() {
        assertTrue(DateValidator.isValid("01/01/2023"));
    }

    @Test
    void testDataValidaFormatoMes() {
        assertTrue(DateValidator.isValid("01/12/2023"));
    }

    @Test
    void testDataValidaFormatoDia() {
        assertTrue(DateValidator.isValid("31/12/2023"));
    }

    // --- Testes de Validação de Ano Bissexto ---
    @Test
    void testAnoBissextoValido() {
        assertTrue(DateValidator.isValid("29/02/2020"));
    }

    @Test
    void testAnoBissextoInvalido() {
        assertFalse(DateValidator.isValid("29/02/2021"));
    }

    // --- Testes de Validação de Meses com 30 Dias ---
    @Test
    void testMes30DiasValido() {
        assertTrue(DateValidator.isValid("30/04/2023"));
    }

    @Test
    void testMes30DiasInvalido() {
        assertFalse(DateValidator.isValid("31/04/2023"));
    }

    // --- Testes de Validação de Meses com 31 Dias ---
    @Test
    void testMes31DiasValido() {
        assertTrue(DateValidator.isValid("31/01/2023"));
    }

    @Test
    void testMes31DiasInvalido() {
        assertFalse(DateValidator.isValid("32/01/2023"));
    }

    // --- Testes de Validação de Exceção ---
    @Test
    void testValidateDataNulaLancaExcecao() {
        assertThrows(InvalidDocumentException.class, () -> {
            DateValidator.validate(null);
        });
    }

    @Test
    void testValidateDataVaziaLancaExcecao() {
        assertThrows(InvalidDocumentException.class, () -> {
            DateValidator.validate("");
        });
    }

    @Test
    void testValidateDataInvalidaLancaExcecao() {
        assertThrows(InvalidDocumentException.class, () -> {
            DateValidator.validate("32/13/2023");
        });
    }


    // --- Testes de Cálculo de Idade ---
    @Test
    void testCalcularIdadeBasica() {
        assertEquals(23, DateValidator.calculateAge("01/01/2000"));
    }
}