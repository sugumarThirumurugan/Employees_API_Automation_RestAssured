package employees.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import employees.endpoints.Endpoints;
import employees.payloads.Payloads;
import employees.utilities.Utilities;
import io.restassured.response.Response;

public class Tests {

    Payloads payloads;
    String createEmployeeID;
    String getEmployeeID;
    String updateEmployeeID;
    String deleteEmployeeID;

    @DataProvider(name = "ExcelData")
    public String[][] getExcelData() throws Exception {
        Utilities utilities = new Utilities();
        int row = utilities.getRow("sheet1");
        int cell = utilities.getCell("sheet1");
        String[][] data = new String[row][cell];
        for (int i = 1; i <= row; i++) {
            for (int j = 0; j < cell; j++) {
                data[i - 1][j] = utilities.getSheetData("sheet1", i, j);
            }
        }
        return data;
    }

    @Test(priority = 1, dataProvider = "ExcelData")
    public void testCreateEmployee(String[] data) {
        payloads = new Payloads();
        payloads.setName(data[0]);
        payloads.setDesignation(data[1]);
        payloads.setDepartment(data[2]);

        Response createEmployeeResponse = Endpoints.createEmployee(payloads);
        Assert.assertEquals(createEmployeeResponse.getStatusCode(), 201);
        createEmployeeResponse.then().log().body();
        try {
            createEmployeeID = createEmployeeResponse.jsonPath().getString("id");
        } catch (Exception e) {
            System.out.println("Failed to parse JSON response for createEmployee: " + e.getMessage());
            
        }

        // Test Methods
        testGetEmployee(createEmployeeID);
        testUpdateEmployee(createEmployeeID);
        testDeleteEmployee(createEmployeeID);
    }

    public void testGetEmployee(String employeeID) {
        Response getEmployeeResponse = Endpoints.getEmployee(employeeID);
        try {
        	
            getEmployeeID = getEmployeeResponse.jsonPath().getString("id");
        } catch (Exception e) {
            System.out.println("Failed to parse JSON response for getEmployee: " + e.getMessage());
           
        }

        Assert.assertEquals(employeeID, getEmployeeID);
        Assert.assertEquals(getEmployeeResponse.getStatusCode(), 200);
        getEmployeeResponse.then().log().body();
    }

    public void testUpdateEmployee(String employeeID) {
        payloads.setDesignation("Software QA");
        Response updateEmployeeResponse = Endpoints.updateEmployee(employeeID, payloads);

        try {
            updateEmployeeID = updateEmployeeResponse.jsonPath().getString("id");
        } catch (Exception e) {
            System.out.println("Failed to parse JSON response for updateEmployee: " + e.getMessage());
           
        }

        Assert.assertEquals(employeeID, updateEmployeeID);
        Assert.assertEquals(updateEmployeeResponse.getStatusCode(), 200);
        updateEmployeeResponse.then().log().body();
    }

    public void testDeleteEmployee(String employeeID) {
        Response deleteEmployeeResponse = Endpoints.deleteEmployee(employeeID);

        try {
            deleteEmployeeID = deleteEmployeeResponse.jsonPath().getString("id");
        } catch (Exception e) {
            System.out.println("Failed to parse JSON response for deleteEmployee: " + e.getMessage());
            Assert.fail("Invalid JSON response");
        }

        Assert.assertEquals(employeeID, deleteEmployeeID);
        Assert.assertEquals(deleteEmployeeResponse.getStatusCode(), 200);
        deleteEmployeeResponse.then().log().body();
    }
}
