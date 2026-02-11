package br.ufjf.ead.validator.chatgpt;

import br.ufjf.ead.exception.InvalidDocumentException;
import br.ufjf.ead.validator.UrlValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes gerados por ChatGPT para UrlValidator.
 * 
 * Demonstração da abordagem LLM (Large Language Models) para geração de testes.
 * 
 * @author ChatGPT
 */
public class UrlValidator_ChatGPTTest {

    // --- Testes de Validação Básica ---
    @Test
    void testUrlValidaBasica() {
        assertTrue(UrlValidator.isValid("http://www.exemplo.com"));
    }

    @Test
    void testUrlValidaHttps() {
        assertTrue(UrlValidator.isValid("https://www.exemplo.com"));
    }

    @Test
    void testUrlValidaComPath() {
        assertTrue(UrlValidator.isValid("http://www.exemplo.com/caminho"));
    }

    @Test
    void testUrlValidaComPorta() {
        assertTrue(UrlValidator.isValid("http://www.exemplo.com:8080"));
    }

    // --- Testes de Validação de Protocolo ---
    @Test
    void testUrlValidaProtocoloHttp() {
        assertTrue(UrlValidator.isValid("http://www.exemplo.com"));
    }

    @Test
    void testUrlValidaProtocoloHttps() {
        assertTrue(UrlValidator.isValid("https://www.exemplo.com"));
    }

    @Test
    void testUrlInvalidaProtocoloDesconhecido() {
        assertFalse(UrlValidator.isValid("ftp://www.exemplo.com"));
    }

    // --- Testes de Validação de Domínio ---
    @Test
    void testUrlValidaDominioComTLD() {
        assertTrue(UrlValidator.isValid("http://www.exemplo.com"));
    }

    @Test
    void testUrlInvalidaDominioSemTLD() {
        assertFalse(UrlValidator.isValid("http://www.exemplo"));
    }

    @Test
    void testUrlValidaDominioComSubdominio() {
        assertTrue(UrlValidator.isValid("http://subdominio.exemplo.com"));
    }

    // --- Testes de Validação de Path ---
    @Test
    void testUrlValidaPathSimples() {
        assertTrue(UrlValidator.isValid("http://www.exemplo.com/caminho"));
    }

    @Test
    void testUrlValidaPathComplexo() {
        assertTrue(UrlValidator.isValid("http://www.exemplo.com/caminho/arquivo.html"));
    }

    @Test
    void testUrlInvalidaPathInvalido() {
        assertFalse(UrlValidator.isValid("http://www.exemplo.com/caminho/inválido"));
    }

    // --- Testes de Validação de Exceção ---
    @Test
    void testValidateUrlNulaLancaExcecao() {
        assertThrows(InvalidDocumentException.class, () -> {
            UrlValidator.validate(null);
        });
    }

    @Test
    void testValidateUrlVaziaLancaExcecao() {
        assertThrows(InvalidDocumentException.class, () -> {
            UrlValidator.validate("");
        });
    }

    @Test
    void testValidateUrlInvalidaLancaExcecao() {
        assertThrows(InvalidDocumentException.class, () -> {
            UrlValidator.validate("url invalida");
        });
    }

    // --- Testes de Identificação de Componentes ---
    @Test
    void testObterProtocolo() {
        assertEquals("http", UrlValidator.getProtocol("http://www.exemplo.com"));
    }

    @Test
    void testObterDominio() {
        assertEquals("www.exemplo.com", UrlValidator.getDomain("http://www.exemplo.com"));
    }

    @Test
    void testObterPath() {
        assertEquals("/caminho", UrlValidator.getPath("http://www.exemplo.com/caminho"));
    }

    // --- Testes de Segurança ---
    @Test
    void testUrlSegura() {
        assertTrue(UrlValidator.isSecure("https://www.exemplo.com"));
    }

    @Test
    void testUrlInsegura() {
        assertFalse(UrlValidator.isSecure("http://www.exemplo.com"));
    }
}