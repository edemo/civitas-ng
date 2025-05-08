/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.bboard.common;

import java.io.PrintWriter;

import civitas.common.Util;
import civitas.common.XMLSerializable;
import civitas.crypto.CryptoUtil;
import civitas.crypto.MessageDigest;
import civitas.crypto.MessageDigestWriter;
import civitas.crypto.PublicKey;
import civitas.crypto.Signature;

/**
 * A representation of a bulletin board post.
 */
public final class BBPost implements XMLSerializable, Comparable {
	public static final String OPENING_TAG = "post";
	/**
	 * The ID of the bulletin board this post is from
	 */
	public final String bbid;
	/**
	 * The timestamp of this post
	 */
	public final long timestamp;

	/**
	 * The meta field of this post
	 */
	public final String meta;
	/**
	 * The message field of this post
	 */
	public final XMLSerializable msg;
	/**
	 * The signature field of this post. If this field is non-null then it should be
	 * a signature of the msg field.
	 */
	public final Signature sig;

	public BBPost(String bbid, long timestamp, String meta, XMLSerializable msg, Signature sig) {
		this.bbid = bbid;
		this.timestamp = timestamp;
		this.meta = meta;
		this.msg = msg;
		this.sig = sig;
	}

	/**
	 * Verify that the signature is correct. If no signature exists for this post,
	 * then false is returned.
	 */
	public boolean verify(final PublicKey K)

	{
		if (sig == null)
			return false;
		try {
			MessageDigest md = CryptoUtil.factory().messageDigest();
			MessageDigestWriter mdw = new MessageDigestWriter(null, md);
			if (msg != null)
				msg.toXML(new PrintWriter(mdw));

			return CryptoUtil.factory().publicKeyVerifySignature(K, sig, md.digest());
		} catch (NullPointerException imposs) {
			return false;
		}
	}
//    public String{this;signer;p} meet {*->*;p <- signer} verify (principal p, PublicKey signer)  
//        if (sig == null) return null;
//        String s = null;
//        if (p instanceof PublicKey) {
//            try {
//                s = CryptoUtil.factory().publicKeyVerifySignatureMsg(signer, sig, CryptoUtil.factory().publicKeyMsg(msg)).toString();
//            }
//            catch (NullPointerException ignore) {
//            }
//            catch (CryptoException e) {
//            }
//        }
//        return endorse(s, {this;signer;p} meet {*->*; p <- signer});        
//    }
//

	public boolean equals(Comparable obj) {
		if (obj instanceof BBPost) {
			BBPost that = (BBPost) obj;
			return eq(this.bbid, that.bbid) && this.timestamp == that.timestamp && eq(this.meta, that.meta)
					&& this.msg == that.msg;
		}
		return false;
	}

	/**
	 * Utility method to compare two strings.
	 */
	private boolean eq(String s, String t) {
		return s == t || (s != null && s.equals(t));
	}

	@Override
	public String toString() {
		return "BBPost(unimpl)";
	}

	@Override
	public void toXML(PrintWriter sb) {
		if (sb == null)
			return;
		sb.print("<" + OPENING_TAG + ">");

		sb.print("<id>");
		Util.escapeString(this.bbid, sb);
		sb.print("</id>");
		sb.print("<stamp>");
		sb.print(this.timestamp);
		sb.print("</stamp>");
		sb.print("<meta>");
		Util.escapeString(this.meta, sb);
		sb.print("</meta>");
		sb.print("<msg>");
		if (msg != null) {
			msg.toXML(sb);
		}
		sb.print("</msg>");
		if (sig != null) {
			sig.toXML(sb);
		}

		sb.print("</" + OPENING_TAG + ">");
	}

	@Override
	public int hashCode() {
		return (bbid == null ? 0 : bbid.hashCode()) ^ ((int) timestamp);
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof BBPost) {
			BBPost p = (BBPost) o;
			return (bbid + timestamp + msg.toString()).compareTo(p.bbid + p.timestamp + p.msg.toString());
		}
		return this.hashCode() > o.hashCode() ? 1 : -1;
	}

}