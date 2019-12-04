package com.datorama.automation.General.requests;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author khalil
 *
 *Singleton class which handle the api requests cache over the whole run
 */

public class CachedApisHelper {

    private static CachedApisHelper cachedApisHelper = null;

    public Map<String, String> etags = new HashMap<String, String>();

    public static CachedApisHelper getInstance()
    {
        if (cachedApisHelper == null)
            cachedApisHelper = new CachedApisHelper();

        return cachedApisHelper;
    }
}
