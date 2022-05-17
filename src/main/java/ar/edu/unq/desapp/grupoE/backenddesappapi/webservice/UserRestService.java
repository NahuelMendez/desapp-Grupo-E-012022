package ar.edu.unq.desapp.grupoE.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoE.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoE.backenddesappapi.model.UserException;
import ar.edu.unq.desapp.grupoE.backenddesappapi.service.IntentionService;
import ar.edu.unq.desapp.grupoE.backenddesappapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@EnableAutoConfiguration
@Validated
public class UserRestService {

    @Autowired
    private UserService userService;
    @Autowired
    private IntentionService intentionService;

    @GetMapping("/api/users")
    public ResponseEntity<List<User>> allUsers() {
        List<User> list = userService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @PostMapping(value = "/api/users", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public UserDTO register(@Valid @RequestBody UserDTO userDTO) throws UserException {
        User user = userDTO.createUser();
        userService.save(user);
        return userDTO;
    }

    @PostMapping(value = "/api/users/{id}/intention", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public IntentionDTO expressIntention(@PathVariable("id") Integer id, @Valid @RequestBody IntentionDTO intentionDTO) throws UserException {
        userService.expressIntention(
                id,
                intentionDTO.getCrypto(),
                intentionDTO.getNominalAmount(),
                intentionDTO.getCryptoPrice(),
                intentionDTO.getOperationAmount(),
                intentionDTO.getOperation());
        return intentionDTO;
    }

    @GetMapping("/api/users/intentions")
    public ResponseEntity<List<IntentionResponse>> allIntention() {
        List<Intention> list = intentionService.findAll();
        List<IntentionResponse> response = list.stream().map(IntentionResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(response);
    }
}
