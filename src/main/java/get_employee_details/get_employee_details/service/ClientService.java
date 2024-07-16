package get_employee_details.get_employee_details.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import get_employee_details.get_employee_details.dto.EmployeeDto;

@Service
// @AllArgsConstructor
public class ClientService {

    private final RestTemplate restTemplate;
    private final String url;

    @Autowired
    public ClientService(RestTemplate restTemplate, @Value("${employee.baseurl}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public List<EmployeeDto> getAllEmployeeDetails() {
        String endpoint = url + "/employee";
        EmployeeDto[] response = restTemplate.getForObject(endpoint, EmployeeDto[].class);
        return Arrays.asList(response);
    }

    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        String endpoint = url + "/employee";
        EmployeeDto response = restTemplate.postForObject(endpoint, employeeDto, EmployeeDto.class);
        return response;
    }

    // public List<EmployeeDto> getAllEmployeeDetails() {
    //     String endpoint = url + "/employee";
    //     ResponseEntity<EmployeeDto[]> response = restTemplate.getForEntity(endpoint, EmployeeDto[].class);
    //     return List.of(response.getBody());
    // }
}