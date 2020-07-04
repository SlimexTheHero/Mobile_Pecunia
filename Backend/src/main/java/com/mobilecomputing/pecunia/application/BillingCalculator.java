package com.mobilecomputing.pecunia.application;

import com.mobilecomputing.pecunia.model.Transaction;
import com.mobilecomputing.pecunia.model.Trip;
import com.mobilecomputing.pecunia.model.User;
import com.mobilecomputing.pecunia.repository.TransactionRepository;
import com.mobilecomputing.pecunia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BillingCalculator {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;


    public String calcBill(Trip trip) {
        String bill ="Bill"+"\n";
        HashMap<String,Object> currencyMap = new CurrencyManager().getCurrencyValue(trip.getCurrency());
        ArrayList<billingRelation> ListOfBillingRealations = new ArrayList<>();
        ArrayList<Transaction> allTransactionsOfTrip = new ArrayList<>();
        //Adds all transctions from the trip to allTransactionsOfTrip
        trip.getTransactions().forEach(t->{
            allTransactionsOfTrip.add(transactionRepository.findById(t).get());
        });
        // 1.Beziehungen des Trips bestimmen
        // Create all unique relations between the users
        for (int i = 0; i < trip.getTripParticipants().size(); i++) {
            User tempA = userRepository.findById(trip.getTripParticipants().get(i)).get();
            for (int k = i + 1; k < trip.getTripParticipants().size(); k++) {
                User tempB = userRepository.findById(trip.getTripParticipants().get(k)).get();
                ArrayList<Transaction> tempListOfTransactions = new ArrayList<>();
                // 2. Transaktionen aller Parteien suchen
                allTransactionsOfTrip.forEach(transaction -> {
                    if((transaction.getCreditor().equals(tempA.geteMail()) // User A is Creditor and User B ist Debtor
                            &&transaction.getDebtor().equals(tempB.geteMail()))
                            ||(transaction.getCreditor().equals(tempB.geteMail()) // User B is Creditor and User A ist Debtor
                            &&transaction.getDebtor().equals(tempA.geteMail()))){
                        tempListOfTransactions.add(transaction);
                    }
                });
                ListOfBillingRealations.add(new billingRelation(tempA,tempB,tempListOfTransactions,currencyMap,trip.getCurrency()));
            }
        }
        for(int i = 0; i< ListOfBillingRealations.size();i++){
            bill+=ListOfBillingRealations.get(i).calcBill();
        }
        return bill;
    }

    private class billingRelation {
        private User userA, userB;
        private double billingValueA = 0;
        private double billingValueB = 0;
        private List<Transaction> transactions;
        private HashMap<String, Object> currencyMap;
        private String baseCurrency;

        public billingRelation(User a, User b, List<Transaction> transactions,
                               HashMap<String, Object> currencyMap,String baseCurrency) {
            this.userA = a;
            this.userB = b;
            this.transactions = transactions;
            this.currencyMap = currencyMap;
            this.baseCurrency=baseCurrency;
        }

        public String calcBill(){
            double status =0;
            String billString= userA.getName()+" and "+userB.getName()+"\n";
            for(int i=0; i<transactions.size();i++){
                //if transaction currency doesnt matches the trip currency
                double tempLoan = transactions.get(i).getLoan();
                if(!transactions.get(i).getCurrency().equals(baseCurrency)){
                    tempLoan=tempLoan/(Double)currencyMap.get(transactions.get(i).getCurrency());
                }
                billString+= transactions.get(i).getTransactionName()+": "+transactions.get(i).getCreditor()+" owes "+
                        transactions.get(i).getDebtor()+" "+transactions.get(i).getLoan()+" "+baseCurrency+"\n";

                if(transactions.get(i).getDebtor().equals(userA.geteMail())){
                    status-=tempLoan;
                }else {
                    status+=tempLoan;
                }

            }
            billString+="------------Sum-------------"+"\n";
            if(status>=0){ // User A muss zahlen
                billString+= userA.getName()+" owes "+status+" "+baseCurrency+" to "+userB.getName();
            }else{
                status*=-1;
                billString+= userB.getName()+" owes "+status+" "+baseCurrency+" to "+userA.getName();
            }
            System.out.println(billString);
            return billString;
        }



    }
}
