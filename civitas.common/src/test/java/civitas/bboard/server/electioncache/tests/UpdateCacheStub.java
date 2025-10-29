package civitas.bboard.server.electioncache.tests;

import static org.mockito.Mockito.mock;

import civitas.bboard.server.electioncache.UpdateCache;

public class UpdateCacheStub {
	public static UpdateCache stub() {
		return mock(UpdateCache.class);
	}
}
