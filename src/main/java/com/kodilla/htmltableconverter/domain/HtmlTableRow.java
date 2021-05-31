package com.kodilla.htmltableconverter.domain;

import java.util.ArrayList;
import java.util.List;

import static com.kodilla.htmltableconverter.domain.TableFormatter.CELL_DELIMITER;

public class HtmlTableRow {

    List<String> cells = new ArrayList<>();

    public HtmlTableRow(List<String> cells) {
        this.cells = cells;
    }

    public List<String> getCells() {
        return cells;
    }

    public String toFormattedString() {
        StringBuilder sb = new StringBuilder("| ");
        for (int cellIndex = 0; cellIndex < getCells().size(); cellIndex++) {
            String cell = getCells().get(cellIndex);
            sb.append(cell)
                    .append(TableFormatter.createTrailingSpacesForCell(cellIndex, cell))
                    .append(CELL_DELIMITER);
        }
        return sb.toString();
    }

}
