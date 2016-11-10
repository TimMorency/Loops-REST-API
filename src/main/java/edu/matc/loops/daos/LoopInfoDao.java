package edu.matc.loops.daos;

import edu.matc.loops.enitity.LoopInfoObj;
import edu.matc.loops.persistance.SessionFactoryProvider;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tim on 11/10/2016.
 */
public class LoopInfoDao {


    public List<LoopInfoObj> getAllLoopInfoObj() {
        List<LoopInfoObj> rms = new ArrayList<LoopInfoObj>();
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        rms = session.createCriteria(LoopInfoObj.class).list();
        session.close();
        return rms;
    }

    public LoopInfoObj getLoopInfoObj(int id) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        LoopInfoObj rm = (LoopInfoObj) session.get(LoopInfoObj.class, id);
        session.close();
        return rm;
    }

    public LoopInfoObj insertLoopInfo(LoopInfoObj rm1) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(rm1);
        session.getTransaction().commit();
        session.close();
        return rm1;
    }

    public List<LoopInfoObj> insertList(List<LoopInfoObj> coords) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        session.beginTransaction();
        for(LoopInfoObj c : coords) {
            session.save(c);
        }
        session.getTransaction().commit();
        session.close();
        return coords;
    }

    public List<LoopInfoObj> searchLoopInfoObj(String fieldName, int searchVal) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(LoopInfoObj.class);
        criteria.add(Restrictions.eq(fieldName, searchVal));
        System.out.println(criteria.list());
        List<LoopInfoObj> coords = criteria.list();
        session.close();
        return coords;
    }

    public LoopInfoObj deleteLoopInfoObj(int id) {
        LoopInfoObj rm = getLoopInfoObj(id);
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(rm);
        session.getTransaction().commit();
        session.close();
        return rm;
    }
}
