package tn.esprit.autoloc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.dto.ClientRequest;
import tn.esprit.autoloc.dto.ClientResponse;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.mapper.ClientMapper;
import tn.esprit.autoloc.repository.ClientRepository;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final ClientRepository clientRepository;

    @Override
    public List<ClientResponse> retrieveAllClients() {
        log.info("Récupération de tous les clients");
        return clientRepository.findAll().stream()
                .map(ClientMapper::toResponse)
                .toList();
    }

    @Override
    public ClientResponse addClient(ClientRequest c) {
        log.info("Ajout d'un nouveau client : {} {}", c.getPrenom(), c.getNom());
        Client saved = clientRepository.save(ClientMapper.toEntity(c));
        log.info("Client créé avec l'id {}", saved.getIdClient());
        return ClientMapper.toResponse(saved);
    }

    @Override
    public ClientResponse updateClient(Long idClient, ClientRequest c) {
        log.info("Mise à jour du client {}", idClient);
        Client existing = retrieveClientEntity(idClient);
        existing.setNom(c.getNom());
        existing.setPrenom(c.getPrenom());
        existing.setEmail(c.getEmail());
        existing.setTelephone(c.getTelephone());
        existing.setNumPermis(c.getNumPermis());
        existing.setDateInscription(c.getDateInscription());
        Client updated = clientRepository.save(existing);
        log.info("Client {} mis à jour", idClient);
        return ClientMapper.toResponse(updated);
    }

    @Override
    public ClientResponse retrieveClient(Long idClient) {
        return ClientMapper.toResponse(retrieveClientEntity(idClient));
    }

    @Override
    public void removeClient(Long idClient) {
        checkExistence(idClient);
        clientRepository.deleteById(idClient);
        log.info("Client {} supprimé", idClient);
    }

    @Override
    public List<ClientResponse> addClients(List<ClientRequest> clients) {
        log.info("Ajout de {} clients", clients.size());
        return clientRepository.saveAll(clients.stream().map(ClientMapper::toEntity).toList()).stream()
                .map(ClientMapper::toResponse)
                .toList();
    }

    private Client retrieveClientEntity(Long idClient) {
        return clientRepository.findById(idClient)
                .orElseThrow(() -> new ResourceNotFoundException("Client", idClient));
    }

    private void checkExistence(Long idClient) {
        if (!clientRepository.existsById(idClient)) {
            throw new ResourceNotFoundException("Client", idClient);
        }
    }
}
