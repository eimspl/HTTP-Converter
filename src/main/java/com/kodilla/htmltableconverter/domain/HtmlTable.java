package com.kodilla.htmltableconverter.domain;

import java.util.ArrayList;
import java.util.List;

import static com.kodilla.htmltableconverter.domain.TableFormatter.createHorizontalLine;


public class HtmlTable {

    List<HtmlTableRow> rows = new ArrayList<>();

    public HtmlTable(List<HtmlTableRow> rows) {
        this.rows = rows;
        TableFormatter.init(this);
    }

    public List<HtmlTableRow> getRows() {
        return rows;
    }

    public String toFormattedString() {
        StringBuilder sb = new StringBuilder()
                .append(createHorizontalLine());
        getRows().forEach(row -> sb.append("\n")
                .append(row.toFormattedString())
                .append("\n")
                .append(createHorizontalLine())
        );

        return sb.toString();
    }


}
