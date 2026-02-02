package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        // Validate amount
        if (Double.isNaN(amount)){
            throw new IllegalArgumentException("Amount is not a number");
        }
        if (amount < 0){
            throw new IllegalArgumentException("Amount must be non-negative");
        }

        // Define a minimum meaningful withdrawal (e.g., 1 cent)
        final double MIN_WITHDRAW = 0.01;
        if (amount > 0 && amount < MIN_WITHDRAW){
            throw new IllegalArgumentException("Amount is too small");
        }

        // Check funds (use a small epsilon to avoid floating point issues)
        final double EPS = 1e-9;
        if (amount - balance > EPS){
            throw new InsufficientFundsException("Not enough money");
        }

        // perform withdrawal
        balance -= amount;
    }


    public static boolean isEmailValid(String email){
        if (email == null) return false;
        email = email.trim();
        if (email.length() == 0) return false;

        // must contain exactly one @
        int atCount = 0;
        for (char c : email.toCharArray()) if (c == '@') atCount++;
        if (atCount != 1) return false;

        int atPos = email.indexOf('@');
        String local = email.substring(0, atPos);
        String domain = email.substring(atPos+1);

        // local and domain must be non-empty
        if (local.length() == 0 || domain.length() == 0) return false;

        // local part must not end with a dash (test case: "a-@mail.com" invalid)
        if (local.charAt(local.length()-1) == '-') return false;

        // allowed chars: local -> letters, digits, ., _, +, -
        for (char c : local.toCharArray()){
            if (!(Character.isLetterOrDigit(c) || c=='.' || c=='_' || c=='+' || c=='-')) return false;
        }

        // domain allowed chars: letters, digits, -, .
        for (char c : domain.toCharArray()){
            if (!(Character.isLetterOrDigit(c) || c=='.' || c=='-')) return false;
        }

        // domain must contain at least one dot
        if (domain.indexOf('.') == -1) return false;

        // domain parts (split by .) must be non-empty and last part (TLD) at least length 2
        String[] labels = domain.split("\\.");
        if (labels.length < 2) return false;
        for (String lab : labels) if (lab.length() == 0) return false; // no consecutive dots or leading/trailing dot

        String tld = labels[labels.length-1];
        if (tld.length() < 2) return false;

        // domain labels shouldn't start or end with dash
        for (String lab : labels){
            if (lab.charAt(0) == '-' || lab.charAt(lab.length()-1) == '-') return false;
        }

        return true;
    }
}