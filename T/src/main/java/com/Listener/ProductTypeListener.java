package com.Listener;

import com.pojo.ProductType;
import com.service.ProductTypeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class ProductTypeListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ApplicationContext ap=new ClassPathXmlApplicationContext("applicationContext_*.xml");
        ProductTypeService service= (ProductTypeService) ap.getBean("productTypeService");
        List<ProductType> productTypes = service.selectAllType();
        servletContextEvent.getServletContext().setAttribute("ptlist",productTypes);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
