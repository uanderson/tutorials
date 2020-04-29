package es.usoar.tutorials.micronaut;

import io.micronaut.runtime.event.annotation.EventListener;

import javax.inject.Singleton;

@Singleton
public class AccountEventHandler {

  @EventListener
  public void on(AccountCreatedEvent event) {
    var account = event.getAccount();
    System.out.println("(Method) Account created: " + account.getName());
  }
}
