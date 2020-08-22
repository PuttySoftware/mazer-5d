/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.gui;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.Modes;
import com.puttysoftware.mazer5d.editor.MazeEditor;
import com.puttysoftware.mazer5d.prefs.Prefs;

public class MenuManager {
    // Fields
    private final JMenuBar mainMenuBar;
    private JMenu editMenu, gameMenu, editorMenu;
    private JMenu editorFillSubMenu;
    private JMenuItem editUndo, editRedo, editCutLevel, editCopyLevel,
            editPasteLevel, editInsertLevelFromClipboard, editPreferences,
            editClearHistory;
    private JMenuItem gameObjectInventory, gameUse, gameSwitchBow, gameReset,
            gameShowScore, gameShowTable;
    private JMenuItem editorGoToLocation, editorGoToDestination,
            editorUpOneFloor, editorDownOneFloor, editorUpOneLevel,
            editorDownOneLevel, editorAddLevel, editorRemoveLevel,
            editorResizeLevel, editorToggleLayer, editorLevelPreferences,
            editorMazePreferences, editorSetStartPoint,
            editorSetFirstMovingFinish;
    private JMenuItem editorFillFloor, editorFillLevel, editorFillFloorRandomly,
            editorFillLevelRandomly, editorFillRuleSets;
    private JCheckBoxMenuItem editorFillUseRuleSets;
    private KeyStroke editUndoAccel, editRedoAccel, editCutLevelAccel,
            editCopyLevelAccel, editPasteLevelAccel,
            editInsertLevelFromClipboardAccel, editPreferencesAccel,
            editClearHistoryAccel;
    private KeyStroke gameObjectInventoryAccel, gameUseAccel,
            gameSwitchBowAccel, gameResetAccel, gameShowScoreAccel,
            gameShowTableAccel;
    private KeyStroke editorGoToLocationAccel, editorUpOneFloorAccel,
            editorDownOneFloorAccel, editorUpOneLevelAccel,
            editorDownOneLevelAccel, editorToggleLayerAccel;

    // Constructors
    public MenuManager() {
        this.mainMenuBar = new JMenuBar();
        this.createAccelerators();
        this.createMenus();
        this.createMenuItems();
        this.attachAccelerators();
        this.attachEventHandlers();
        this.assembleMenuItems();
        this.assembleMenus();
        this.setInitialMenuState();
    }

    // Methods
    public JMenuBar getMainMenuBar() {
        return this.mainMenuBar;
    }

    public void setGameMenus() {
        this.editUndo.setEnabled(false);
        this.editRedo.setEnabled(false);
        this.editCutLevel.setEnabled(false);
        this.editCopyLevel.setEnabled(false);
        this.editPasteLevel.setEnabled(false);
        this.editInsertLevelFromClipboard.setEnabled(false);
        this.editPreferences.setEnabled(true);
        this.editClearHistory.setEnabled(false);
        this.editorGoToLocation.setEnabled(false);
        this.editorGoToDestination.setEnabled(false);
        this.editorUpOneFloor.setEnabled(false);
        this.editorDownOneFloor.setEnabled(false);
        this.editorUpOneLevel.setEnabled(false);
        this.editorDownOneLevel.setEnabled(false);
        this.editorAddLevel.setEnabled(false);
        this.editorRemoveLevel.setEnabled(false);
        this.editorResizeLevel.setEnabled(false);
        this.editorFillFloor.setEnabled(false);
        this.editorFillLevel.setEnabled(false);
        this.editorFillFloorRandomly.setEnabled(false);
        this.editorFillLevelRandomly.setEnabled(false);
        this.editorFillRuleSets.setEnabled(false);
        this.editorFillUseRuleSets.setEnabled(false);
        this.editorToggleLayer.setEnabled(false);
        this.editorLevelPreferences.setEnabled(false);
        this.editorMazePreferences.setEnabled(false);
        this.editorSetStartPoint.setEnabled(false);
        this.editorSetFirstMovingFinish.setEnabled(false);
        this.gameObjectInventory.setEnabled(true);
        this.gameUse.setEnabled(true);
        this.gameSwitchBow.setEnabled(true);
        this.gameReset.setEnabled(true);
        this.gameShowScore.setEnabled(true);
        this.gameShowTable.setEnabled(true);
        this.checkFlags();
        this.checkFlags();
    }

