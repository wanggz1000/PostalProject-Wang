import com.fh.packageservice.service.QueryHandler;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryHandlerTests {

    @Test
    void testDeterminePackageStatus() {
        QueryHandler queryHandler = null;
        try {
            queryHandler = new QueryHandler();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        assertEquals("approved", queryHandler.determinePackageStatus(20), "Package weight 20 should be approved");

        assertEquals("denied", queryHandler.determinePackageStatus(25), "Package weight 25 should be denied");

        assertEquals("denied", queryHandler.determinePackageStatus(30), "Package weight 30 should be denied");
    }

    @Test
    void testDetermineLetterStatus() {
        QueryHandler queryHandler = null;
        try {
            queryHandler = new QueryHandler();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        assertEquals("approved", queryHandler.determineLetterStatus("AT"), "Letter destination country AT should be approved");


        assertEquals("denied", queryHandler.determineLetterStatus("CA"), "Letter destination country CA should be denied");


        assertEquals("approved", queryHandler.determineLetterStatus("CH"), "Letter destination country CH should be approved");
    }
}