import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    CustomerTest.class
})
public class TicketMinerTestSuite {
}