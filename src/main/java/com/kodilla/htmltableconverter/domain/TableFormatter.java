package com.kodilla.htmltableconverter.domain;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

public class TableFormatter {
    public static final String CELL_DELIMITER = " | ";
    private static HtmlTable table;

    public static void init(HtmlTable htmlTable) {
        table = htmlTable;
    }

    public static String createHorizontalLine() {
        int cellsCount = table.getRows().get(0).getCells().size();
        int additionalCharactersCount = cellsCount * (CELL_DELIMITER.length() - 1) + CELL_DELIMITER.length();
        return "-" .repeat(getMaxRowWitdh() + additionalCharactersCount);
    }

    public static int getMaxRowWitdh() {
        return getMaxCellWidths(table.getRows()).stream()
                .reduce(0, Integer::sum);
    }

    public static String createTrailingSpacesForCell(int cellIndex, String cell) {
        return " " .repeat(getRemainingSpacesCount(cell, cellIndex));
    }

    private static int getRemainingSpacesCount(String cell, int cellIndex) {
        Integer maxLength = getMaxCellWidths(table.getRows()).get(cellIndex);
        if (maxLength != null) {
            int difference = maxLength - cell.length();
            return Math.max(difference, 0);
        }
        return cell.length();
    }

    private static List<Integer> getMaxCellWidths(List<HtmlTableRow> rows) {
        List<Integer> maxCellsWidths = new ArrayList<>();
        for (HtmlTableRow row : rows) {
            List<String> cells = row.getCells();
            for (int i = 0; i < cells.size(); i++) {
                int currentLength = cells.get(i).length();
                if (maxCellsWidths.size() >= i + 1) {
                    int max = max(currentLength, maxCellsWidths.get(i));
                    maxCellsWidths.set(i, max);
                } else {
                    maxCellsWidths.add(currentLength);
                }
            }
        }
        return maxCellsWidths;
    }

}
