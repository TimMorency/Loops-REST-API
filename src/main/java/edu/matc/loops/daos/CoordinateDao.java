package edu.matc.loops.daos;

import edu.matc.loops.enitity.CoordinateObj;
import edu.matc.loops.persistance.SessionFactoryProvider;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tim on 10/11/2016.
 */
public class CoordinateDao {

    public List<CoordinateObj> getAllCoordinateObj() {
        List<CoordinateObj> rms = new ArrayList<CoordinateObj>();
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        rms = session.createCriteria(CoordinateObj.class).list();
        session.close();
        return rms;
    }

    public CoordinateObj getCoordinateObj(int id) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        CoordinateObj rm = (CoordinateObj) session.get(CoordinateObj.class, id);
        session.close();
        return rm;
    }

    public CoordinateObj insertCoordinate(CoordinateObj rm1) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(rm1);
        session.getTransaction().commit();
        session.close();
        return rm1;
    }

    public List<CoordinateObj> insertList (List<CoordinateObj> coords) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        session.beginTransaction();
        for(CoordinateObj c : coords) {
            session.save(c);
        }
        session.getTransaction().commit();
        session.close();
        return coords;
    }

    public List<CoordinateObj> searchCoordinateObj(String fieldName, int searchVal) {
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(CoordinateObj.class);
        criteria.add(Restrictions.eq(fieldName, searchVal));
        System.out.println(criteria.list());
        List<CoordinateObj> coords = criteria.list();
        session.close();
        return coords;
    }

    public CoordinateObj deleteCoordinate(int id) {
        CoordinateObj rm = getCoordinateObj(id);
        Session session = SessionFactoryProvider.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(rm);
        session.getTransaction().commit();
        session.close();
        return rm;
    }

}
