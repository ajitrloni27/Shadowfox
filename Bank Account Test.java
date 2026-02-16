import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankAccountTest {

    // ================= BankAccount Class =================
    static class BankAccount {

        private String accountHolderName;
        private double balance;
        private List<String> transactionHistory;

        public BankAccount(String accountHolderName, double initialBalance) {
            if (accountHolderName == null || accountHolderName.isEmpty()) {
                throw new IllegalArgumentException("Name cannot be empty");
            }
            if (initialBalance < 0) {
                throw new IllegalArgumentException("Initial balance cannot be negative");
            }

            this.accountHolderName = accountHolderName;
            this.balance = initialBalance;
            this.transactionHistory = new ArrayList<>();
            transactionHistory.add("Account created with balance: " + initialBalance);
        }

        // Deposit validation
        public void deposit(double amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Deposit amount must be positive");
            }
            balance += amount;
            transactionHistory.add("Deposited: " + amount);
        }

        // Withdraw validation
        public void withdraw(double amount) throws InsufficientBalanceException {
            if (amount <= 0) {
                throw new IllegalArgumentException("Withdraw amount must be positive");
            }
            if (amount > balance) {
                throw new InsufficientBalanceException("Insufficient balance");
            }
            balance -= amount;
            transactionHistory.add("Withdrawn: " + amount);
        }

        public double getBalance() {
            return balance;
        }

        public List<String> getTransactionHistory() {
            return transactionHistory;
        }
    }

    // ================= Custom Exception =================
    static class InsufficientBalanceException extends Exception {
        public InsufficientBalanceException(String message) {
            super(message);
        }
    }

    // ================= User Input (Console) =================
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter Account Holder Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Initial Balance: ");
            double balance = sc.nextDouble();

            BankAccount account = new BankAccount(name, balance);

            System.out.print("Enter Deposit Amount: ");
            double depositAmount = sc.nextDouble();
            account.deposit(depositAmount);

            System.out.print("Enter Withdraw Amount: ");
            double withdrawAmount = sc.nextDouble();
            account.withdraw(withdrawAmount);

            System.out.println("Current Balance: " + account.getBalance());
            System.out.println("Transaction History:");
            for (String t : account.getTransactionHistory()) {
                System.out.println(t);
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            sc.close();
        }
    }

    // ================= JUnit Tests =================
    private BankAccount account;

    @BeforeEach
    public void setUp() {
        account = new BankAccount("Ajit", 1000.0);
    }

    @Test
    public void testValidDeposit() {
        account.deposit(500);
        assertEquals(1500, account.getBalance());
    }

    @Test
    public void testInvalidDeposit() {
        assertThrows(IllegalArgumentException.class, () -> {
            account.deposit(-100);
        });
    }

    @Test
    public void testValidWithdraw() throws InsufficientBalanceException {
        account.withdraw(200);
        assertEquals(800, account.getBalance());
    }

    @Test
    public void testWithdrawInsufficientBalance() {
        assertThrows(InsufficientBalanceException.class, () -> {
            account.withdraw(2000);
        });
    }

    @Test
    public void testTransactionHistory() {
        account.deposit(100);
        assertTrue(account.getTransactionHistory().size() > 1);
    }
}
