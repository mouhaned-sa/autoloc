package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.ClientRequest;
import tn.esprit.autoloc.dto.ClientResponse;
import java.util.List;

public interface IClientService {
    List<ClientResponse> retrieveAllClients();
    ClientResponse addClient(ClientRequest c);
    ClientResponse updateClient(Long idClient, ClientRequest c);
    ClientResponse retrieveClient(Long idClient);
    void removeClient(Long idClient);
    List<ClientResponse> addClients(List<ClientRequest> clients);
}
