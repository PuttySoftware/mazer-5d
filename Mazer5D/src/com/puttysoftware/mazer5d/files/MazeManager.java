/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.files;

import java.awt.Frame;
import java.awt.desktop.OpenFilesEvent;
import java.awt.desktop.OpenFilesHandler;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.puttysoftware.fileutils.FilenameChecker;
import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.Modes;
import com.puttysoftware.mazer5d.abc.MazeObject;
import com.puttysoftware.mazer5d.commondialogs.CommonDialogs;
import com.puttysoftware.mazer5d.files.format.XMLExtension;
import com.puttysoftware.mazer5d.files.format.XMLGameFilter;
import com.puttysoftware.mazer5d.files.format.XMLLoadTask;
import com.puttysoftware.mazer5d.files.format.XMLMazeFilter;
import com.puttysoftware.mazer5d.files.format.XMLSaveTask;
import com.puttysoftware.mazer5d.files.locking.LockedFilter;
import com.puttysoftware.mazer5d.files.locking.LockedLoadTask;
import com.puttysoftware.mazer5d.files.locking.LockedSaveTask;
import com.puttysoftware.mazer5d.gui.BagOStuff;
import com.puttysoftware.mazer5d.maze.Maze;
import com.puttysoftware.mazer5d.prefs.Prefs;

public class MazeManager implements OpenFilesHandler {
    // Fields
    private Maze gameMaze;
    private boolean loaded, isDirty, locked;
    private String scoresFileName;
    private String lastUsedMazeFile;
    private String lastUsedGameFile;
    private boolean mazeXML1Compatible;
    private boolean mazeXML2Compatible;
    private boolean mazeXML4Compatible;

    // Constructors
    public MazeManager() {
        this.loaded = false;
        this.isDirty = false;
        this.lastUsedMazeFile = "";
        this.lastUsedGameFile = "";
        this.scoresFileName = "";
        this.mazeXML1Compatible = false;
        this.mazeXML2Compatible = false;
        this.mazeXML4Compatible = false;
        this.gameMaze = new Maze();
    }

    // Methods
    public Maze getMaze() {
        return this.gameMaze;
    }

    public void setMaze(final Maze newMaze) {
        this.gameMaze = newMaze;
    }

    public boolean isMazeXML1Compatible() {
        return this.mazeXML1Compatible;
    }

    public void setMazeXML1Compatible(final boolean value) {
        this.mazeXML1Compatible = value;
    }

    public boolean isMazeXML2Compatible() {
        return this.mazeXML2Compatible;
    }

    public void setMazeXML2Compatible(final boolean value) {
        this.mazeXML2Compatible = value;
    }

    public boolean isMazeXML4Compatible() {
        return this.mazeXML4Compatible;
    }

    public void setMazeXML4Compatible(final boolean value) {
        this.mazeXML4Compatible = value;
    }

    public void handleDeferredSuccess(final boolean value) {
        if (value) {
            this.setLoaded(true);
        }
        this.setDirty(false);
        Mazer5D.getBagOStuff().getGameManager().stateChanged();
        Mazer5D.getBagOStuff().getEditor().mazeChanged();
    }

    public MazeObject getMazeObject(final int x, final int y, final int z,
            final int e) {
        try {
            return this.gameMaze.getCell(x, y, z, e);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return null;
        }
    }

    public int showSaveDialog() {
        String type, source;
        if (Modes.inEditor()) {
            type = "maze";
            source = "Editor";
        } else if (Modes.inGame()) {
            type = "game";
            source = "Mazer5D";
        } else {
            // Not in the game or editor, so abort
            return CommonDialogs.NO_OPTION;
        }
        int status = CommonDialogs.DEFAULT_OPTION;
        status = CommonDialogs.showYNCConfirmDialog(
                "Do you want to save your " + type + "?", source);
        return status;
    }

    public boolean getLoaded() {
        return this.loaded;
    }

