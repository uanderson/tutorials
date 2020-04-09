package es.usoar.tutorials.micronaut;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.util.List;
import java.util.UUID;

@Controller("/accounts")
public class AccountController {

  @Get
  public List<Account> get() {
    var uuid = UUID.randomUUID();
    return List.of(new Account(uuid.toString(), "Account " + uuid));
  }
}
