import org.apache.commons.configuration.ConfigurationException;
import org.junit.Test;


import java.io.IOException;

public class TestRunner {

    Customer customer;

    @Test
    public void doLogin() throws IOException, ConfigurationException {
        customer = new Customer();
        customer.callingCustomerLoginAPI();

    }

    @Test
    public void getCustomerList() throws IOException {
        customer = new Customer();
        customer.callingCustomerListAPI();

    }

    @Test
    public void searchCustomer() throws IOException {
        customer = new Customer();
        customer.callingSearchCustomerAPI();

    }
    @Test
    public void GenerateCustomer() throws IOException, ConfigurationException {
        customer = new Customer();
        customer.callingGenerateCustomerAPI();

    }
    @Test
    public void createCustomer() throws IOException, ConfigurationException {
        customer = new Customer();
        customer.callingCreateCustomerAPI();

    }

    @Test
    public void updateCustomer() throws IOException {
        customer = new Customer();
        customer.updateCustomerAPI();

    }
    @Test
    public void deleteCustomer() throws IOException {
        customer = new Customer();
        customer.deleteCustomerAPI();

    }


}
