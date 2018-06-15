package io.github.lucasduete.mobileHubApi.infraSecurity;

import javax.ws.rs.core.CacheControl;

public class CacheController {

    public static CacheControl getCacheControl() {
        CacheControl cacheControl = new CacheControl();

        cacheControl.setPrivate(true);
        cacheControl.setNoCache(false);
        cacheControl.setNoStore(false);
        cacheControl.setNoTransform(true);
        cacheControl.setMaxAge(300);
        cacheControl.setSMaxAge(300);

        return cacheControl;
    }
}
