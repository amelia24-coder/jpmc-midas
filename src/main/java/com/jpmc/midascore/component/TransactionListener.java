package com.jpmc.midascore.component;  
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;



@Component
public class TransactionListener {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    

    public TransactionListener(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-core")
    public void listen(Transaction transaction) {
        // System.out.println(transaction.getAmount());
        //if senderId in userRecord and recipientId in userRecord and amount <= senderID balance
        UserRecord sender = userRepository.findById(transaction.getSenderId());
        UserRecord recipient = userRepository.findById(transaction.getRecipientId());

        if (sender == null || recipient == null) {
            //failure
            return;
        }
        else if (sender.getBalance() >= transaction.getAmount()) {
            //success

            //Task4
            RestTemplate restTemplate = new RestTemplate();
            Incentive i = restTemplate.postForObject("http://localhost:8080/incentive", transaction, Incentive.class);
            long incentiveAmount = i.getIncentiveAmount();

            sender.setBalance(sender.getBalance() - transaction.getAmount());
            recipient.setBalance(recipient.getBalance() + transaction.getAmount() + incentiveAmount);
            userRepository.save(sender);
            userRepository.save(recipient);

            TransactionRecord t = new TransactionRecord(transaction.getSenderId(), transaction.getRecipientId(), transaction.getAmount(), incentiveAmount);
            transactionRepository.save(t);

        }
        else {
            return;
            //failure
        }
    }
}

