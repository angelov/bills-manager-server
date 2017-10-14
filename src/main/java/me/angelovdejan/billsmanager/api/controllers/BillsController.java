package me.angelovdejan.billsmanager.api.controllers;

import me.angelovdejan.billsmanager.bills.Bill;
import me.angelovdejan.billsmanager.bills.repositories.BillsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
