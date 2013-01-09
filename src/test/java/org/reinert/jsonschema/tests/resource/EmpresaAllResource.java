package org.reinert.jsonschema.tests.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.reinert.jsonschema.Media;
import org.reinert.jsonschema.Rel;
import org.reinert.jsonschema.tests.model.Empresa;
import org.reinert.jsonschema.tests.model.Empresas;
import org.reinert.jsonschema.tests.model.Funcionarios;

@Path("/empresas")
@Consumes( { MediaType.APPLICATION_JSON })
@Produces( { MediaType.APPLICATION_JSON })
public class EmpresaAllResource {
	
	@GET
    public Empresas getAll(@QueryParam("funcionarios") boolean funcionarios) {
    	Empresas empresas = null;
    	return empresas;
    }
    
	@POST
	@Rel("create")
    public Short createEmpresa(Empresa empresa) {
        return (short)0;
    }
	
    @GET
    @Path("/{id}")
    @Rel("empresa")
    public Empresa getEmpresa(@PathParam("id") Short id) {
    	Empresa empresa = null;
    	return empresa;
    }
    
    @GET
    @Path("/{id}/funcionarios")
    @Rel("funcionarios")
    public Funcionarios getFuncionarios(@PathParam("id") Short id) {
    	Funcionarios funcionarios = null;
    	return funcionarios;
    }
    
    @PUT
    @Path("/{id}")
    @Rel("update")
    public void updateEmpresa(@PathParam("id") Short id, Empresa empresa) {
    	return;
    }
    
    @PUT
    @Path("/{id}")
    public void hasBodyParam(@PathParam("id") Short id, @Media(type="image/png", binaryEncoding="base64") Byte[] img) {
    	return;
    }
    
    @POST
    @Path("/{id}")
    public void hasParam(@PathParam("id") Short id, @FormParam("img") @Media(type="image/png", binaryEncoding="base64") Byte[] img) {
    	return;
    }
    
//    @POST
//    @Path("/{id}")
//    public void hasParamAndBodyParam(@PathParam("id") Short id, @Media(type="image/png", binaryEncoding="base64") Byte[] img2, @FormParam("img") @Media(type="image/png", binaryEncoding="base64") Byte[] img) {
//    	return;
//    }
    
    @DELETE
    @Path("/{id}")
    @Rel("delete")
    public void deleteEmpresa(@PathParam("id") Short id) {
    	return;
    }

}
