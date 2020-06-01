import edu.touro.mco152.bm.*;
import edu.touro.mco152.bm.externalsys.SlackManager;
import edu.touro.mco152.bm.persist.AddPersistence;
import edu.touro.mco152.bm.persist.DiskRun;
import edu.touro.mco152.bm.ui.Gui;
import edu.touro.mco152.bm.ui.MainFrame;
import edu.touro.mco152.bm.ui.uiworker;
import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
/**
 * testing the read and write commands
 */
public class Commandtest implements uiworker {
    testEcuter excuteer=new testEcuter();
    public static readTest reader=null;
    public static writetest writer=null;
    public static boolean recive=false;
    @Override
    public void pusdopublish(DiskMark d) {
        System.out.println("to prove i got the mark the mark adv is " + d.getCumAvg());

    }


    @Override
    public boolean wascan() {
        System.out.println("it was cancelled");
        return false;
    }



    @Override
    public void updateprog(int progress) {
        try {
  //          Assertions.assertEquals(progress >= 0 && progress <= 100, true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private final static ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final static PrintStream originalOut = System.out;

    void setup() throws Exception {
        SwitchWorker switchWorker= new SwitchWorker(this);
        switchWorker.dostuff();
    }
/**
 * setting up the vaurbles
 */
    @BeforeAll
    void setupDefaultAsPerProperties()
    {

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

        System.setOut(new PrintStream(outContent));
        commadBuilder commadBuilder=new commadBuilder();

        Bechmarkparams bechmarkparams=commadBuilder.blockSequence(DiskRun.BlockSequence.SEQUENTIAL).numberOfBlocks(128).numberOfMark(25).sizeOfBlocks(2048).build();
        writer= new writetest(bechmarkparams);
        reader=new readTest(bechmarkparams);
        reader.addObserver(new testReciver());
        writer.addObserver(new testReciver());

    }
    @BeforeEach
    public  void resetstream() throws IOException {
        outContent.reset();
    }
    /**
     * testing the write command
     * @throws IOException
     */
    @Test
    public void writetest() throws IOException {
      boolean ran= excuteer.run(this,"write");
      Assertions.assertEquals(true,ran);

    }
    @Test
    public void excuterwritetest() throws IOException {
        recive=false;
        excuteer.run(this,"write");
        Assertions.assertEquals(true,recive);
    }

    /**
     * testing the read command
     * @throws IOException
     */
    @Test
    public void readtest() throws IOException {
     boolean ran= excuteer.run(this,"read");
        Assertions.assertEquals(true,ran);
    }
    @Test
    public void excuterreadtest() throws IOException {
        recive=false;
        excuteer.run(this,"read");
        Assertions.assertEquals(true,recive);
    }
    public static class testEcuter {
        public boolean run (uiworker uiworker, String title) throws IOException {
            readWriteCommands readWriteCommands = null;
            if (title.equalsIgnoreCase("read")){
                readWriteCommands=Commandtest.reader;
            }
            else if (title.equalsIgnoreCase("write")){
                readWriteCommands=Commandtest.writer;
            }
            assert readWriteCommands != null;
            return  readWriteCommands.excute(uiworker);

        }
    }
    public static class testReciver implements Observer{

        @Override
        public void update(Observable o, Object arg) {
            Commandtest.recive=true;
        }
    }
}
