/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objects;

import com.puttysoftware.mazer5d.objects.abc.GenericProgrammableKey;
import com.puttysoftware.mazer5d.utilities.MazeObjects;

class DarkRedCrystal extends GenericProgrammableKey {
    // Constructors
    public DarkRedCrystal() {
        super("Dark Red");
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.DARK_RED_CRYSTAL;
    }
}