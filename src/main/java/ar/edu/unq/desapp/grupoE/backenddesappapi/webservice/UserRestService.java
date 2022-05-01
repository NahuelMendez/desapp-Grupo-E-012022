package ar.edu.unq.desapp.grupoE.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoE.backenddesappapi.model.UserException;
import ar.edu.unq.desapp.grupoE.backenddesappapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@EnableAutoConfiguration
@Validated
public class UserRestService {

    @Autowired
    private UserService userService;

    @GetMapping("/api/users")
    public ResponseEntity<List<User>> allUsers() {
        List<User> list = userService.findAll();;
        return ResponseEntity.ok().body(list);
    }

    @PostMapping(value = "/api/users", consumes = {MediaType.ALL_VALUE})
    public UserDTO registrar(@Valid @RequestBody UserDTO userDTO) throws UserException {
        User user = userDTO.createUser();
        userService.save(user);
        return userDTO;
    }
}
