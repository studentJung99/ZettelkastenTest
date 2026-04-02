package de.danielluedecke.zettelkasten.ui;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.LookAndFeel;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.InsetsUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;

/**
 * MacOS8LookAndFeel – a Swing {@link MetalLookAndFeel} subclass that applies
 * the "Platinum" visual language of Mac OS 8 (1997).
 *
 * <p>Key traits reproduced:
 * <ul>
 *   <li>Medium-gray (#CCCCCC) Platinum control background.</li>
 *   <li>Raised-bevel button and panel borders.</li>
 *   <li>Navy-blue (#000099) selection highlight.</li>
 *   <li>Pinstripe gradient for internal-frame / dialog title panes.</li>
 *   <li>Chicago-size (12 pt Dialog) system font.</li>
 * </ul>
 *
 * <p>Usage: call {@link #install()} once before any UI component is shown.
 */
public class MacOS8LookAndFeel extends MetalLookAndFeel {

    // ── Palette ──────────────────────────────────────────────────────────────
    /** Platinum background – the characteristic Mac OS 8 gray. */
    public static final Color PLATINUM        = new Color(0xCC, 0xCC, 0xCC);
    /** Lighter highlight edge on 3-D controls. */
    public static final Color HIGHLIGHT       = new Color(0xFF, 0xFF, 0xFF);
    /** Darker shadow edge on 3-D controls. */
    public static final Color SHADOW          = new Color(0x66, 0x66, 0x66);
    /** Mid shadow used for inner bevel edge. */
    public static final Color MID_SHADOW      = new Color(0x99, 0x99, 0x99);
    /** Navy-blue selection (system highlight colour in Mac OS 8). */
    public static final Color SELECTION_BG    = new Color(0x00, 0x00, 0x99);
    public static final Color SELECTION_FG    = Color.WHITE;
    /** Active title-pane gradient start (dark end, left). */
    static final Color TITLE_DARK             = new Color(0x88, 0x88, 0x88);
    /** Active title-pane gradient end (light end, right). */
    static final Color TITLE_LIGHT            = new Color(0xBB, 0xBB, 0xBB);

    // ── Theme ─────────────────────────────────────────────────────────────
    @Override
    protected void createDefaultTheme() {
        setCurrentTheme(new MacOS8Theme());
    }

    @Override
    public String getName()        { return "Mac OS 8 Platinum"; }
    @Override
    public String getID()          { return "MacOS8"; }
    @Override
    public String getDescription() { return "Reproduces the Platinum Look&Feel of Mac OS 8 (1997)."; }

    // ── UI Defaults ──────────────────────────────────────────────────────────
    @Override
    public UIDefaults getDefaults() {
        UIDefaults defaults = super.getDefaults();
        applyPlatinumOverrides(defaults);
        return defaults;
    }

