package get_employee_details.get_employee_details.unitTesting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import get_employee_details.get_employee_details.dto.EmployeeDto;
import get_employee_details.get_employee_details.service.ClientService;

@SpringBootTest
public class ClientServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ClientService clientService;

    @Value("${employee.baseurl}")
    private String baseUrl;

    private EmployeeDto employee1;
    private EmployeeDto employee2;
    private List<EmployeeDto> employees;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        employee1 = new EmployeeDto(1, "priyanka", "Reddy", "priya@gmail.com");
        employee2 = new EmployeeDto(2, "nandhini", "Reddy", "nandhini@gmail.com");
        employees = Arrays.asList(employee1, employee2);

        clientService = new ClientService(restTemplate, baseUrl);
    }

    @Test
    public void testGetAllEmployeeDetails() {
        String url = baseUrl + "/employee";
        when(restTemplate.getForObject(url, EmployeeDto[].class)).thenReturn(employees.toArray(new EmployeeDto[0]));

        List<EmployeeDto> result = clientService.getAllEmployeeDetails();
        assertEquals(2, result.size());
        assertEquals("priyanka", result.get(0).getFirstName());
        assertEquals("nandhini", result.get(1).getFirstName());
    }

    @Test
    public void testCreateEmployee() {
        String url = baseUrl + "/employee";
        when(restTemplate.postForObject(url, employee1, EmployeeDto.class)).thenReturn(employee1);

        EmployeeDto result = clientService.createEmployee(employee1);
        assertEquals("priyanka", result.getFirstName());
        assertEquals("Reddy", result.getLastName());
        assertEquals("priya@gmail.com", result.getEmail());
    }
}
