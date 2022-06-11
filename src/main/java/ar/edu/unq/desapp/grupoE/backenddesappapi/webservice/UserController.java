package ar.edu.unq.desapp.grupoE.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoE.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoE.backenddesappapi.model.UserException;
import ar.edu.unq.desapp.grupoE.backenddesappapi.service.IntentionService;
import ar.edu.unq.desapp.grupoE.backenddesappapi.service.UserService;
import ar.edu.unq.desapp.grupoE.backenddesappapi.webservice.DTO.*;
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
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private IntentionService intentionService;

    @GetMapping("/api/users")
    public ResponseEntity<List<SimpleUser>> allUsers() {
        List<User> list = userService.findAll();
        List<SimpleUser> response = list.stream().map(SimpleUser::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/api/users", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserRegisterResponse> register(@Valid @RequestBody UserDTO userDTO) throws UserException {
        User user = userDTO.createUser();
        User userResponse = userService.save(user);
        return ResponseEntity.ok().body(new UserRegisterResponse(userResponse));
    }

    @PostMapping(value = "/api/users/{id}/intentions", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<IntentionResponse> expressIntention(@PathVariable("id") Integer id, @Valid @RequestBody IntentionDTO intentionDTO) throws UserException {
        Intention intention = userService.expressIntention(
                id,
                intentionDTO.getCrypto(),
                intentionDTO.getNominalAmount(),
                intentionDTO.getCryptoPrice(),
                intentionDTO.getOperation());
        return ResponseEntity.ok().body(new IntentionResponse(intention));
    }

    @GetMapping("/api/intentions")
    public ResponseEntity<List<IntentionResponse>> allActiveIntention() {
        List<Intention> list = intentionService.findAllActiveIntentions();
        List<IntentionResponse> response = list.stream().map(IntentionResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(response);
    }
}
