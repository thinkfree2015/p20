package com.efeiyi.ec.courier.grasp.listener;

import com.efeiyi.ec.courier.model.Capture;
import com.efeiyi.ec.courier.organization.util.ContextUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.apache.log4j.Logger;

/**
 * Created by Administrator on 2015/12/10.
 *
 */
public class ContextInitializedListener implements WebApplicationInitializer,ApplicationContextAware {
    private static Logger log = Logger.getLogger(ContextInitializedListener.class);
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        String exe = ((Capture)ContextUtils.getBean("capture")).getIsCapture();
        if (exe!=null && !"".equals(exe)){
            if ("on".equals(exe)){//去抓取数据

            }else{
                log.info("i don not  need do anything");
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ContextUtils.setApplicationContext(applicationContext);
        log.debug("ApplicationContext registed");
    }
}
