import edu.touro.mco152.bm.DiskMark;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiskMarkTest {
    DiskMark test= new DiskMark(DiskMark.MarkType.WRITE);
    @BeforeAll
    public static void init(){
        DiskMark test= new DiskMark(DiskMark.MarkType.WRITE);
    }

    /**
     * cross checking a getter and seeter to make sure they have the same vlelie
     */
    @Test
    public void Crosstest(){
        test.setBwMbSec(123.33);
        assertEquals(123.33,test.getBwMbSec());
    }
    /**
     * testing performance characteristics by making sure the get method takes very short
     */
    @org.junit.jupiter.api.Test
    @Timeout(value = 20,unit = TimeUnit.MILLISECONDS)
    void timetest(){
        assertEquals(0,test.getMarkNum());
    }
}