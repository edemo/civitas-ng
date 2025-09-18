package civitas.crypto.messagedigest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import civitas.crypto.Constants;
import civitas.crypto.CryptoError;
import civitas.util.CivitasBigInteger;
import civitas.util.CivitasBigIntegerFactory;
import civitas.util.CivitasBigintegerBase;

@Service
public class CryptoHash implements Constants {

	MessageDigest messageDigest;

	CryptoHash() {
		try {
			messageDigest = MessageDigest.getInstance(MESSAGE_DIGEST_ALG);
		} catch (NoSuchAlgorithmException e) {
			throw new CryptoError(e);
		}
	}

	public byte[] apply(List<CivitasBigInteger> list) {
		return apply(list.stream()
				.filter(Objects::nonNull)
				.map(CivitasBigintegerBase::toByteArray)
				.toList()
				.toArray(new byte[0][0]));
	}

	public CivitasBigInteger apply(CivitasBigInteger a, CivitasBigInteger b, CivitasBigInteger c, byte[] d) {
		Stream<byte[]> bigintStream =
				Stream.of(a, b, c).filter(Objects::nonNull).map(CivitasBigintegerBase::toByteArray);
		List<byte[]> nums = Stream.concat(bigintStream, Stream.of(d)).toList();
		return CivitasBigIntegerFactory.obtain(apply(nums.toArray(new byte[0][0])));
	}

	public byte[] apply(byte[]... bytearrays) {
		Arrays.asList(bytearrays).forEach(x -> {
			if (null != x) {
				messageDigest.update(x);
			}
		});
		return messageDigest.digest();
	}
}
