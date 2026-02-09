package br.ufjf.ead.validator.copilot;

import br.ufjf.ead.exception.InvalidDocumentException;
import br.ufjf.ead.validator.UrlValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes gerados por GitHub Copilot para UrlValidator.
 * 
 * Demonstração da abordagem LLM (Large Language Models) para geração de testes.
 * 
 * @author GitHub Copilot
 */
public class UrlValidator_CopilotTest {

    // --- Casos Válidos ---
    @Test
    void testUrlValidaHttp() {
        assertTrue(UrlValidator.isValid("http://www.exemplo.com"));
    }

    @Test
    void testUrlValidaHttps() {
        assertTrue(UrlValidator.isValid("https://www.exemplo.com"));
    }

    @Test
    void testUrlValidaComPath() {
        assertTrue(UrlValidator.isValid("https://www.exemplo.com/caminho"));
    }

    @Test
    void testUrlValidaComPorta() {
        assertTrue(UrlValidator.isValid("https://www.exemplo.com:8080"));
    }

    @Test
    void testUrlValidaComQuery() {
        assertTrue(UrlValidator.isValid("https://www.exemplo.com?param=valor"));
    }

    @Test
    void testUrlValidaComFragmento() {
        assertTrue(UrlValidator.isValid("https://www.exemplo.com#secao"));
    }

    @Test
    void testUrlValidaSubdominio() {
        assertTrue(UrlValidator.isValid("https://subdominio.exemplo.com"));
    }

    @Test
    void testUrlValidaTldBrasileiro() {
        assertTrue(UrlValidator.isValid("https://www.exemplo.com.br"));
    }

    @Test
    void testUrlValidaTldInternacional() {
        assertTrue(UrlValidator.isValid("https://www.exemplo.co.uk"));
    }

    // --- Casos Inválidos ---
    @Test
    void testUrlNula() {
        assertFalse(UrlValidator.isValid(null));
    }

    @Test
    void testUrlVazia() {
        assertFalse(UrlValidator.isValid(""));
    }

    @Test
    void testUrlSemProtocolo() {
        assertFalse(UrlValidator.isValid("www.exemplo.com"));
    }

    @Test
    void testUrlSemDominio() {
        assertFalse(UrlValidator.isValid("https://"));
    }

    @Test
    void testUrlSemPonto() {
        assertFalse(UrlValidator.isValid("https://exemplo"));
    }

    @Test
    void testUrlComEspacos() {
        assertFalse(UrlValidator.isValid("https://www.exemplo .com"));
    }

    @Test
    void testUrlComCaracteresInvalidos() {
        assertFalse(UrlValidator.isValid("https://www.exemplo.com/ação"));
    }

    @Test
    void testUrlProtocoloInvalido() {
        assertFalse(UrlValidator.isValid("ftp://www.exemplo.com"));
    }

    @Test
    void testUrlTldInvalido() {
        assertFalse(UrlValidator.isValid("https://www.exemplo.invalido"));
    }

    @Test
    void testUrlMuitoLonga() {
        String urlLonga = "https://www." + "a".replaceAll(".", "a").substring(0, 100) + ".com";
        assertFalse(UrlValidator.isValid(urlLonga));
    }

    // --- Casos de Borda ---
    @Test
    void testUrlComEspacosNoInicioEFim() {
        assertTrue(UrlValidator.isValid("  https://www.exemplo.com  "));
    }

    @Test
    void testUrlComMultiplosPaths() {
        assertTrue(UrlValidator.isValid("https://www.exemplo.com/caminho1/caminho2"));
    }

    @Test
    void testUrlComQueryComplexa() {
        assertTrue(UrlValidator.isValid("https://www.exemplo.com?param1=valor1&param2=valor2"));
    }

    @Test
    void testUrlComPortaPadrao() {
        assertTrue(UrlValidator.isValid("https://www.exemplo.com:443"));
    }

    @Test
    void testUrlComPortaInvalida() {
        assertFalse(UrlValidator.isValid("https://www.exemplo.com:99999"));
    }

    // --- Testes de Protocolo ---
    @Test
    void testObterProtocolo() {
        assertEquals("https", UrlValidator.getProtocol("https://www.exemplo.com"));
    }

    @Test
    void testObterProtocoloHttp() {
        assertEquals("http", UrlValidator.getProtocol("http://www.exemplo.com"));
    }

    @Test
    void testObterProtocoloNulo() {
        assertNull(UrlValidator.getProtocol(null));
    }

    @Test
    void testObterProtocoloInvalido() {
        assertNull(UrlValidator.getProtocol("www.exemplo.com"));
    }

    // --- Testes de Domínio ---
    @Test
    void testObterDominio() {
        assertEquals("www.exemplo.com", UrlValidator.getDomain("https://www.exemplo.com"));
    }

    @Test
    void testObterDominioComPath() {
        assertEquals("www.exemplo.com", UrlValidator.getDomain("https://www.exemplo.com/caminho"));
    }

    @Test
    void testObterDominioNulo() {
        assertNull(UrlValidator.getDomain(null));
    }

    @Test
    void testObterDominioInvalido() {
        assertNull(UrlValidator.getDomain("https://"));
    }

    // --- Testes de Path ---
    @Test
    void testObterPath() {
        assertEquals("/caminho", UrlValidator.getPath("https://www.exemplo.com/caminho"));
    }

    @Test
    void testObterPathRaiz() {
        assertEquals("/", UrlValidator.getPath("https://www.exemplo.com"));
    }

    @Test
    void testObterPathNulo() {
        assertNull(UrlValidator.getPath(null));
    }

    @Test
    void testObterPathInvalido() {
        assertNull(UrlValidator.getPath("https://"));
    }

    // --- Testes de Formatação ---
    @Test
    void testFormatarUrl() {
        assertEquals("https://www.exemplo.com", UrlValidator.format("https://www.exemplo.com"));
    }

    @Test
    void testFormatarUrlSemProtocolo() {
        assertEquals("http://www.exemplo.com", UrlValidator.format("www.exemplo.com"));
    }

    @Test
    void testFormatarUrlNula() {
        assertNull(UrlValidator.format(null));
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

    @Test
    void testUrlSeguraNula() {
        assertFalse(UrlValidator.isSecure(null));
    }

    // --- Testes de Exceção ---
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

    // --- Testes de Geração de URL ---
    @Test
    void testGerarUrlValida() {
        String url = UrlValidator.generateRandomUrl(true);
        assertTrue(UrlValidator.isValid(url));
    }

    @Test
    void testGerarUrlSemPath() {
        String url = UrlValidator.generateRandomUrl(false);
        assertTrue(UrlValidator.isValid(url));
    }

    @Test
    void testGerarUrlHttps() {
        String url = UrlValidator.generateRandomUrl(true);
        assertTrue(url.startsWith("https://"));
    }
}