/**
 * Testes gerados automaticamente para DocumentFormatter usando EvoSuite.
 *
 * @author Fabio Oliveira
 */

package br.ufjf.ead.validator;

import org.junit.Test;
import static org.junit.Assert.*;
import br.ufjf.ead.validator.DocumentFormatter;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class DocumentFormatter_ESTest extends DocumentFormatter_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      String string0 = DocumentFormatter.formatCnpj(";,WXf");
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test1()  throws Throwable  {
      String string0 = DocumentFormatter.formatCpf("");
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test2()  throws Throwable  {
      String string0 = DocumentFormatter.removeFormatting("1p=UXK=p0rDcd)=");
      assertEquals("10", string0);
  }

  @Test(timeout = 4000)
  public void test3()  throws Throwable  {
      String string0 = DocumentFormatter.removeFormatting("");
      assertEquals("", string0);
  }

  @Test(timeout = 4000)
  public void test4()  throws Throwable  {
      String string0 = DocumentFormatter.formatCnpj((String) null);
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test5()  throws Throwable  {
      String string0 = DocumentFormatter.formatCpf("PKO1142s5%74a77:P1S2");
      assertEquals("114.257.477-12", string0);
      assertNotNull(string0);
  }

  @Test(timeout = 4000)
  public void test6()  throws Throwable  {
      String string0 = DocumentFormatter.formatCpf((String) null);
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test7()  throws Throwable  {
      String string0 = DocumentFormatter.formatCpf("PKO1142s5%74a779:P12");
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test8()  throws Throwable  {
      String string0 = DocumentFormatter.removeFormatting((String) null);
      assertNull(string0);
  }

  @Test(timeout = 4000)
  public void test9()  throws Throwable  {
      DocumentFormatter documentFormatter0 = new DocumentFormatter();
  }
}
