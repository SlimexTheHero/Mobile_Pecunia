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

    /**
     * this function inits the calculation process
     * @param trip
     * @return String which contains the bill
     */
    public String calcBill(Trip trip) {
        String bill ="Bill"+"\n";
        HashMap<String,Object> currencyMap = new CurrencyManager().getCurrencyValue(trip.getCurrency());
        ArrayList<billingRelation> ListOfBillingRealations = new ArrayList<>();
        ArrayList<Transaction> allTransactionsOfTrip = new ArrayList<>();
        //Adds all transctions from the trip to allTransactionsOfTrip
        trip.getTransactions().forEach(t->{
            allTransactionsOfTrip.add(transactionRepository.findById(t).get());
        });
        // Create all unique relations between the users
        for (int i = 0; i < trip.getTripParticipants().size(); i++) {
            User tempA = userRepository.findById(trip.getTripParticipants().get(i)).get();
            for (int k = i + 1; k < trip.getTripParticipants().size(); k++) {
                User tempB = userRepository.findById(trip.getTripParticipants().get(k)).get();
                ArrayList<Transaction> tempListOfTransactions = new ArrayList<>();
                // Search transactions of all pairs
                allTransactionsOfTrip.forEach(transaction -> {
                    if((transaction.getCreditor().equals(tempA.geteMail()) // User A is Creditor and User B is Debtor
                            &&transaction.getDebtor().equals(tempB.geteMail()))
                            ||(transaction.getCreditor().equals(tempB.geteMail()) // User B is Creditor and User A is Debtor
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

    /**
     * This class represents the bllingRelation between the pairs
     */
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

        /**
         * this function calculates the bill between to trip participants
         * @return String which contains the bill between two participants
         */
        public String calcBill(){
            double status =0;
            String billString="";
            billString= userA.getName()+" and "+userB.getName()+"\n";
            for(int i=0; i<transactions.size();i++){
                boolean otherCurrency= false;
                //if transaction currency doesnt matches the trip currency
                double tempLoan = transactions.get(i).getLoan();
                if(!transactions.get(i).getCurrency().equals(baseCurrency)){
                    tempLoan=tempLoan/(Double)currencyMap.get(transactions.get(i).getCurrency());
                    tempLoan= Math.round(tempLoan*100.0)/100.0;
                    otherCurrency=true;
                }
                if(otherCurrency){
                    billString+= transactions.get(i).getLocation()+": "+transactions.get(i).getCreditor()+" owes "+
                            transactions.get(i).getDebtor()+" "+transactions.get(i).getLoan()+" "+transactions.get(i).getCurrency()
                            +" ("+tempLoan+" "+baseCurrency+")"+"\n\n";
                }else{
                    billString+= transactions.get(i).getLocation()+": "+transactions.get(i).getCreditor()+" owes "+
                            transactions.get(i).getDebtor()+" "+transactions.get(i).getLoan()+" "+transactions.get(i).getCurrency()
                            +"\n\n";
                }

                if(transactions.get(i).getDebtor().equals(userA.geteMail())){
                    status-=tempLoan;
                }else {
                    status+=tempLoan;
                }

                status=Math.round(status*100.0)/100.0;

            }
            billString+="------------Sum-------------"+"\n";
            if(status>=0){
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
