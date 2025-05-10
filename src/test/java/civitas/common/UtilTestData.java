package civitas.common;

public class UtilTestData {

	public static final String UNESCAPED = "h&el&'<>&\"lo' \" <mum>";
	public static final String ESCAPED = "h&amp;el&amp;&apos;&lt;&gt;&amp;&quot;lo&apos; &quot; &lt;mum&gt;";
	public static final String VERSIONSTRING = "JCivitas-v0.1";
	public static final String TRAILING_WHITESPACE = VERSIONSTRING + "  ";
	public static final String LEADING_WHITESPACE = "  " + VERSIONSTRING;
	public static final String ALL_WHITESPACE = "   \t ";
	public static final String LEADING_TRAILING_WHITESPACE = "  " + VERSIONSTRING
			+ "  ";
	public static final String TAG_NAME = "div";
	public static final String AFTER_TAG = " is the next end tag";
	public static final String AFTER_IS = " the next end tag";
	public static final String TAG = "<div>";
	public static final String ENDTAG = "</div>";
	public static final String READER_CONTENT = TAG + AFTER_TAG;
	public static final String READER_CONTENT_ENDTAG = ENDTAG + AFTER_TAG;
	public static final String READER_CONTENT_WITH_WORD_BEFORE_TAG = "but "
			+ READER_CONTENT;
	public static final String UP_TO_FOUND_STRING = TAG + " ";
	public static final String FOUND_STRING = "is";
	public static final String NOTFOUND_STRING = "alsdkj";
	public static final String HALFFOUND_STRING = "tagalog";
	public static final String AMPERSAND = "&a";
	public static final String UNKNOWN_SEQUENCE = "&ridiculous;";

	public static final String UNESCAPED_QUOT = "hello \" mum";
	public static final String ESCAPED_QUOT = "hello &quot; mum";
	public static final String UNESCAPED_APOS = "hello ' mum";
	public static final String ESCAPED_APOS = "hello &apos; mum";
	public static final String UNESCAPED_GT = "hello > mum";
	public static final String ESCAPED_GT = "hello &gt; mum";
	public static final String UNESCAPED_LT = "hello < mum";
	public static final String ESCAPED_LT = "hello &lt; mum";
	public static final String UNESCAPED_AMP = "hello & mum";
	public static final String ESCAPED_AMP = "hello &amp; mum";

}