    /**
     * Applies all Platinum-specific overrides on top of the Metal defaults.
     *
     * @param d the {@link UIDefaults} table to modify in place
     */
    private static void applyPlatinumOverrides(UIDefaults d) {
        // ── Selection ────────────────────────────────────────────────────────
        ColorUIResource selBg = new ColorUIResource(SELECTION_BG);
        ColorUIResource selFg = new ColorUIResource(SELECTION_FG);
        for (String key : new String[]{
                "List.selectionBackground",
                "Table.selectionBackground",
                "Tree.selectionBackground",
                "TextArea.selectionColor",
                "TextField.selectionColor",
                "PasswordField.selectionColor",
                "ComboBox.selectionBackground"}) {
            d.put(key, selBg);
        }
        for (String key : new String[]{
                "List.selectionForeground",
                "Table.selectionForeground",
                "Tree.selectionForeground",
                "TextArea.selectedTextColor",
                "TextField.selectedTextColor",
                "PasswordField.selectedTextColor",
                "ComboBox.selectionForeground"}) {
            d.put(key, selFg);
        }

        // ── Focus (navy ring) ────────────────────────────────────────────────
        d.put("Button.focus",     new ColorUIResource(SELECTION_BG));
        d.put("TextField.caretForeground", new ColorUIResource(Color.BLACK));

        // ── Backgrounds ──────────────────────────────────────────────────────
        ColorUIResource platinum = new ColorUIResource(PLATINUM);
        for (String key : new String[]{
                "Panel.background",
                "OptionPane.background",
                "ScrollPane.background",
                "Viewport.background",
                "ToolBar.background",
                "TabbedPane.background",
                "TabbedPane.tabAreaBackground",
                "MenuBar.background",
                "PopupMenu.background",
                "Menu.background",
                "MenuItem.background",
                "CheckBoxMenuItem.background",
                "RadioButtonMenuItem.background",
                "Separator.background",
                "SplitPane.background",
                "ToolTip.background",
                "ScrollBar.background",
                "ScrollBar.thumb",
                "ScrollBar.thumbShadow",
                "ComboBox.background",
                "Label.background",
                "CheckBox.background",
                "RadioButton.background",
                "Spinner.background",
                "FormattedTextField.background",
                "Slider.background",
                "ProgressBar.background",
                "TitledBorder.background"}) {
            d.put(key, platinum);
        }

        // ── Foregrounds ──────────────────────────────────────────────────────
        ColorUIResource black = new ColorUIResource(Color.BLACK);
        for (String key : new String[]{
                "Panel.foreground",
                "Label.foreground",
                "MenuItem.foreground",
                "Menu.foreground",
                "MenuBar.foreground",
                "CheckBoxMenuItem.foreground",
                "RadioButtonMenuItem.foreground",
                "ToolTip.foreground",
                "TabbedPane.foreground"}) {
            d.put(key, black);
        }

        // ── Borders (raised-bevel) ───────────────────────────────────────────
        Border raisedBevel = BorderFactory.createBevelBorder(
                BevelBorder.RAISED,
                HIGHLIGHT, PLATINUM, SHADOW, MID_SHADOW);
        Border loweredBevel = BorderFactory.createBevelBorder(
                BevelBorder.LOWERED,
                MID_SHADOW, SHADOW, HIGHLIGHT, PLATINUM);
        Border buttonBorder = new CompoundBorder(
                raisedBevel,
                BorderFactory.createEmptyBorder(2, 6, 2, 6));
        Border textBorder = new CompoundBorder(
                loweredBevel,
                BorderFactory.createEmptyBorder(1, 2, 1, 2));

        d.put("Button.border",            new BorderUIResource(buttonBorder));
        d.put("ToggleButton.border",      new BorderUIResource(buttonBorder));
        d.put("TextField.border",         new BorderUIResource(textBorder));
        d.put("PasswordField.border",     new BorderUIResource(textBorder));
        d.put("ComboBox.border",          new BorderUIResource(textBorder));
        d.put("Panel.border",             new BorderUIResource(BorderFactory.createEmptyBorder()));
        d.put("ScrollPane.border",        new BorderUIResource(loweredBevel));
        d.put("Table.scrollPaneBorder",   new BorderUIResource(loweredBevel));
        d.put("Tree.editorBorder",        new BorderUIResource(raisedBevel));
        d.put("ToolBar.border",           new BorderUIResource(raisedBevel));

        // ── Insets (keep existing Metal insets, just ensure button feels right) ─
        d.put("Button.margin",        new InsetsUIResource(3, 8, 3, 8));
        d.put("ToggleButton.margin",  new InsetsUIResource(3, 8, 3, 8));

        // ── ScrollBar ────────────────────────────────────────────────────────
        d.put("ScrollBar.width",        Integer.valueOf(16));
        d.put("ScrollBar.thumbMinimumSize", new java.awt.Dimension(16, 16));

        // ── Menu highlight ───────────────────────────────────────────────────
        d.put("Menu.selectionBackground",          selBg);
        d.put("Menu.selectionForeground",          selFg);
        d.put("MenuItem.selectionBackground",      selBg);
        d.put("MenuItem.selectionForeground",      selFg);
        d.put("CheckBoxMenuItem.selectionBackground", selBg);
        d.put("CheckBoxMenuItem.selectionForeground", selFg);
        d.put("RadioButtonMenuItem.selectionBackground", selBg);
        d.put("RadioButtonMenuItem.selectionForeground", selFg);
        d.put("PopupMenu.border", new BorderUIResource(raisedBevel));
        d.put("Menu.border",      new BorderUIResource(
                BorderFactory.createEmptyBorder(2, 2, 2, 2)));
        d.put("MenuItem.border",  new BorderUIResource(
                BorderFactory.createEmptyBorder(2, 2, 2, 2)));

        // ── TabbedPane ───────────────────────────────────────────────────────
        d.put("TabbedPane.selected",         platinum);
        d.put("TabbedPane.shadow",           new ColorUIResource(SHADOW));
        d.put("TabbedPane.highlight",        new ColorUIResource(HIGHLIGHT));
        d.put("TabbedPane.darkShadow",       new ColorUIResource(SHADOW));
        d.put("TabbedPane.focus",            new ColorUIResource(SELECTION_BG));

        // ── InternalFrame / title pane (pinstripe painter) ───────────────────
        d.put("InternalFrameUI",
              "de.danielluedecke.zettelkasten.ui.MacOS8LookAndFeel$PinstripeInternalFrameUI");
        d.put("InternalFrame.titleFont",
              new javax.swing.plaf.FontUIResource("Dialog", java.awt.Font.BOLD, 12));
        d.put("InternalFrame.activeTitleBackground",  new ColorUIResource(TITLE_DARK));
        d.put("InternalFrame.activeTitleForeground",  new ColorUIResource(Color.WHITE));
        d.put("InternalFrame.inactiveTitleBackground", platinum);
        d.put("InternalFrame.inactiveTitleForeground", new ColorUIResource(MID_SHADOW));

        // ── ProgressBar ──────────────────────────────────────────────────────
        d.put("ProgressBar.selectionBackground", new ColorUIResource(Color.BLACK));
        d.put("ProgressBar.selectionForeground", new ColorUIResource(Color.WHITE));
        d.put("ProgressBar.foreground",          new ColorUIResource(SELECTION_BG));
        d.put("ProgressBar.border",              new BorderUIResource(raisedBevel));

        // ── ToolTip ──────────────────────────────────────────────────────────
        d.put("ToolTip.border", new BorderUIResource(raisedBevel));
    }

