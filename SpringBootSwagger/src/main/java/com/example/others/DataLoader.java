package com.example.others;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.example.entity.User;
import com.example.repository.UserRepository;
 
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {
     
    private final UserRepository userRepository;
 
    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
 
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        User user_a = new User();
        user_a.setName("Jane");
        user_a.setEmail("jdoe@gmail.com");
        user_a.setPhone("202-555-1234");
        user_a.setCommPreference("email");
        userRepository.save(user_a);
         
        User user_b = new User();
        user_b.setName("Jack");
        user_b.setEmail("jfrost@gmail.com");
        user_b.setPhone("202-555-5678");
        user_b.setCommPreference("email");
        userRepository.save(user_b);
         
    }
}
