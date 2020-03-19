import edu.touro.mco152.bm.DiskMark;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiskMarkTest {
    DiskMark test= new DiskMark(DiskMark.MarkType.WRITE);
    @BeforeAll
    public static void init(){
        DiskMark test= new DiskMark(DiskMark.MarkType.WRITE);
    }
    @Test
    public void Crosstest(){
        test.setBwMbSec(123.33);
        assertEquals(123.33,test.getBwMbSec());
    }
}
