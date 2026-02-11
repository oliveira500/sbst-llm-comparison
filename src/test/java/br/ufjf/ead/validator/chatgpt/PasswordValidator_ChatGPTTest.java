package br.ufjf.ead.validator.chatgpt;

import br.ufjf.ead.exception.InvalidDocumentException;
import br.ufjf.ead.validator.PasswordValidator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes gerados por ChatGPT para PasswordValidator.
 * 
 * Demonstração da abordagem LLM (Large Language Models) para geração de testes.
 * 
 * @author ChatGPT
 */
public class PasswordValidator_ChatGPTTest {

    // --- Testes de Validação Básica ---
    @Test
    void testSenhaValidaBasica() {
        assertTrue(PasswordValidator.isValid("senha123"));
    }

    @Test
    void testSenhaValidaComMaiuscula() {
        assertTrue(PasswordValidator.isValid("Senha123"));
    }

    @Test
    void testSenhaValidaComCaractereEspecial() {
        assertTrue(PasswordValidator.isValid("Senha@123"));
    }

    @Test
    void testSenhaValidaComNumero() {
        assertTrue(PasswordValidator.isValid("Senha123"));
    }

    // --- Testes de Validação de Tamanho ---
    @Test
    void testSenhaValidaTamanhoMinimo() {
        assertTrue(PasswordValidator.isValid("Abc123@"));
    }

