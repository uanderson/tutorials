package es.usoar.tutorials.micronaut;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class AccountData {

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
