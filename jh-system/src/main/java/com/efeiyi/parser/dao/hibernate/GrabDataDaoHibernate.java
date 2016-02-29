package com.efeiyi.parser.dao.hibernate;

import com.efeiyi.ec.organization.model.AddressDistrict;
import com.efeiyi.parser.dao.GrabDataDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2016/2/24.
 * 数据抓取 dao hibernate
 */
@SuppressWarnings("JpaQlInspection")
@Repository
public class GrabDataDaoHibernate implements GrabDataDao {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public AddressDistrict getProvince(String province) throws Exception {
        List list = getDistrict(province);
        if (null != list){
            switch (list.size()){
                case 0:return null;
                case 1:return (AddressDistrict) list.get(0);
                default:{
                    String hql = "select ad from AddressDistrict ad,AddressProvince ap where ad.id = ap.id and ap.name like :province";
                    Query query = this.getSession().createQuery(hql).setString("province", province + "%");
                    list = query.list();
                    if (null != list && list.size() == 1){
                        return (AddressDistrict) list.get(0);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public AddressDistrict getProvinceCity(String province, String city) throws Exception {
        List list = getDistrict(city);
        if (null != list){
            switch (list.size()){
                case 0:return getProvince(province);
                case 1:return (AddressDistrict) list.get(0);
                default:{
                    String hql;
                    if (province.equals("北京") || province.equals("天津") || province.equals("上海") || province.equals("重庆")){
                        hql = "select ad from AddressDistrict ad where ad.addressCity.name like :province and ad.name like :city";
                    }else {
                        hql = "select ad from AddressDistrict ad,AddressCity ac where ad.id = ac.id and ac.addressProvince.name like :province and ac.name like :city";
                    }
                    Query query = this.getSession().createQuery(hql).setString("province", province + "%").setString("city", city+"%");
                    list = query.list();
                    if (null != list && list.size() == 1){
                        return (AddressDistrict) list.get(0);
                    }
                }
            }
        }

        return null;
    }

    @Override
    public AddressDistrict getProvinceCityCounty(String province, String city, String county) throws Exception {
        List list = getDistrict(county);
        if (null != list){
            switch (list.size()){
                case 0:return getProvinceCity(province, city);
                case 1:return (AddressDistrict) list.get(0);
                default:{
                    String hql = "select ad from AddressDistrict ad where ad.addressCity.addressProvince.name like :province and ad.addressCity.name like :city and ad.name like :county";
                    Query query = this.getSession().createQuery(hql).setString("province", province+"%").setString("city", city+"%").setString("county", county+"%");
                    list = query.list();
                    if (null != list && list.size() == 1){
                        return (AddressDistrict) list.get(0);
                    }
                }
            }
        }

        return null;
    }


    private List getDistrict(String district) throws Exception {
        String hql = "from AddressDistrict ad where ad.name like :district ";
        Query query = this.getSession().createQuery(hql).setString("district", district+"%");
        return query.list();
    }
}
