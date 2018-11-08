package org.jcouchdb.models.servers;

import java.util.List;

import javax.json.bind.annotation.JsonbProperty;

import lombok.Data;

@Data
public class Welcome {

  String couchdb;
  String version;
  @JsonbProperty("git_sha") String gitSha;
  List<String> features;
  Vendor vendor;

}