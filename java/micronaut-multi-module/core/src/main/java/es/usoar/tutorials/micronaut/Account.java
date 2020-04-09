package es.usoar.tutorials.micronaut;

import io.micronaut.core.annotation.Introspected;

import java.util.Objects;

@Introspected
public class Account {

  private final String id;
  private final String name;

  public Account(String id, String name) {
    Objects.requireNonNull(id, "Id must not be null");
    Objects.requireNonNull(name, "Name must not be null");

    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
