/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.util.Base64;

import civitas.util.CivitasBigInteger;

/**
 * Some miscellaneous utility functions.
 */
public class Util {
	public static String currentVersion() {
		return "JCivitas-v0.1";
	}

	public static String stripLeadingWhitespace(String s) {
		if (s == null)
			return s;
		int i = 0, length = s.length();
		while (i < length && Character.isWhitespace(s.charAt(i))) {
			i++;
		}
		if (i == 0)
			return s;
		if (i == length)
			return "";
		return s.substring(i);
	}

	public static String stripTrailingWhitespace(String s) {
		if (s == null)
			return s;
		int i = s.length();
		while (i > 0 && Character.isWhitespace(s.charAt(i - 1))) {
			i--;
		}
		if (i == s.length())
			return s;
		if (i <= 0)
			return "";
		return s.substring(0, i);
	}

	public static String stripTrailingLeadingWhitespace(String s) {
		if (s == null)
			return s;
		int length = s.length();
		int l = 0, r = length;
		while (l < length && Character.isWhitespace(s.charAt(l))) {
			l++;
		}
		while (r > l && Character.isWhitespace(s.charAt(r - 1))) {
			r--;
		}
		if (l == r)
			return "";
		if (l == 0 && r == length)
			return s;
		return s.substring(l, r);
	}

	static boolean[] is_meta = new boolean[128];
	static char[] metachars = { '&', '<', '>', '\'', '"' };
	static {
		for (int i = 0; i < metachars.length; i++)
			is_meta[metachars[i]] = true;
	}

	/**
	 * Escape the characters: & (&amp;) < (&lt;) > (&gt;) , (&apos;) " (&quot;)
	 */
	public static String escapeString(final String s) {
		StringWriter sb = new StringWriter();
		escapeString(s, new PrintWriter(sb));
		return sb.toString();
	}

	public static void escapeString(final String s, final PrintWriter sb) {
		if (s == null || sb == null)
			return;
		int n = s.length();
		int lastOut = -1;

		for (int i = 0; i < n; i++) {
			char c = s.charAt(i);
			if (!is_meta[c])
				continue;
			switch (c) {
			case '&':
				if (lastOut != i - 1)
					sb.append(s.substring(lastOut + 1, i));
				sb.append("&amp;");
				lastOut = i;
				break;
			case '<':
				if (lastOut != i - 1)
					sb.append(s.substring(lastOut + 1, i));
				sb.append("&lt;");
				lastOut = i;
				break;
			case '>':
				if (lastOut != i - 1)
					sb.append(s.substring(lastOut + 1, i));
				sb.append("&gt;");
				lastOut = i;
				break;
			case '\'':
				if (lastOut != i - 1)
					sb.append(s.substring(lastOut + 1, i));
				sb.append("&apos;");
				lastOut = i;
				break;
			case '\"':
				if (lastOut != i - 1)
					sb.append(s.substring(lastOut + 1, i));
				sb.append("&quot;");
				lastOut = i;
				break;
			default:
				throw new Error("Implementation error");
			}
		}
		if (lastOut != n - 1)
			sb.append(s.substring(lastOut + 1, n));
	}

	public static String unescapeString(final String s) {
		if (s == null)
			return s;
		int n = s.length();
		int nextAmp = s.indexOf('&', 0);
		if (nextAmp < 0)
			return s;

		StringBuffer t = new StringBuffer();
		for (int ind = 0; ind < n;) {
			if (nextAmp < 0) {
				t.append(s.subSequence(ind, n));
				ind = n;
				break;
			}
			t.append(s.substring(ind, nextAmp));
			int nextSemi = s.indexOf(';', nextAmp);
			if (nextSemi < 0) {
				t.append(s.subSequence(ind, n));
				ind = n;
				break;
			}
			String esc = s.substring(nextAmp + 1, nextSemi);
			if ("amp".equals(esc)) {
				t.append('&');
			} else if ("lt".equals(esc)) {
				t.append('<');
			} else if ("gt".equals(esc)) {
				t.append('>');
			} else if ("apos".equals(esc)) {
				t.append('\'');
			} else if ("quot".equals(esc)) {
				t.append('\"');
			} else {
				t.append('&');
				t.append(esc);
				t.append(';');
			}
			ind = nextSemi + 1;
			nextAmp = s.indexOf('&', ind);
		}
		return t.toString();
	}

