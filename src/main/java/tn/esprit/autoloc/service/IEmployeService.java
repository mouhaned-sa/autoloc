package tn.esprit.autoloc.service;

import tn.esprit.autoloc.dto.EmployeRequest;
import tn.esprit.autoloc.dto.EmployeResponse;
import java.util.List;

public interface IEmployeService {
    List<EmployeResponse> retrieveAllEmployes();
    EmployeResponse addEmploye(EmployeRequest e);
    EmployeResponse updateEmploye(Long idEmploye, EmployeRequest e);
    EmployeResponse retrieveEmploye(Long idEmploye);
    void removeEmploye(Long idEmploye);
    List<EmployeResponse> addEmployes(List<EmployeRequest> employes);
}
