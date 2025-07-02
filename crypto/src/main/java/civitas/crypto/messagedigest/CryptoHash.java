package civitas.crypto.messagedigest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import civitas.crypto.CryptoBase;
import civitas.util.CivitasBigInteger;

@Service
public class CryptoHash {
	@Autowired
	public CryptoBase cryptoBase;

	public byte[] apply(List<CivitasBigInteger> list) {
		return apply(list.stream().filter(x -> x != null).map(x -> x.toByteArray())
				.toList().toArray(new byte[0][0]));
	}

	public CivitasBigInteger apply(CivitasBigInteger a, CivitasBigInteger b,
			CivitasBigInteger c, byte[] d) {
		Stream<byte[]> bigintStream = Stream.of(a, b, c).filter(x -> x != null)
				.map(x -> x.toByteArray());
		List<byte[]> nums = Stream.concat(bigintStream, Stream.of(d)).toList();
		return new CivitasBigInteger(apply(nums.toArray(new byte[0][0])));
	}

	public byte[] apply(byte[]... bytearrays) {
		MessageDigest md = cryptoBase.messageDigest;
		Arrays.asList(bytearrays).forEach(x -> {
			if (null != x)
				md.md.update(x);
		});
		return md.md.digest();
	}

}
