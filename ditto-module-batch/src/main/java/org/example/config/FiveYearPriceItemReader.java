package org.example.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class FiveYearPriceItemReader implements ItemReader<List<PricePerDay>> {
    private final String stockApi = "https://fchart.stock.naver.com/sise.nhn?symbol=";
    private final CompanyRepository companyRepository;
    @Override
    public List<PricePerDay> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        List<Company> companyList = companyRepository.findAll();
        List<CompletableFuture<List<PricePerDay>>> prices = new ArrayList<>();
        List<PricePerDay> priceList = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        int threadSize = 1;

        for (Company company : companyList){
            CompletableFuture<List<PricePerDay>> price = CompletableFuture.supplyAsync(
                    () -> {
                        String itemCode = company.getItemCode();
                        String api = stockApi + itemCode + "&timeframe=day&count=10&requestType=0";
                        List<String> results = fnGetAttribute(get(api));
                        log.info(String.valueOf(company.getId()));
                        log.info(results.get(0));
                        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");

                        List<PricePerDay> priceList1 = results.stream().map(res -> {
                            String[] r = res.split("\\|");
                            return PricePerDay.builder().companyId(company.getId())
                                    .date(LocalDate.parse(r[0], format))
                                    .startPrice(Integer.parseInt(r[1]))
                                    .highPrice(Integer.parseInt(r[2]))
                                    .lowPrice(Integer.parseInt(r[3]))
                                    .lastPrice(Integer.parseInt(r[4]))
                                    .tradingVolume(Long.parseLong(r[5])).build();
                        }).collect(Collectors.toList());
                        return priceList1;
                    }, executorService);
            prices.add(price);

            if (prices.size() == threadSize){
                log.info("print");
                prices.forEach(price1 -> {
                    try {
                        priceList.addAll(price1.get());
                        log.info(String.valueOf(priceList.size()));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                });
                prices.clear();
            }
        }
        log.info("끝");
        return priceList;
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
