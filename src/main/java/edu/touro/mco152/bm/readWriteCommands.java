package edu.touro.mco152.bm;

import edu.touro.mco152.bm.ui.uiworker;

import java.io.IOException;

/**
 * interface for the command pattert
 */
public interface readWriteCommands {
    /**
     * the command method
     * @param uiworker
     * @return if it ran
     * @throws IOException
     */
    public boolean excute(uiworker uiworker) throws IOException;

}
