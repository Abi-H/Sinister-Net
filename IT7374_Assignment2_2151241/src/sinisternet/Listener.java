package sinisternet;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

@WebListener
public class Listener implements ServletContextListener{
	
	@Override
    public final void contextInitialized(final ServletContextEvent sce) {
		System.out.println("Listener fired up.");
		
		//new Thread(new Controller()).start();
		try {
			new NetworkScanner();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @Override
    public final void contextDestroyed(final ServletContextEvent sce) {
    	System.out.println("Listener destroyed");
    }
}