	public static String nextTag(Reader r)
			throws IllegalArgumentException, IOException {
		swallowString(r, "<");
		return readUntil(r, ">");
	}

	public static String readUntil(Reader r, String s)
			throws IllegalArgumentException, IOException {
		return readUntilImpl(r, s, true);
	}

	public static void skipUntil(Reader r, String s)
			throws IllegalArgumentException, IOException {
		readUntilImpl(r, s, false);
	}

	private static String readUntilImpl(Reader r, String s, boolean record)
			throws IllegalArgumentException, IOException {
		if (s == null || s.length() == 0)
			return "";
		int sentinalChar = s.charAt(0);

		StringBuffer sb = new StringBuffer();

		while (true) {
			int ch = r.read();
			if (ch == -1)
				throw new IOException("Unexpected end of stream");
			if (((char) ch) == sentinalChar) {
				boolean matchesSentinal = true;
				for (int i = 1; i < s.length(); i++) {
					ch = r.read();
					if (ch == -1)
						throw new IOException("Unexpected end of stream");
					if (((char) ch) != s.charAt(i)) {
						matchesSentinal = false;
						if (record) {
							sb.append(s.substring(0, i));
							sb.append((char) ch);
						}
						break;
					}
				}
				if (matchesSentinal) {
					// we've matched the sentinal
					return sb.toString();
				}
			} else {
				if (record)
					sb.append((char) ch);
			}
		}
	}

	/**
	 * Swallows initial white space, and the initial "<tag>" string
	 */
	public static void swallowTag(Reader r, String tag)
			throws IllegalArgumentException, IOException {
		swallowString(r, "<");
		swallowString(r, tag);
		swallowString(r, ">");
	}

	/**
	 * Swallows initial white space, and the initial "</tag>" string
	 */
	public static void swallowEndTag(Reader r, String tag)
			throws IllegalArgumentException, IOException {
		swallowString(r, "</");
		swallowString(r, tag);
		swallowString(r, ">");
	}

	private static void swallowString(Reader r, String s)
			throws IllegalArgumentException, IOException {
		if (r == null || s == null) {
			throw new IllegalArgumentException("Null arguments");
		}

		int ch = r.read();

		// skip over white space
		while (ch != -1 && Character.isWhitespace((char) ch))
			ch = r.read();

		// now go through and swallow the string s
		for (int i = 0; i < s.length(); i++) {
			if (ch == -1) {
				throw new IOException("Unexpected end of file: expecting " + s);
			}
			try {
				if (((char) ch) != s.charAt(i)) {
					// try finding some more context, for a better error message...
					StringBuffer received = new StringBuffer();
					received.append(s.substring(0, i));
					received.append((char) ch);
					int count = 10;
					while (count-- > 0) {
						ch = r.read();
						received.append((char) ch);
						if (ch == '>')
							break;
					}
					throw new IOException(
							"Expecting " + s + " got " + received.toString());
				}
			} catch (StringIndexOutOfBoundsException imposs) {
			}

			if (i + 1 < s.length())
				ch = r.read();
		}
	}

	/**
	 * Returns everything in s up to the next '<' char
	 */
	public static String readSimpleTag(Reader r, String tag)
			throws IllegalArgumentException, IOException {
		if (r == null || tag == null) {
			throw new IllegalArgumentException("Null arguments");
		}

		swallowString(r, "<");
		swallowString(r, tag);
		int ch;
		do {
			ch = r.read();
		} while (ch != -1 && Character.isWhitespace(ch));

		if (ch == '/') {
			// it's an empty tag, e.g. <applause/>
			swallowString(r, ">");
			return "";
		}
		if (ch != '>') {
			// error!
			throw new IOException("Expecting <" + tag + ">");
		}

		StringBuffer s = new StringBuffer();

		do {
			ch = r.read();
			if (ch != -1 && ch != '<')
				s.append((char) ch);
		} while (ch != -1 && ch != '<');

		if (ch == -1) {
			throw new IOException("Unexpected end of file");
		}
		swallowString(r, "/");
		swallowString(r, tag);
		swallowString(r, ">");
		return stripTrailingLeadingWhitespace(s.toString());
	}

