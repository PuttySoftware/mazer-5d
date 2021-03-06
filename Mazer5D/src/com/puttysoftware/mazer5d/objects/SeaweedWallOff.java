/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objects;

import com.puttysoftware.mazer5d.objects.abc.GenericToggleWall;
import com.puttysoftware.mazer5d.utilities.MazeObjects;

class SeaweedWallOff extends GenericToggleWall {
    // Constructors
    public SeaweedWallOff() {
        super(false);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Seaweed Wall Off";
    }

    @Override
    public String getPluralName() {
        return "Seaweed Walls Off";
    }

    @Override
    public String getDescription() {
        return "Seaweed Walls Off can be walked through, and will change to Seaweed Walls On when a Seaweed Button is pressed.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.SEAWEED_WALL_OFF;
    }
}