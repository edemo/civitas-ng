package civitas.crypto.messagedigest;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import civitas.bboard.common.BBPost;
import civitas.crypto.CryptoBase;
import civitas.crypto.CryptoError;
import civitas.crypto.signature.Signature;
import civitas.util.CivitasBigInteger;
import jakarta.annotation.Nonnull;

@Controller
public class CryptoHash {
	@Autowired
	public CryptoBase cryptoBase;

	public byte[] apply(List<CivitasBigInteger> list) {
		MessageDigest md = cryptoBase.messageDigest;
		// list.forEach(x -> md.md.update(x.toByteArray()));
		for (CivitasBigInteger x : list) {
			md.md.update(x.toByteArray());
		}
		return md.md.digest();
	}

	public CivitasBigInteger apply(CivitasBigInteger a, CivitasBigInteger b) {
		return apply(a, b, null);
	}

	public CivitasBigInteger apply(CivitasBigInteger a, CivitasBigInteger b,
			CivitasBigInteger c) {
		return apply(a, b, c, null);
	}

	public CivitasBigInteger apply(CivitasBigInteger a, CivitasBigInteger b,
			CivitasBigInteger c, byte[] d) {
		MessageDigest md = cryptoBase.messageDigest;
		md.md.update(a.toByteArray());
		md.md.update(b.toByteArray());
		if (c != null)
			md.md.update(c.toByteArray());
		if (d != null)
			md.md.update(d);
		return new CivitasBigInteger(md.md.digest());
	}

	public byte[] apply(byte[] a) throws CryptoError {
		MessageDigest md = cryptoBase.messageDigest;
		if (a != null)
			md.md.update(a);
		return md.md.digest();
	}

	public byte[] apply(byte[] a, byte[] b) throws CryptoError {
		MessageDigest md = cryptoBase.messageDigest;
		if (a != null)
			md.md.update(a);
		md.md.update(b);
		return md.md.digest();
	}

	public byte[] apply(byte[] a, int i) {
		MessageDigest md = cryptoBase.messageDigest;
		md.md.update(a);
		byte[] dword = intToBytes(i);
		md.md.update(dword);
		return md.md.digest();
	}

	public byte[] apply(BBPost post, byte[] b) {
		MessageDigest md = cryptoBase.messageDigest;
		md.md.update(post.sig.signature);
		md.md.update(b);
		return md.md.digest();
	}

	public byte[] apply(String s) {
		MessageDigest md = cryptoBase.messageDigest;
		if (s != null) {
			byte[] bytes;
			try {
				bytes = s.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new Error("impossible");
			}
			md.md.update(bytes);
		}
		return md.md.digest();
	}

	public byte[] apply(byte b) {
		MessageDigest md = cryptoBase.messageDigest;
		md.md.update(b);
		return md.md.digest();
	}

	public byte[] apply(int i) {
		return apply(intToBytes(i));
	}

	public byte[] apply(long l) {
		return apply(longToBytes(l));
	}

	public byte[] apply(char[] cbuf, int off, int len)
			throws UnsupportedEncodingException {
		return apply(new String(cbuf, off, len));
	}

	private byte[] intToBytes(int i) {
		byte[] dword = new byte[4];
		dword[0] = (byte) (i & 0x00FF);
		dword[1] = (byte) ((i >> 8) & 0x000000FF);
		dword[2] = (byte) ((i >> 16) & 0x000000FF);
		dword[3] = (byte) ((i >> 24) & 0x000000FF);
		return dword;
	}

	private byte[] longToBytes(long i) {
		byte[] dword = new byte[8];
		dword[7] = (byte) (i & 0x00FF);
		dword[6] = (byte) ((i >> 8) & 0x000000FF);
		dword[5] = (byte) ((i >> 2 * 8) & 0x000000FF);
		dword[4] = (byte) ((i >> 3 * 8) & 0x000000FF);
		dword[3] = (byte) ((i >> 4 * 8) & 0x000000FF);
		dword[2] = (byte) ((i >> 5 * 8) & 0x000000FF);
		dword[1] = (byte) ((i >> 6 * 8) & 0x000000FF);
		dword[0] = (byte) ((i >> 7 * 8) & 0x000000FF);
		return dword;
	}

	public byte[] apply(BBPost post, long currentTime, @Nonnull Signature sig) {
		byte[] hash = new byte[0];
		if (post != null)
			hash = post.hash;
		byte[] signature = sig.signature;
		byte[] bytes = new byte[hash.length + 8 + signature.length];
		System.arraycopy(hash, 0, bytes, 0, hash.length);
		System.arraycopy(longToBytes(currentTime), 0, bytes, hash.length, 8);
		System.arraycopy(signature, 0, bytes, hash.length + 8, signature.length);
		return apply(bytes);
	}

}
