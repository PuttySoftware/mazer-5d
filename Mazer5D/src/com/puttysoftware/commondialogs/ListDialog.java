/*  Diane Game Engine
Copyleft (C) 2019 Eric Ahnell

Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.commondialogs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import com.puttysoftware.images.BufferedImageIcon;

class ListDialog {
    private static MainWindow dialogFrame;
    private static MainWindowContent dialogPane;
    private static String value = null;
    private static JList<String> list;

    /**
     * Set up and show the dialog. The first Component argument determines which
     * frame the dialog depends on; it should be a component in the dialog's
     * controlling frame. The second Component argument should be null if you
     * want the dialog to come up with its left corner in the center of the
     * screen; otherwise, it should be the component on top of which the dialog
     * should appear.
     */
    public static String showDialog(final String labelText, final String title,
            final BufferedImageIcon icon, final String[] possibleValues,
            final String initialValue) {
        ListDialog.value = null;
        // Create and initialize the dialog.
        dialogFrame = MainWindow.getMainWindow();
        dialogPane = dialogFrame.createContent();
        // Create and initialize the buttons.
        final JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(h -> {
            ListDialog.setValue(null);
            dialogFrame.restoreSaved();
        });
        //
        final JButton setButton = new JButton("OK");
        setButton.setActionCommand("OK");
        setButton.addActionListener(h -> {
            ListDialog.setValue(ListDialog.list.getSelectedValue());
            dialogFrame.restoreSaved();
        });
        // main part of the dialog
        ListDialog.list = new SubJList<>(possibleValues);
        ListDialog.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListDialog.list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        ListDialog.list.setVisibleRowCount(-1);
        ListDialog.list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (e.getClickCount() == 2) {
                    setButton.doClick(); // emulate button click
                }
            }
        });
        final JScrollPane listScroller = new JScrollPane(ListDialog.list);
        listScroller.setPreferredSize(new Dimension(250, 80));
        listScroller.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Create a container so that we can add a title around
        // the scroll pane. Can't add a title directly to the
        // scroll pane because its background would be white.
        // Lay out the label and scroll pane from top to bottom.
        final JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
        final JLabel label = new JLabel(labelText);
        label.setIcon(icon);
        label.setLabelFor(ListDialog.list);
        listPane.add(label);
        listPane.add(Box.createRigidArea(new Dimension(0, 5)));
        listPane.add(listScroller);
        listPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Lay out the buttons from left to right.
        final JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(cancelButton);
        buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPane.add(setButton);
        // Put everything together, using the content pane's BorderLayout.
        dialogPane.add(listPane, BorderLayout.NORTH);
        dialogPane.add(buttonPane, BorderLayout.PAGE_END);
        // Initialize values.
        ListDialog.setValue(initialValue);
        dialogFrame.attachAndSave(dialogPane);
        return ListDialog.value;
    }

    private static void setValue(final String newValue) {
        ListDialog.value = newValue;
        ListDialog.list.setSelectedValue(ListDialog.value, true);
    }

    private static class SubJList<T> extends JList<T> {
        private static final long serialVersionUID = 1L;

        // Subclass JList to workaround bug 4832765, which can cause the
        // scroll pane to not let the user easily scroll up to the beginning
        // of the list. An alternative would be to set the unitIncrement
        // of the JScrollBar to a fixed value. You wouldn't get the nice
        // aligned scrolling, but it should work.
        SubJList(final T[] data) {
            super(data);
        }

        @Override
        public int getScrollableUnitIncrement(final Rectangle visibleRect,
                final int orientation, final int direction) {
            int row;
            if (orientation == SwingConstants.VERTICAL && direction < 0
                    && (row = this.getFirstVisibleIndex()) != -1) {
                final Rectangle r = this.getCellBounds(row, row);
                if (r.y == visibleRect.y && row != 0) {
                    final Point loc = r.getLocation();
                    loc.y--;
                    final int prevIndex = this.locationToIndex(loc);
                    final Rectangle prevR = this.getCellBounds(prevIndex,
                            prevIndex);
                    if (prevR == null || prevR.y >= r.y) {
                        return 0;
                    }
                    return prevR.height;
                }
            }
            return super.getScrollableUnitIncrement(visibleRect, orientation,
                    direction);
        }
    }
}
