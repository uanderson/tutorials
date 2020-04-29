package es.usoar.tutorials.micronaut;

import java.util.Objects;

public class AccountCreatedEvent {

  private final Account account;

  public AccountCreatedEvent(Account account) {
    Objects.requireNonNull(account, "Account must not be null");
    this.account = account;
  }

  public Account getAccount() {
    return account;
  }
}
