package by.kazakevich.spring.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.*;

public class MySpringMvcDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
     
     @Override
     protected Class<?>[] getRootConfigClasses() {
          return new Class[0];
     }
     
     @Override
     protected Class<?>[] getServletConfigClasses() {
          return new Class[] {SpringConfig.class};
     }
     
     @Override
     protected String[] getServletMappings() {
          return new String[] {"/"};
     }
     
     @Override
     public void onStartup(ServletContext aServletContext) throws ServletException {
          super.onStartup(aServletContext);
          registerHiddenFieldFilter(aServletContext);
     
          //код используется, чтобы считывались русские буквы при добавлении имени и т.п.
          CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
          characterEncodingFilter.setEncoding("UTF-8");
          characterEncodingFilter.setForceEncoding(true);
          FilterRegistration.Dynamic filterRegistration = aServletContext.addFilter("characterEncodingFilter", characterEncodingFilter);
          filterRegistration.addMappingForUrlPatterns(null, false, "/*");
     }
     
     /*
     фильтр, который смотрит на значение скрытого поля _method,
     смотрит какой HTTP метод используется.
     Фильтр будет перенапровлять входящие HTTP запросы на нужные методы контроллера
      */
     private void registerHiddenFieldFilter(ServletContext aContext) {
          aContext.addFilter("hiddenHttpMethodFilter",
                  new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true, "/*");
     }
}
