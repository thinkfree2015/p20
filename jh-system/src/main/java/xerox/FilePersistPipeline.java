package xerox;

import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/4.
 */
public class FilePersistPipeline implements Pipeline {

    @Deprecated
    private Class clazz;
    @Deprecated
    private SessionFactory sessionFactory;
    @Deprecated
    private Session session;
    private Collection<String> fieldCollection;

    @Deprecated
    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    @Deprecated
    public FilePersistPipeline() {
        session = sessionFactory.getCurrentSession();
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


    private BufferedWriter bw;

    public FilePersistPipeline(String filePath) throws IOException {
        bw = new BufferedWriter(new FileWriter(filePath, true));
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        try {
            if (!resultItems.isSkip()) {
                for (Map.Entry<String, Object> extraEntry : resultItems.getRequest().getExtras().entrySet()) {
                    if (!"statusCode".equals(extraEntry.getKey())) {
                        bw.write(extraEntry.getKey() + ":\t" + extraEntry.getValue() + "\t");
                    }
                }
                for (Map.Entry<String, Object> detailEntry : resultItems.getAll().entrySet()) {
                    bw.write(detailEntry.getKey() + ":\t" + detailEntry.getValue() + "\t");
                }
                bw.write("\n\r");
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (bw != null) {
            bw.close();
        }
    }
}
