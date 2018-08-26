package sinisternet;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("crack")
public class Crack {

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response getIP(@Context javax.servlet.http.HttpServletRequest request) throws UnknownHostException { 

        String remoteHost = request.getRemoteHost();
        String remoteAddr = request.getRemoteAddr();
        if (remoteAddr.equals("0:0:0:0:0:0:0:1")) {
            InetAddress localip = java.net.InetAddress.getLocalHost();
            remoteAddr = localip.getHostAddress();
            remoteHost = localip.getHostName();
        }
        int remotePort = request.getRemotePort();

        String msgoutput = remoteHost + " (" + remoteAddr + " : " + remotePort + ")";
                
        return Response.status(200).entity(msgoutput).build(); 
    }
}
