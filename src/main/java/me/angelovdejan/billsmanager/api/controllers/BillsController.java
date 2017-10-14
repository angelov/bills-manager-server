package me.angelovdejan.billsmanager.api.controllers;

import me.angelovdejan.billsmanager.api.requests.ChangeStateRequest;
import me.angelovdejan.billsmanager.api.responses.BadRequestResponse;
import me.angelovdejan.billsmanager.api.responses.Response;
import me.angelovdejan.billsmanager.api.responses.SuccessfulResponse;
import me.angelovdejan.billsmanager.bills.Bill;
import me.angelovdejan.billsmanager.bills.BillState;
import me.angelovdejan.billsmanager.bills.repositories.BillsRepository;
import me.angelovdejan.billsmanager.core.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bills")
public class BillsController {

    private final BillsRepository bills;

    @Autowired
    public BillsController(BillsRepository bills) {
        this.bills = bills;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Bill> index() {
        return this.bills.findAll();
    }

    @RequestMapping(value = "/{id}/changeState")
    public ResponseEntity<Response> changeState(
            @PathVariable(name = "id") String id,
            @RequestBody ChangeStateRequest request
            ) throws ResourceNotFoundException {

        Bill bill = bills.findOne(Long.valueOf(id));
        String state = request.getState();

        if (bill == null) {
            throw new ResourceNotFoundException("Bill not found");
        }

        try {
            bill.setState(BillState.valueOf(state));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new BadRequestResponse("Invalid bill state"), HttpStatus.BAD_REQUEST);
        }

        bills.save(bill);

        return new ResponseEntity<>(new SuccessfulResponse("Bill state changed"), HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Response> store(Bill bill) {
        bills.save(bill);

        return new ResponseEntity<>(new SuccessfulResponse("Bill stored successfully"), HttpStatus.OK);
    }

}
