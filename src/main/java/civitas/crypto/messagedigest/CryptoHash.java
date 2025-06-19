package civitas.crypto.messagedigest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.CryptoBase;
import civitas.crypto.CryptoError;
import civitas.crypto.publickeymsg.PublicKeyMsg;
import civitas.util.CivitasBigInteger;
import lombok.SneakyThrows;

@Service
public class CryptoHash {
	@Autowired
	CryptoBase cryptoBase;

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

	public byte[] apply(PublicKeyMsg mc) {
		return apply(mc.m.getBytes());
	}

	public byte[] apply(byte[] a) throws CryptoError {
		MessageDigest md = cryptoBase.messageDigest;
		if (a != null)
			md.md.update(a);
		return md.md.digest();
	}

	public byte[] apply(byte[] a, int i) {
		MessageDigest md = cryptoBase.messageDigest;
		md.md.update(a);
		byte[] dword = intToBytes(i);
		md.md.update(dword);
		return md.md.digest();
	}

	@SneakyThrows
	public byte[] apply(String s) {
		MessageDigest md = cryptoBase.messageDigest;
		if (s != null)
			md.md.update(s.getBytes("UTF-8"));
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

	public byte[] apply(char[] cbuf, int off, int len) {
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
		dword[0] = (byte) (i & 0x00FF);
		dword[1] = (byte) ((i >> 8) & 0x000000FF);
		dword[2] = (byte) ((i >> 2 * 8) & 0x000000FF);
		dword[3] = (byte) ((i >> 3 * 8) & 0x000000FF);
		dword[4] = (byte) ((i >> 4 * 8) & 0x000000FF);
		dword[5] = (byte) ((i >> 5 * 8) & 0x000000FF);
		dword[6] = (byte) ((i >> 6 * 8) & 0x000000FF);
		dword[7] = (byte) ((i >> 7 * 8) & 0x000000FF);
		return dword;
	}

}
