package boot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import boot.utils.Terms;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;

import com.complexible.stardog.api.ConnectionConfiguration;
import com.complexible.stardog.ext.spring.DataSource;
import com.complexible.stardog.ext.spring.DataSourceFactoryBean;
import com.complexible.stardog.reasoning.api.ReasoningType;
@Configuration
@EnableAutoConfiguration
@ComponentScan
@PropertySource(value = "classpath:application.properties")
public class Application {
	@Autowired
	private Environment env;
	//private static Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println("Loaded Application Context. Let Magic to begin ...");
		System.out.println(locateLogfile());
//		ApplicationContext ctx = SpringApplication.run(Application.class, args);
//		System.out.println("Let's inspect the beans provided by Spring Boot:");
//		String[] beanNames = ctx.getBeanDefinitionNames();
//		Arrays.sort(beanNames);
//		for (String beanName : beanNames) {
//			System.out.println(beanName);
//		}
	}
	
	@Bean
	public ThreadPoolTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
		pool.setCorePoolSize(5);
		pool.setMaxPoolSize(10);
		pool.setWaitForTasksToCompleteOnShutdown(true);
		return pool;
	}
	
    @Bean(name="searchConnectionConfiguration")
    public ConnectionConfiguration initSearchConnectionConfiguration() {
		return ConnectionConfiguration.
				to(env.getProperty("triplestore.stardog.database")).
				credentials(env.getProperty("triplestore.stardog.username"),
				env.getProperty("triplestore.stardog.password")).
				server(env.getProperty("triplestore.stardog.url"));
    }
    
 
    @Bean(name="stardogDataSource")
    public DataSourceFactoryBean initOwlDataSource() {
    	DataSourceFactoryBean dataSource = new DataSourceFactoryBean();
    	dataSource.setTo(env.getProperty("triplestore.stardog.database"));
    	dataSource.setUsername(env.getProperty("triplestore.stardog.username"));
    	dataSource.setPassword(env.getProperty("triplestore.stardog.password"));
    	dataSource.setUrl(env.getProperty("triplestore.stardog.url"));
    	dataSource.setReasoningType(ReasoningType.QL);
    	return dataSource;
    }

    @Bean(name="drugClassConcepts")
	public Map<String, String> initDrugClassConcepts(){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(Terms.ANALGESIC_CONCEPT, Terms.ANALGESIC_LBL);
		map.put(Terms.HYPNOTIC_SEDATIVE_CONCEPT, Terms.HYPNOTIC_SEDATIVE_LBL);
		map.put(Terms.ANTIDEPRESSANT_CONCEPT, Terms.ANTIDEPRESSANT_LBL);
		map.put(Terms.ANXIOLYTIC_CONCEPT, Terms.ANXIOLYTIC_LBL);
		return map;
	}
        

    /* Find location of log file for display. It's nice to have, so swallow all exceptions*/
	private static String locateLogfile() {
		String out = "";
		try {
			LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
			Logger logger = loggerContext.getLogger("ROOT");
			FileAppender<ILoggingEvent> fileAppender = (FileAppender<ILoggingEvent>) logger.getAppender("FILE");
			out = "Log File located : " + fileAppender.getFile();
		} catch (Exception e) {
			out = "Problem identifiyng log file location. Check logback.xml file.";
		}
		return out;
	}
   
}