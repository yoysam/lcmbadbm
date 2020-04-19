
package edu.touro.mco152.bm.ui;

import edu.touro.mco152.bm.DiskMark;
/**
 * interface for the ui methods
 */
public interface uiworker {
    /**
     * swings publish method
     * @param d
     */
    void pusdopublish(DiskMark d );

    /**
     * swings wascancelled method
     * @return if it was cancelled
     */
    boolean wascan();
    /**
     * swings setprogrees method
     * @param progress
     */
    void updateprog(int progress);
}