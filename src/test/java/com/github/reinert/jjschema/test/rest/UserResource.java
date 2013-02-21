/*
 * Copyright (c) 2013, Danilo Reinert <daniloreinert@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.reinert.jjschema.test.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


import com.github.reinert.jjschema.Rel;
import com.github.reinert.jjschema.test.model.User;
import com.github.reinert.jjschema.test.model.Users;

@Path("/users")
@Consumes( { MediaType.APPLICATION_JSON })
@Produces( { MediaType.APPLICATION_JSON })
public class UserResource {
	
	@GET
    public Users getAll(@QueryParam("minAge") int min, @QueryParam("maxAge") int max) {
    	Users users = null;
    	return users;
    }
    
	@POST
	@Rel("create")
    public Short createUser(User user) {
        return (short)0;
    }
	
    @GET
    @Path("/{id}")
    @Rel("user")
    public User getUser(@PathParam("id") Short id) {
    	User user = null;
    	return user;
    }
    
    @GET
    @Path("/{id}/admin")
    @Rel("admin")
    public Users getAdminUsers(@PathParam("id") Short id, @QueryParam("minAge") int min, @QueryParam("maxAge") int max) {
    	Users users = null;
    	return users;
    }
    
    @PUT
    @Path("/{id}")
    @Rel("update")
    public void updateUser(@PathParam("id") Short id, User user) {
    	return;
    }
    
//    @PUT
//    @Path("/{id}")
//    public void hasBodyParam(@PathParam("id") Short id, @Media(type="image/png", binaryEncoding="base64") Byte[] img) {
//    	return;
//    }
//    
//    @POST
//    @Path("/{id}")
//    public void hasParam(@PathParam("id") Short id, @FormParam("img") @Media(type="image/png", binaryEncoding="base64") Byte[] img) {
//    	return;
//    }
    
//    @POST
//    @Path("/{id}")
//    public void hasParamAndBodyParam(@PathParam("id") Short id, @Media(type="image/png", binaryEncoding="base64") Byte[] img2, @FormParam("img") @Media(type="image/png", binaryEncoding="base64") Byte[] img) {
//    	return;
//    }
    
    @DELETE
    @Path("/{id}")
    @Rel("delete")
    public void deleteUser(@PathParam("id") Short id) {
    	return;
    }

}
