package org.reinert.jsonschema.tests.resource;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.reinert.jsonschema.tests.model.Funcionario;

@Path("/funcionarios")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class FuncionariosResource {
   
    @GET
    public Collection<Funcionario> getAll() {
    	Collection<Funcionario> fs = null;
    	return fs;
    }
    
    @POST
    public Long createFuncionario(Funcionario funcionario) {
        return 0l;
    }
    
}
