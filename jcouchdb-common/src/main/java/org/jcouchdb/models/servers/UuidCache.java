package org.jcouchdb.models.servers;

import java.util.List;

import lombok.Data;

@Data
public class UuidCache {

    // TODO - This should be a List<UUID> but there is no adapter that
    //        works with an undelimited String uuid.
    List<String> uuids;

}