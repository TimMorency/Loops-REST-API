package edu.matc.loops.daos;

import edu.matc.loops.enitity.LoopsObj;
import edu.matc.loops.persistance.SessionFactoryProvider;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tim on 10/11/2016.
 */
public class LoopsDao {

    public List<LoopsObj> getAllLoopsObj() {
        List<LoopsObj> rms = new ArrayList<LoopsObj>();
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        rms = session.createCriteria(LoopsObj.class).list();
        session.close();
        return rms;
    }

    public List<LoopsObj> getLoopsFromLoopInfo(List<Integer> loopInfos) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(LoopsObj.class);
        criteria.add(Restrictions.in("loop_info_id", loopInfos));
        session.close();
        return criteria.list();
    }

    public LoopsObj getLoopsObj(int id) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        LoopsObj rm = (LoopsObj) session.get(LoopsObj.class, id);
        session.close();
        return rm;
    }

    public LoopsObj insertLoopsObj(LoopsObj rm) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(rm);
        session.getTransaction().commit();
        session.close();
        return rm;
    }

    public List<LoopsObj> searchLoopsObj(String fieldName, String searchVal) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(LoopsObj.class);
        criteria.add(Restrictions.eq(fieldName, searchVal));
        session.close();
        return criteria.list();
    }

    public LoopsObj deleteLoopsObj(int id) {
        LoopsObj rm = getLoopsObj(id);
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(rm);
        session.getTransaction().commit();
        session.close();
        return rm;
    }

}
