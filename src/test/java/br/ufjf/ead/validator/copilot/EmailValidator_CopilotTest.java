package br.ufjf.ead.validator.copilot;

import br.ufjf.ead.exception.InvalidDocumentException;
import br.ufjf.ead.validator.EmailValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes gerados por GitHub Copilot para EmailValidator.
 * 
 * Demonstração da abordagem LLM (Large Language Models) para geração de testes.
 * 
 * @author GitHub Copilot
 */
public class EmailValidator_CopilotTest {

    // --- Casos Válidos ---
    @Test
    void testEmailValidoSemFormatacao() {
        assertTrue(EmailValidator.isValid("usuario@dominio.com"));
    }

    @Test
    void testEmailValidoComFormatoCompleto() {
        assertTrue(EmailValidator.isValid("usuario.nome@dominio.com.br"));
    }

    @Test
    void testEmailValidoComCaracteresEspeciais() {
        assertTrue(EmailValidator.isValid("usuario+tag@dominio.com"));
    }

    @Test
    void testEmailValidoComSubdominio() {
        assertTrue(EmailValidator.isValid("usuario@sub.dominio.com"));
    }

    @Test
    void testEmailValidoComNumeros() {
        assertTrue(EmailValidator.isValid("usuario123@dominio456.com"));
    }

    // --- Casos Inválidos ---
    @Test
    void testEmailNulo() {
        assertFalse(EmailValidator.isValid(null));
    }

    @Test
    void testEmailVazio() {
        assertFalse(EmailValidator.isValid(""));
    }

    @Test
    void testEmailSemArroba() {
        assertFalse(EmailValidator.isValid("usuario.dominio.com"));
    }

    @Test
    void testEmailSemPonto() {
        assertFalse(EmailValidator.isValid("usuario@dominio"));
    }

    @Test
    void testEmailSemUsuario() {
        assertFalse(EmailValidator.isValid("@dominio.com"));
    }

    @Test
    void testEmailSemDominio() {
        assertFalse(EmailValidator.isValid("usuario@"));
    }

    @Test
    void testEmailComEspacos() {
        assertFalse(EmailValidator.isValid("usuario @dominio.com"));
    }

    @Test
    void testEmailComMultiplosArrobas() {
        assertFalse(EmailValidator.isValid("usuario@@dominio.com"));
    }

    @Test
    void testEmailComPontoNoInicio() {
        assertFalse(EmailValidator.isValid(".usuario@dominio.com"));
    }

    @Test
    void testEmailComPontoNoFim() {
        assertFalse(EmailValidator.isValid("usuario.@dominio.com"));
    }

    @Test
    void testEmailComPontoDuplo() {
        assertFalse(EmailValidator.isValid("usuario..nome@dominio.com"));
    }

    @Test
    void testEmailMuitoCurto() {
        assertFalse(EmailValidator.isValid("a@b.c"));
    }

    @Test
    void testEmailMuitoLongo() {
        String emailLongo = "a".replaceAll(".", "a").substring(0, 100) + "@" + "b".replaceAll(".", "b").substring(0, 100) + ".com";
        assertFalse(EmailValidator.isValid(emailLongo));
    }

    // --- Casos de Borda ---
    @Test
    void testEmailComEspacosNoInicioEFim() {
        assertTrue(EmailValidator.isValid("  usuario@dominio.com  "));
    }

    @Test
    void testEmailComCaracteresEspeciaisNoDominio() {
        assertFalse(EmailValidator.isValid("usuario@dominio@com"));
    }

    @Test
    void testEmailComCaracteresInvalidos() {
        assertFalse(EmailValidator.isValid("usuário@domínio.com"));
    }

    @Test
    void testEmailComTldMuitoCurto() {
        assertFalse(EmailValidator.isValid("usuario@dominio.c"));
    }

    @Test
    void testEmailComTldMuitoLongo() {
        assertFalse(EmailValidator.isValid("usuario@dominio.abcd"));
    }

    // --- Testes de Exceção ---
    @Test
    void testValidateEmailNuloLancaExcecao() {
        assertThrows(InvalidDocumentException.class, () -> {
            EmailValidator.validate(null);
        });
    }

    @Test
    void testValidateEmailVazioLancaExcecao() {
        assertThrows(InvalidDocumentException.class, () -> {
            EmailValidator.validate("");
        });
    }

    @Test
    void testValidateEmailInvalidoLancaExcecao() {
        assertThrows(InvalidDocumentException.class, () -> {
            EmailValidator.validate("email.invalido");
        });
    }

    // --- Testes de Formatação ---
    @Test
    void testFormatEmail() {
        assertEquals("usuario@dominio.com", EmailValidator.format("USUARIO@DOMINIO.COM"));
    }

    @Test
    void testFormatEmailComEspacos() {
        assertEquals("usuario@dominio.com", EmailValidator.format("  USUARIO@DOMINIO.COM  "));
    }

    @Test
    void testFormatEmailNulo() {
        assertNull(EmailValidator.format(null));
    }

    // --- Testes de Domínios Conhecidos como Inválidos ---
    @Test
    void testEmailComDominioTemporario() {
        assertFalse(EmailValidator.isValid("usuario@tempmail.org"));
    }

    @Test
    void testEmailComDominioGuerrilla() {
        assertFalse(EmailValidator.isValid("usuario@guerrillamail.com"));
    }
}