    public void setLoaded(final boolean status) {
        this.loaded = status;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void clearLockedFlag() {
        this.locked = false;
    }

    public void setLockedFlag() {
        this.locked = true;
    }

    public boolean getDirty() {
        return this.isDirty;
    }

    public void setDirty(final boolean newDirty) {
        this.isDirty = newDirty;
    }

    public void clearLastUsedFilenames() {
        this.lastUsedMazeFile = "";
        this.lastUsedGameFile = "";
    }

    public String getLastUsedMaze() {
        return this.lastUsedMazeFile;
    }

    public String getLastUsedGame() {
        return this.lastUsedGameFile;
    }

    public void setLastUsedMaze(final String newFile) {
        this.lastUsedMazeFile = newFile;
    }

    public void setLastUsedGame(final String newFile) {
        this.lastUsedGameFile = newFile;
    }

    public String getScoresFileName() {
        return this.scoresFileName;
    }

    public void setScoresFileName(final String filename) {
        this.scoresFileName = filename;
    }

    @Override
    public void openFiles(final OpenFilesEvent inE) {
        final BagOStuff app = Mazer5D.getBagOStuff();
        if (!this.loaded) {
            for (final File file : inE.getFiles()) {
                String extension;
                String loadFile;
                loadFile = file.getAbsolutePath();
                extension = MazeManager.getExtension(file);
                app.getGameManager().resetObjectInventory();
                if (extension.equals(XMLExtension.getXMLMazeExtension())) {
                    this.lastUsedMazeFile = loadFile;
                    this.scoresFileName = MazeManager
                            .getNameWithoutExtension(file.getName());
                    MazeManager.loadFile(loadFile, false, false);
                } else if (extension
                        .equals(Extension.getLockedMazeExtension())) {
                    this.lastUsedMazeFile = loadFile;
                    this.scoresFileName = MazeManager
                            .getNameWithoutExtension(file.getName());
                    MazeManager.loadFile(loadFile, false, true);
                } else if (extension
                        .equals(XMLExtension.getXMLGameExtension())) {
                    this.lastUsedGameFile = loadFile;
                    MazeManager.loadFile(loadFile, true, false);
                } else if (extension
                        .equals(XMLExtension.getXMLScoresExtension())) {
                    CommonDialogs.showDialog(
                            "You double-clicked a scores file. These are automatically loaded when their associated maze is loaded, and need not be double-clicked.");
                } else if (extension
                        .equals(Extension.getPreferencesExtension())) {
                    CommonDialogs.showDialog(
                            "You double-clicked a preferences file. These are automatically loaded when the program is loaded, and need not be double-clicked.");
                } else if (extension
                        .equals(XMLExtension.getXMLRuleSetExtension())) {
                    CommonDialogs.showDialog(
                            "You double-clicked a rule set file. These are loaded by the Rule Set Picker, and need not be double-clicked.");
                }
            }
        }
    }

    public void loadMaze() {
        final BagOStuff app = Mazer5D.getBagOStuff();
        int status = 0;
        boolean saved = true;
        String filename, extension;
        final String lastOpen = Prefs.getLastDirOpen();
        File lastOpenDir = null;
        if (lastOpen != null) {
            lastOpenDir = new File(lastOpen);
        }
        final JFileChooser fc = new JFileChooser(lastOpenDir);
        final XMLMazeFilter xmf = new XMLMazeFilter();
        final XMLGameFilter xgf = new XMLGameFilter();
        if (this.getDirty()) {
            status = this.showSaveDialog();
            if (status == CommonDialogs.YES_OPTION) {
                this.saveMaze();
                saved = !this.getDirty();
            } else if (status == CommonDialogs.CANCEL_OPTION) {
                saved = false;
            } else {
                this.setDirty(false);
            }
        }
        if (saved) {
            fc.setAcceptAllFileFilterUsed(false);
            fc.addChoosableFileFilter(xmf);
            fc.addChoosableFileFilter(xgf);
            final int filter = Prefs.getLastFilterUsedOpen();
            if (filter == Prefs.FILTER_MAZE) {
                fc.setFileFilter(xmf);
            } else {
                fc.setFileFilter(xgf);
            }
            final int returnVal = fc.showOpenDialog((Frame) null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                final File file = fc.getSelectedFile();
                final FileFilter ff = fc.getFileFilter();
                if (ff.getDescription().equals(xmf.getDescription())) {
                    Prefs.setLastFilterUsedOpen(Prefs.FILTER_MAZE);
                } else {
                    Prefs.setLastFilterUsedOpen(Prefs.FILTER_GAME);
                }
                Prefs.setLastDirOpen(
                        fc.getCurrentDirectory().getAbsolutePath());
                filename = file.getAbsolutePath();
                extension = MazeManager.getExtension(file);
                app.getGameManager().resetObjectInventory();
                if (extension.equals(XMLExtension.getXMLMazeExtension())) {
                    this.lastUsedMazeFile = filename;
                    this.scoresFileName = MazeManager
                            .getNameWithoutExtension(file.getName());
                    MazeManager.loadFile(filename, false, false);
                    this.setLoaded(true);
                } else if (extension
                        .equals(XMLExtension.getXMLGameExtension())) {
                    this.lastUsedGameFile = filename;
                    MazeManager.loadFile(filename, true, false);
                    this.setLoaded(true);
                } else {
                    CommonDialogs.showDialog(
                            "You opened something other than a maze file. Select a maze file, and try again.");
                    this.setLoaded(false);
                }
            }
        }
    }

    public void loadLockedMaze() {
        final BagOStuff app = Mazer5D.getBagOStuff();
        int status = 0;
        boolean saved = true;
        String filename, extension;
        final String lastOpen = Prefs.getLastDirOpen();
        File lastOpenDir = null;
        if (lastOpen != null) {
            lastOpenDir = new File(lastOpen);
        }
        final JFileChooser fc = new JFileChooser(lastOpenDir);
        final LockedFilter lf = new LockedFilter();
        if (this.getDirty()) {
            status = this.showSaveDialog();
            if (status == CommonDialogs.YES_OPTION) {
                this.saveMaze();
                saved = !this.getDirty();
            } else if (status == CommonDialogs.CANCEL_OPTION) {
                saved = false;
            } else {
                this.setDirty(false);
            }
        }
        if (saved) {
            fc.setAcceptAllFileFilterUsed(false);
            fc.addChoosableFileFilter(lf);
            fc.setFileFilter(lf);
            final int returnVal = fc.showOpenDialog((Frame) null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                final File file = fc.getSelectedFile();
                Prefs.setLastDirOpen(
                        fc.getCurrentDirectory().getAbsolutePath());
                filename = file.getAbsolutePath();
                extension = MazeManager.getExtension(file);
                app.getGameManager().resetObjectInventory();
                if (extension.equals(Extension.getLockedMazeExtension())) {
                    this.lastUsedMazeFile = filename;
                    this.scoresFileName = MazeManager
                            .getNameWithoutExtension(file.getName());
                    MazeManager.loadFile(filename, false, true);
                    this.setLoaded(true);
                } else {
                    CommonDialogs.showDialog(
                            "You opened something other than a locked maze file. Select a locked maze file, and try again.");
                    this.setLoaded(false);
                }
            }
        }
    }

    private static void loadFile(final String filename,
            final boolean isSavedGame, final boolean locked) {
        if (!FilenameChecker.isFilenameOK(MazeManager.getNameWithoutExtension(
                MazeManager.getFileNameOnly(filename)))) {
            CommonDialogs.showErrorDialog(
                    "The file you selected contains illegal characters in its\n"
                            + "name. These characters are not allowed: /?<>\\:|\"\n"
                            + "Files named con, nul, or prn are illegal, as are files\n"
                            + "named com1 through com9 and lpt1 through lpt9.",
                    "Load");
        } else {
            if (locked) {
                final LockedLoadTask llt = new LockedLoadTask(filename);
                llt.start();
            } else {
                final XMLLoadTask xlt = new XMLLoadTask(filename, isSavedGame);
                xlt.start();
            }
        }
    }

    public void saveMaze() {
        if (this.getLoaded()) {
            this.setMazeXML1Compatible(false);
            this.setMazeXML2Compatible(false);
            this.setMazeXML4Compatible(false);
            if (Modes.inGame()) {
                if (this.lastUsedGameFile != null
                        && !this.lastUsedGameFile.equals("")) {
                    final String extension = MazeManager
                            .getExtension(this.lastUsedGameFile);
                    if (extension != null) {
                        if (!extension
                                .equals(XMLExtension.getXMLGameExtension())) {
                            this.lastUsedGameFile = MazeManager
                                    .getNameWithoutExtension(
                                            this.lastUsedGameFile)
                                    + XMLExtension
                                            .getXMLGameExtensionWithPeriod();
                        }
                    } else {
                        this.lastUsedGameFile += XMLExtension
                                .getXMLGameExtensionWithPeriod();
                    }
                    MazeManager.saveFile(this.lastUsedGameFile, true, false);
                } else {
                    this.saveMazeAs();
                }
            } else {
                if (this.lastUsedMazeFile != null
                        && !this.lastUsedMazeFile.equals("")) {
                    final String extension = MazeManager
                            .getExtension(this.lastUsedMazeFile);
                    if (extension != null) {
                        if (!extension
                                .equals(XMLExtension.getXMLMazeExtension())) {
                            this.lastUsedMazeFile = MazeManager
                                    .getNameWithoutExtension(
                                            this.lastUsedMazeFile)
                                    + XMLExtension
                                            .getXMLMazeExtensionWithPeriod();
                        }
                    } else {
                        this.lastUsedMazeFile += XMLExtension
                                .getXMLMazeExtensionWithPeriod();
                    }
                    MazeManager.saveFile(this.lastUsedMazeFile, false, false);
                } else {
                    this.saveMazeAs();
                }
            }
        } else {
            CommonDialogs.showDialog("No Maze Opened");
        }
    }

    public void saveMazeAs() {
        if (this.getLoaded()) {
            this.setMazeXML1Compatible(false);
            this.setMazeXML2Compatible(false);
            this.setMazeXML4Compatible(false);
            String filename = "";
            String fileOnly = "\\";
            String extension;
            final String lastSave = Prefs.getLastDirSave();
            File lastSaveDir = null;
            if (lastSave != null) {
                lastSaveDir = new File(lastSave);
            }
            final JFileChooser fc = new JFileChooser(lastSaveDir);
            final XMLMazeFilter xmf = new XMLMazeFilter();
            final XMLGameFilter xgf = new XMLGameFilter();
            fc.setAcceptAllFileFilterUsed(false);
            if (Modes.inGame()) {
                fc.addChoosableFileFilter(xgf);
                fc.setFileFilter(xgf);
            } else {
                fc.addChoosableFileFilter(xmf);
                fc.setFileFilter(xmf);
            }
            while (!FilenameChecker.isFilenameOK(fileOnly)) {
                final int returnVal = fc.showSaveDialog((Frame) null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    final File file = fc.getSelectedFile();
                    extension = MazeManager.getExtension(file);
                    filename = file.getAbsolutePath();
                    final String dirOnly = fc.getCurrentDirectory()
                            .getAbsolutePath();
                    fileOnly = filename.substring(dirOnly.length() + 1);
                    if (!FilenameChecker.isFilenameOK(fileOnly)) {
                        CommonDialogs.showErrorDialog(
                                "The file name you entered contains illegal characters.\n"
                                        + "These characters are not allowed: /?<>\\:|\"\n"
                                        + "Files named con, nul, or prn are illegal, as are files\n"
                                        + "named com1 through com9 and lpt1 through lpt9.",
                                "Save");
                    } else {
                        Prefs.setLastDirSave(
                                fc.getCurrentDirectory().getAbsolutePath());
                        if (Modes.inGame()) {
                            if (extension != null) {
                                if (!extension.equals(
                                        XMLExtension.getXMLGameExtension())) {
                                    filename = MazeManager
                                            .getNameWithoutExtension(file)
                                            + XMLExtension
                                                    .getXMLGameExtensionWithPeriod();
                                }
                            } else {
                                filename += XMLExtension
                                        .getXMLGameExtensionWithPeriod();
                            }
                            this.lastUsedGameFile = filename;
                            MazeManager.saveFile(filename, true, false);
                        } else {
                            if (extension != null) {
                                if (!extension.equals(
                                        XMLExtension.getXMLMazeExtension())) {
                                    filename = MazeManager
                                            .getNameWithoutExtension(file)
                                            + XMLExtension
                                                    .getXMLMazeExtensionWithPeriod();
                                }
                            } else {
                                filename += XMLExtension
                                        .getXMLMazeExtensionWithPeriod();
                            }
                            this.lastUsedMazeFile = filename;
                            this.scoresFileName = MazeManager
                                    .getNameWithoutExtension(file.getName());
                            MazeManager.saveFile(filename, false, false);
                        }
                    }
                } else {
                    break;
                }
            }
        } else {
            CommonDialogs.showDialog("No Maze Opened");
        }
    }

    public void saveLockedMaze() {
        if (this.getLoaded()) {
            this.setMazeXML1Compatible(false);
            this.setMazeXML2Compatible(false);
            this.setMazeXML4Compatible(false);
            String filename = "";
            String fileOnly = "\\";
            String extension;
            final String lastSave = Prefs.getLastDirSave();
            File lastSaveDir = null;
            if (lastSave != null) {
                lastSaveDir = new File(lastSave);
            }
            final JFileChooser fc = new JFileChooser(lastSaveDir);
            final LockedFilter lf = new LockedFilter();
            fc.setAcceptAllFileFilterUsed(false);
            fc.addChoosableFileFilter(lf);
            fc.setFileFilter(lf);
            while (!FilenameChecker.isFilenameOK(fileOnly)) {
                final int returnVal = fc.showSaveDialog((Frame) null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    final File file = fc.getSelectedFile();
                    extension = MazeManager.getExtension(file);
                    filename = file.getAbsolutePath();
                    final String dirOnly = fc.getCurrentDirectory()
                            .getAbsolutePath();
                    fileOnly = filename.substring(dirOnly.length() + 1);
                    if (!FilenameChecker.isFilenameOK(fileOnly)) {
                        CommonDialogs.showErrorDialog(
                                "The file name you entered contains illegal characters.\n"
                                        + "These characters are not allowed: /?<>\\:|\"\n"
                                        + "Files named con, nul, or prn are illegal, as are files\n"
                                        + "named com1 through com9 and lpt1 through lpt9.",
                                "Save");
                    } else {
                        Prefs.setLastDirSave(
                                fc.getCurrentDirectory().getAbsolutePath());
                        if (extension != null) {
                            if (!extension.equals(
                                    Extension.getLockedMazeExtension())) {
                                filename = MazeManager
                                        .getNameWithoutExtension(file)
                                        + Extension
                                                .getLockedMazeExtensionWithPeriod();
                            }
                        } else {
                            filename += Extension
                                    .getLockedMazeExtensionWithPeriod();
                        }
                        this.lastUsedMazeFile = filename;
                        this.scoresFileName = MazeManager
                                .getNameWithoutExtension(file.getName());
                        MazeManager.saveFile(filename, false, true);
                    }
                } else {
                    break;
                }
            }
        } else {
            CommonDialogs.showDialog("No Maze Opened");
        }
    }

    private static void saveFile(final String filename,
            final boolean isSavedGame, final boolean locked) {
        final String sg;
        if (isSavedGame) {
            sg = "Saved Game";
        } else {
            if (locked) {
                sg = "Locked Maze";
            } else {
                sg = "Maze";
            }
        }
        Mazer5D.getBagOStuff().showMessage("Saving " + sg + " file...");
        if (locked) {
            final LockedSaveTask lst = new LockedSaveTask(filename);
            lst.start();
        } else {
            final XMLSaveTask xst = new XMLSaveTask(filename, isSavedGame);
            xst.start();
        }
    }

    private static String getExtension(final File f) {
        String ext = null;
        final String s = f.getName();
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    private static String getExtension(final String s) {
        String ext = null;
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    private static String getNameWithoutExtension(final File f) {
        String ext = null;
        final String s = f.getAbsolutePath();
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(0, i);
        } else {
            ext = s;
        }
        return ext;
    }

    private static String getNameWithoutExtension(final String s) {
        String ext = null;
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(0, i);
        } else {
            ext = s;
        }
        return ext;
    }

    private static String getFileNameOnly(final String s) {
        String fno = null;
        final int i = s.lastIndexOf(File.separatorChar);
        if (i > 0 && i < s.length() - 1) {
            fno = s.substring(i + 1);
        } else {
            fno = s;
        }
        return fno;
    }
}
