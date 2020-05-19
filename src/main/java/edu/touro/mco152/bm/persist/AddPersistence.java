package edu.touro.mco152.bm.persist;

import edu.touro.mco152.bm.persist.DiskRun;
import edu.touro.mco152.bm.persist.EM;
import edu.touro.mco152.bm.readTest;
import edu.touro.mco152.bm.ui.Gui;
import edu.touro.mco152.bm.writetest;

import javax.persistence.EntityManager;
import java.util.Observable;
import java.util.Observer;

public class AddPersistence implements Observer {

    @Override
    public void update(Observable o, Object run){

        EntityManager em = EM.getEntityManager();
        em.getTransaction().begin();
        em.persist(run);
        em.getTransaction().commit();

        System.out.println("database was updated");
    }
}
