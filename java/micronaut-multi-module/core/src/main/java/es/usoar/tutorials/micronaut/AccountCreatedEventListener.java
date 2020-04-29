package es.usoar.tutorials.micronaut;

import io.micronaut.context.event.ApplicationEventListener;

import javax.inject.Singleton;

@Singleton
public class AccountCreatedEventListener implements ApplicationEventListener<AccountCreatedEvent> {
  @Override
  public void onApplicationEvent(AccountCreatedEvent event) {
    var account = event.getAccount();
    System.out.println("(Interface) Account created: " + account.getName());
  }
}
