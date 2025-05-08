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
	public static final String READER_CONTENT = "<div> is the next tag";
	public static final String FOUND_STRING = "is";
	public static final String NOTFOUND_STRING = "alsdkj";
	public static final String HALFFOUND_STRING = "tagalog";

}
