/**
 * Testes gerados automaticamente para CnpjValidator usando EvoSuite.
 *
 * @author Fabio Oliveira
 */

package br.ufjf.ead.validator;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import br.ufjf.ead.validator.CnpjValidator;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class CnpjValidator_ESTest extends CnpjValidator_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      try { 
        CnpjValidator.validate("8888888888884D8");
        fail("Expecting exception: Exception");
      
      } catch(Exception e) {
         //
         // CNPJ inv\u00E1lido: d\u00EDgitos verificadores incorretos
         //
         verifyException("br.ufjf.ead.validator.CnpjValidator", e);
      }
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      try { 
        CnpjValidator.validate("333335333333333");
        fail("Expecting exception: Exception");
      
      } catch(Exception e) {
         //
         // CNPJ deve conter 14 d\u00EDgitos
         //
         verifyException("br.ufjf.ead.validator.CnpjValidator", e);
      }
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      CnpjValidator.validate("90123456789002");
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      try { 
        CnpjValidator.validate("99999999999999");
        fail("Expecting exception: Exception");
      
      } catch(Exception e) {
         //
         // CNPJ inv\u00E1lido: todos os d\u00EDgitos s\u00E3o iguais
         //
         verifyException("br.ufjf.ead.validator.CnpjValidator", e);
      }
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      try { 
        CnpjValidator.validate("@W?=vta`Yht)* m}");
        fail("Expecting exception: Exception");
      
      } catch(Exception e) {
         //
         // CNPJ deve conter 14 d\u00EDgitos
         //
         verifyException("br.ufjf.ead.validator.CnpjValidator", e);
      }
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      try { 
        CnpjValidator.validate("");
        fail("Expecting exception: Exception");
      
      } catch(Exception e) {
         //
         // CNPJ n\u00E3o pode ser nulo ou vazio
         //
         verifyException("br.ufjf.ead.validator.CnpjValidator", e);
      }
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      try { 
        CnpjValidator.validate((String) null);
        fail("Expecting exception: Exception");
      
      } catch(Exception e) {
         //
         // CNPJ n\u00E3o pode ser nulo ou vazio
         //
         verifyException("br.ufjf.ead.validator.CnpjValidator", e);
      }
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      String string0 = CnpjValidator.generateValidCnpj();
      assertNotNull(string0);
      
      String string1 = CnpjValidator.generateValidCnpj();
      assertEquals("23.456.789/0123-63", string1);
      
      String string2 = CnpjValidator.generateValidCnpj();
      assertEquals("45.678.901/2345-91", string2);
      
      String string3 = CnpjValidator.generateValidCnpj();
      assertNotNull(string3);
      assertEquals("67.890.123/4567-80", string3);
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      try { 
        CnpjValidator.validate("88888858888888");
        fail("Expecting exception: Exception");
      
      } catch(Exception e) {
         //
         // CNPJ inv\u00E1lido: d\u00EDgitos verificadores incorretos
         //
         verifyException("br.ufjf.ead.validator.CnpjValidator", e);
      }
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      try { 
        CnpjValidator.validate("77777777777770");
        fail("Expecting exception: Exception");
      
      } catch(Exception e) {
         //
         // CNPJ inv\u00E1lido: d\u00EDgitos verificadores incorretos
         //
         verifyException("br.ufjf.ead.validator.CnpjValidator", e);
      }
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      boolean boolean0 = CnpjValidator.isValid("99999999999999");
      assertFalse(boolean0);
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      boolean boolean0 = CnpjValidator.isValid("01.234.567/8901-07");
      assertTrue(boolean0);
  }

  @Test(timeout = 4000)
  public void test12()  throws Throwable  {
      CnpjValidator cnpjValidator0 = new CnpjValidator();
  }
}
