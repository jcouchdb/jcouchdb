package org.jcouchdb.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.jcouchdb.models.servers.UuidCache;
import org.jcouchdb.models.servers.Welcome;

@Consumes("application/json")
@Produces("application/json")
public interface Servers {

  @Path("/")
  @GET
  Welcome get();

  @Path("/_uuids")
  @GET
  UuidCache getUuids(@QueryParam("count") long count);

}