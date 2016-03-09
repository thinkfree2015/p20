package xerox;

import com.ming800.core.base.service.BaseManager;
import com.ming800.core.util.ApplicationContextUtil;
import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/9.
 */
public class DBPersistPipeline implements Pipeline {

    private Class clazz;
    private SessionFactory sessionFactory;
    private Session session;
    private Collection<String> fieldCollection;
    private BaseManager baseManager = (BaseManager) ApplicationContextUtil.getApplicationContext().getBean("baseManagerImpl");

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public DBPersistPipeline(Class clazz) {
        this.clazz = clazz;
//        session = sessionFactory.getCurrentSession();
    }

    public void setFieldCollection(Collection<String> fieldCollection) {
        this.fieldCollection = fieldCollection;
    }

    public void process2(ResultItems resultItems, Task task) {

        session.setCacheMode(CacheMode.IGNORE);
        Object targetObj;

        try {
            targetObj = clazz.newInstance();
            for (String fieldString : fieldCollection) {
                Field field = clazz.getField(fieldString);
                field.setAccessible(true);
                field.set(targetObj, resultItems.get(fieldString));
            }
            session.saveOrUpdate(targetObj.getClass().getName(), targetObj);
            session.flush();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void process(ResultItems resultItems, Task task) {
        Object targetObj;
        try {
            targetObj = clazz.newInstance();
            if (!resultItems.isSkip()) {
                for (Map.Entry<String, Object> extraEntry : resultItems.getRequest().getExtras().entrySet()) {
                    if (!"statusCode".equals(extraEntry.getKey())) {
                        save(targetObj, extraEntry.getKey(), (String)extraEntry.getValue());
                    }
                }
                for (Map.Entry<String, Object> detailEntry : resultItems.getAll().entrySet()) {
                    save(targetObj, detailEntry.getKey(), detailEntry.getValue().toString());
                }
                baseManager.saveOrUpdate(targetObj.getClass().getName(),targetObj);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    private void save(Object targetObj, String fieldName, String value) throws IllegalAccessException {
        Field field;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            System.err.println("Ignore invalid field: " + fieldName + " in class: " + clazz.getName());
            return;
        }
        field.setAccessible(true);
        field.set(targetObj, value);
    }

}
