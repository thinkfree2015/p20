package com.efeiyi.ec.system.yale.question.dao.hibernate;

import com.efeiyi.ec.system.yale.question.dao.ExaminationDao;
import com.efeiyi.ec.yale.question.model.Examination;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2015/12/23.
 * 答题活动 hibernate
 */

@SuppressWarnings("JpaQlInspection")
@Repository
public class ExaminationDaoHibernate implements ExaminationDao {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List getExaminationQuestionList(Examination examination) throws Exception {
        String hql = "from ExaminationQuestion eq where eq.examination = :examination order by eq.id desc";
        Query query = this.getSession().createQuery(hql)
                .setParameter("examination", examination);
        return query.list();
    }

    @Override
    public List getQuestionList(Examination examination) throws Exception {
        String hql = "select q from Question q where q not in (select eq.question from ExaminationQuestion eq where eq.examination =:examination) order by q.id desc ";
        Query query = this.getSession().createQuery(hql)
                .setParameter("examination", examination);
        return query.list();
    }
}
