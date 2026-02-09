package br.ufjf.ead.validator.copilot;

import br.ufjf.ead.exception.InvalidDocumentException;
import br.ufjf.ead.validator.CepValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes gerados por GitHub Copilot para CepValidator.
 * 
 * Demonstração da abordagem LLM (Large Language Models) para geração de testes.
 * 
 * @author GitHub Copilot
 */
public class CepValidator_CopilotTest {

    // --- Casos Válidos ---
    @Test
    void testCepValidoFormatoNumerico() {
        assertTrue(CepValidator.isValid("20040001"));
    }

    @Test
    void testCepValidoFormatoComHifen() {
        assertTrue(CepValidator.isValid("20040-001"));
    }

    @Test
    void testCepValidoFormatoComEspacos() {
        assertTrue(CepValidator.isValid(" 20040-001 "));
    }

    @Test
    void testCepValidoFormatoSemHifen() {
        assertTrue(CepValidator.isValid("20040001"));
    }

    @Test
    void testCepValidoFormatoMisto() {
        assertTrue(CepValidator.isValid("20.040-001"));
    }

    // --- Casos Inválidos ---
    @Test
    void testCepNulo() {
        assertFalse(CepValidator.isValid(null));
    }

    @Test
    void testCepVazio() {
        assertFalse(CepValidator.isValid(""));
    }

    @Test
    void testCepComLetras() {
        assertFalse(CepValidator.isValid("20040-00A"));
    }

    @Test
    void testCepMuitoCurto() {
        assertFalse(CepValidator.isValid("20040"));
    }

    @Test
    void testCepMuitoLongo() {
        assertFalse(CepValidator.isValid("200400010"));
    }

    @Test
    void testCepComCaracteresEspeciais() {
        assertFalse(CepValidator.isValid("20040-00!"));
    }

    @Test
    void testCepComEspacosExcessivos() {
        assertFalse(CepValidator.isValid("  20040-001  "));
    }

    @Test
    void testCepComMultiplosHifens() {
        assertFalse(CepValidator.isValid("200-40-001"));
    }

    @Test
    void testCepComPontos() {
        assertFalse(CepValidator.isValid("20.040.001"));
    }

    // --- Casos de Borda ---
    @Test
    void testCepComEspacos() {
        assertTrue(CepValidator.isValid(" 20040-001 "));
    }

    @Test
    void testCepComZero() {
        assertTrue(CepValidator.isValid("00000-000"));
    }

    @Test
    void testCepComNove() {
        assertTrue(CepValidator.isValid("99999-999"));
    }

    @Test
    void testCepComSequencia() {
        assertFalse(CepValidator.isValid("12345-678"));
    }

    @Test
    void testCepComRepeticao() {
        assertFalse(CepValidator.isValid("11111-111"));
    }

    // --- Testes de Formatação ---
    @Test
    void testFormatarCep() {
        assertEquals("20040-001", CepValidator.format("20040001"));
    }

    @Test
    void testFormatarCepComHifen() {
        assertEquals("20040-001", CepValidator.format("20040-001"));
    }

    @Test
    void testFormatarCepNulo() {
        assertNull(CepValidator.format(null));
    }

    @Test
    void testFormatarCepInvalido() {
        assertEquals("20040001", CepValidator.format("20040001"));
    }

    // --- Testes de Remoção de Formatação ---
    @Test
    void testRemoverFormatacao() {
        assertEquals("20040001", CepValidator.removeFormatting("20040-001"));
    }

    @Test
    void testRemoverFormatacaoComEspacos() {
        assertEquals("20040001", CepValidator.removeFormatting(" 20040-001 "));
    }

    @Test
    void testRemoverFormatacaoNulo() {
        assertNull(CepValidator.removeFormatting(null));
    }

    // --- Testes de Região ---
    @Test
    void testObterRegiaoSudeste() {
        assertEquals("Sudeste (SP)", CepValidator.getRegion("01001-000"));
    }

    @Test
    void testObterRegiaoSudesteRJ() {
        assertEquals("Sudeste (RJ, ES)", CepValidator.getRegion("20040-001"));
    }

    @Test
    void testObterRegiaoSudesteMG() {
        assertEquals("Sudeste (MG)", CepValidator.getRegion("30130-010"));
    }

    @Test
    void testObterRegiaoNordeste() {
        assertEquals("Nordeste (BA, SE)", CepValidator.getRegion("40010-000"));
    }

    @Test
    void testObterRegiaoNorte() {
        assertEquals("Norte (AM, PA, AP, TO, RR, AC)", CepValidator.getRegion("69900-000"));
    }

    @Test
    void testObterRegiaoCentroOeste() {
        assertEquals("Centro-Oeste (DF, GO, MT, MS)", CepValidator.getRegion("70000-000"));
    }

    @Test
    void testObterRegiaoSul() {
        assertEquals("Sul (PR, SC)", CepValidator.getRegion("80010-000"));
    }

    @Test
    void testObterRegiaoSulRS() {
        assertEquals("Sul (RS)", CepValidator.getRegion("90010-000"));
    }

    @Test
    void testObterRegiaoDesconhecida() {
        assertEquals("Desconhecida", CepValidator.getRegion("50000-000"));
    }

    @Test
    void testObterRegiaoNula() {
        assertNull(CepValidator.getRegion(null));
    }

    // --- Testes de Capital ---
    @Test
    void testCepCapital() {
        assertTrue(CepValidator.isCapital("01001-000"));
    }

    @Test
    void testCepNaoCapital() {
        assertFalse(CepValidator.isCapital("20040-001"));
    }

    @Test
    void testCepCapitalNulo() {
        assertFalse(CepValidator.isCapital(null));
    }

    // --- Testes de Exceção ---
    @Test
    void testValidateCepNuloLancaExcecao() {
        assertThrows(InvalidDocumentException.class, () -> {
            CepValidator.validate(null);
        });
    }

    @Test
    void testValidateCepVazioLancaExcecao() {
        assertThrows(InvalidDocumentException.class, () -> {
            CepValidator.validate("");
        });
    }

    @Test
    void testValidateCepInvalidoLancaExcecao() {
        assertThrows(InvalidDocumentException.class, () -> {
            CepValidator.validate("cep invalido");
        });
    }

    // --- Testes de Geração de CEP ---
    @Test
    void testGerarCepValido() {
        String cep = CepValidator.generateValidCep();
        assertTrue(CepValidator.isValid(cep));
    }

    @Test
    void testGerarCepFormatado() {
        String cep = CepValidator.generateValidCep();
        assertEquals(9, cep.length()); // Formato XXXXX-XXX
    }

    // --- Testes de Região Igual ---
    @Test
    void testMesmaRegiao() {
        assertTrue(CepValidator.isSameRegion("01001-000", "01002-000"));
    }

    @Test
    void testRegioesDiferentes() {
        assertFalse(CepValidator.isSameRegion("01001-000", "20040-001"));
    }

    @Test
    void testMesmaRegiaoNula() {
        assertFalse(CepValidator.isSameRegion(null, "01001-000"));
    }
}