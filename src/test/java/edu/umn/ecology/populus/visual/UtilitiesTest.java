package edu.umn.ecology.populus.visual;

import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.*;

class UtilitiesTest {

    @Test
    void getColor() {
        assertEquals(new Color(0x123456), Utilities.getColor(" #123456"));
        assertEquals(new Color(987654), Utilities.getColor("987654"));
        assertEquals(Color.RED, Utilities.getColor("RED"));
        assertEquals(Color.gray, Utilities.getColor("gRaY"));
    }

    @Test
    void getColorString() {
        assertEquals("#ff0000", Utilities.getColorString(Color.RED));
        assertEquals("#00ff00", Utilities.getColorString(Color.GREEN));
    }

    @Test
    void popHTMLToSwingHTML() {
        String x = "test";
        String xHTML = Utilities.PopHTMLToSwingHTML(x);
        assertEquals("<html>test</html>", xHTML);
    }
}