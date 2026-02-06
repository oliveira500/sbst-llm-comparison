package br.ufjf.ead.validator.llm;

import br.ufjf.ead.validator.DocumentFormatter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes exaustivos para DocumentFormatter.
 *
 * @author Fabio Oliveira
 */
public class DocumentFormatter_LLMTest {

    // --- Casos de Borda ---
    // Testa formatação, remoção de caracteres especiais e tratamento de null/vazio

    // Testa formatação de CPF válido
    @Test
    void testFormatarCpfValido() {
        String cpf = "12345678909";
        String esperado = "123.456.789-09";
        assertEquals(esperado, DocumentFormatter.formatCpf(cpf), "Deve formatar CPF corretamente");
    }

    // Testa formatação de CPF já formatado
    @Test
    void testFormatarCpfJaFormatado() {
        String cpf = "123.456.789-09";
        assertEquals(cpf, DocumentFormatter.formatCpf(cpf), "CPF já formatado deve permanecer igual");
    }

    // Testa formatação de CNPJ válido
    @Test
    void testFormatarCnpjValido() {
        String cnpj = "12345678000195";
        String esperado = "12.345.678/0001-95";
        assertEquals(esperado, DocumentFormatter.formatCnpj(cnpj), "Deve formatar CNPJ corretamente");
    }

    // Testa formatação de CNPJ já formatado
    @Test
    void testFormatarCnpjJaFormatado() {
        String cnpj = "12.345.678/0001-95";
        assertEquals(cnpj, DocumentFormatter.formatCnpj(cnpj), "CNPJ já formatado deve permanecer igual");
    }

    // Testa remoção de caracteres especiais do CPF
    @Test
    void testRemoverCaracteresEspeciaisCpf() {
        String cpf = "123@456#789-09";
        String esperado = "12345678909";
        assertEquals(esperado, DocumentFormatter.unformatCpf(cpf), "Deve remover caracteres especiais do CPF");
    }

    // Testa remoção de caracteres especiais do CNPJ
    @Test
    void testRemoverCaracteresEspeciaisCnpj() {
        String cnpj = "12.345.678/0001-95";
        String esperado = "12345678000195";
        assertEquals(esperado, DocumentFormatter.unformatCnpj(cnpj), "Deve remover caracteres especiais do CNPJ");
    }

    // Testa tratamento de null para CPF
    @Test
    void testFormatarCpfNull() {
        assertNull(DocumentFormatter.formatCpf(null), "Deve retornar null para CPF nulo");
        assertNull(DocumentFormatter.unformatCpf(null), "Deve retornar null para CPF nulo");
    }

    // Testa tratamento de vazio para CPF
    @Test
    void testFormatarCpfVazio() {
        assertEquals("", DocumentFormatter.formatCpf(""), "Deve retornar vazio para CPF vazio");
        assertEquals("", DocumentFormatter.unformatCpf(""), "Deve retornar vazio para CPF vazio");
    }

    // Testa tratamento de null para CNPJ
    @Test
    void testFormatarCnpjNull() {
        assertNull(DocumentFormatter.formatCnpj(null), "Deve retornar null para CNPJ nulo");
        assertNull(DocumentFormatter.unformatCnpj(null), "Deve retornar null para CNPJ nulo");
    }

    // Testa tratamento de vazio para CNPJ
    @Test
    void testFormatarCnpjVazio() {
        assertEquals("", DocumentFormatter.formatCnpj(""), "Deve retornar vazio para CNPJ vazio");
        assertEquals("", DocumentFormatter.unformatCnpj(""), "Deve retornar vazio para CNPJ vazio");
    }

    // Testa string muito longa para CPF
    @Test
    void testFormatarCpfMuitoLongo() {
        String cpf = "1234567890912345678909";
        String esperado = "1234567890912345678909";
        assertEquals(esperado, DocumentFormatter.formatCpf(cpf), "CPF muito longo deve ser retornado como está");
    }

    // Testa string muito longa para CNPJ
    @Test
    void testFormatarCnpjMuitoLongo() {
        String cnpj = "1234567800019512345678000195";
        String esperado = "1234567800019512345678000195";
        assertEquals(esperado, DocumentFormatter.formatCnpj(cnpj), "CNPJ muito longo deve ser retornado como está");
    }
}
