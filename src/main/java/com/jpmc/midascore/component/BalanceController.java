package com.jpmc.midascore.component;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.repository.UserRepository;



@RestController
@RequestMapping("/balance")
public class BalanceController {

    private final UserRepository userRepository;

    public BalanceController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping()
    @ResponseBody //serialises to json!
    public Balance getBalanceEndpoint(@RequestParam String userId) {
        
        //look for id in users: found return balance object else return 0
        Balance responsBalance = new Balance();
        long idAsLong = Long.parseLong(userId); 
        UserRecord user = userRepository.findById(idAsLong);
        if(user == null) {
            responsBalance.setAmount(0);
            return responsBalance;
        }
        else {
            responsBalance.setAmount(user.getBalance());
            return responsBalance;
        }
    }
    
}
