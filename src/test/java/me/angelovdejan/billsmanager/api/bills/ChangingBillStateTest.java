package me.angelovdejan.billsmanager.api.bills;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.angelovdejan.billsmanager.BillsManagerApplication;
import me.angelovdejan.billsmanager.api.requests.ChangeStateRequest;
import me.angelovdejan.billsmanager.bills.Bill;
import me.angelovdejan.billsmanager.bills.BillState;
import me.angelovdejan.billsmanager.bills.repositories.BillsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.logging.Logger;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BillsManagerApplication.class)
@WebAppConfiguration
public class ChangingBillStateTest {

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
    public void trying_to_change_state_of_non_existing_bill() throws Exception {
        mockMvc.perform(
                    put("/bills/10/changeState")
                    .content(asJsonString(new ChangeStateRequest("RECEIVED")))
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(jsonPath("$.message", is("Bill not found")));
    }

    @Test
    public void trying_to_set_invalid_state_to_bill() throws Exception {
        Bill bill = prepareBill();

        mockMvc.perform(
                put("/bills/{id}/changeState", bill.getId())
                    .content(asJsonString(new ChangeStateRequest("INVALID STATE")))
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Invalid bill state")));
    }

    @Test
    public void changing_bill_state() throws Exception {
        Bill b1 = prepareBill(BillState.RECEIVED);

        mockMvc.perform(
                    put("/bills/{id}/changeState", b1.getId())
                    .content(asJsonString(new ChangeStateRequest("PAID")))
                    .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Bill state changed")));

        b1 = bills.findOne(b1.getId());

        assertThat(b1.getState(), is(BillState.PAID));
    }

    // took this from some SO answer
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Bill prepareBill() {
        return prepareBill(BillState.RECEIVED);
    }

    private Bill prepareBill(BillState billState) {
        Bill bill = new Bill();
        bill.setTitle("Electricity");
        bill.setState(billState);

        this.bills.save(bill);

        return bill;
    }
}
