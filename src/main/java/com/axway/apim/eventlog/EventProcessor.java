package com.axway.apim.eventlog;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import io.micrometer.core.instrument.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventProcessor implements ItemProcessor<Resource, List<Transaction>> {
    Logger logger = LoggerFactory.getLogger(EventProcessor.class);
    Configuration configuration = Configuration.defaultConfiguration().addOptions(Option.SUPPRESS_EXCEPTIONS);
    ;


//    @Override
//    public String process(String item) throws Exception {
//        logger.info("{}", item);
//        return null;
//    }

    @Override
    public List<Transaction> process(Resource item) throws Exception {
        logger.info("Processing file : {}", item);
        List<Transaction> transactions = new ArrayList<>();
        try (InputStream inputStream = item.getInputStream(); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                DocumentContext documentContext = JsonPath.using(configuration).parse(line);
                String type = documentContext.read("$.type");
                if (type.equals("transaction")) {
                    String path = documentContext.read("$.path");
                    Date time = documentContext.read("$.time", Date.class);
                    int duration = getInt(documentContext,"$.duration");
                    String status = documentContext.read("$.status");
                    Transaction transaction = new Transaction();
                    String apiName = documentContext.read("$.serviceContexts[0].service");
                    String methodName = documentContext.read("$.serviceContexts[0].method");
                    int backendDuration = getInt(documentContext, "$.legs[1].duration");
                    String backendHost = documentContext.read("$.legs[1].remoteName");

                    transaction.setApiName(apiName);
                    transaction.setPath(path);
                    transaction.setApiMethodName(methodName);
                    transaction.setBackendTime(backendDuration);
                    transaction.setBackendHost(backendHost);
                    transaction.setDate(time);
                    transaction.setStatus(status);
                    transaction.setOverallDuration(duration);
                    transactions.add(transaction);
                }
            }
        }
        return transactions;
    }

    public int getInt(DocumentContext documentContext, String jsonPath) {
        try {
            return documentContext.read(jsonPath);
        } catch (NullPointerException e) {
            return 0;
        }
    }
}
