/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objects;

import com.puttysoftware.mazer5d.objects.abc.GenericButton;
import com.puttysoftware.mazer5d.utilities.MazeObjects;

class WhiteButton extends GenericButton {
    public WhiteButton() {
        super(new WhiteWallOff(), new WhiteWallOn());
    }

    @Override
    public String getName() {
        return "White Button";
    }

    @Override
    public String getPluralName() {
        return "White Buttons";
    }

    @Override
    public String getDescription() {
        return "White Buttons will cause all White Walls Off to become On, and all White Walls On to become Off.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.WHITE_BUTTON;
    }
}