    public void setEditorMenus() {
        this.editCutLevel.setEnabled(true);
        this.editCopyLevel.setEnabled(true);
        this.editPasteLevel.setEnabled(true);
        this.editInsertLevelFromClipboard.setEnabled(true);
        this.editPreferences.setEnabled(true);
        this.editorGoToLocation.setEnabled(true);
        this.editorGoToDestination.setEnabled(true);
        this.editorResizeLevel.setEnabled(true);
        this.editorFillFloor.setEnabled(true);
        this.editorFillLevel.setEnabled(true);
        this.editorFillFloorRandomly.setEnabled(true);
        this.editorFillLevelRandomly.setEnabled(true);
        this.editorFillRuleSets.setEnabled(true);
        this.editorFillUseRuleSets.setEnabled(true);
        this.editorToggleLayer.setEnabled(true);
        this.editorLevelPreferences.setEnabled(true);
        this.editorMazePreferences.setEnabled(true);
        this.editorSetStartPoint.setEnabled(true);
        this.editorSetFirstMovingFinish.setEnabled(true);
        this.gameObjectInventory.setEnabled(false);
        this.gameUse.setEnabled(false);
        this.gameSwitchBow.setEnabled(false);
        this.gameReset.setEnabled(false);
        this.gameShowScore.setEnabled(false);
        this.gameShowTable.setEnabled(false);
        this.checkFlags();
    }

    public void setPrefMenus() {
        this.editUndo.setEnabled(false);
        this.editRedo.setEnabled(false);
        this.editCutLevel.setEnabled(false);
        this.editCopyLevel.setEnabled(false);
        this.editPasteLevel.setEnabled(false);
        this.editInsertLevelFromClipboard.setEnabled(false);
        this.editPreferences.setEnabled(false);
        this.editClearHistory.setEnabled(false);
        this.editorGoToLocation.setEnabled(false);
        this.editorGoToDestination.setEnabled(false);
        this.editorUpOneFloor.setEnabled(false);
        this.editorDownOneFloor.setEnabled(false);
        this.editorUpOneLevel.setEnabled(false);
        this.editorDownOneLevel.setEnabled(false);
        this.editorAddLevel.setEnabled(false);
        this.editorRemoveLevel.setEnabled(false);
        this.editorResizeLevel.setEnabled(false);
        this.editorFillFloor.setEnabled(false);
        this.editorFillLevel.setEnabled(false);
        this.editorFillFloorRandomly.setEnabled(false);
        this.editorFillLevelRandomly.setEnabled(false);
        this.editorFillRuleSets.setEnabled(false);
        this.editorFillUseRuleSets.setEnabled(false);
        this.editorToggleLayer.setEnabled(false);
        this.editorLevelPreferences.setEnabled(false);
        this.editorMazePreferences.setEnabled(false);
        this.editorSetStartPoint.setEnabled(false);
        this.editorSetFirstMovingFinish.setEnabled(false);
        this.gameObjectInventory.setEnabled(false);
        this.gameUse.setEnabled(false);
        this.gameSwitchBow.setEnabled(false);
        this.gameReset.setEnabled(false);
        this.gameShowScore.setEnabled(false);
        this.gameShowTable.setEnabled(false);
    }

