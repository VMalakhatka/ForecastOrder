package org.example.repository;

import jakarta.annotation.PostConstruct;
import org.example.entity.entity_enum.Authority;
import org.example.entity.user.User;
import org.example.entity.user.credentials.Credentials;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader {
 private final UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    private void loadData(){
     //   c=userRepository.findAll();
   //     if(userList.isEmpty()) {
          User user=new User();
          user.setCredentials(new Credentials("buh","1"));
          user.setFirstName("BUH");
          user.setLastName("BUH");
          user.getAuthorities().add(Authority.ROLE_WRITE);
          user.getAuthorities().add(Authority.ROLE_READ);
          User user1= new User();
          user1.setCredentials(new Credentials("user","1"));
          user1.setLastName("User");
          user1.setFirstName("Pety");
          user1.getAuthorities().add(Authority.ROLE_READ);
          List<User> userList=List.of(user,user1);
          userRepository.deleteAll();
         // userRepository.save(user);
        userRepository.saveAll(userList);
   //     }

    }

}
