package edu.touro.mco152.bm;

import edu.touro.mco152.bm.persist.DiskRun;
import edu.touro.mco152.bm.ui.uiworker;

import java.io.IOException;

/**
 * class for excutering commads
 */
public class Excuteer {
    /**
     * runs the read or write commands depending on the parmas
     * @param uiworker
     * @param title
     * @return if the
     * @throws IOException
     */
    public boolean run (uiworker uiworker, String title) throws IOException {
        readWriteCommands readWriteCommands = null;
        if (title.equalsIgnoreCase("read")){
            readWriteCommands=new readTest(DiskRun.BlockSequence.SEQUENTIAL,128,25,2048);
        }
        else if (title.equalsIgnoreCase("write")){
            readWriteCommands=new writetest(DiskRun.BlockSequence.SEQUENTIAL,128,25,2048);
        }
        assert readWriteCommands != null;
      return  readWriteCommands.excute(uiworker);

    }
}
