package org.reinert.jsonschema.tests.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.reinert.jsonschema.tests.model.Funcionario;

@Path("/funcionarios/{id}")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class FuncionarioResource {
   
    @GET
    public Funcionario getFuncionario(@PathParam("id") Integer id) {
    	Funcionario f = null;
    	return f;
    }
    
    @PUT
    public void updateFuncionario(@PathParam("id") Integer id, Funcionario funcionario) {
    	return;
    }
    
    @DELETE
    public void updateFuncionario(@PathParam("id") Integer id) {
    	return;
    }

}
