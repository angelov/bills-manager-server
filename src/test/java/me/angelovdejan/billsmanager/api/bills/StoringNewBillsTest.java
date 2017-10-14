package me.angelovdejan.billsmanager.api.bills;

import me.angelovdejan.billsmanager.api.ApiTest;
import me.angelovdejan.billsmanager.bills.Bill;
import me.angelovdejan.billsmanager.bills.BillState;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StoringNewBillsTest extends ApiTest {

    @Test
    public void storing_bill_with_valid_information() throws Exception {
        Bill bill = new Bill();
        bill.setState(BillState.RECEIVED);
        bill.setTitle("New bill");

        mockMvc.perform(
                post("/bills/")
                        .content(asJsonString(bill))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.message", is("Bill stored successfully"))
        );
    }

}
