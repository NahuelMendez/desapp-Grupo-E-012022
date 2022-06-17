package ar.edu.unq.desapp.grupoE.backenddesappapi.service;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.Intention;
import ar.edu.unq.desapp.grupoE.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoE.backenddesappapi.persistence.IntentionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class IntentionService {

    @Autowired
    private IntentionRepository intentionRepository;

    public List<Intention> findAllActiveIntentions() {
        return intentionRepository.findAllActiveIntentions();
    }
}
