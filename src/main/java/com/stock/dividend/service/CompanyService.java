package com.stock.dividend.service;

import com.stock.dividend.model.Company;
import com.stock.dividend.model.ScrapedResult;
import com.stock.dividend.persist.CompanyRepository;
import com.stock.dividend.persist.DividendRepository;
import com.stock.dividend.persist.entity.CompanyEntity;
import com.stock.dividend.persist.entity.DividendEntity;
import com.stock.dividend.scraper.Scraper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanyService {

    private  final Scraper yahooFinanveScraper;

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;
    public Company save(String ticker) {
        boolean exists = this.companyRepository.existsByTicker(ticker);
        if (exists) {
            throw new RuntimeException("already exists ticker -> " + ticker);
        }
        return this.storeCompanyAndDividend(ticker);
    }

    public List<CompanyEntity> getAllCompany() {
        return this.companyRepository.findAll();
    }

    private Company storeCompanyAndDividend(String ticker) {
        // ticker 를 기준으로 회사를 스크래핑
        Company company = this.yahooFinanveScraper.scrapCompanyByTicker(ticker);
        if (ObjectUtils.isEmpty(company)) {
            throw new RuntimeException("failed to scrap ticker -> " + ticker);
        }

        //해당 회사가 존재할 경우, 회사의 배당금 정보를 스크래핑
        ScrapedResult scrapedResult = this.yahooFinanveScraper.scrap(company);

        // 스크래핑 결과
        CompanyEntity companyEntity = this.companyRepository.save(new CompanyEntity(company));
        List<DividendEntity> dividendEntityList = scrapedResult.getDividends().stream()
                .map(e -> new DividendEntity(companyEntity.getId(), e))
                .collect(Collectors.toList());
        this.dividendRepository.saveAll((dividendEntityList));
        return null;
    }
}
