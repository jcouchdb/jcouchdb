package org.jcouchdb.rest;

import java.util.List;
import java.util.UUID;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jcouchdb.models.servers.UuidCache;
import org.jcouchdb.models.servers.Welcome;
import org.junit.jupiter.api.Test;

import lombok.Data;

public class ServersTests {

    static void beforeAll() {

    }

    @Data
    public class JsonListInObject<T> {

    }

    @Test
    void getWelcome() {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:5984");
        Servers servers = target.proxy(Servers.class);
        Welcome welcome = servers.get();
        System.out.println(welcome);
    }

    @Test
    void getUuids() {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target("http://localhost:5984");
        Servers servers = target.proxy(Servers.class);
        UuidCache uuidCache = servers.getUuids(10);
        System.out.println(uuidCache.getUuids());
    }

}