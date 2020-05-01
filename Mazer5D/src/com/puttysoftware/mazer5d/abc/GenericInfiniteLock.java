/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.abc;

import com.puttysoftware.mazer5d.utilities.TypeConstants;

public abstract class GenericInfiniteLock extends GenericLock {
    protected GenericInfiniteLock(final GenericInfiniteKey mgk) {
        super(mgk);
    }

    protected GenericInfiniteLock(final GenericInfiniteKey mgk,
            final boolean doesAcceptPushInto) {
        super(mgk, doesAcceptPushInto);
    }

    protected GenericInfiniteLock(final boolean isSolid, final GenericKey mgk) {
        super(isSolid, mgk);
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_UNLOCKED_KEEP_KEY);
        this.type.set(TypeConstants.TYPE_LOCK);
    }
}