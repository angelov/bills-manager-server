package me.angelovdejan.billsmanager.api.bills;

import me.angelovdejan.billsmanager.BillsManagerApplication;
import me.angelovdejan.billsmanager.bills.Bill;
import me.angelovdejan.billsmanager.bills.BillState;
import me.angelovdejan.billsmanager.bills.repositories.BillsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillsManagerApplication.class)
@WebAppConfiguration
public class ListingBillsTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private BillsRepository bills;

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void it_returns_empty_array_if_there_are_no_bills() throws Exception {
        mockMvc.perform(get("/bills/"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void it_returns_list_of_bills() throws Exception {
        insertDummyBills();

        mockMvc.perform(get("/bills/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Electricity")))
                .andExpect(jsonPath("$[0].state", is("RECEIVED")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("House keeping")))
                .andExpect(jsonPath("$[1].state", is("PAID")));
    }

    private void insertDummyBills() {
        Bill b1 = new Bill();
        b1.setTitle("Electricity");
        b1.setState(BillState.RECEIVED);
        bills.save(b1);

        Bill b2 = new Bill();
        b2.setTitle("House keeping");
        b2.setState(BillState.PAID);
        bills.save(b2);
    }
}
