import edu.touro.mco152.bm.DiskMark;
import edu.touro.mco152.bm.Util;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


class UtilTest {


    /**
     *  testing boundary conditions
     *  max and min
     */
    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE,Integer.MAX_VALUE})
    void testString(int num) {
        assertEquals(Util.displayString(num), Integer.toString(num));
    }


    /**
     * checking boundary conditions a file that doesn't exist
     */
    @org.junit.jupiter.api.Test
    void FileTest(){
        File file=new File("aa");
        assertNull(Util.getDriveType(file));
    }

    /**
     * forcing a exception by trying to write when access is denied.
     */
    @org.junit.jupiter.api.Test
    void  ExceptionTest() throws IOException {
        assertThrows(Exception.class,()->Util.readPhysicalDrive());
    }






}