package table;

/**
 *
 * @author Mark
 *
 * A table represented by a graph internally.
 *
 */
public class Table {

    /**
     * A cell in the table
     */
    private class Cell {

        /**
         * Create a cell with no content
         */
        Cell() {
            this(null);
        }

        /**
         * Create a cell and make content its content
         *
         * @param content the new content
         */
        Cell(Comparable content) {
            this.content = content;
            this.right = null;
            this.down = null;
        }

        Comparable content;//content of this cell; must be comparable
        Cell right;//pointer to cell to the right
        Cell down;//pointer to cell below

        /**
         * Set the pointer to the cell below
         *
         * @param down the cell below
         */
        void setDown(Cell down) {
            this.down = down;
        }

        /**
         * Set the pointer to the cell right
         *
         * @param right the cell to the right
         */
        void setRight(Cell right) {
            this.right = right;
        }

        /**
         * Set the cell's content
         *
         * @param content the content
         */
        void setContent(Comparable content) {
            this.content = content;
        }

        /**
         * @return the cell below
         */
        Cell getDown() {
            return down;
        }

        /**
         * @return the cell to the right
         */
        Cell getRight() {
            return right;
        }

        /**
         * @return the cell's content
         */
        Comparable getContent() {
            return content;
        }

    }

    /**
     * Creates a new table
     */
    public Table() {
        this.first = new Cell();
        this.cursor = first;
    }

    /**
     * Creates a new table with r rows and c columns
     */
    public Table(int r, int c) {
        this();

        for (int i = 0; i < r; i++) {
            newRow();
        }

        for (int j = 0; j < c; j++) {
            newCol();
        }

    }

    //number of rows and cols, respectively (not including header)
    private int rows = 0;
    private int cols = 0;

    private Cell cursor;//the cursor on the table
    private Cell first;//the top left corner fo teh table; in the row-col spot
    /*
    
     XX is first
    
     XX|b ...
     -----
     a |c    
     .    .
     .      .
     .        .
    
     */

    /**
     * @return the number of columns
     */
    public int getWidth() {
        return cols;
    }

    /**
     * @return the number of rows
     */
    public int getHeight() {
        return rows;
    }

    /**
     * Creates a new row.
     *
     * The new row becomes the last row in the table
     */
    public void newRow() {
        newRow(rows + 1);
    }

    /**
     * Creates a new row.
     *
     * The new row becomes the pos-th row in the table. Indexing of the table
     * starts with 0 as the row/column headers and 1 being the first cell.
     *
     * @param pos the position of the new row
     */
    public void newRow(int pos) {

        if (pos > rows + 1) {
            throw new IndexOutOfBoundsException("This row number is too large. Max row number allowed: " + (rows + 1));
        }

        if (pos < 1) {
            throw new IndexOutOfBoundsException("This row number is too small. Min row number allowed: 1");
        }

        Cell row1;

        if (pos == 1) {
            row1 = first;
        } else {
            row1 = getRowHead(pos - 1);
        }

        Cell row2 = row1.getDown();
        Cell row = new Cell();

        for (int c = 0; c <= cols; c++) {

            row1.setDown(row);
            row.setDown(row2);

            if (c < cols) {
                Cell newCell = new Cell();
                row.setRight(newCell);
                row = newCell;
            }

            row1 = row1.getRight();

            if (row2 != null) {
                row2 = row2.getRight();
            }

        }

        rows++;

    }

    /**
     * Creates a new column
     *
     * The new column becomes the last column in the table
     */
    public void newCol() {
        newCol(cols + 1);
    }

    /**
     * Creates a new column
     *
     * The new column becomes the pos-th column in the table
     *
     * @param pos the position of the new column
     */
    public void newCol(int pos) {

        if (pos > cols + 1) {
            throw new IndexOutOfBoundsException("This column number is too large. Max column number allowed: " + (cols + 1));
        }

        if (pos < 1) {
            throw new IndexOutOfBoundsException("This column number is too small. Min column number allowed: 1");
        }

        Cell col1;
        if (pos == 1) {
            col1 = first;
        } else {
            col1 = getColHead(pos - 1);
        }

        Cell col2 = col1.getRight();
        Cell col = new Cell();

        for (int r = 0; r <= rows; r++) {

            col1.setRight(col);
            col.setRight(col2);

            if (r < rows) {
                Cell newCell = new Cell();
                col.setDown(newCell);
                col = newCell;
            }

            col1 = col1.getDown();

            if (col2 != null) {
                col2 = col2.getDown();
            }

        }

        cols++;

    }