	/**
	 * Returns the value of the next simple tag, interpreted as an integer.
	 */
	public static int readSimpleIntTag(Reader r, String tag)
			throws IllegalArgumentException, IOException {
		String s = readSimpleTag(r, tag);
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Expected an int: '" + s + "'");
		}
	}

	/**
	 * Returns the value of the next simple tag, interpreted as a long.
	 */
	public static long readSimpleLongTag(Reader r, String tag)
			throws IllegalArgumentException, IOException {
		String s = readSimpleTag(r, tag);
		try {
			return Long.parseLong(s);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Expected a long: '" + s + "'");
		}
	}

	/**
	 * Returns the value of the next simple tag, interpreted as a boolean.
	 */
	public static boolean readSimpleBooleanTag(Reader r, String tag)
			throws IllegalArgumentException, IOException {
		return stringToBoolean(readSimpleTag(r, tag));
	}

	public static boolean stringToBoolean(String s) {
		return "true".equalsIgnoreCase(s) || "yes".equalsIgnoreCase(s)
				|| "y".equalsIgnoreCase(s);
	}

	/**
	 * returns true if the next tag is "<tag>". does not consume any characters
	 * except whitespace
	 */
	public static boolean isNextTag(Reader r, String tag)
			throws IllegalArgumentException, IOException {
		if (r == null || tag == null) {
			throw new IllegalArgumentException("Null arguments");
		}
		int ch;
		do {
			r.mark(tag.length() + 3);
			ch = r.read();
		} while (ch != -1 && Character.isWhitespace((char) ch));
		if (ch != '<') {
			r.reset();
			return false;
		}

		int tagLength = tag.length();
		for (int i = 0; i < tagLength; i++) {
			ch = r.read();
			if (ch == -1 || ch != tag.charAt(i)) {
				r.reset();
				return false;
			}
		}
		ch = r.read();
		boolean result = (ch == '>');
		r.reset();
		return result;
	}

	public static int[] invertPermutation(int[] p) {
		if (p == null)
			return p;
		int[] q = new int[p.length];
		for (int i = 0; i < p.length; i++) {
			try {
				q[p[i]] = i;
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}
		return q.clone();
	}

	/**
	 * Convert a byte array into a string suitable for an xml message.
	 */
	public static String bytesToString(byte[] bs) {
		if (bs == null)
			return null;

		return Base64.getEncoder().encodeToString(bs);
	}

	public static CivitasBigInteger asBigint(String s) {
		return new CivitasBigInteger(Base64.getDecoder().decode(s));
	}

	public static String fromBigInt(CivitasBigInteger a) {
		return Base64.getEncoder().encodeToString(a.toByteArray());
	}

	/**
	 * Convert a byte array into a string suitable for an xml message.
	 */
	@Deprecated
	public static String constBytesToString(byte[] bs) {
		return bytesToString(bs);
	}

	/**
	 * Convert string produced by bytesToString back into a byte array.
	 */
	public static byte[] stringToBytes(String s) {
		if (s == null)
			return null;
		return Base64.getDecoder().decode(s);
	}

	/**
	 * Convert string produced by bytesToString back into a byte array.
	 */
	@Deprecated
	public static byte[] stringToConstBytes(String s) {
		return stringToBytes(s);
	}

	/**
	 * Check that two byte arrays are equal.
	 */
	@Deprecated // Arrays.equal
	public static boolean equals(byte[] a, byte[] b) {
		if (a == b)
			return true;
		if (a == null || b == null)
			return false;
		if (a.length != b.length)
			return false;
		for (int i = 0; i < a.length; i++) {
			try {
				if (a[i] != b[i])
					return false;
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}
		return true;
	}

	@Deprecated
	public static boolean equals(byte[] a, byte[] b, boolean constArrays) {
		if (a == b)
			return true;
		if (a == null || b == null)
			return false;
		if (a.length != b.length)
			return false;
		for (int i = 0; i < a.length; i++) {
			try {
				if (a[i] != b[i])
					return false;
			} catch (ArrayIndexOutOfBoundsException imposs) {
			}
		}
		return true;
	}
}
