package com.mobilecomputing.pecunia.application;

import com.mobilecomputing.pecunia.model.Transaction;
import com.mobilecomputing.pecunia.model.Trip;
import com.mobilecomputing.pecunia.model.User;

import java.util.List;

public class BillingCalculator {

    public String calcBill(Trip trip){
        // 1.Beziehungen des Trips bestimmen
        // 2. Transaktionen aller Parteien suchen
        // 3. Transaktionen aus Punkt 3 zusammenaddieren
        // An PDF weitergeben
        return "Bill";
    }

    private class billingRelation{
        private User A,B;
        private double billingValueA=0;
        private double billingValueB=0;
        private List<Transaction> transactions;

        public billingRelation(User a, User b, List<Transaction> transactions) {
            A = a;
            B = b;
            this.transactions = transactions;
        }

        //public String

    }
}
