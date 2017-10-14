package me.angelovdejan.billsmanager.api.bills;

import me.angelovdejan.billsmanager.api.ApiTest;
import me.angelovdejan.billsmanager.bills.Bill;
import me.angelovdejan.billsmanager.bills.BillState;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ListingBillsTest extends ApiTest {

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
