package by.bsuir.spp.jewelryrentsystem.service.impl.document.csv;

import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.constraint.StrNotNullOrEmpty;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvListWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

@Service
public class CsvBuilder {
    public void buildDocument(OutputStream outputStream, String[] header, List<List<String>> data) throws IOException {
        CellProcessor[] processors = getProcessorsForCsvGeneration(header.length);

        try (ICsvListWriter listWriter = new CsvListWriter(new OutputStreamWriter(outputStream),
                CsvPreference.STANDARD_PREFERENCE)) {
            listWriter.writeHeader(header);

            for (List<String> row : data) {
                listWriter.write(row, processors);
            }
        }
    }

    private CellProcessor[] getProcessorsForCsvGeneration(int columnsAmount) {
        CellProcessor[] processors = new CellProcessor[columnsAmount];

        for (int i = 0; i < columnsAmount; i++) {
            processors[i] = new StrNotNullOrEmpty();
        }

        return processors;
    }
}
