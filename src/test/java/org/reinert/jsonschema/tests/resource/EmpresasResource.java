package org.reinert.jsonschema.tests.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.reinert.jsonschema.tests.model.Empresa;
import org.reinert.jsonschema.tests.model.Empresas;

@Path("/empresas")
@Consumes( { MediaType.APPLICATION_JSON })
@Produces( { MediaType.APPLICATION_JSON })
public class EmpresasResource {
	
	@GET
    public Empresas getAll(@QueryParam("funcionarios") boolean funcionarios) {
    	Empresas empresas = null;
    	return empresas;
    }
    
	@POST
    public Short createEmpresa(Empresa empresa) {
        return (short)0;
    }
}
