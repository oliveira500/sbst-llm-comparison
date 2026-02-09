package br.ufjf.ead.validator.copilot;

import br.ufjf.ead.exception.InvalidDocumentException;
import br.ufjf.ead.validator.PasswordValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes gerados por GitHub Copilot para PasswordValidator.
 * 
 * Demonstração da abordagem LLM (Large Language Models) para geração de testes.
 * 
 * @author GitHub Copilot
 */
public class PasswordValidator_CopilotTest {

    // --- Casos Válidos ---
    @Test
    void testPasswordValidoMinimo() {
        assertTrue(PasswordValidator.isValid("Abc123!"));
    }

    @Test
    void testPasswordValidoMaximo() {
        assertTrue(PasswordValidator.isValid("Abc123!@#$%^&*()_+-=[]{}|;:,.<>?"));
    }

    @Test
    void testPasswordValidoComCaracteresEspeciais() {
        assertTrue(PasswordValidator.isValid("Password123!@#"));
    }

    @Test
    void testPasswordValidoComLetrasMaiusculasMinusculas() {
        assertTrue(PasswordValidator.isValid("AbCdEfGh123!"));
    }

    @Test
    void testPasswordValidoComNumeros() {
        assertTrue(PasswordValidator.isValid("Password123!"));
    }

    // --- Casos Inválidos ---
    @Test
    void testPasswordNulo() {
        assertFalse(PasswordValidator.isValid(null));
    }

    @Test
    void testPasswordVazio() {
        assertFalse(PasswordValidator.isValid(""));
    }

    @Test
    void testPasswordMuitoCurto() {
        assertFalse(PasswordValidator.isValid("Ab1!"));
    }

    @Test
    void testPasswordSemLetraMaiuscula() {
        assertFalse(PasswordValidator.isValid("abc123!"));
    }

    @Test
    void testPasswordSemLetraMinuscula() {
        assertFalse(PasswordValidator.isValid("ABC123!"));
    }

    @Test
    void testPasswordSemNumero() {
        assertFalse(PasswordValidator.isValid("Abcdef!"));
    }

    @Test
    void testPasswordSemCaractereEspecial() {
        assertFalse(PasswordValidator.isValid("Abcdef123"));
    }

    @Test
    void testPasswordComum() {
        assertFalse(PasswordValidator.isValid("password"));
    }

    @Test
    void testPasswordComumComNumero() {
        assertFalse(PasswordValidator.isValid("password123"));
    }

    @Test
    void testPasswordComumMaiusculo() {
        assertFalse(PasswordValidator.isValid("Password1"));
    }

    @Test
    void testPasswordComumAdmin() {
        assertFalse(PasswordValidator.isValid("admin"));
    }

    @Test
    void testPasswordComumQwerty() {
        assertFalse(PasswordValidator.isValid("qwerty"));
    }

    @Test
    void testPasswordComumWelcome() {
        assertFalse(PasswordValidator.isValid("welcome"));
    }

    // --- Casos de Borda ---
    @Test
    void testPasswordComEspacos() {
        assertFalse(PasswordValidator.isValid("Abc 123!"));
    }

    @Test
    void testPasswordComCaracteresInvalidos() {
        assertFalse(PasswordValidator.isValid("Abc123!á"));
    }

    @Test
    void testPasswordComRepeticoes() {
        assertFalse(PasswordValidator.isValid("Abc123!AAA"));
    }

    @Test
    void testPasswordComSequencia() {
        assertFalse(PasswordValidator.isValid("Abc123!abcdef"));
    }

    @Test
    void testPasswordComSequenciaNumerica() {
        assertFalse(PasswordValidator.isValid("Abc123!123456"));
    }

    @Test
    void testPasswordComSequenciaTeclado() {
        assertFalse(PasswordValidator.isValid("Abc123!qwerty"));
    }

    // --- Testes de Exceção ---
    @Test
    void testValidatePasswordNuloLancaExcecao() {
        assertThrows(InvalidDocumentException.class, () -> {
            PasswordValidator.validate(null);
        });
    }

    @Test
    void testValidatePasswordVazioLancaExcecao() {
        assertThrows(InvalidDocumentException.class, () -> {
            PasswordValidator.validate("");
        });
    }

    @Test
    void testValidatePasswordInvalidoLancaExcecao() {
        assertThrows(InvalidDocumentException.class, () -> {
            PasswordValidator.validate("senhafraca");
        });
    }

    // --- Testes de Força da Senha ---
    @Test
    void testAvaliarForcaSenhaMuitoFraca() {
        assertEquals(0, PasswordValidator.evaluateStrength(null));
        assertEquals(0, PasswordValidator.evaluateStrength(""));
    }

    @Test
    void testAvaliarForcaSenhaFraca() {
        assertEquals(1, PasswordValidator.evaluateStrength("Abc123!"));
    }

    @Test
    void testAvaliarForcaSenhaMedia() {
        assertEquals(3, PasswordValidator.evaluateStrength("Password123!"));
    }

    @Test
    void testAvaliarForcaSenhaForte() {
        assertEquals(4, PasswordValidator.evaluateStrength("Password123!@#"));
    }

    @Test
    void testAvaliarForcaSenhaMuitoForte() {
        assertEquals(5, PasswordValidator.evaluateStrength("Password123!@#$%"));
    }

    // --- Testes de Geração de Senha ---
    @Test
    void testGerarSenhaSegura() {
        String senha = PasswordValidator.generateSecurePassword(12);
        assertTrue(PasswordValidator.isValid(senha));
        assertEquals(12, senha.length());
    }

    @Test
    void testGerarSenhaMinima() {
        String senha = PasswordValidator.generateSecurePassword(8);
        assertTrue(PasswordValidator.isValid(senha));
        assertEquals(8, senha.length());
    }

    @Test
    void testGerarSenhaMaxima() {
        String senha = PasswordValidator.generateSecurePassword(128);
        assertTrue(PasswordValidator.isValid(senha));
        assertEquals(128, senha.length());
    }

    @Test
    void testGerarSenhaInvalida() {
        String senha = PasswordValidator.generateSecurePassword(5);
        assertTrue(PasswordValidator.isValid(senha));
        assertEquals(8, senha.length()); // Deve usar o mínimo
    }
}