package edu.touro.mco152.bm;

import edu.touro.mco152.bm.persist.DiskRun;

/**
 * a class to build the read and write commands
 */
public class commadBuilder {
    private type testtype=type.read;
    private DiskRun.BlockSequence blockSequence= DiskRun.BlockSequence.SEQUENTIAL;
   private int numberOfBlocks=0;
   private int numberOfMarks=0;
   private int sizeOfBlocks=0;

    /**
     * a method to set the type of command used
     * @param type the type of command used
     * @return this object to  continue building
     */
    public commadBuilder type(type type){
        this.testtype=type;
        return this;
    }

    /**
     * a method to set the blockseqence
     * @param blockSequence the type of sequence used
     * @return this object to continue bulding
     */
    public commadBuilder blockSequence(DiskRun.BlockSequence blockSequence){
        this.blockSequence=blockSequence;
        return this;
    }

    /**
     * a method to set the number if blocks used
     * @param numberOfBlocks the number of blocks used
     * @return this object to continue bulding
     */
    public commadBuilder numberOfBlocks(int numberOfBlocks){
        this.numberOfBlocks=numberOfBlocks;
        return this;
    }

    /**
     * a method toe set the number of marks
     * @param numberOfMarks the number of marks used
     * @return this object to continue bulding
     */
    public commadBuilder numberOfMark(int numberOfMarks){
        this.numberOfMarks=numberOfMarks;
        return this;
    }

    /**
     * a method to set the size of the blocks used
     * @param sizeOfBlocks the size of the blocks used
     * @return this object to continue bulding
     */
    public commadBuilder sizeOfBlocks(int sizeOfBlocks){
        this.sizeOfBlocks=sizeOfBlocks;
        return this;
    }

    /**
     * a method to build the commnad we need
     * @return the read or write command we want
     */
    public readWriteCommands build(){
        if (testtype==type.read)
            return new readTest(blockSequence,numberOfBlocks,numberOfMarks,sizeOfBlocks);
        else
            return new writetest(blockSequence,numberOfBlocks,numberOfMarks,sizeOfBlocks);
    }
}
