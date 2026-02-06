/**
 * Testes gerados automaticamente para InvalidDocumentException usando EvoSuite.
 *
 * @author Fabio Oliveira
 */

package br.ufjf.ead.exception;

import org.junit.Test;
import static org.junit.Assert.*;
import br.ufjf.ead.exception.InvalidDocumentException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = true, useJEE = true) 
public class InvalidDocumentException_ESTest extends InvalidDocumentException_ESTest_scaffolding {

  @Test(timeout = 4000)
  public void test0()  throws Throwable  {
      InvalidDocumentException invalidDocumentException0 = new InvalidDocumentException("");
      InvalidDocumentException invalidDocumentException1 = new InvalidDocumentException("", invalidDocumentException0);
      assertFalse(invalidDocumentException1.equals((Object)invalidDocumentException0));
  }
}