    /**
     * Moves the row at position pos to position to. The row at position to goes
     * to position (to + 1) if pos > to, or (to - 1) if pos < to
     *
     * @param pos the row number of the row to move
     * @param to the new position of the row to move
     */
    public void moveRow(int pos, int to) {

        if (pos > rows || to > rows) {
            throw new IndexOutOfBoundsException("This row number is too large. Max row number allowed: " + rows);
        }

        if (pos < 1 || to < 1) {
            throw new IndexOutOfBoundsException("This row number is too small. Min row number allowed: 1");
        }

        if (to == pos) {
            return;
        }

        Cell row = getRowHead(pos);

        deleteRow(pos);

        Cell row1;
        if (to == 1) {
            row1 = first;
        } else {
            row1 = getRowHead(to - 1);
        }

        Cell row2 = row1.getDown();

        for (int c = 0; c <= cols; c++) {

            row1.setDown(row);
            row.setDown(row2);

            row1 = row1.getRight();
            row = row.getRight();

            if (row2 != null) {
                row2 = row2.getRight();
            }

        }

        rows++;//compensate for delete

    }

    /**
     * Moves the column at position pos to position to. The column at position
     * to goes to position (to + 1) if pos > to, or (to - 1) if pos < to
     *
     * @param pos the column number of the column to move
     * @param to the new position of the column to move
     */
    public void moveCol(int pos, int to) {

        if (pos > cols) {
            throw new IndexOutOfBoundsException("This column number is too large. Max column number allowed: " + cols);
        }

        if (pos < 1) {
            throw new IndexOutOfBoundsException("This column number is too small. Min column number allowed: 1");
        }

        if (to == pos) {
            return;
        }

        Cell col = getColHead(pos);

        deleteCol(pos);

        Cell col1;
        if (to == 1) {
            col1 = first;
        } else {
            col1 = getColHead(to - 1);
        }

        Cell col2 = col1.getRight();

        for (int r = 0; r <= rows; r++) {

            col1.setRight(col);
            col.setRight(col2);

            col1 = col1.getDown();
            col = col.getDown();

            if (col2 != null) {
                col2 = col2.getDown();
            }

        }

        cols++;// compensate for delete

    }

    /**
     * Deletes the last row of the table
     */
    public void deleteRow() {
        deleteRow(rows);
    }

    /**
     * Deletes the row at position pos
     *
     * @param pos the position of the row to delete
     */
    public void deleteRow(int pos) {

        if (pos > rows) {
            throw new IndexOutOfBoundsException("This row number is too large. Max row number allowed: " + rows);
        }

        if (pos < 1) {
            throw new IndexOutOfBoundsException("This row number is too small. Min row number allowed: 1");
        }

        Cell row1;
        if (pos == 1) {
            row1 = first;
        } else {
            row1 = getRowHead(pos - 1);
        }

        Cell row2 = row1.getDown();

        for (int c = 0; c <= cols; c++) {

            row1.setDown(row2.getDown());

            row1 = row1.getRight();
            row2 = row2.getRight();

        }

        rows--;

    }

    /**
     * Deletes the last column of the table
     */
    public void deleteCol() {
        deleteCol(cols);
    }

    /**
     * Deletes the column at position pos
     *
     * @param pos the position of the column to delete
     */
    public void deleteCol(int pos) {

        if (pos > cols) {
            throw new IndexOutOfBoundsException("This column number is too large. Max column number allowed: " + cols);
        }

        if (pos < 1) {
            throw new IndexOutOfBoundsException("This column number is too small. Min column number allowed: 1");
        }

        Cell col1;
        if (pos == 1) {
            col1 = first;
        } else {
            col1 = getColHead(pos - 1);
        }

        Cell col2 = col1.getRight();

        for (int r = 0; r <= rows; r++) {

            col1.setRight(col2.getRight());

            col1 = col1.getDown();
            col2 = col2.getDown();

        }

        cols--;

    }

    /**
     * Get the row header of the row at position pos
     *
     * @param pos row number
     * @return row header contents
     */
    public Comparable getRow(int pos) {
        return getRowHead(pos).getContent();
    }

    /**
     * Get the column header of the column at position pos
     *
     * @param pos column number
     * @return column header contents
     */
    public Comparable getCol(int pos) {
        return getColHead(pos).getContent();
    }

    /**
     * Get the cell at (row, col)
     *
     * @param row the row number
     * @param col the column number
     * @return the cell's contents
     */
    public Comparable getCell(int row, int col) {
        return getCellObj(row, col).getContent();
    }

    public void setRow(int pos, Comparable content) {
        getRowHead(pos).setContent(content);
    }

    public void setCol(int pos, Comparable content) {
        getColHead(pos).setContent(content);
    }

    public void setCell(int row, int col, Comparable content) {
        getCellObj(row, col).setContent(content);
    }

    /**
     * Sets the position of the cursor to a cell, or row/column header.
     *
     * @param row the row number
     * @param col the column number
     */
    public void setCursor(int row, int col) {

        if (row == 0 && col == 0) {
            throw new IllegalArgumentException("Cannot set first");
        }

        if (row == 0) {
            cursor = getColHead(col);
        } else if (col == 0) {
            cursor = getRowHead(row);
        } else {
            cursor = getCellObj(row, col);
        }

    }

    /**
     * Sets the contents of the cell at the cursor
     */
    public void set(Comparable contents) {
        cursor.setContent(contents);
    }

    /**
     * Gets the contents of the cell at the cursor
     *
     * @return the cell's contents
     */
    public Comparable get() {
        if (cursor == first) {
            throw new IllegalStateException("The cursor must first be set.");
        }

        return cursor.getContent();
    }

