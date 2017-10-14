package me.angelovdejan.billsmanager.bills.repositories;

import me.angelovdejan.billsmanager.bills.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillsRepository extends JpaRepository<Bill, Long> {
}
