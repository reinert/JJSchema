package org.reinert.jsonschema.tests.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.reinert.jsonschema.tests.model.Empresa;
import org.reinert.jsonschema.tests.model.Funcionarios;

@Path("/empresas/{id}")
@Consumes( { MediaType.APPLICATION_JSON })
@Produces( { MediaType.APPLICATION_JSON })
public class EmpresaResource {
	
    @GET
    public Empresa getEmpresa(@PathParam("id") Short id) {
    	Empresa empresa = null;
    	return empresa;
    }
    
    @GET
    @Path("/funcionarios")
    public Funcionarios getFuncionarios(@PathParam("id") Short id) {
    	Funcionarios funcionarios = null;
    	return funcionarios;
    }
    
    @PUT
    public void updateEmpresa(@PathParam("id") Short id, Empresa empresa) {
    	return;
    }
    
    @DELETE
    public void updateEmpresa(@PathParam("id") Short id) {
    	return;
    }

}
