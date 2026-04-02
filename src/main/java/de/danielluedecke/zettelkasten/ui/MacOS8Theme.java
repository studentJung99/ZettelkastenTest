package de.danielluedecke.zettelkasten.ui;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;

/**
 * MacOS8Theme – a Swing MetalTheme that reproduces the "Platinum" color
 * palette of Mac OS 8 (1997).
 *
 * Palette reference
 * ─────────────────
 *  Platinum background  : #CCCCCC  (secondary3)
 *  Dark shadow          : #666666  (secondary1)
 *  Mid shadow           : #999999  (secondary2)
 *  Title / primary accent: #333333 (primary1)
 *  Active title gradient : #888888 (primary2)
 *  Highlight tint       : #AAAAAA  (primary3)
 *  Selection            : #000099  (via UIDefaults override in MacOS8LookAndFeel)
 */
public class MacOS8Theme extends DefaultMetalTheme {

    // ── Platinum grays ──────────────────────────────────────────────────────
    private static final ColorUIResource PRIMARY1   = new ColorUIResource(0x33, 0x33, 0x33);
    private static final ColorUIResource PRIMARY2   = new ColorUIResource(0x88, 0x88, 0x88);
    private static final ColorUIResource PRIMARY3   = new ColorUIResource(0xAA, 0xAA, 0xAA);

    private static final ColorUIResource SECONDARY1 = new ColorUIResource(0x66, 0x66, 0x66);
    private static final ColorUIResource SECONDARY2 = new ColorUIResource(0x99, 0x99, 0x99);
    private static final ColorUIResource SECONDARY3 = new ColorUIResource(0xCC, 0xCC, 0xCC);

    // ── Fonts (Dialog / Lucida Grande proxy) ────────────────────────────────
    private static final FontUIResource CONTROL_FONT =
            new FontUIResource("Dialog", java.awt.Font.PLAIN, 12);
    private static final FontUIResource MENU_FONT =
            new FontUIResource("Dialog", java.awt.Font.PLAIN, 12);
    private static final FontUIResource WINDOW_TITLE_FONT =
            new FontUIResource("Dialog", java.awt.Font.BOLD, 12);

    @Override public String getName() { return "Mac OS 8 Platinum"; }

    // ── Primary palette ─────────────────────────────────────────────────────
    @Override protected ColorUIResource getPrimary1()   { return PRIMARY1; }
    @Override protected ColorUIResource getPrimary2()   { return PRIMARY2; }
    @Override protected ColorUIResource getPrimary3()   { return PRIMARY3; }

    // ── Secondary palette ───────────────────────────────────────────────────
    @Override protected ColorUIResource getSecondary1() { return SECONDARY1; }
    @Override protected ColorUIResource getSecondary2() { return SECONDARY2; }
    @Override protected ColorUIResource getSecondary3() { return SECONDARY3; }

    // ── Fonts ────────────────────────────────────────────────────────────────
    @Override public FontUIResource getControlTextFont()    { return CONTROL_FONT; }
    @Override public FontUIResource getSystemTextFont()     { return CONTROL_FONT; }
    @Override public FontUIResource getUserTextFont()       { return CONTROL_FONT; }
    @Override public FontUIResource getMenuTextFont()       { return MENU_FONT; }
    @Override public FontUIResource getWindowTitleFont()    { return WINDOW_TITLE_FONT; }
    @Override public FontUIResource getSubTextFont()        { return CONTROL_FONT; }
}
