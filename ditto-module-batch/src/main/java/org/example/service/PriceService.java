package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.Company;
import org.example.domain.PricePerDay;
import org.example.global.exception.NoSuchCompanyException;
import org.example.repository.CompanyRepository;
import org.example.repository.PricePerDayRepository;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriceService {

    private final String stockApi = "https://fchart.stock.naver.com/sise.nhn?symbol=";
    private final CompanyRepository companyRepository;
    private final PricePerDayRepository pricePerDayRepository;

    public List<String> saveStockPrice(String itemCode) {
        String api = stockApi + itemCode + "&timeframe=day&count=365&requestType=0";
        List<String> results = fnGetAttribute(get(api));
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");

        Company company = companyRepository.findByItemCode(itemCode).orElseThrow(() -> {
            throw new NoSuchCompanyException();
        });

        List<PricePerDay> priceList = results.stream().map(res -> {
            String[] r = res.split("\\|");
            log.info(r[0] + r[1]);
            return PricePerDay.builder().companyId(company.getId())
                    .date(LocalDate.parse(r[0], format))
                    .startPrice(Integer.parseInt(r[1]))
                    .highPrice(Integer.parseInt(r[2]))
                    .lowPrice(Integer.parseInt(r[3]))
                    .lastPrice(Integer.parseInt(r[4]))
                    .tradingVolume(Long.parseLong(r[5])).build();
        }).collect(Collectors.toList());

        pricePerDayRepository.saveAll(priceList);

        return results;
    }

    public String get(String url){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public List<String> fnGetAttribute(String xml){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc;
        List<String> rAttribute = new ArrayList<>();
        try {
            InputSource is = new InputSource(new StringReader(xml));
            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);

            doc.getDocumentElement().normalize();

            NodeList children = doc.getElementsByTagName("item");

            for(int i = 0; i < children.getLength(); i++){
                Node node = children.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element ele = (Element) node;
                    rAttribute.add(ele.getAttribute("data"));
                }
            }
        } catch (Exception e){
            throw new RuntimeException("xml 파싱 불가");
        }
        return rAttribute;
    }
}
