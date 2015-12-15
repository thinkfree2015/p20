package com.efeiyi.jh.dao.companyGift;

import java.util.Set;

/**
 * Created by Administrator on 2015/9/8.
 *
 */
public interface ModalDao {

    Set<Object> getListLikesName(String name, String type, String status) throws Exception;

}
