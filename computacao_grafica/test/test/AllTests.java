package test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	TesteAlgebra.class,
	TesteCameraVirtual.class, 
	TesteForma.class, 
	TesteMatriz.class, 
	TestePonto.class, 
	TesteTriangulo.class,
	TesteVetor.class })
public class AllTests {

}
