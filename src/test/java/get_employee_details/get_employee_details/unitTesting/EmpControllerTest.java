package get_employee_details.get_employee_details.unitTesting;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import get_employee_details.get_employee_details.controller.ClientController;
import get_employee_details.get_employee_details.dto.EmployeeDto;
import get_employee_details.get_employee_details.service.ClientService;

@WebMvcTest(ClientController.class)
@ExtendWith(SpringExtension.class)
public class EmpControllerTest {
    @MockBean
    ClientService clientService;

    @InjectMocks
    ClientController clientController;

    @Autowired
    MockMvc mockMvc;

    EmployeeDto employee1;
    EmployeeDto employee2;
    List<EmployeeDto> employees;

    @BeforeEach
    public void init() {
        employee1 = new EmployeeDto(1, "priyanka", "Reddy", "priya@gmail.com");
        employee2 = new EmployeeDto(2, "nandhini", "Reddy", "nandhini@gmail.com");
        employees = Arrays.asList(employee1, employee2);
    }

    @Test
    public void testInsertEmployee() throws Exception {
        // Mock service method
        when(clientService.createEmployee(Mockito.any(EmployeeDto.class))).thenReturn(employee1);
        mockMvc.perform(MockMvcRequestBuilders.post("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{ \"empNo\": 1, \"firstName\": \"priyanka\", \"lastName\": \"Reddy\", \"email\": \"priya@gmail.com\" }")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.empNo").value(1))
                .andExpect(jsonPath("$.firstName").value("priyanka"))
                .andExpect(jsonPath("$.lastName").value("Reddy"))
                .andExpect(jsonPath("$.email").value("priya@gmail.com"));
    }

    @Test
    public void testGetAllEmployees() throws Exception {

        // Mock service method
        when(clientService.getAllEmployeeDetails()).thenReturn(employees);

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/employee")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(employees.size()))
                .andExpect(jsonPath("$[0].empNo").value(employee1.getEmpNo()))
                .andExpect(jsonPath("$[0].firstName").value(employee1.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(employee1.getLastName()))
                .andExpect(jsonPath("$[0].email").value(employee1.getEmail()))
                .andExpect(jsonPath("$[1].empNo").value(employee2.getEmpNo()))
                .andExpect(jsonPath("$[1].firstName").value(employee2.getFirstName()))
                .andExpect(jsonPath("$[1].lastName").value(employee2.getLastName()))
                .andExpect(jsonPath("$[1].email").value(employee2.getEmail()));
    }
}