package com.example._configuration.gateway;

class GatewayServerProperties {

  Schema schema;
  Domain domain;
  Port port;

  GatewayServerProperties(Schema schema, Domain domain, Port port) {
    this.schema = schema;
    this.domain = domain;
    this.port = port;
  }

  String toUrl() {
    return String.format("%s://%s:%s", schema, domain, port);
  }

  void setSchema(String schema) {
    this.schema = Schema.valueOf(schema);
  }

  void setDomain(String domain) {
    this.domain = new Domain(domain);
  }

  void setPort(Integer port) {
    this.port = new Port(port);
  }

  @Override
  public String toString() {
    return "GatewayServerProperties{"
        + "schema="
        + schema
        + ", domain="
        + domain
        + ", port="
        + port
        + '}';
  }

  GatewayServerProperties() {}
}
