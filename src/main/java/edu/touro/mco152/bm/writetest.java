package edu.touro.mco152.bm;

import edu.touro.mco152.bm.App;
import edu.touro.mco152.bm.DiskMark;
import edu.touro.mco152.bm.Util;
import edu.touro.mco152.bm.persist.DiskRun;
import edu.touro.mco152.bm.persist.EM;
import edu.touro.mco152.bm.ui.Gui;
import edu.touro.mco152.bm.ui.uiworker;

import javax.persistence.EntityManager;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import static edu.touro.mco152.bm.App.*;
import static edu.touro.mco152.bm.App.msg;
import static edu.touro.mco152.bm.DiskMark.MarkType.WRITE;

/**
 * class for the write benchmark
 * now supports overservs
 */
public class writetest extends Observable implements readWriteCommands  {
    DiskRun.BlockSequence blockSequence;
    int numberOfBlocks;
    int numberOfMarks;
    int sizeOfBlocks;
    public writetest(DiskRun.BlockSequence blockSequence,int numberOfBlocks, int numberOfMarks, int sizeOfBlocks) {
        this.sizeOfBlocks=sizeOfBlocks;
        this.numberOfMarks=numberOfMarks;
        this.blockSequence=blockSequence;
        this.numberOfBlocks=numberOfBlocks;
        for (int b=0; b<blockArr.length; b++) {
            if (b%2==0) {
                blockArr[b]=(byte)0xFF;
            }
        }
    }
    int wUnitsComplete = 0,
            rUnitsComplete = 0,
            unitsComplete;

    int wUnitsTotal = App.writeTest ? numOfBlocks * numOfMarks : 0;
    int rUnitsTotal = App.readTest ? numOfBlocks * numOfMarks : 0;
    int unitsTotal = wUnitsTotal + rUnitsTotal;
    float percentComplete;

    int blockSize = blockSizeKb*KILOBYTE;
    byte [] blockArr = new byte [blockSize];
    DiskRun run;

    DiskMark wMark;
    int startFileNum = App.nextMarkNumber;

    /**
     * for the running the write becnhmark command
     * @param uiworker
     * @return if it ran
     */
    public  boolean excute (uiworker uiworker){


             run = new DiskRun(DiskRun.IOMode.WRITE, App.blockSequence);
            run.setNumMarks(numberOfMarks);
            run.setNumBlocks(numberOfBlocks);
            run.setBlockSize(sizeOfBlocks);
            run.setTxSize(App.targetTxSizeKb());
            run.setDiskInfo(Util.getDiskInfo(dataDir));

            // Tell logger and GUI to display what we know so far about the Run
            msg("disk info: (" + run.getDiskInfo() + ")");

            Gui.chartPanel.getChart().getTitle().setVisible(true);
            Gui.chartPanel.getChart().getTitle().setText(run.getDiskInfo());

            // Create a test data file using the default file system and config-specified location
            if (!App.multiFile) {
                testFile = new File(dataDir.getAbsolutePath() + File.separator + "testdata.jdm");
            }

            /**
             * Begin an outer loop for specified duration (number of 'marks') of benchmark,
             * that keeps writing data (in its own loop - for specified # of blocks). Each 'Mark' is timed
             * and is reported to the GUI for display as each Mark completes.
             */
            for (int m = startFileNum; m < startFileNum + App.numOfMarks && !uiworker.wascan(); m++) {

                if (App.multiFile) {
                    testFile = new File(dataDir.getAbsolutePath()
                            + File.separator + "testdata" + m + ".jdm");
                }
                wMark = new DiskMark(WRITE);    // starting to keep track of a new bench Mark
                wMark.setMarkNum(m);
                long startTime = System.nanoTime();
                long totalBytesWrittenInMark = 0;

                String mode = "rw";
                if (App.writeSyncEnable) {
                    mode = "rwd";
                }

                try {
                    try (RandomAccessFile rAccFile = new RandomAccessFile(testFile, mode)) {
                        for (int b = 0; b < numOfBlocks; b++) {
                            if (blockSequence == DiskRun.BlockSequence.RANDOM) {
                                int rLoc = Util.randInt(0, numOfBlocks - 1);
                                rAccFile.seek(rLoc * blockSize);
                            } else {
                                rAccFile.seek(b * blockSize);
                            }
                            rAccFile.write(blockArr, 0, blockSize);
                            totalBytesWrittenInMark += blockSize;
                            wUnitsComplete++;
                            unitsComplete = rUnitsComplete + wUnitsComplete;
                            percentComplete = (float) unitsComplete / (float) unitsTotal * 100f;

                            /**
                             * Report to GUI what percentage level of Entire BM (#Marks * #Blocks) is done.
                             */

                            uiworker.updateprog((int) percentComplete);
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }

                /**
                 * Compute duration, throughput of this Mark's step of BM
                 */
                long endTime = System.nanoTime();
                long elapsedTimeNs = endTime - startTime;
                double sec = (double) elapsedTimeNs / (double) 1000000000;
                double mbWritten = (double) totalBytesWrittenInMark / (double) MEGABYTE;
                wMark.setBwMbSec(mbWritten / sec);
                msg("m:" + m + " write IO is " + wMark.getBwMbSecAsString() + " MB/s     "
                        + "(" + Util.displayString(mbWritten) + "MB written in "
                        + Util.displayString(sec) + " sec)");
                App.updateMetrics(wMark);

                /**
                 * Let the GUI know the interim result described by the current Mark
                 */
                uiworker.pusdopublish(wMark);

                // Keep track of statistics to be displayed and persisted after all Marks are done.
                run.setRunMax(wMark.getCumMax());
                run.setRunMin(wMark.getCumMin());
                run.setRunAvg(wMark.getCumAvg());
                run.setEndTime(new Date());
            } // END outer loop for specified duration (number of 'marks') for WRITE bench mark

            /**
             * Persist info about the Write BM Run (e.g. into Derby Database) and add it to a GUI panel
             */

            setChanged();
            notifyObservers(run);
            return true;
        }
    }

