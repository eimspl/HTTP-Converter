package com.kodilla.htmltableconverter.controller;

import com.kodilla.htmltableconverter.domain.HtmlTable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/table/")
public class HtmlTableController {

    @ResponseBody
    @PostMapping(path = "add")
    public String acceptCustomTextType(@RequestBody HtmlTable table) {
        String formattedTableOutput = table.toFormattedString();
        System.out.println(formattedTableOutput);
        return formattedTableOutput;
    }
}