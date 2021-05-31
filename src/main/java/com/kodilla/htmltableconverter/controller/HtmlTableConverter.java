package com.kodilla.htmltableconverter.controller;

import com.kodilla.htmltableconverter.domain.HtmlTable;
import com.kodilla.htmltableconverter.domain.HtmlTableRow;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HtmlTableConverter implements HttpMessageConverter<Object> {

    public static final String TR_TAG_CLOSED = "</tr>";
    public static final String TD_TAG_CLOSED = "</td>";
    public static final String TH_TAG_CLOSED = "</th>";
    public static final String WHITE_SPACE = "\\s";
    public static final String EMPTY_STRING = "";
    public static final String TR_SEPARATOR = "TR_SEPARATOR";
    public static final String TD_SEPARATOR = "TD_SEPARATOR";
    public static final String TH_SEPARATOR = "TH_SEPARATOR";
    public static final String OPEN_TAG = "<";
    public static final String CLOSE_TAG = ">";


    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return clazz.getName().equals("com.kodilla.htmltableconverter.domain.HtmlTable") &&
                mediaType.getSubtype().equals("plain") && mediaType.getType().equals("text");

    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return clazz.getName().equals("com.kodilla.htmltableconverter.domain.HtmlTable") &&
                mediaType.getSubtype().equals("plain") && mediaType.getType().equals("text");

    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return List.of(MediaType.ALL);
    }

    @Override
    public Object read(Class<?> clazz, HttpInputMessage inputMessage) throws IOException {
        StringBuilder builder = new StringBuilder();

        try (Reader reader = new BufferedReader(
                new InputStreamReader(
                        inputMessage.getBody(),
                        Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;

            while ((c = reader.read()) != -1)
                builder.append((char) c);
        }

        List<HtmlTableRow> tableRows = parseHtmlTableRows(builder.toString());
        return new HtmlTable(tableRows);
    }

    private List<HtmlTableRow> parseHtmlTableRows(String tableHtmlCode) {
        tableHtmlCode = cleanHtmlText(tableHtmlCode);
        String[] rows = tableHtmlCode.split(TR_SEPARATOR);
        return parseTableRows(rows);
    }

    private String cleanHtmlText(String htmlTableCode) {
        htmlTableCode = htmlTableCode.replaceAll(WHITE_SPACE, EMPTY_STRING);
        htmlTableCode = htmlTableCode.replace(TR_TAG_CLOSED, TR_SEPARATOR);
        htmlTableCode = htmlTableCode.replace(TD_TAG_CLOSED, TD_SEPARATOR);
        htmlTableCode = htmlTableCode.replace(TH_TAG_CLOSED, TH_SEPARATOR);

        while (htmlTableCode.contains(OPEN_TAG) && htmlTableCode.contains(CLOSE_TAG)) {
            String str = htmlTableCode.substring(htmlTableCode.indexOf(OPEN_TAG), htmlTableCode.indexOf(CLOSE_TAG) + 1);
            htmlTableCode = htmlTableCode.replace(str, EMPTY_STRING);
        }
        return htmlTableCode;
    }

    private List<HtmlTableRow> parseTableRows(String[] rows) {
        return Stream.of(rows)
                .map(row -> {
                    String[] cells;
                    cells = row.split(TD_SEPARATOR);
                    List<String> collectedCells = Stream.of(cells).map(cell -> cell.split(TH_SEPARATOR))
                            .flatMap(Arrays::stream)
                            .collect(Collectors.toList());
                    return new HtmlTableRow(collectedCells);
                }).collect(Collectors.toList());
    }


    @Override
    public void write(
            Object o, MediaType contentType, HttpOutputMessage outputMessage) throws IOException,
            HttpMessageNotWritableException {

    }

}