package unit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TesteCameraVirtual.class, TesteForma.class, TesteMatriz.class, TestePonto.class, TesteTriangulo.class,
		TesteVetor.class })
public class AllUnitTests {

}
