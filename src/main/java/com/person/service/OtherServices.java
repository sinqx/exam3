package com.person.service;

import com.person.dao.PersonDao;
import com.person.model.Person;
import com.person.model.Requests;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;

@Path("/person")
public class OtherServices {

    @GET
    @Path("/{personFind}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Person> searchPerson(@PathParam("personFind") String personFind) throws SQLException { return PersonDao.searchPerson(personFind);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String Create(Person person) {  return PersonDao.addPerson(person);}

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Requests> getAllRequests(){ return PersonDao.getAllRequests(); }

    @DELETE
    @Path("/{requestId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean deleteUser(@PathParam("requestId") int requestId){
        return PersonDao.deleteRequestById(requestId);
    }
}