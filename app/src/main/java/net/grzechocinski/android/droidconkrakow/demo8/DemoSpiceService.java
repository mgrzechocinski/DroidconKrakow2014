package net.grzechocinski.android.droidconkrakow.demo8;

import android.app.Application;
import android.content.Context;
import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.networkstate.NetworkStateChecker;
import com.octo.android.robospice.persistence.CacheManager;
import com.octo.android.robospice.persistence.exception.CacheCreationException;
import com.octo.android.robospice.persistence.memory.CacheItem;
import com.octo.android.robospice.persistence.memory.LruCache;
import com.octo.android.robospice.persistence.memory.LruCacheObjectPersister;
import net.grzechocinski.android.droidconkrakow.data.Match;

public class DemoSpiceService extends SpiceService {

    public static LruCache<Object, CacheItem<Match>> responseCache = new LruCache<>(128);

    @Override
    protected NetworkStateChecker getNetworkStateChecker() {
        return new NetworkStateChecker() {
            @Override
            public boolean isNetworkAvailable(Context context) {
                return true;
            }

            @Override
            public void checkPermissions(Context context) {

            }
        };
    }

    @Override
    public CacheManager createCacheManager(Application application) throws CacheCreationException {
        CacheManager cacheManager = new CacheManager();
        LruCacheObjectPersister<Match> jacksonObjectPersisterFactory = new LruCacheObjectPersister<>(Match.class, responseCache);
        cacheManager.addPersister(jacksonObjectPersisterFactory);
        return cacheManager;
    }
}
