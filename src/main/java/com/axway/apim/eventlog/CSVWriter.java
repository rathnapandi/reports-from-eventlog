package com.axway.apim.eventlog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class CSVWriter implements ItemWriter<List<Transaction>> {
    Logger logger = LoggerFactory.getLogger(CSVWriter.class);
    File outputFile = new File("output_" + System.currentTimeMillis() + ".csv");

    final CsvMapper CSV_MAPPER = new CsvMapper();
//    final CsvSchema schema = CSV_MAPPER.schemaFor(Transaction.class)
//        .withHeader();

    final CsvSchema schemaWithoutHeader = CSV_MAPPER.schemaFor(Transaction.class);

    @Override
    public void write(Chunk<? extends List<Transaction>> chunk) throws Exception {
        logger.info("writer");
        try (FileWriter fileWriter = new FileWriter(outputFile, true)) {
            SequenceWriter sequenceWriter = CSV_MAPPER.writer(schemaWithoutHeader).writeValues(fileWriter);
            for (List<Transaction> transactions : chunk) {
                for (Transaction transaction : transactions) {
                    sequenceWriter.write(transaction);
                }
            }
        }
    }
}
