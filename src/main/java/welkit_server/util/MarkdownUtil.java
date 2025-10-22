package welkit_server.util;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

public final class MarkdownUtil {

    private static final Parser PARSER = Parser.builder().build();
    private static final HtmlRenderer RENDERER = HtmlRenderer.builder().build();

    private MarkdownUtil() {}

    /** Markdown → HTML */
    public static String markdownToHtml(String markdown) {
        if (markdown == null || markdown.isBlank()) return "";
        Node document = PARSER.parse(markdown);
        return RENDERER.render(document);
    }

    /**
     * Markdown → (HTML) → sanitize → plain text
     * - Jsoup.clean(..., Safelist.none()) 로 모든 태그 제거 (XSS 방지)
     * - 이후 Jsoup.parse(...).text() 로 순수 텍스트 추출
     */
    public static String markdownToPlainText(String markdown) {
        String html = markdownToHtml(markdown);
        // HTML 태그/스크립트 등 모두 제거
        String cleaned = Jsoup.clean(html, Safelist.none());
        // 문서에서 텍스트 추출
        return Jsoup.parse(cleaned).text();
    }

}