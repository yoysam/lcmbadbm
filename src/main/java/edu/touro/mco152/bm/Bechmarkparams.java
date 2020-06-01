package edu.touro.mco152.bm;

import edu.touro.mco152.bm.persist.DiskRun;

public class Bechmarkparams {
    final DiskRun.BlockSequence blockSequence;
    final int numberOfBlocks;
    final int numberOfMarks;
    final int sizeOfBlocks;
    public Bechmarkparams (DiskRun.BlockSequence blockSequence,int numberOfBlocks, int numberOfMarks, int sizeOfBlocks) {
        this.sizeOfBlocks=sizeOfBlocks;
        this.numberOfMarks=numberOfMarks;
        this.blockSequence=blockSequence;
        this.numberOfBlocks=numberOfBlocks;
}}
