import edu.touro.mco152.bm.App;
import edu.touro.mco152.bm.DiskMark;
import edu.touro.mco152.bm.SwitchWorker;
import edu.touro.mco152.bm.ui.Gui;
import edu.touro.mco152.bm.ui.MainFrame;
import edu.touro.mco152.bm.ui.uiworker;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

/**
 * the jnuit implmataion
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class uitest implements uiworker {
    /**
     * a method to tell me the cum adavge
     * @param d
     */
    @Override
    public void pusdopublish(DiskMark d) {
        System.out.println("to prove i got the mark the mark adv is " + d.getCumAvg());

    }

    /**
     * a test to see weather or not the something as canceeled
     * @return for the test true
     */
    @Override
    public boolean wascan() {
        System.out.println("it was cancelled");
        return true;
    }

    /**
     * A method to check if the progress is being messured
     * @param progress
     */
    @Test
    @Override
    public void updateprog(int progress) {
        Assertions.assertEquals(progress >=0 && progress<=100,true);
    }


    private final static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final static PrintStream originalOut = System.out;
    @BeforeAll
    void setup() throws Exception {
        setupDefaultAsPerProperties();
       SwitchWorker switchWorker= new SwitchWorker(this);
       switchWorker.dostuff();
    }


     void setupDefaultAsPerProperties()
    {
        System.setOut(new PrintStream(outContent));
        /// Do the minimum of what  App.init() would do to allow to run.
        Gui.mainFrame = new MainFrame();
        App.p = new Properties();
        App.loadConfig();
        System.out.println(App.getConfigString());
        Gui.progressBar = Gui.mainFrame.getProgressBar(); //must be set or get Nullptr

        // configure the embedded DB in .jDiskMark
        System.setProperty("derby.system.home", App.APP_CACHE_DIR);

        // code from startBenchmark
        //4. create data dir reference
        App.dataDir = new File(App.locationDir.getAbsolutePath()+File.separator+App.DATADIRNAME);

        //5. remove existing test data if exist
        if (App.dataDir.exists()) {
            if (App.dataDir.delete()) {
                App.msg("removed existing data dir");
            } else {
                App.msg("unable to remove existing data dir");
            }
        }
        else
        {
            App.dataDir.mkdirs(); // create data dir if not already present
        }


    }
    @BeforeEach
    public  void resetstream() throws IOException {
        outContent.reset();
    }
    @AfterAll
    public static void reset(){
        System.setOut(originalOut);
    }

    /**
     *  a test to ckeck the the diskmark dualts to zero
     */
    @Test
    public void pubtest(){
        DiskMark diskMark=new DiskMark(DiskMark.MarkType.WRITE);
        pusdopublish(diskMark);
        Assertions.assertEquals("to prove i got the mark the mark adv is 0.0\r\n", outContent.toString());
    }

    /**
     * a test to show that it can tell it was canecle.
     */
    @Test


    public void cancel(){
        wascan();
        Assertions.assertEquals("it was cancelled\r\n",outContent.toString());

    }



}
