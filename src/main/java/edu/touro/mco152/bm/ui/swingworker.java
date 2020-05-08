package edu.touro.mco152.bm.ui;

import edu.touro.mco152.bm.DiskMark;
import edu.touro.mco152.bm.SwitchWorker;

import javax.swing.*;

/**
 * calls the the swing implemations of the methods
 */
public class swingworker extends SwingWorker<Boolean, DiskMark> implements uiworker {
    @Override
    public void pusdopublish(DiskMark d) {
        publish(d);
    }

    @Override
    public boolean wascan() {
        return isCancelled();
    }

    @Override
    public void updateprog(int progress) {
        setProgress(progress);

    }

    @Override
    protected Boolean doInBackground() throws Exception {
        SwitchWorker switchWorker=new SwitchWorker(this);
        return switchWorker.dostuff();
    }
}