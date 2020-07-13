package com.mobilecomputing.pecunia;

import com.mobilecomputing.pecunia.application.BillingCalculator;
import com.mobilecomputing.pecunia.application.CurrencyManager;
import com.mobilecomputing.pecunia.application.MailManager;
import com.mobilecomputing.pecunia.repository.TripRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class PecuniaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PecuniaApplication.class, args);
    }
}


