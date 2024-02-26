package org.example.config;

import lombok.RequiredArgsConstructor;
import org.example.domain.Company;
import org.example.domain.PricePerDay;
import org.example.repository.CompanyRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.http.*;
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

@RequiredArgsConstructor
public class PriceItemReader implements ItemReader<List<PricePerDay>> {
    private final String stockApi = "https://fchart.stock.naver.com/sise.nhn?symbol=";
    private final CompanyRepository companyRepository;
    @Override
    public List<PricePerDay> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        List<Company> companyList = companyRepository.findAll();
        List<PricePerDay> pricePerDayList = new ArrayList<>();

        for (Company company:companyList) {
            String itemCode = company.getItemCode();
            String api = stockApi + itemCode + "&timeframe=day&count=1&requestType=0";
            String result = fnGetAttribute(get(api));
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");

            String[] r = result.split("\\|");
            PricePerDay ppd = PricePerDay.builder().companyId(company.getId())
                        .date(LocalDate.parse(r[0], format))
                        .startPrice(Integer.parseInt(r[1]))
                        .highPrice(Integer.parseInt(r[2]))
                        .lowPrice(Integer.parseInt(r[3]))
                        .lastPrice(Integer.parseInt(r[4]))
                        .tradingVolume(Long.parseLong(r[5])).build();

            pricePerDayList.add(ppd);
        }
        return pricePerDayList;
    }

    public String get(String url){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public String fnGetAttribute(String xml){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc;
        String rAttribute = null;
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
                    rAttribute = ele.getAttribute("data");
                }
            }
        } catch (Exception e){
            throw new RuntimeException("xml 파싱 불가");
        }
        return rAttribute;
    }
}
