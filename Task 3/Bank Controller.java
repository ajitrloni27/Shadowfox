package com.example.bank;

import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RestController
@RequestMapping("/api")
public class BankingController {

    @Autowired UserRepository userRepo;
    @Autowired AccountRepository accRepo;
    @Autowired TransactionRepository txnRepo;
    @Autowired BCryptPasswordEncoder encoder;

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password) {
        User u = new User();
        u.username = username;
        u.password = encoder.encode(password);
        userRepo.save(u);
        return "User Registered";
    }

    @PostMapping("/account")
    public Account createAccount(@RequestParam String owner,
                                 @RequestParam double balance) {
        Account a = new Account();
        a.owner = owner;
        a.balance = balance;
        return accRepo.save(a);
    }

    @PostMapping("/transfer")
    public String transfer(@RequestParam Long from,
                           @RequestParam Long to,
                           @RequestParam double amount) {

        Account fromAcc = accRepo.findById(from).orElseThrow();
        Account toAcc = accRepo.findById(to).orElseThrow();

        if (fromAcc.balance < amount) {
            return "Insufficient Balance";
        }

        fromAcc.balance -= amount;
        toAcc.balance += amount;

        accRepo.save(fromAcc);
        accRepo.save(toAcc);

        Transaction t = new Transaction();
        t.fromAcc = from;
        t.toAcc = to;
        t.amount = amount;
        txnRepo.save(t);

        return "Transfer Successful";
    }
}

interface UserRepository extends JpaRepository<User, Long> {}
interface AccountRepository extends JpaRepository<Account, Long> {}
interface TransactionRepository extends JpaRepository<Transaction, Long> {}

@Configuration
class AppConfig {
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