    public void setHelpMenus() {
        this.editUndo.setEnabled(false);
        this.editRedo.setEnabled(false);
        this.editCutLevel.setEnabled(false);
        this.editCopyLevel.setEnabled(false);
        this.editPasteLevel.setEnabled(false);
        this.editInsertLevelFromClipboard.setEnabled(false);
        this.editPreferences.setEnabled(false);
        this.editClearHistory.setEnabled(false);
        this.editorGoToLocation.setEnabled(false);
        this.editorGoToDestination.setEnabled(false);
        this.editorUpOneFloor.setEnabled(false);
        this.editorDownOneFloor.setEnabled(false);
        this.editorUpOneLevel.setEnabled(false);
        this.editorDownOneLevel.setEnabled(false);
        this.editorAddLevel.setEnabled(false);
        this.editorRemoveLevel.setEnabled(false);
        this.editorResizeLevel.setEnabled(false);
        this.editorFillFloor.setEnabled(false);
        this.editorFillLevel.setEnabled(false);
        this.editorFillFloorRandomly.setEnabled(false);
        this.editorFillLevelRandomly.setEnabled(false);
        this.editorFillRuleSets.setEnabled(false);
        this.editorFillUseRuleSets.setEnabled(false);
        this.editorToggleLayer.setEnabled(false);
        this.editorLevelPreferences.setEnabled(false);
        this.editorMazePreferences.setEnabled(false);
        this.editorSetStartPoint.setEnabled(false);
        this.gameObjectInventory.setEnabled(false);
        this.gameUse.setEnabled(false);
        this.gameSwitchBow.setEnabled(false);
        this.gameReset.setEnabled(false);
        this.gameShowScore.setEnabled(false);
        this.gameShowTable.setEnabled(false);
    }

    public void setMainMenus() {
        this.editUndo.setEnabled(false);
        this.editRedo.setEnabled(false);
        this.editCutLevel.setEnabled(false);
        this.editCopyLevel.setEnabled(false);
        this.editPasteLevel.setEnabled(false);
        this.editInsertLevelFromClipboard.setEnabled(false);
        this.editPreferences.setEnabled(true);
        this.editClearHistory.setEnabled(false);
        this.editorGoToLocation.setEnabled(false);
        this.editorGoToDestination.setEnabled(false);
        this.editorUpOneFloor.setEnabled(false);
        this.editorDownOneFloor.setEnabled(false);
        this.editorUpOneLevel.setEnabled(false);
        this.editorDownOneLevel.setEnabled(false);
        this.editorAddLevel.setEnabled(false);
        this.editorRemoveLevel.setEnabled(false);
        this.editorFillFloor.setEnabled(false);
        this.editorFillLevel.setEnabled(false);
        this.editorFillFloorRandomly.setEnabled(false);
        this.editorFillLevelRandomly.setEnabled(false);
        this.editorFillRuleSets.setEnabled(false);
        this.editorFillUseRuleSets.setEnabled(false);
        this.editorResizeLevel.setEnabled(false);
        this.editorToggleLayer.setEnabled(false);
        this.editorLevelPreferences.setEnabled(false);
        this.editorMazePreferences.setEnabled(false);
        this.editorSetStartPoint.setEnabled(false);
        this.editorSetFirstMovingFinish.setEnabled(false);
        this.gameObjectInventory.setEnabled(false);
        this.gameUse.setEnabled(false);
        this.gameSwitchBow.setEnabled(false);
        this.gameReset.setEnabled(false);
        this.gameShowScore.setEnabled(false);
        this.gameShowTable.setEnabled(false);
        this.checkFlags();
    }

    public void enableUpOneFloor() {
        this.editorUpOneFloor.setEnabled(true);
    }

    public void disableUpOneFloor() {
        this.editorUpOneFloor.setEnabled(false);
    }

    public void enableDownOneFloor() {
        this.editorDownOneFloor.setEnabled(true);
    }

    public void disableDownOneFloor() {
        this.editorDownOneFloor.setEnabled(false);
    }

    public void enableUpOneLevel() {
        this.editorUpOneLevel.setEnabled(true);
    }

    public void disableUpOneLevel() {
        this.editorUpOneLevel.setEnabled(false);
    }

    public void enableDownOneLevel() {
        this.editorDownOneLevel.setEnabled(true);
    }

    public void disableDownOneLevel() {
        this.editorDownOneLevel.setEnabled(false);
    }

    public void enableAddLevel() {
        this.editorAddLevel.setEnabled(true);
    }

    public void disableAddLevel() {
        this.editorAddLevel.setEnabled(false);
    }

    public void enableRemoveLevel() {
        this.editorRemoveLevel.setEnabled(true);
    }

    public void disableRemoveLevel() {
        this.editorRemoveLevel.setEnabled(false);
    }

    public void enableUndo() {
        this.editUndo.setEnabled(true);
    }

    public void disableUndo() {
        this.editUndo.setEnabled(false);
    }

