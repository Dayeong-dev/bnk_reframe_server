package com.example.reframe.util;

import java.util.Arrays;

import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

public class MarkdownUtil {
	private static final MutableDataSet options = new MutableDataSet()
	        .set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create()));	// 표 지원 추가
	
	private static final Parser parser = Parser.builder(options).build();
    private static final HtmlRenderer renderer = HtmlRenderer.builder(options).build();

    public static String toHtml(String markdown) {
        Node document = parser.parse(markdown);
        return renderer.render(document);
    }
}
