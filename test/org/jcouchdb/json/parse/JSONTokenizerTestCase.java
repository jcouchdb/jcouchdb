package org.jcouchdb.json.parse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.jcouchdb.json.parse.JSONParseException;
import org.jcouchdb.json.parse.JSONTokenizer;
import org.jcouchdb.json.parse.Token;
import org.jcouchdb.json.parse.TokenType;
import org.junit.Test;

public class JSONTokenizerTestCase
{
    protected static Logger log = Logger.getLogger(JSONTokenizerTestCase.class);

    private List<Token> tokenize(String json)
    {
        List<Token> tokens = new ArrayList<Token>();

        JSONTokenizer tokenizer = new JSONTokenizer(json);
        Token token;
        while ( (token = tokenizer.next()).type() != TokenType.END)
        {
            tokens.add(token);
        }
        return tokens;
    }

    private Token createToken(TokenType type, Object value)
    {
        return new Token(type,value,Integer.MIN_VALUE);
    }

    private Token createToken(TokenType type)
    {
        if (type.isClassRestricted())
        {
            return new Token(type, Integer.MIN_VALUE);
        }
        else
        {
            return new Token(type, type.getDefaultContent(), Integer.MIN_VALUE);
        }
    }

    @Test
    public void thatTokenizingNumbersWorks()
    {
        assertThat(tokenize(" \n107"), is( Arrays.asList( createToken(TokenType.INTEGER, Long.valueOf(107)))));
        assertThat(tokenize("-19 \r"), is( Arrays.asList( createToken(TokenType.INTEGER, Long.valueOf(-19)))));

        assertThat(tokenize("3.1415"), is( Arrays.asList( createToken(TokenType.DECIMAL, Double.valueOf(3.1415)))));
        assertThat(tokenize("10e5"), is( Arrays.asList( createToken(TokenType.DECIMAL, Double.valueOf(10e5)))));
        assertThat(tokenize("92233720368547758070"), is( Arrays.asList( createToken(TokenType.DECIMAL, Double.valueOf("92233720368547758070")))));

    }

    @Test
    public void thatTokenizingStringsWorks()
    {
        assertThat(tokenize("\"\""), is( Arrays.asList( createToken(TokenType.STRING, ""))));
        assertThat(tokenize("\"foo bar\""), is( Arrays.asList( createToken(TokenType.STRING, "foo bar"))));
        assertThat(tokenize("\"\\\"\""), is( Arrays.asList( createToken(TokenType.STRING, "\""))));
        assertThat(tokenize("\"\\\\\\\"\""), is( Arrays.asList( createToken(TokenType.STRING, "\\\""))));
        assertThat(tokenize("\"\\r\\n\\f\\b\\/\\u0020\""), is( Arrays.asList( createToken(TokenType.STRING, "\r\n\f\b/ "))));
    }

    @Test
    public void thatObjectTokenizingWorks()
    {
        assertThat(tokenize(" {  \"foo\" :  \"bar\" , \"baz\"  : 42 } "), is( Arrays.asList(
            createToken(TokenType.BRACE_OPEN),
            createToken(TokenType.STRING, "foo"),
            createToken(TokenType.COLON),
            createToken(TokenType.STRING, "bar"),
            createToken(TokenType.COMMA),
            createToken(TokenType.STRING, "baz"),
            createToken(TokenType.COLON),
            createToken(TokenType.INTEGER, Long.valueOf(42)),
            createToken(TokenType.BRACE_CLOSE) )));

        assertThat(tokenize("{}"), is( Arrays.asList(
            createToken(TokenType.BRACE_OPEN),
            createToken(TokenType.BRACE_CLOSE) )));

    }

    @Test
    public void thatArrayTokenizingWorks()
    {
        assertThat(tokenize(" [  \"foo\" ,  1.2 ,  1    ,  true , false  ]  "), is( Arrays.asList(
            createToken(TokenType.BRACKET_OPEN),
            createToken(TokenType.STRING, "foo"),
            createToken(TokenType.COMMA),
            createToken(TokenType.DECIMAL, 1.2),
            createToken(TokenType.COMMA),
            createToken(TokenType.INTEGER, 1l),
            createToken(TokenType.COMMA),
            createToken(TokenType.TRUE, Boolean.TRUE),
            createToken(TokenType.COMMA),
            createToken(TokenType.FALSE, Boolean.FALSE),
            createToken(TokenType.BRACKET_CLOSE) )));

        assertThat(tokenize(" [ ] "), is( Arrays.asList(
            createToken(TokenType.BRACKET_OPEN),
            createToken(TokenType.BRACKET_CLOSE) )));
    }

    @Test(expected = JSONParseException.class)
    public void thatUnclosedQuotesDontWork()
    {
        tokenize("\"");
    }

    @Test(expected = JSONParseException.class)
    public void thatUnknownKeywordDoesntWork()
    {
        // wrong keyword with right first char
        tokenize("foo");
    }

    @Test(expected = JSONParseException.class)
    public void thatUnknownKeywordDoesntWork2()
    {
        // wrong keyword with wrong first char
        tokenize("bar");
    }

    @Test(expected = JSONParseException.class)
    public void thatInvalidEscapeSequencesDontWork()
    {
        tokenize("\"\\e\"");
    }

    @Test()
    public void thatKeywordTokenizingWorks()
    {
        assertThat(tokenize("true"), is( Arrays.asList( createToken(TokenType.TRUE, Boolean.TRUE))));
        assertThat(tokenize("false"), is( Arrays.asList( createToken(TokenType.FALSE, Boolean.FALSE))));
        assertThat(tokenize("null"), is( Arrays.asList( createToken(TokenType.NULL))));
    }

    @Test
    public void thatPushBackWorks()
    {
        JSONTokenizer tokenizer = new JSONTokenizer("{\"foo\":[1,1.2,true,false,null]}");
        Token token;
        while ( (token = tokenizer.next()).type() != TokenType.END)
        {
            tokenizer.pushBack(token);
            Token token2 = tokenizer.next();

            assertThat(token, is(token2));
            assertThat(token.getIndex(), is(token2.getIndex()));
        }
    }
}
