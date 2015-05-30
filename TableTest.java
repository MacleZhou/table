package table;

import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author mark
 *
 * Test harness for the table.
 *
 */
public class TableTest {

    private final int rows = 103;
    private final int cols = 102;

    /**
     * Test of newRow method, of class Table.
     */
    @Test
    public void testNewRowCol_0args1() {

        Table t = new Table();

        assertEquals(0, t.getWidth());
        assertEquals(0, t.getHeight());

        t.newRow();

        assertEquals(0, t.getWidth());
        assertEquals(1, t.getHeight());

        t.newCol();

        assertEquals(1, t.getWidth());
        assertEquals(1, t.getHeight());

        t.newCol();

        assertEquals(2, t.getWidth());
        assertEquals(1, t.getHeight());

        t.newRow();

        assertEquals(2, t.getWidth());
        assertEquals(2, t.getHeight());

        t.newRow();

        assertEquals(2, t.getWidth());
        assertEquals(3, t.getHeight());

        t.newCol();

        assertEquals(3, t.getWidth());
        assertEquals(3, t.getHeight());

    }

    /**
     * Test of newRow method, of class Table.
     */
    @Test
    public void testNewRowCol_0args2() {

        Table t = new Table();

        assertEquals(0, t.getWidth());
        assertEquals(0, t.getHeight());

        t.newCol();

        assertEquals(1, t.getWidth());
        assertEquals(0, t.getHeight());

        t.newRow();

        assertEquals(1, t.getWidth());
        assertEquals(1, t.getHeight());

        t.newRow();

        assertEquals(1, t.getWidth());
        assertEquals(2, t.getHeight());

        t.newCol();

        assertEquals(2, t.getWidth());
        assertEquals(2, t.getHeight());

        t.newCol();

        assertEquals(3, t.getWidth());
        assertEquals(2, t.getHeight());

        t.newRow();

        assertEquals(3, t.getWidth());
        assertEquals(3, t.getHeight());

    }

    /**
     * Test of newRow method, of class Table.
     */
    @Test
    public void testNewRowCol_int() {
        Table t1 = new Table();

        for (int i = 1; i <= 10; i++) {
            t1.newCol(1);
            assertEquals(i, t1.getWidth());
            assertEquals(0, t1.getHeight());
        }

        Table t2 = new Table();

        for (int i = 1; i <= 10; i++) {
            t2.newRow(1);
            assertEquals(0, t2.getWidth());
            assertEquals(i, t2.getHeight());
        }

        Table t3 = new Table();

        for (int i = 1; i <= 10; i++) {
            t3.newCol(1);
            t3.newRow(1);
            assertEquals(i, t3.getWidth());
            assertEquals(i, t3.getHeight());
        }

        Table t4 = new Table();

        for (int i = 1; i <= 10; i++) {
            t4.newRow(1);
            t4.newCol(1);
            assertEquals(i, t4.getWidth());
            assertEquals(i, t4.getHeight());
        }

    }

    /**
     * Test of moveRow method, of class Table.
     */
    @Test
    public void testMoveRow() {

        Table t = bigTable();
        Table t2 = bigTable();

        t.moveRow(1, rows);
        assertFalse(t.equals(t2));

        t.moveRow(rows, 1);
        assertEquals(t, t2);

        t.moveRow(rows, 1);
        assertFalse(t.equals(t2));

        t.moveRow(1, rows);
        assertEquals(t, t2);

        Random r = new Random();
        int rand1, rand2;

        for (int i = 0; i < cols; i++) {
            rand1 = r.nextInt(rows) + 1;
            rand2 = r.nextInt(rows) + 1;

            while (rand1 == rand2) {
                rand1 = r.nextInt(rows) + 1;
                rand2 = r.nextInt(rows) + 1;
            }

            t.moveRow(rand1, rand2);
            assertFalse(t.equals(t2));

            t.moveRow(rand2, rand1);
            assertEquals(t, t2);

        }

        assertEquals(t, t2);
        assertEquals(t2, bigTable());

    }

