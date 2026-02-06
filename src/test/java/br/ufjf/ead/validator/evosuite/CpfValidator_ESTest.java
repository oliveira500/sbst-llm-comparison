/**
 * Testes gerados automaticamente para CpfValidator usando EvoSuite.
 *
 * @author Fabio Oliveira
 */

package br.ufjf.ead.validator;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import br.ufjf.ead.validator.CpfValidator;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class CpfValidator_ESTest extends CpfValidator_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test00()  throws Throwable  {
      try { 
        CpfValidator.validate("456.787.654-09");
        fail("Expecting exception: Exception");
      
      } catch(Exception e) {
         //
         // CPF inv\u00E1lido: d\u00EDgitos verificadores incorretos
         //
         verifyException("br.ufjf.ead.validator.CpfValidator", e);
      }
  }

  @Test(timeout = 4000)
  public void test01()  throws Throwable  {
      boolean boolean0 = CpfValidator.isValid("999909999999");
      assertFalse(boolean0);
  }

  @Test(timeout = 4000)
  public void test02()  throws Throwable  {
      CpfValidator.validate("012.345.678-90");
  }

  @Test(timeout = 4000)
  public void test03()  throws Throwable  {
      try { 
        CpfValidator.validate("55555555555");
        fail("Expecting exception: Exception");
      
      } catch(Exception e) {
         //
         // CPF inv\u00E1lido: todos os d\u00EDgitos s\u00E3o iguais
         //
         verifyException("br.ufjf.ead.validator.CpfValidator", e);
      }
  }

  @Test(timeout = 4000)
  public void test04()  throws Throwable  {
      try { 
        CpfValidator.validate("Cxl4Dlf@qcAyv5");
        fail("Expecting exception: Exception");
      
      } catch(Exception e) {
         //
         // CPF deve conter 11 d\u00EDgitos
         //
         verifyException("br.ufjf.ead.validator.CpfValidator", e);
      }
  }

  @Test(timeout = 4000)
  public void test05()  throws Throwable  {
      try { 
        CpfValidator.validate("");
        fail("Expecting exception: Exception");
      
      } catch(Exception e) {
         //
         // CPF n\u00E3o pode ser nulo ou vazio
         //
         verifyException("br.ufjf.ead.validator.CpfValidator", e);
      }
  }

  @Test(timeout = 4000)
  public void test06()  throws Throwable  {
      String string0 = CpfValidator.generateValidCpf();
      assertEquals("012.345.678-90", string0);
      assertNotNull(string0);
  }

  @Test(timeout = 4000)
  public void test07()  throws Throwable  {
      try { 
        CpfValidator.validate("99999996999");
        fail("Expecting exception: Exception");
      
      } catch(Exception e) {
         //
         // CPF inv\u00E1lido: d\u00EDgitos verificadores incorretos
         //
         verifyException("br.ufjf.ead.validator.CpfValidator", e);
      }
  }

  @Test(timeout = 4000)
  public void test08()  throws Throwable  {
      boolean boolean0 = CpfValidator.isValid("012.345.678-90");
      assertTrue(boolean0);
  }

  @Test(timeout = 4000)
  public void test09()  throws Throwable  {
      try { 
        CpfValidator.validate((String) null);
        fail("Expecting exception: Exception");
      
      } catch(Exception e) {
         //
         // CPF n\u00E3o pode ser nulo ou vazio
         //
         verifyException("br.ufjf.ead.validator.CpfValidator", e);
      }
  }

  @Test(timeout = 4000)
  public void test10()  throws Throwable  {
      boolean boolean0 = CpfValidator.isValid("56718754398");
      assertFalse(boolean0);
  }

  @Test(timeout = 4000)
  public void test11()  throws Throwable  {
      CpfValidator cpfValidator0 = new CpfValidator();
  }
}
