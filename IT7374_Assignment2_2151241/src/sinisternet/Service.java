package sinisternet;

import java.io.IOException;
import java.net.URI;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.container.*;
import javax.ws.rs.core.*;

@Path("service")

public class Service {

	//@Context
	//ResourceContext resourceContext;
	@Context
	private ServletContext servletContext;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response handleDetails(@QueryParam("algorithm") String algorithm, @QueryParam("hash") String hash)
			throws ClassNotFoundException, IOException, InterruptedException {

		URI uri;
		boolean response = false;
		Client client = new Client(hash, algorithm);
		Thread clientThread = new Thread(client);
		clientThread.start();

		String output = "";

		while (true) {

			System.out.println("Inside loop");
			
			String crackedHash = client.getCrackedHash();

			if (!crackedHash.equals("")) {
				
				output = "Cracked hash is " + crackedHash; // uri =
				//UriBuilder.fromPath("/rest/index.jsp").queryParam("crackedHash", crackedHash);
				uri = UriBuilder.fromPath(servletContext.getContextPath() + "/result.jsp").queryParam("crackedHash", crackedHash).build();				
				break;
			}
		}

		 //return Response.status(200).entity(output).build(); 
		 //return
		return Response.seeOther(uri).build();

	}

}