    public void enableRedo() {
        this.editRedo.setEnabled(true);
    }

    public void disableRedo() {
        this.editRedo.setEnabled(false);
    }

    public void enableClearHistory() {
        this.editClearHistory.setEnabled(true);
    }

    public void disableClearHistory() {
        this.editClearHistory.setEnabled(false);
    }

    public void enableCutLevel() {
        this.editCutLevel.setEnabled(true);
    }

    public void disableCutLevel() {
        this.editCutLevel.setEnabled(false);
    }

    public void enablePasteLevel() {
        this.editPasteLevel.setEnabled(true);
    }

    public void disablePasteLevel() {
        this.editPasteLevel.setEnabled(false);
    }

    public void enableInsertLevelFromClipboard() {
        this.editInsertLevelFromClipboard.setEnabled(true);
    }

    public void disableInsertLevelFromClipboard() {
        this.editInsertLevelFromClipboard.setEnabled(false);
    }

    public void enableSetStartPoint() {
        this.editorSetStartPoint.setEnabled(true);
    }

    public void disableSetStartPoint() {
        this.editorSetStartPoint.setEnabled(false);
    }

    public void checkFlags() {
        final BagOStuff bag = Mazer5D.getBagOStuff();
        if (Modes.inEditor()) {
            if (bag.getMazeManager().getMaze().isPasteBlocked()) {
                this.disablePasteLevel();
                this.disableInsertLevelFromClipboard();
            } else {
                this.enablePasteLevel();
                this.enableInsertLevelFromClipboard();
            }
            if (bag.getMazeManager().getMaze().isCutBlocked()) {
                this.disableCutLevel();
            } else {
                this.enableCutLevel();
            }
        }
    }

    public boolean useFillRuleSets() {
        return this.editorFillUseRuleSets.getState();
    }

