package br.ufjf.ead.validator.chatgpt;

import br.ufjf.ead.exception.InvalidDocumentException;
import br.ufjf.ead.validator.EmailValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes gerados por ChatGPT para EmailValidator.
 * 
 * Demonstração da abordagem LLM (Large Language Models) para geração de testes.
 * 
 * @author ChatGPT
 */
public class EmailValidator_ChatGPTTest {

    // --- Testes de Validação Básica ---
    @Test
    void testEmailValidoBasico() {
        assertTrue(EmailValidator.isValid("usuario@dominio.com"));
    }

    @Test
    void testEmailValidoComPonto() {
        assertTrue(EmailValidator.isValid("usuario.nome@dominio.com"));
    }

    @Test
    void testEmailValidoComHifen() {
        assertTrue(EmailValidator.isValid("usuario-nome@dominio.com"));
    }

    @Test
    void testEmailValidoComSubdominio() {
        assertTrue(EmailValidator.isValid("usuario@sub.dominio.com"));
    }

    @Test
    void testEmailValidoComNumeros() {
        assertTrue(EmailValidator.isValid("usuario123@dominio456.com"));
    }

    // --- Testes de Validação com Caracteres Especiais ---
    @Test
    void testEmailValidoComUnderline() {
        assertTrue(EmailValidator.isValid("usuario_nome@dominio.com"));
    }

    @Test
    void testEmailValidoComMais() {
        assertTrue(EmailValidator.isValid("usuario+tag@dominio.com"));
    }

    @Test
    void testEmailValidoComPontoNoFinalDoUsuario() {
        assertTrue(EmailValidator.isValid("usuario.@dominio.com"));
    }

    // --- Testes de Validação de TLD ---
    @Test
    void testEmailValidoComTldDuplo() {
        assertTrue(EmailValidator.isValid("usuario@dominio.com.br"));
    }

    @Test
    void testEmailValidoComTldTriplo() {
        assertTrue(EmailValidator.isValid("usuario@dominio.co.uk"));
    }

    // --- Testes de Validação de Casos Inválidos ---
    @Test
    void testEmailInvalidoSemArroba() {
        assertFalse(EmailValidator.isValid("usuario.dominio.com"));
    }

    @Test
    void testEmailInvalidoSemPonto() {
        assertFalse(EmailValidator.isValid("usuario@dominio"));
    }

    @Test
    void testEmailInvalidoSemUsuario() {
        assertFalse(EmailValidator.isValid("@dominio.com"));
    }

    @Test
    void testEmailInvalidoSemDominio() {
        assertFalse(EmailValidator.isValid("usuario@"));
    }

    @Test
    void testEmailInvalidoComArrobaDuplo() {
        assertFalse(EmailValidator.isValid("usuario@@dominio.com"));
    }

    @Test
    void testEmailInvalidoComPontoDuplo() {
        assertFalse(EmailValidator.isValid("usuario..nome@dominio.com"));
    }

    @Test
    void testEmailInvalidoComPontoNoInicio() {
        assertFalse(EmailValidator.isValid(".usuario@dominio.com"));
    }

    @Test
    void testEmailInvalidoComPontoNoFim() {
        assertFalse(EmailValidator.isValid("usuario.@dominio.com"));
    }

    // --- Testes de Validação de Caracteres Inválidos ---
    @Test
    void testEmailInvalidoComEspacos() {
        assertFalse(EmailValidator.isValid("usuario @dominio.com"));
    }

    @Test
    void testEmailInvalidoComCaracteresEspeciais() {
        assertFalse(EmailValidator.isValid("usuario@dominio@com"));
    }

    @Test
    void testEmailInvalidoComCaracteresAcentuados() {
        assertFalse(EmailValidator.isValid("usuário@domínio.com"));
    }

    @Test
    void testEmailInvalidoComCaracteresEspeciaisNoDominio() {
        assertFalse(EmailValidator.isValid("usuario@dominio!@com"));
    }

    // --- Testes de Validação de Tamanho ---
    @Test
    void testEmailInvalidoMuitoCurto() {
        assertFalse(EmailValidator.isValid("a@b.c"));
    }

    @Test
    void testEmailInvalidoMuitoLongo() {
        String emailLongo = "a".replaceAll(".", "a").substring(0, 100) + "@" + "b".replaceAll(".", "b").substring(0, 100) + ".com";
        assertFalse(EmailValidator.isValid(emailLongo));
    }

    // --- Testes de Validação de Exceção ---
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

    @Test
    void testEmailComDominio10Minutos() {
        assertFalse(EmailValidator.isValid("usuario@10minutemail.com"));
    }

    // --- Testes de Casos de Borda ---
    @Test
    void testEmailComEspacosNoInicioEFim() {
        assertTrue(EmailValidator.isValid("  usuario@dominio.com  "));
    }

    @Test
    void testEmailComCaracteresEspeciaisNoDominio() {
        assertFalse(EmailValidator.isValid("usuario@dominio@com"));
    }

    @Test
    void testEmailComTldMuitoCurto() {
        assertFalse(EmailValidator.isValid("usuario@dominio.c"));
    }

    @Test
    void testEmailComTldMuitoLongo() {
        assertFalse(EmailValidator.isValid("usuario@dominio.abcd"));
    }

    // --- Testes de Casos Extremos ---
    @Test
    void testEmailComMuitosSubdominios() {
        assertTrue(EmailValidator.isValid("usuario@sub1.sub2.sub3.dominio.com"));
    }

    @Test
    void testEmailComMuitosCaracteresEspeciais() {
        assertTrue(EmailValidator.isValid("usuario+tag1+tag2@dominio.com"));
    }

    @Test
    void testEmailComNumerosNoDominio() {
        assertTrue(EmailValidator.isValid("usuario@dominio123.com"));
    }
}