package org.elasticsearch.index.analysis;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.tests.analysis.BaseTokenStreamTestCase;
import org.apache.lucene.tests.analysis.MockTokenizer;

import java.io.IOException;
import java.io.StringReader;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class UnicodeNormalizationCharFilterTests extends BaseTokenStreamTestCase {
    public void testSimpleNFKC() throws IOException {
        String input = "ｱｲｳｴｵ １２３ ＡＢＣ";
        CharFilter charFilter = new UnicodeNormalizationCharFilter(new StringReader(input), Normalizer.Form.NFKC, true);


        MockTokenizer tokenizer = new MockTokenizer();
        tokenizer.setReader(charFilter);

        String[] expected = new String[] {"アイウエオ", "123", "abc"};
        assertTokenStreamContents(tokenizer, expected);
    }

    public void testComposeNfkc() throws IOException {
        String input = "ガギグゲゴ";
        CharFilter charFilter = new UnicodeNormalizationCharFilter(new StringReader(input), Normalizer.Form.NFKC, true);


        MockTokenizer tokenizer = new MockTokenizer();
        tokenizer.setReader(charFilter);

        String[] expected = new String[] {"ガギグゲゴ"};
        assertTokenStreamContents(tokenizer, expected);
    }

    public void testComposeNfkcHalfWidthKatakana() throws IOException {
        String input = "ｶﾞｷﾞｸﾞｹﾞｺﾞ";
        CharFilter charFilter = new UnicodeNormalizationCharFilter(new StringReader(input), Normalizer.Form.NFKC, true);


        MockTokenizer tokenizer = new MockTokenizer();
        tokenizer.setReader(charFilter);

        String[] expected = new String[] {"ガギグゲゴ"};
        assertTokenStreamContents(tokenizer, expected);
    }
}
