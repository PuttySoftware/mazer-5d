/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericPlug;
import com.puttysoftware.mazer5d.utilities.MazeObjects;

class UPlug extends GenericPlug {
    // Constructors
    public UPlug() {
        super('U');
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.U_PLUG;
    }
}