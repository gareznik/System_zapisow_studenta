import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Zestaw testów tylko z mockami")
@SelectPackages({ "Model", "Kontroler_Test", "KontrolerTestMock" })
@IncludeTags("mock")
public class SuiteMocki {
}
