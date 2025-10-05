
package demo.elastic.rag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		System.setProperty("http.proxyHost", "192.168.49.1");
		System.setProperty("http.proxyPort", "8282");
		System.setProperty("https.proxyHost", "192.168.49.1");
		System.setProperty("https.proxyPort", "8282");
		SpringApplication.run(DemoApplication.class, args);
	}

}