    /**
     * Test of moveCol/Row methods, of class Table.
     */
    @Test
    public void testMoveRowCol() {

        for (int j = 0; j < 100; j++) {
            Table t = bigTable();
            Table t2 = bigTable();

            Random r = new Random();
            int rand1, rand2, rand3, rand4;

            for (int i = 0; i < cols; i++) {
                rand3 = r.nextInt(rows) + 1;
                rand4 = r.nextInt(rows) + 1;
                rand1 = r.nextInt(cols) + 1;
                rand2 = r.nextInt(cols) + 1;

                while (rand1 == rand2) {
                    rand1 = r.nextInt(cols) + 1;
                    rand2 = r.nextInt(cols) + 1;
                    rand3 = r.nextInt(rows) + 1;
                    rand4 = r.nextInt(rows) + 1;
                }

                assertEquals(rows, t.getHeight());
                assertEquals(cols, t.getWidth());

                t.moveCol(rand1, rand2);

                assertEquals(rows, t.getHeight());
                assertEquals(cols, t.getWidth());

                t.moveRow(rand3, rand4);

                assertEquals(rows, t.getHeight());
                assertEquals(cols, t.getWidth());

                t.moveCol(rand2, rand1);

                assertEquals(rows, t.getHeight());
                assertEquals(cols, t.getWidth());

                t.moveRow(rand4, rand3);

                assertEquals(rows, t.getHeight());
                assertEquals(cols, t.getWidth());

                t.moveRow(rand3, rand4);

                assertEquals(rows, t.getHeight());
                assertEquals(cols, t.getWidth());

                t.moveCol(rand1, rand2);

                assertEquals(rows, t.getHeight());
                assertEquals(cols, t.getWidth());

                t.moveRow(rand4, rand3);

                assertEquals(rows, t.getHeight());
                assertEquals(cols, t.getWidth());

                t.moveCol(rand2, rand1);

                assertEquals(rows, t.getHeight());
                assertEquals(cols, t.getWidth());

                if (!t.equals(t2)) {
                    System.out.println(t);
                    System.out.println(t2);

                    fail();
                }

            }

            assertEquals(t, t2);
            assertEquals(t2, bigTable());
        }
    }

    /**
     * Test of moveCol method, of class Table.
     */
    @Test
    public void testMoveCol() {

        Table t = bigTable();
        Table t2 = bigTable();

        t.moveCol(1, cols);
        assertFalse(t.equals(t2));

        t.moveCol(cols, 1);
        assertEquals(t, t2);

        t.moveCol(cols, 1);
        assertFalse(t.equals(t2));

        t.moveCol(1, cols);
        assertEquals(t, t2);

        Random r = new Random();
        int rand1, rand2;

        for (int i = 0; i < cols; i++) {
            rand1 = r.nextInt(cols) + 1;
            rand2 = r.nextInt(cols) + 1;

            while (rand1 == rand2) {
                rand1 = r.nextInt(cols) + 1;
                rand2 = r.nextInt(cols) + 1;
            }

            t.moveCol(rand1, rand2);
            assertFalse(t.equals(t2));

            t.moveCol(rand2, rand1);
            assertEquals(t, t2);

        }

        assertEquals(t, t2);
        assertEquals(t2, bigTable());

    }

    /**
     * Test of deleteRow method, of class Table.
     */
    @Test
    public void testDeleteRow_0args() {

        Table t = bigTable();

        for (int i = rows; i > 0; i--) {

            t.deleteRow();
            assertEquals(i - 1, t.getHeight());

            for (int j = 0; j <= i - 1; j++) {

                for (int k = 0; k <= cols; k++) {

                    if (j == k && k == 0) {
                        continue;
                    }

                    Comparable c;

                    if (j == 0) {
                        c = t.getCol(k);
                    } else if (k == 0) {
                        c = t.getRow(j);
                    } else {
                        c = t.getCell(j, k);
                    }

                    assertEquals(j * (cols + 1) + k + "", c);

                }

            }

        }

    }

    /**
     * Test of deleteRow method, of class Table.
     */
    @Test
    public void testDeleteRow_int() {

        Table t;
        Random r = new Random();

        for (int i = 0; i < 1000; i++) {

            t = bigTable();

            int rand = r.nextInt(rows) + 1;

            t.deleteRow(rand);

            assertEquals(rows - 1, t.getHeight());

            int j = 0;

            for (; j < rand; j++) {

                for (int k = 0; k <= cols; k++) {

                    if (j == k && k == 0) {
                        continue;
                    }

                    Comparable c;

                    if (j == 0) {
                        c = t.getCol(k);
                    } else if (k == 0) {
                        c = t.getRow(j);
                    } else {
                        c = t.getCell(j, k);
                    }

                    assertEquals(j * (cols + 1) + k + "", c);

                }

            }

            for (; j <= rows - 1; j++) {

                for (int k = 0; k <= cols; k++) {

                    Comparable c;

                    if (k == 0) {
                        c = t.getRow(j);
                    } else {
                        c = t.getCell(j, k);
                    }

                    assertEquals((j + 1) * (cols + 1) + k + "", c);

                }

            }

        }

    }

    /**
     * Test of deleteCol method, of class Table.
     */
    @Test
    public void testDeleteCol_0args() {

        Table t = bigTable();

        for (int i = cols; i > 0; i--) {

            t.deleteCol();
            assertEquals(i - 1, t.getWidth());

            for (int j = 0; j <= i - 1; j++) {

                for (int k = 0; k <= rows; k++) {

                    if (j == k && k == 0) {
                        continue;
                    }

                    Comparable c;

                    if (j == 0) {
                        c = t.getRow(k);
                    } else if (k == 0) {
                        c = t.getCol(j);
                    } else {
                        c = t.getCell(k, j);
                    }

                    assertEquals((k * (cols + 1) + j) + "", c);

                }

            }

        }

    }

