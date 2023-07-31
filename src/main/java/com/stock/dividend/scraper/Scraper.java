package com.stock.dividend.scraper;

import com.stock.dividend.model.Company;
import com.stock.dividend.model.ScrapedResult;

public interface Scraper {
    Company scrapCompanyByTicker(String ricker);
    ScrapedResult scrap(Company company);
}
