/*
 * This file is part of the Civitas software distribution.
 * Copyright (c) 2007-2008, Civitas project group, Cornell University.
 * See the LICENSE file accompanying this distribution for further license
 * and copyright information.
 */
package civitas.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Cache information useful for plaintext equivalence tests (PETs) Information
 * is stored persistently on disk.
 */
class FileBasedPETCache extends FileBasedDistDecryptCache implements PETCache {
	FileBasedPETCache(FileBasedElectionCache cache, String meta, int numBlocks, int numTellers) {
		super(cache, meta, numBlocks, numTellers);
	}

	private String petCommitCachename(String meta, int block, int ballotIndex, int tellerIndex) {
		return "petCommit" + meta + ":B" + block + ":B" + ballotIndex + "I" + tellerIndex;
	}

	@Override
	public boolean hasCommitment(String meta, int block, int ballotIndex, int tellerIndex) {
		if (!this.meta.equals(meta)) {
			throw new Error("fault: " + this.meta + " not equals " + meta);
		}
		String cachename = petCommitCachename(meta, block, ballotIndex, tellerIndex);
		return electionCache.cachefileExists(cachename);
	}

	@Override
	public TabTellerPETShareCommitments getCommitment(String meta, int block, int ballotIndex, int tellerIndex) {
		try {
			if (!this.meta.equals(meta)) {
				throw new Error("fault: " + this.meta + " not equals " + meta);
			}
			String cachename = petCommitCachename(meta, block, ballotIndex, tellerIndex);

			// try getting it from file
			BufferedReader reader = this.electionCache.getFileContents(cachename);
			if (reader != null) {
				try {
					return TabTellerPETShareCommitments.fromXML(reader);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					this.electionCache.failedCaching(cachename);
				} catch (IOException e) {
					e.printStackTrace();
					this.electionCache.failedCaching(cachename);
				} finally {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return null;
		} catch (NullPointerException e) {
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return null;
	}

	@Override
	public void setCommitment(String meta, int block, int ballotIndex, int tellerIndex,
			TabTellerPETShareCommitments c) {
		try {
			if (!this.meta.equals(meta)) {
				throw new Error("fault: " + this.meta + " not equals " + meta);
			}
			if (c != null) {
				String cachename = petCommitCachename(meta, block, ballotIndex, tellerIndex);
				PrintWriter out = this.electionCache.getFilePrintWriter(cachename);
				c.toXML(out);
				out.close();
			}
		} catch (NullPointerException e) {
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	}

	private String petDecommitCachename(String meta, int block, int ballotIndex, int tellerIndex) {
		return "petDecommit" + meta + ":B" + block + ":B" + ballotIndex + "I" + tellerIndex;
	}

	@Override
	public boolean hasDecommitment(String meta, int block, int ballotIndex, int tellerIndex) {
		if (!this.meta.equals(meta)) {
			throw new Error("fault: " + this.meta + " not equals " + meta);
		}
		String cachename = petDecommitCachename(meta, block, ballotIndex, tellerIndex);
		return electionCache.cachefileExists(cachename);
	}

	@Override
	public TabTellerPETShareDecommitments getDecommitment(String meta, int block, int ballotIndex, int tellerIndex) {
		try {
			if (!this.meta.equals(meta)) {
				throw new Error("fault: " + this.meta + " not equals " + meta);
			}
			String cachename = petDecommitCachename(meta, block, ballotIndex, tellerIndex);

			// try getting it from file
			BufferedReader reader = this.electionCache.getFileContents(cachename);
			if (reader != null) {
				try {
					return TabTellerPETShareDecommitments.fromXML(reader);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					this.electionCache.failedCaching(cachename);
				} catch (IOException e) {
					e.printStackTrace();
					this.electionCache.failedCaching(cachename);
				} finally {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return null;
		} catch (NullPointerException e) {
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		return null;
	}

	@Override
	public void setDecommitment(String meta, int block, int ballotIndex, int tellerIndex,
			TabTellerPETShareDecommitments c) {
		try {
			if (!this.meta.equals(meta)) {
				throw new Error("fault: " + this.meta + " not equals " + meta);
			}
			if (c != null) {
				String cachename = petDecommitCachename(meta, block, ballotIndex, tellerIndex);
				PrintWriter out = this.electionCache.getFilePrintWriter(cachename);
				c.toXML(out);
				out.close();
			}
		} catch (NullPointerException e) {
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	}

}