    /**
     * Test of deleteCol method, of class Table.
     */
    @Test
    public void testDeleteCol_int() {

        Table t;
        Random r = new Random();

        for (int i = cols; i > 0; i--) {

            t = bigTable();
            int rand = r.nextInt(cols) + 1;

            t.deleteCol(rand);

            assertEquals(cols - 1, t.getWidth());

            int j = 0;

            for (; j < rand; j++) {

                for (int k = 0; k <= rows; k++) {

                    if (j == k && k == 0) {
                        continue;
                    }

                    Comparable c;

                    if (j == 0) {
                        c = t.getRow(k);
                    } else if (k == 0) {
                        c = t.getCol(j);
                    } else {
                        c = t.getCell(k, j);
                    }

                    assertEquals((k * (cols + 1) + j) + "", c);

                }

            }

            for (; j <= cols - 1; j++) {

                for (int k = 0; k <= rows; k++) {

                    Comparable c;

                    if (k == 0) {
                        c = t.getCol(j);
                    } else {
                        c = t.getCell(k, j);
                    }

                    assertEquals((k * (cols + 1) + j + 1) + "", c);

                }

            }

        }

    }

    /**
     * Test of getRow method, of class Table.
     */
    @Test
    public void testGetRow() {

        Table t = bigTable();

        for (int i = 1; i <= rows; i++) {
            assertEquals(i * rows + "", t.getRow(i));
        }

    }

    /**
     * Test of getCol method, of class Table.
     */
    @Test
    public void testGetCol() {

        Table t = bigTable();

        for (int i = 1; i <= cols; i++) {
            assertEquals(i + "", t.getCol(i));
        }

    }

    /**
     * Test of getCell method, of class Table.
     */
    @Test
    public void testGetCell() {

        Table t = bigTable();

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                assertEquals(i * (cols + 1) + j + "", t.getCell(i, j));
            }
        }

    }

    /**
     * Test of setRow method, of class Table.
     */
    @Test
    public void testSetRow() {

        Table t = new Table(rows, 0);

        for (int i = 1; i <= rows; i++) {
            t.setRow(i, i * rows + "");
            assertEquals(i * rows + "", t.getRow(i));
        }

    }

    /**
     * Test of setCol method, of class Table.
     */
    @Test
    public void testSetCol() {

        Table t = new Table(0, cols);

        for (int i = 1; i <= cols; i++) {
            t.setCol(i, i + "");
            assertEquals(i + "", t.getCol(i));
        }
    }

    /**
     * Test of setCell method, of class Table.
     */
    @Test
    public void testSetCell() {

        Table t = new Table(rows, cols);

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                t.setCell(i, j, i * (cols + 1) + j + "");
                assertEquals(i * (cols + 1) + j + "", t.getCell(i, j));
            }
        }

    }

    /**
     * Test of hasRight method, of class Table.
     */
    @Test
    public void testHasRightDown() {

        Table t = new Table(rows, cols);

        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= cols; j++) {

                if (i == j && j == 0) {
                    continue;
                }

                t.setCursor(i, j);

                if (j == cols) {
                    assertFalse(t.hasRight());
                } else {
                    assertTrue(t.hasRight());
                }

                if (i == rows) {
                    assertFalse(t.hasDown());
                } else {
                    assertTrue(t.hasDown());
                }

            }
        }

    }

    /**
     * Test of equals method, of class Table.
     */
    @Test
    public void testEquals() {

        Table t = bigTable();
        Table t2 = bigTable();

        assertEquals(t, t);
        assertEquals(t, t2);
        assertFalse((new Table(1, cols)).equals(t));
        assertFalse((new Table(rows, 10)).equals(t));
        assertFalse((new Table(rows, cols)).equals(t));
        assertFalse((new Table(1, cols)).equals(t));

        t.moveCol(1, 10);

        assertFalse(t.equals(bigTable()));
        assertFalse(t.equals(t2));

        t2.moveCol(1, 10);

        assertEquals(t, t2);

    }

    private Table bigTable() {

        Table t = new Table(rows, cols);

        assertEquals(rows, t.getHeight());
        assertEquals(cols, t.getWidth());

        t.setCursor(1, 0);

        for (int i = 1; i <= rows; i++) {
            t.set((i * (cols + 1)) + "");
            t.down();
        }

        for (int i = 0; i <= rows; i++) {

            t.setCursor(i, 1);

            for (int j = 1; j <= cols; j++) {

                t.set((i * (cols + 1) + j) + "");

                t.right();

            }
        }

        return t;

    }

}