    /**
     * @return T if there is another cell to the right of the cell the cursor is
     * currently on; F otherwise
     */
    public boolean hasRight() {
        return cursor.getRight() != null;
    }

    /**
     * @return T if there is another cell below the cell the cursor is currently
     * on; F otherwise
     */
    public boolean hasDown() {
        return cursor.getDown() != null;
    }

    /**
     * Moves the cursor right.
     *
     * If there is no cell to the right, nothing happens.
     */
    public void right() {
        if (cursor == null) {
            throw new IllegalStateException("null cursor");
        }
        cursor = cursor.getRight();
    }

    /**
     * Moves the cursor down.
     *
     * If there is no cell to the down, nothing happens.
     */
    public void down() {
        if (cursor == null) {
            throw new IllegalStateException("null cursor");
        }
        cursor = cursor.getDown();
    }

    /**
     * Gets the row header object of the pos-th row
     *
     * @param pos row number
     * @return row header object
     */
    private Cell getRowHead(int pos) {

        if (pos > rows) {
            throw new IndexOutOfBoundsException("This row number is too large. Max row number allowed: " + rows);
        }

        if (pos < 1) {
            throw new IndexOutOfBoundsException("This row number is too small. Min row number allowed: 1");
        }

        Cell row = first;

        int r = 0;

        while (r < pos) {
            row = row.getDown();
            r++;
        }

        return row;

    }

    /**
     * Get the column header object of the pos-th column
     *
     * @param pos column number
     * @return column header object
     */
    private Cell getColHead(int pos) {

        if (pos > cols) {
            throw new IndexOutOfBoundsException("This column number is too large. Max column number allowed: " + cols);
        }

        if (pos < 1) {
            throw new IndexOutOfBoundsException("This column number is too small. Min column number allowed: 1");
        }

        Cell col = first;

        int c = 0;

        while (c < pos) {
            col = col.getRight();
            c++;
        }

        return col;

    }

    /**
     * Returns the cell object at (row, col)
     *
     * @param row the row number
     * @param col the column number
     * @return the cell object
     */
    private Cell getCellObj(int row, int col) {

        if (row > rows) {
            throw new IndexOutOfBoundsException("This row number is too large. Max row number allowed: " + rows);
        }

        if (row < 1) {
            throw new IndexOutOfBoundsException("This row number is too small. Min row number allowed: 1");
        }

        if (col > cols) {
            throw new IndexOutOfBoundsException("This column number is too large. Max column number allowed: " + cols);
        }

        if (col < 1) {
            throw new IndexOutOfBoundsException("This column number is too small. Min column number allowed: 1");
        }

        Cell cell = getRowHead(row);

        int c = 0;

        while (c < col) {
            cell = cell.getRight();
            c++;
        }

        return cell;

    }

    @Override
    public String toString() {

        String s = "";

        Cell row = first;
        Cell cell = first;

        for (int r = 0; r < rows + 1; r++) {

            for (int c = 0; c < cols + 1; c++) {

                if (cell != first) {
                    s += cell.getContent() + "\t|";
                } else {
                    s += "\t|";
                }
                cell = cell.getRight();

            }

            s += "\n";

            row = row.getDown();
            cell = row;
        }

        return s;

    }

    /**
     * May move the cursors of o and this table
     *
     * @param o the object to compare
     * @return T if they contain exactly the same data; F otherwise
     */
    @Override
    public boolean equals(Object o) {

        if (!(o instanceof Table)) {
            return false;
        }

        if (o == this) {
            return true;
        }

        Table t = (Table) o;

        if (t.getHeight() != rows || t.getWidth() != cols) {
            return false;
        }

        t.setCursor(1, 0);
        setCursor(1, 0);

        for (int i = 1; i <= rows; i++) {

            if ((get() == null || t.get() == null) && t.get() != get()) {
                return false;
            }

            if (t.get().compareTo(get()) != 0) {
                return false;
            }
            t.down();
            down();
        }

        for (int i = 0; i <= rows; i++) {

            t.setCursor(i, 1);
            setCursor(i, 1);

            for (int j = 1; j <= cols; j++) {

                if (t.get().compareTo(get()) != 0) {
                    return false;
                }

                t.right();
                right();

            }
        }

        return true;

    }

    public void printAll() {

        System.out.print("--\n\t");

        for (int r = 0; r <= rows; r++) {

            for (int c = 0; c <= cols; c++) {

                Cell cell;

                if (r == 0 && c == 0) {
                    continue;
                }

                if (r == 0) {
                    cell = getColHead(c);
                } else if (c == 0) {
                    cell = getRowHead(r);
                } else {
                    cell = getCellObj(r, c);
                }

                if (cell != null) {
                    System.out.print(cell.getContent() + (cell.getDown() == null ? "" : "v") + (cell.getRight() == null ? "" : ">") + "\t");
                } else {
                    System.out.print("\t");
                }

            }

            System.out.println();

        }

        System.out.println("--\n");

    }

}
