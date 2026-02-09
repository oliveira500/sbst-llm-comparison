package br.ufjf.ead.validator.copilot;

import br.ufjf.ead.exception.InvalidDocumentException;
import br.ufjf.ead.validator.DateValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes gerados por GitHub Copilot para DateValidator.
 * 
 * Demonstração da abordagem LLM (Large Language Models) para geração de testes.
 * 
 * @author GitHub Copilot
 */
public class DateValidator_CopilotTest {

    // --- Casos Válidos ---
    @Test
    void testDataValidaFormatoBrasileiro() {
        assertTrue(DateValidator.isValid("01/01/2023"));
    }

    @Test
    void testDataValidaFormatoISO() {
        assertTrue(DateValidator.isValid("2023-01-01"));
    }

    @Test
    void testDataValidaFormatoAmericano() {
        assertTrue(DateValidator.isValid("01/01/2023"));
    }

    @Test
    void testDataValidaAnoBissexto() {
        assertTrue(DateValidator.isValid("29/02/2020"));
    }

    @Test
    void testDataValidaAnoNaoBissexto() {
        assertTrue(DateValidator.isValid("28/02/2021"));
    }

    @Test
    void testDataValidaMes31Dias() {
        assertTrue(DateValidator.isValid("31/01/2023"));
    }

    @Test
    void testDataValidaMes30Dias() {
        assertTrue(DateValidator.isValid("30/04/2023"));
    }

    // --- Casos Inválidos ---
    @Test
    void testDataNula() {
        assertFalse(DateValidator.isValid(null));
    }

    @Test
    void testDataVazia() {
        assertFalse(DateValidator.isValid(""));
    }

    @Test
    void testDataInvalidaDia() {
        assertFalse(DateValidator.isValid("32/01/2023"));
    }

    @Test
    void testDataInvalidaMes() {
        assertFalse(DateValidator.isValid("01/13/2023"));
    }

    @Test
    void testDataInvalidaAno() {
        assertFalse(DateValidator.isValid("01/01/1899"));
    }

    @Test
    void testDataInvalidaFevereiroBissexto() {
        assertFalse(DateValidator.isValid("29/02/2021"));
    }

    @Test
    void testDataInvalidaFevereiroNaoBissexto() {
        assertFalse(DateValidator.isValid("30/02/2023"));
    }

    @Test
    void testDataInvalidaMes30Dias() {
        assertFalse(DateValidator.isValid("31/04/2023"));
    }

    @Test
    void testDataInvalidaMes31Dias() {
        assertFalse(DateValidator.isValid("32/01/2023"));
    }

    @Test
    void testDataFormatoInvalido() {
        assertFalse(DateValidator.isValid("01-01-2023"));
    }

    @Test
    void testDataComLetras() {
        assertFalse(DateValidator.isValid("01/AB/2023"));
    }

    @Test
    void testDataMuitoCurta() {
        assertFalse(DateValidator.isValid("01/01/23"));
    }

    @Test
    void testDataMuitoLonga() {
        assertFalse(DateValidator.isValid("01/01/20230"));
    }

    // --- Casos de Borda ---
    @Test
    void testDataComEspacos() {
        assertTrue(DateValidator.isValid(" 01/01/2023 "));
    }

    @Test
    void testDataAnoMinimo() {
        assertTrue(DateValidator.isValid("01/01/1900"));
    }

    @Test
    void testDataAnoMaximo() {
        assertTrue(DateValidator.isValid("01/01/2100"));
    }

    @Test
    void testDataAnoBissextoSecular() {
        assertTrue(DateValidator.isValid("29/02/2000")); // 2000 é bissexto
    }

    @Test
    void testDataAnoNaoBissextoSecular() {
        assertFalse(DateValidator.isValid("29/02/1900")); // 1900 não é bissexto
    }

    // --- Testes de Ano Bissexto ---
    @Test
    void testAnoBissextoDivisivelPor4() {
        assertTrue(DateValidator.isLeapYear(2020));
    }

    @Test
    void testAnoNaoBissextoNaoDivisivelPor4() {
        assertFalse(DateValidator.isLeapYear(2021));
    }

    @Test
    void testAnoBissextoSecularDivisivelPor400() {
        assertTrue(DateValidator.isLeapYear(2000));
    }

    @Test
    void testAnoNaoBissextoSecularNaoDivisivelPor400() {
        assertFalse(DateValidator.isLeapYear(1900));
    }

    // --- Testes de Formatação ---
    @Test
    void testFormatarDataBrasileira() {
        assertEquals("01/01/2023", DateValidator.formatBrazilian("01/01/2023"));
    }

    @Test
    void testFormatarDataISO() {
        assertEquals("2023-01-01", DateValidator.formatISO("01/01/2023"));
    }

    @Test
    void testFormatarDataNula() {
        assertNull(DateValidator.formatBrazilian(null));
    }

    // --- Testes de Cálculo de Idade ---
    @Test
    void testCalcularIdade() {
        assertEquals(3, DateValidator.calculateAge("01/01/2020"));
    }
    @Test
    void testCalcularIdadeAniversarioNaoChegou() {
        assertEquals(2, DateValidator.calculateAge("01/03/2020"));
    }

    @Test
    void testCalcularIdadeDataInvalida() {
        assertEquals(0, DateValidator.calculateAge("data invalida"));
    }

    // --- Testes de Intervalo ---
    @Test
    void testDataDentroDoIntervalo() {
        assertTrue(DateValidator.isInRange("15/01/2023", "01/01/2023", "31/01/2023"));
    }

    @Test
    void testDataForaDoIntervalo() {
        assertFalse(DateValidator.isInRange("15/02/2023", "01/01/2023", "31/01/2023"));
    }

    @Test
    void testDataInicioDoIntervalo() {
        assertTrue(DateValidator.isInRange("01/01/2023", "01/01/2023", "31/01/2023"));
    }

    @Test
    void testDataFimDoIntervalo() {
        assertTrue(DateValidator.isInRange("31/01/2023", "01/01/2023", "31/01/2023"));
    }

    // --- Testes de Exceção ---
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
            DateValidator.validate("32/01/2023");
        });
    }

    // --- Testes de Geração de Data ---
    @Test
    void testGerarDataValida() {
        String data = DateValidator.generateRandomDate(2020, 2023);
        assertTrue(DateValidator.isValid(data));
    }

    @Test
    void testGerarDataAnoMinimo() {
        String data = DateValidator.generateRandomDate(1900, 1900);
        assertTrue(DateValidator.isValid(data));
    }

    @Test
    void testGerarDataAnoMaximo() {
        String data = DateValidator.generateRandomDate(2100, 2100);
        assertTrue(DateValidator.isValid(data));
    }
}