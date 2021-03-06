/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objects.abc;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.abc.MazeObject;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;
import com.puttysoftware.mazer5d.utilities.Layers;
import com.puttysoftware.mazer5d.utilities.TypeConstants;

public abstract class GenericButton extends MazeObject {
    // Fields
    private GenericToggleWall offState;
    private GenericToggleWall onState;

    // Constructors
    protected GenericButton(final GenericToggleWall off,
            final GenericToggleWall on) {
        super(false);
        this.offState = off;
        this.onState = on;
        this.setType(TypeConstants.TYPE_BUTTON);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final GenericButton other = (GenericButton) obj;
        if (this.offState != other.offState && (this.offState == null
                || !this.offState.equals(other.offState))) {
            return false;
        }
        if (this.onState != other.onState && (this.onState == null
                || !this.onState.equals(other.onState))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (this.offState != null ? this.offState.hashCode()
                : 0);
        hash = 13 * hash + (this.onState != null ? this.onState.hashCode() : 0);
        return hash;
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        Mazer5D.getBagOStuff().getMazeManager().getMaze()
                .findAllObjectPairsAndSwap(this.offState, this.onState);
        Mazer5D.getBagOStuff().getGameManager().redrawMazeNoRebuild();
        SoundPlayer.playSound(SoundIndex.BUTTON, SoundGroup.GAME);
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int arrowType,
            final ObjectInventory inv) {
        // Behave as if the button was stepped on
        this.postMoveAction(false, dirX, dirY, inv);
        return false;
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return Layers.OBJECT;
    }
}