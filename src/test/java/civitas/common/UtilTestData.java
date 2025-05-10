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
	public static final String TAG_START = "<div";
	public static final String TAG_CUT = "<di";
	public static final String TAG_DIFFERENT_END = "<dia>";
	public static final String AIRY_TAG = TAG_START + ALL_WHITESPACE + ">";
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
	public static final String READER_CONTENT_SIMPLE_TAG = TAG + FOUND_STRING
			+ ENDTAG;
	public static final String TAG_WITH_TRUE_MIXED = TAG + "tRue" + ENDTAG;
	public static final String TAG_WITH_Y_CAPS = TAG + "Y" + ENDTAG;
	public static final String TAG_WITH_YES_MIXED = TAG + "yEs" + ENDTAG;
	public static final String TAG_WITH_TRUE = TAG + "true" + ENDTAG;
	public static final String TARG_WITH_Y = TAG + "y" + ENDTAG;
	public static final String TAG_WITH_YES = TAG + "yes" + ENDTAG;
	public static final String TAG_WITH_INT = TAG + 42 + ENDTAG;
	public static final String TAG_WITH_NOTHING = TAG + ENDTAG;
	public static final String EMPTY_TAG = TAG_START + "/>";

	public static int[] PERMUTED_ARRAY = new int[] { 1, 3, 2, 0 };
	public static int[] PERMUTED_ARRAY_OUT_OF_BONDS = new int[] { 1, 5, 2, 0 };
	public static int[] PERMUTED_ARRAY_W_DUPLICATE = new int[] { 1, 2, 2, 0 };
	public static int[] INVERT_PERMUTED_ARRAY = new int[] { 3, 0, 2, 1 };
	public static int[] INVERT_PERMUTED_ARRAY_OOB = new int[] { 3, 0, 2, 0 };
	public static int[] INVERT_PERMUTED_ARRAY_W_DUP = new int[] { 3, 0, 2, 0 };

	public String BASE_64_ENCODED = "Zm9vYmFy";
	public byte[] BASE_64_DECODED = "foobar".getBytes();

}
