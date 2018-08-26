package sinisternet;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("details")

public class Details {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response handleDetails(
		@QueryParam("algorithm") String algorithm,
		@QueryParam("dictionary") String dictionary,
		@QueryParam("hash") String hash) {
		
		String msgoutput = algorithm + " " + dictionary + " " + hash;
	    return Response.status(200).entity(msgoutput).build();
				
	}
}