    @Test
    void testSenhaValidaTamanhoMaximo() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append("A");
        }
        sb.append("123@");
        String senhaLonga = sb.toString();
        assertTrue(PasswordValidator.isValid(senhaLonga));
    }

    @Test
    void testSenhaInvalidaMuitoCurta() {
        assertFalse(PasswordValidator.isValid("Ab1@"));
    }

    @Test
    void testSenhaInvalidaMuitoLonga() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 200; i++) {
            sb.append("A");
        }
        sb.append("123@");
        String senhaMuitoLonga = sb.toString();
        assertFalse(PasswordValidator.isValid(senhaMuitoLonga));
    }

    // --- Testes de Validação de Força ---
    @Test
    void testSenhaFraca() {
        assertEquals(1, PasswordValidator.evaluateStrength("senha"));
    }

    @Test
    void testSenhaMedia() {
        assertEquals(2, PasswordValidator.evaluateStrength("Senha123"));
    }

    @Test
    void testSenhaForte() {
        assertEquals(3, PasswordValidator.evaluateStrength("Senha@123"));
    }

    @Test
    void testSenhaMuitoForte() {
        assertEquals(4, PasswordValidator.evaluateStrength("Senha@123!A"));
    }

    // --- Testes de Validação de Caracteres ---
    @Test
    void testSenhaInvalidaSemMaiuscula() {
        assertFalse(PasswordValidator.isValid("senha123@"));
    }

    @Test
    void testSenhaInvalidaSemMinuscula() {
        assertFalse(PasswordValidator.isValid("SENHA123@"));
    }

    @Test
    void testSenhaInvalidaSemNumero() {
        assertFalse(PasswordValidator.isValid("Senha@"));
    }

    @Test
    void testSenhaInvalidaSemCaractereEspecial() {
        assertFalse(PasswordValidator.isValid("Senha123"));
    }

    // --- Testes de Validação de Sequências ---
    @Test
    void testSenhaInvalidaComSequenciaNumerica() {
        assertFalse(PasswordValidator.isValid("Senha123@"));
    }

    @Test
    void testSenhaInvalidaComSequenciaAlfabetica() {
        assertFalse(PasswordValidator.isValid("Senhaabc@1"));
    }

    @Test
    void testSenhaValidaSemSequencias() {
        assertTrue(PasswordValidator.isValid("Senha@9kL2"));
    }

    // --- Testes de Validação de Repetições ---
    @Test
    void testSenhaInvalidaComRepeticao() {
        assertFalse(PasswordValidator.isValid("Senha@111"));
    }

    @Test
    void testSenhaValidaSemRepeticao() {
        assertTrue(PasswordValidator.isValid("Senha@1kL2"));
    }

    // --- Testes de Validação de Exceção ---
    @Test
    void testValidateSenhaNulaLancaExcecao() {
        assertThrows(InvalidDocumentException.class, () -> {
            PasswordValidator.validate(null);
        });
    }

    @Test
    void testValidateSenhaVaziaLancaExcecao() {
        assertThrows(InvalidDocumentException.class, () -> {
            PasswordValidator.validate("");
        });
    }

    @Test
    void testValidateSenhaInvalidaLancaExcecao() {
        assertThrows(InvalidDocumentException.class, () -> {
            PasswordValidator.validate("senha");
        });
    }

    // --- Testes de Geração de Senhas ---
    @Test
    void testGerarSenhaSegura() {
        String senha = PasswordValidator.generateSecurePassword(12);
        assertTrue(PasswordValidator.isValid(senha));
        assertEquals(12, senha.length());
    }

    @Test
    void testGerarSenhaTamanhoMinimo() {
        String senha = PasswordValidator.generateSecurePassword(5);
        assertTrue(PasswordValidator.isValid(senha));
        assertEquals(8, senha.length()); // tamanho mínimo
    }

    @Test
    void testGerarSenhaTamanhoMaximo() {
        String senha = PasswordValidator.generateSecurePassword(200);
        assertTrue(PasswordValidator.isValid(senha));
        assertEquals(128, senha.length()); // tamanho máximo
    }

    // --- Testes de Casos Extremos ---
    @Test
    void testSenhaComEspacos() {
        assertTrue(PasswordValidator.isValid("Senha @123"));
    }

    @Test
    void testSenhaComCaracteresEspeciaisMultiplos() {
        assertTrue(PasswordValidator.isValid("Senha@#$%123"));
    }

    @Test
    void testSenhaComNumerosMultiplos() {
        assertTrue(PasswordValidator.isValid("Senha123456"));
    }

    @Test
    void testSenhaComLetrasMultiplas() {
        assertTrue(PasswordValidator.isValid("SenhaABCdef"));
    }

    // --- Testes de Validação de Caracteres Específicos ---
    @Test
    void testSenhaComCaracteresEspeciaisValidos() {
        assertTrue(PasswordValidator.isValid("Senha!@#$%123"));
    }

    @Test
    void testSenhaComCaracteresEspeciaisInvalidos() {
        assertFalse(PasswordValidator.isValid("Senha<>?123"));
    }

    @Test
    void testSenhaComCaracteresUnicode() {
        assertFalse(PasswordValidator.isValid("Senhañ@123")); // caracteres não ASCII não são permitidos
    }

    // --- Testes de Senhas Comuns ---
    @Test
    void testSenhaComumNaoPermitida() {
        assertFalse(PasswordValidator.isValid("password"));
    }

    @Test
    void testSenhaComumNaoPermitidaCaseInsensitive() {
        assertFalse(PasswordValidator.isValid("PASSWORD"));
    }

    // --- Testes de Força de Senha ---
    @Test
    void testForcaSenhaMuitoFraca() {
        assertEquals(0, PasswordValidator.evaluateStrength(""));
    }

    @Test
    void testForcaSenhaFraca() {
        assertEquals(1, PasswordValidator.evaluateStrength("senha"));
    }

    @Test
    void testForcaSenhaMedia() {
        assertEquals(2, PasswordValidator.evaluateStrength("Senha123"));
    }

    @Test
    void testForcaSenhaForte() {
        assertEquals(3, PasswordValidator.evaluateStrength("Senha@123"));
    }

    @Test
    void testForcaSenhaMuitoForte() {
        assertEquals(4, PasswordValidator.evaluateStrength("Senha@123!A"));
    }

    @Test
    void testForcaSenhaMaxima() {
        assertEquals(5, PasswordValidator.evaluateStrength("Senha@123!A123456"));
    }
} 
