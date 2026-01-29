import org.junit.platform.suite.api.ExcludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Zestaw testów bez mocków")
@SelectPackages({ "Model", "Kontroler_Test", "KontrolerTestMock" })
@ExcludeTags("mock")
public class SuiteBezMockow {
}
