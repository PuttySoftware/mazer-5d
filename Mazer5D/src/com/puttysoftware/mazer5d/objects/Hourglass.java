/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;
import com.puttysoftware.mazer5d.objects.abc.GenericTimeModifier;
import com.puttysoftware.mazer5d.utilities.MazeObjects;

class Hourglass extends GenericTimeModifier {
    // Fields
    private static final long SCORE_GRAB = 10L;

    // Constructors
    public Hourglass() {
        super();
    }

    @Override
    public String getName() {
        return "Hourglass";
    }

    @Override
    public String getPluralName() {
        return "Hourglasses";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        Mazer5D.getBagOStuff().getGameManager().decay();
        Mazer5D.getBagOStuff().getMazeManager().getMaze()
                .extendTimerByInitialValue();
        SoundPlayer.playSound(SoundIndex.GRAB, SoundGroup.GAME);
        Mazer5D.getBagOStuff().getGameManager().addToScore(
                Hourglass.SCORE_GRAB);
    }

    @Override
    public String getDescription() {
        return "Hourglasses extend the time to solve the current level by the initial value.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.HOURGLASS;
    }
}