    // ── Convenience installer ─────────────────────────────────────────────────

    /**
     * Installs {@code MacOS8LookAndFeel} as the current Swing L&F and
     * propagates the change to any already-visible components.
     *
     * @throws UnsupportedOperationException if the L&F cannot be set
     */
    public static void install() {
        try {
            UIManager.setLookAndFeel(new MacOS8LookAndFeel());
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            throw new UnsupportedOperationException(
                    "Cannot install MacOS8LookAndFeel", ex);
        }
    }

    // ── Pinstripe InternalFrame UI ────────────────────────────────────────────

    /**
     * A {@code MetalInternalFrameUI} subclass that replaces the flat active-
     * title-bar with a Mac OS 8–style pinstripe gradient (fine horizontal
     * stripes alternating between two grays).
     */
    public static class PinstripeInternalFrameUI
            extends javax.swing.plaf.metal.MetalInternalFrameUI {

        public PinstripeInternalFrameUI(javax.swing.JInternalFrame f) {
            super(f);
        }

        /** Factory method required by Swing's UI class loader. */
        public static javax.swing.plaf.ComponentUI createUI(javax.swing.JComponent c) {
            return new PinstripeInternalFrameUI((javax.swing.JInternalFrame) c);
        }

        @Override
        protected javax.swing.plaf.basic.BasicInternalFrameTitlePane
                createNorthPane(javax.swing.JInternalFrame w) {
            return new PinstripeTitlePane(w);
        }
    }

    /**
     * Title pane that draws a horizontal pinstripe gradient matching the
     * Mac OS 8 active-window appearance.
     *
     * <p>Active:   horizontal stripes alternating between {@link #TITLE_DARK}
     *              and {@link #TITLE_LIGHT}, with the label centred in white.
     * <p>Inactive: flat {@link #PLATINUM} fill, dark-gray label.
     */
    public static class PinstripeTitlePane
            extends javax.swing.plaf.metal.MetalInternalFrameTitlePane {

        private static final int STRIPE_HEIGHT = 1;

        public PinstripeTitlePane(javax.swing.JInternalFrame f) {
            super(f);
        }

        @Override
        public void paintComponent(Graphics g) {
            // Let Metal set up buttons / layout first.
            super.paintComponent(g);

            // Now re-paint the background with our custom style.
            Rectangle r = getBounds();
            Graphics2D g2 = (Graphics2D) g.create(0, 0, r.width, r.height);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_OFF);

            boolean active = frame.isSelected();
            if (active) {
                paintPinstripes(g2, r.width, r.height);
            } else {
                g2.setColor(PLATINUM);
                g2.fillRect(0, 0, r.width, r.height);
            }

            // Draw title text.
            g2.setFont(getFont());
            String title = frame.getTitle();
            if (title != null && !title.isEmpty()) {
                java.awt.FontMetrics fm = g2.getFontMetrics();
                int textX = (r.width  - fm.stringWidth(title)) / 2;
                int textY = (r.height + fm.getAscent() - fm.getDescent()) / 2;
                g2.setColor(active ? Color.WHITE : SHADOW);
                g2.drawString(title, textX, textY);
            }
            g2.dispose();
        }

        /** Paints alternating 1-pixel horizontal stripes (the pinstripe effect). */
        private static void paintPinstripes(Graphics2D g2, int w, int h) {
            // Use a subtle horizontal gradient behind the stripes.
            GradientPaint grad = new GradientPaint(
                    0, 0,   TITLE_DARK,
                    w, 0,   TITLE_LIGHT);
            g2.setPaint(grad);
            g2.fillRect(0, 0, w, h);

            // Overlay fine alternating transparent / white stripes.
            for (int y = 0; y < h; y += STRIPE_HEIGHT * 2) {
                g2.setColor(new Color(0xFF, 0xFF, 0xFF, 40));
                g2.fillRect(0, y, w, STRIPE_HEIGHT);
            }
        }
    }
}