    private final void createAccelerators() {
        int modKey;
        if (System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            modKey = InputEvent.META_DOWN_MASK;
        } else {
            modKey = InputEvent.CTRL_DOWN_MASK;
        }
        this.editUndoAccel = KeyStroke.getKeyStroke(KeyEvent.VK_Z, modKey);
        this.editRedoAccel = KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                modKey | InputEvent.SHIFT_DOWN_MASK);
        this.editCutLevelAccel = KeyStroke.getKeyStroke(KeyEvent.VK_X, modKey);
        this.editCopyLevelAccel = KeyStroke.getKeyStroke(KeyEvent.VK_C, modKey);
        this.editPasteLevelAccel = KeyStroke.getKeyStroke(KeyEvent.VK_V,
                modKey);
        this.editInsertLevelFromClipboardAccel = KeyStroke
                .getKeyStroke(KeyEvent.VK_F, modKey);
        this.editPreferencesAccel = KeyStroke.getKeyStroke(KeyEvent.VK_COMMA,
                modKey);
        this.editClearHistoryAccel = KeyStroke.getKeyStroke(KeyEvent.VK_Y,
                modKey);
        this.editorGoToLocationAccel = KeyStroke.getKeyStroke(KeyEvent.VK_G,
                modKey | InputEvent.SHIFT_DOWN_MASK);
        this.gameObjectInventoryAccel = KeyStroke.getKeyStroke(KeyEvent.VK_I,
                modKey);
        this.gameUseAccel = KeyStroke.getKeyStroke(KeyEvent.VK_U, modKey);
        this.gameSwitchBowAccel = KeyStroke.getKeyStroke(KeyEvent.VK_B, modKey);
        this.gameResetAccel = KeyStroke.getKeyStroke(KeyEvent.VK_R, modKey);
        this.gameShowScoreAccel = KeyStroke.getKeyStroke(KeyEvent.VK_G, modKey);
        this.gameShowTableAccel = KeyStroke.getKeyStroke(KeyEvent.VK_T, modKey);
        this.editorUpOneFloorAccel = KeyStroke.getKeyStroke(KeyEvent.VK_UP,
                modKey);
        this.editorDownOneFloorAccel = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,
                modKey);
        this.editorUpOneLevelAccel = KeyStroke.getKeyStroke(KeyEvent.VK_UP,
                modKey | InputEvent.SHIFT_DOWN_MASK);
        this.editorDownOneLevelAccel = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,
                modKey | InputEvent.SHIFT_DOWN_MASK);
        this.editorToggleLayerAccel = KeyStroke.getKeyStroke(KeyEvent.VK_L,
                modKey);
    }

    private final void createMenus() {
        this.editMenu = new JMenu("Edit");
        this.gameMenu = new JMenu("Game");
        this.editorMenu = new JMenu("Editor");
        this.editorFillSubMenu = new JMenu("Fill");
    }

    private final void createMenuItems() {
        this.editUndo = new JMenuItem("Undo");
        this.editRedo = new JMenuItem("Redo");
        this.editCutLevel = new JMenuItem("Cut Level");
        this.editCopyLevel = new JMenuItem("Copy Level");
        this.editPasteLevel = new JMenuItem("Paste Level");
        this.editInsertLevelFromClipboard = new JMenuItem(
                "Insert Level From Clipboard");
        this.editPreferences = new JMenuItem("Preferences...");
        this.editClearHistory = new JMenuItem("Clear History");
        this.gameObjectInventory = new JMenuItem("Show Inventory...");
        this.gameUse = new JMenuItem("Use an Item...");
        this.gameSwitchBow = new JMenuItem("Switch Bow...");
        this.gameReset = new JMenuItem("Reset Current Level");
        this.gameShowScore = new JMenuItem("Show Current Score");
        this.gameShowTable = new JMenuItem("Show Score Table");
        this.editorGoToLocation = new JMenuItem("Go To Location...");
        this.editorGoToDestination = new JMenuItem("Go To Destination...");
        this.editorUpOneFloor = new JMenuItem("Up One Floor");
        this.editorDownOneFloor = new JMenuItem("Down One Floor");
        this.editorUpOneLevel = new JMenuItem("Up One Level");
        this.editorDownOneLevel = new JMenuItem("Down One Level");
        this.editorAddLevel = new JMenuItem("Add a Level...");
        this.editorRemoveLevel = new JMenuItem("Remove a Level...");
        this.editorResizeLevel = new JMenuItem("Resize Current Level...");
        this.editorFillFloor = new JMenuItem("Fill Current Floor");
        this.editorFillLevel = new JMenuItem("Fill Current Level");
        this.editorFillFloorRandomly = new JMenuItem(
                "Fill Current Floor Randomly");
        this.editorFillLevelRandomly = new JMenuItem(
                "Fill Current Level Randomly");
        this.editorFillRuleSets = new JMenuItem("Fill Rule Sets...");
        this.editorFillUseRuleSets = new JCheckBoxMenuItem(
                "Use Fill Rule Sets");
        this.editorToggleLayer = new JMenuItem("Toggle Layer");
        this.editorToggleLayer.setAccelerator(this.editorToggleLayerAccel);
        this.editorLevelPreferences = new JMenuItem("Level Preferences...");
        this.editorMazePreferences = new JMenuItem("Maze Preferences...");
        this.editorSetStartPoint = new JMenuItem("Set Start Point...");
        this.editorSetFirstMovingFinish = new JMenuItem(
                "Set First Moving Finish...");
    }

    private final void attachAccelerators() {
        this.editUndo.setAccelerator(this.editUndoAccel);
        this.editRedo.setAccelerator(this.editRedoAccel);
        this.editCutLevel.setAccelerator(this.editCutLevelAccel);
        this.editCopyLevel.setAccelerator(this.editCopyLevelAccel);
        this.editPasteLevel.setAccelerator(this.editPasteLevelAccel);
        this.editInsertLevelFromClipboard
                .setAccelerator(this.editInsertLevelFromClipboardAccel);
        this.editPreferences.setAccelerator(this.editPreferencesAccel);
        this.editClearHistory.setAccelerator(this.editClearHistoryAccel);
        this.gameObjectInventory.setAccelerator(this.gameObjectInventoryAccel);
        this.gameUse.setAccelerator(this.gameUseAccel);
        this.gameSwitchBow.setAccelerator(this.gameSwitchBowAccel);
        this.gameReset.setAccelerator(this.gameResetAccel);
        this.gameShowScore.setAccelerator(this.gameShowScoreAccel);
        this.gameShowTable.setAccelerator(this.gameShowTableAccel);
        this.editorGoToLocation.setAccelerator(this.editorGoToLocationAccel);
        this.editorUpOneFloor.setAccelerator(this.editorUpOneFloorAccel);
        this.editorDownOneFloor.setAccelerator(this.editorDownOneFloorAccel);
        this.editorUpOneLevel.setAccelerator(this.editorUpOneLevelAccel);
        this.editorDownOneLevel.setAccelerator(this.editorDownOneLevelAccel);
    }

    private final void attachEventHandlers() {
        final BagOStuff bag = Mazer5D.getBagOStuff();
        this.editUndo.addActionListener(h -> bag.getEditor().undo());
        this.editRedo.addActionListener(h -> bag.getEditor().redo());
        this.editCutLevel.addActionListener(h -> bag.getEditor().cutLevel());
        this.editCopyLevel.addActionListener(h -> bag.getEditor().copyLevel());
        this.editPasteLevel
                .addActionListener(h -> bag.getEditor().pasteLevel());
        this.editInsertLevelFromClipboard.addActionListener(
                h -> bag.getEditor().insertLevelFromClipboard());
        this.editPreferences.addActionListener(h -> Prefs.showPrefs());
        this.editClearHistory
                .addActionListener(h -> bag.getEditor().clearHistory());
        this.gameObjectInventory.addActionListener(h -> bag.getGameManager().showInventoryDialog());
        this.gameUse.addActionListener(h -> bag.getGameManager().showUseDialog());
        this.gameSwitchBow.addActionListener(h -> bag.getGameManager().showSwitchBowDialog());
        this.gameReset.addActionListener(h -> bag.getGameManager().resetCurrentLevel());
        this.gameShowScore.addActionListener(
                h -> bag.getGameManager().showCurrentScore());
        this.gameShowTable
                .addActionListener(h -> bag.getGameManager().showScoreTable());
        this.editorGoToLocation
                .addActionListener(h -> bag.getEditor().goToLocationHandler());
        this.editorGoToDestination.addActionListener(
                h -> bag.getEditor().goToDestinationHandler());
        this.editorUpOneFloor.addActionListener(
                h -> bag.getEditor().updateEditorPosition(0, 0, 1, 0));
        this.editorDownOneFloor.addActionListener(
                h -> bag.getEditor().updateEditorPosition(0, 0, -1, 0));
        this.editorUpOneLevel.addActionListener(
                h -> bag.getEditor().updateEditorPosition(0, 0, 0, 1));
        this.editorDownOneLevel.addActionListener(
                h -> bag.getEditor().updateEditorPosition(0, 0, 0, -1));
        this.editorAddLevel.addActionListener(h -> bag.getEditor().addLevel());
        this.editorRemoveLevel
                .addActionListener(h -> bag.getEditor().removeLevel());
        this.editorResizeLevel
                .addActionListener(h -> bag.getEditor().resizeLevel());
        this.editorFillFloor
                .addActionListener(h -> bag.getEditor().fillFloor());
        this.editorFillLevel
                .addActionListener(h -> bag.getEditor().fillLevel());
        this.editorFillFloorRandomly
                .addActionListener(h -> bag.getEditor().fillFloorRandomly());
        this.editorFillLevelRandomly
                .addActionListener(h -> bag.getEditor().fillLevelRandomly());
        this.editorFillRuleSets
                .addActionListener(h -> bag.getRuleSetPicker().editRuleSets());
        this.editorToggleLayer
                .addActionListener(h -> bag.getEditor().toggleLayer());
        this.editorLevelPreferences
                .addActionListener(h -> bag.getEditor().setLevelPrefs());
        this.editorMazePreferences
                .addActionListener(h -> bag.getEditor().setMazePrefs());
        this.editorSetStartPoint
                .addActionListener(h -> bag.getEditor().editPlayerLocation());
        this.editorSetFirstMovingFinish
                .addActionListener(h -> bag.getEditor().editTeleportDestination(
                        MazeEditor.TELEPORT_TYPE_FIRST_MOVING_FINISH));
    }

    private final void assembleMenuItems() {
        this.editorFillSubMenu.add(this.editorFillFloor);
        this.editorFillSubMenu.add(this.editorFillLevel);
        this.editorFillSubMenu.add(this.editorFillFloorRandomly);
        this.editorFillSubMenu.add(this.editorFillLevelRandomly);
        this.editorFillSubMenu.add(this.editorFillRuleSets);
        this.editorFillSubMenu.add(this.editorFillUseRuleSets);
        this.editMenu.add(this.editUndo);
        this.editMenu.add(this.editRedo);
        this.editMenu.add(this.editCutLevel);
        this.editMenu.add(this.editCopyLevel);
        this.editMenu.add(this.editPasteLevel);
        this.editMenu.add(this.editInsertLevelFromClipboard);
        if (!System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            this.editMenu.add(this.editPreferences);
        }
        this.editMenu.add(this.editClearHistory);
        this.gameMenu.add(this.gameObjectInventory);
        this.gameMenu.add(this.gameUse);
        this.gameMenu.add(this.gameSwitchBow);
        this.gameMenu.add(this.gameReset);
        this.gameMenu.add(this.gameShowScore);
        this.gameMenu.add(this.gameShowTable);
        this.editorMenu.add(this.editorGoToLocation);
        this.editorMenu.add(this.editorGoToDestination);
        this.editorMenu.add(this.editorUpOneFloor);
        this.editorMenu.add(this.editorDownOneFloor);
        this.editorMenu.add(this.editorUpOneLevel);
        this.editorMenu.add(this.editorDownOneLevel);
        this.editorMenu.add(this.editorAddLevel);
        this.editorMenu.add(this.editorRemoveLevel);
        this.editorMenu.add(this.editorResizeLevel);
        this.editorMenu.add(this.editorFillSubMenu);
        this.editorMenu.add(this.editorToggleLayer);
        this.editorMenu.add(this.editorLevelPreferences);
        this.editorMenu.add(this.editorMazePreferences);
        this.editorMenu.add(this.editorSetStartPoint);
        this.editorMenu.add(this.editorSetFirstMovingFinish);
    }

    private final void assembleMenus() {
        this.mainMenuBar.add(this.editMenu);
        this.mainMenuBar.add(this.gameMenu);
        this.mainMenuBar.add(this.editorMenu);
    }

    private final void setInitialMenuState() {
        this.editUndo.setEnabled(false);
        this.editRedo.setEnabled(false);
        this.editCutLevel.setEnabled(false);
        this.editCopyLevel.setEnabled(false);
        this.editPasteLevel.setEnabled(false);
        this.editInsertLevelFromClipboard.setEnabled(false);
        this.editPreferences.setEnabled(true);
        this.editClearHistory.setEnabled(false);
        this.editorGoToLocation.setEnabled(false);
        this.editorGoToDestination.setEnabled(false);
        this.editorUpOneFloor.setEnabled(false);
        this.editorDownOneFloor.setEnabled(false);
        this.editorUpOneLevel.setEnabled(false);
        this.editorDownOneLevel.setEnabled(false);
        this.editorAddLevel.setEnabled(false);
        this.editorRemoveLevel.setEnabled(false);
        this.editorResizeLevel.setEnabled(false);
        this.editorFillFloor.setEnabled(false);
        this.editorFillLevel.setEnabled(false);
        this.editorFillFloorRandomly.setEnabled(false);
        this.editorFillLevelRandomly.setEnabled(false);
        this.editorFillRuleSets.setEnabled(false);
        this.editorFillUseRuleSets.setEnabled(false);
        this.editorToggleLayer.setEnabled(false);
        this.editorLevelPreferences.setEnabled(false);
        this.editorMazePreferences.setEnabled(false);
        this.editorSetStartPoint.setEnabled(false);
        this.editorSetFirstMovingFinish.setEnabled(false);
        this.gameObjectInventory.setEnabled(false);
        this.gameUse.setEnabled(false);
        this.gameSwitchBow.setEnabled(false);
        this.gameReset.setEnabled(false);
        this.gameShowScore.setEnabled(false);
        this.gameShowTable.setEnabled(false);
    }
}
