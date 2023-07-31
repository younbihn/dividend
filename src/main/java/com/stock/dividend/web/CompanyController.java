package com.stock.dividend.web;

import com.stock.dividend.model.Company;
import com.stock.dividend.persist.CompanyRepository;
import com.stock.dividend.persist.entity.CompanyEntity;
import com.stock.dividend.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/company")
@AllArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("/autocomplete")
    public ResponseEntity<?> autocomplete(@RequestParam String keyword) {
        return null;
    }

    @GetMapping
    public ResponseEntity<?> searchCompany() {
        List<CompanyEntity> companies = this.companyService.getAllCompany();
        return ResponseEntity.ok(companies);
    }

    @PostMapping
    public ResponseEntity<?> addCompany(@RequestBody Company request) {
        String ticker = request.getTicker().trim();
        if (ObjectUtils.isEmpty(ticker)) {
            throw new RuntimeException("tikcer is empty");
        }

        Company company = this.companyService.save(ticker);

        return ResponseEntity.ok(company);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteCompany() {
        return null;
    }
}
