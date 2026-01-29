import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Zestaw testów dla warstwy Kontroli")
@SelectPackages({ "Kontroler_Test", "KontrolerTestMock" })
public class SuiteKontroler {
}
