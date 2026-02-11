package br.ufjf.ead.validator.chatgpt;

import br.ufjf.ead.exception.InvalidDocumentException;
import br.ufjf.ead.validator.CepValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes gerados por ChatGPT para CepValidator.
 * 
 * Demonstração da abordagem LLM (Large Language Models) para geração de testes.
 * 
 * @author ChatGPT
 */
public class CepValidator_ChatGPTTest {

    // --- Testes de Validação Básica ---
    @Test
    void testCepValidoBasico() {
        assertTrue(CepValidator.isValid("01001-000"));
    }

    @Test
    void testCepValidoFormatoSimples() {
        assertTrue(CepValidator.isValid("01001000"));
    }

    @Test
    void testCepValidoFormatoComEspacos() {
        assertTrue(CepValidator.isValid("01001 000"));
    }

    // --- Testes de Validação de Formato ---
    @Test
    void testCepValidoFormatoComHifen() {
        assertTrue(CepValidator.isValid("01001-000"));
    }

    @Test
    void testCepInvalidoFormatoSemDigitos() {
        assertFalse(CepValidator.isValid("01001-00"));
    }

    @Test
    void testCepInvalidoFormatoComLetras() {
        assertFalse(CepValidator.isValid("01001-00A"));
    }

    // --- Testes de Validação de Região ---
    @Test
    void testCepValidoRegiaoSudeste() {
        assertTrue(CepValidator.isValid("01001-000"));
    }

    @Test
    void testCepValidoRegiaoSul() {
        assertTrue(CepValidator.isValid("80010-000"));
    }

    @Test
    void testCepValidoRegiaoNordeste() {
        assertTrue(CepValidator.isValid("40010-000"));
    }

    @Test
    void testCepValidoRegiaoNorte() {
        assertTrue(CepValidator.isValid("60010-000"));
    }

    @Test
    void testCepValidoRegiaoCentroOeste() {
        assertTrue(CepValidator.isValid("70010-000"));
    }

    // --- Testes de Validação de Exceção ---
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

    // --- Testes de Formatação ---
    @Test
    void testFormatarCep() {
        assertEquals("01001-000", CepValidator.format("01001000"));
    }

    @Test
    void testFormatarCepNulo() {
        assertNull(CepValidator.format(null));
    }

    // --- Testes de Identificação de Região ---
    @Test
    void testIdentificarRegiaoSudeste() {
        assertEquals("Sudeste", CepValidator.getRegion("01001-000"));
    }

    @Test
    void testIdentificarRegiaoSul() {
        assertEquals("Sul", CepValidator.getRegion("80010-000"));
    }

    @Test
    void testIdentificarRegiaoNordeste() {
        assertEquals("Nordeste", CepValidator.getRegion("40010-000"));
    }

    @Test
    void testIdentificarRegiaoNorte() {
        assertEquals("Norte", CepValidator.getRegion("60010-000"));
    }

    @Test
    void testIdentificarRegiaoCentroOeste() {
        assertEquals("Centro-Oeste", CepValidator.getRegion("70010-000"));
    }

    @Test
    void testIdentificarRegiaoDesconhecida() {
        assertEquals("Desconhecida", CepValidator.getRegion("90010-000"));
    }
}