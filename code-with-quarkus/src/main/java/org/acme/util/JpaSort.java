package org.acme.util;


import java.util.ArrayList;
import java.util.List;

public class JpaSort {

    public enum Direction {

        Ascending,

        Descending;
    }


    public enum NullPrecedence {

        NULLS_FIRST,

        NULLS_LAST;
    }

    public static class Column {
        private String name;
        private Direction direction;
        private NullPrecedence nullPrecedence;

        public Column(String name) {
            this(name, Direction.Ascending);
        }

        public Column(String name, Direction direction) {
            this.name = name;
            this.direction = direction;
        }

        public Column(String name, Direction direction, NullPrecedence nullPrecedence) {
            this.name = name;
            this.direction = direction;
            this.nullPrecedence = nullPrecedence;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Direction getDirection() {
            return direction;
        }

        public void setDirection(Direction direction) {
            this.direction = direction;
        }

        public NullPrecedence getNullPrecedence() {
            return nullPrecedence;
        }

        public void setNullPrecedence(NullPrecedence nullPrecedence) {
            this.nullPrecedence = nullPrecedence;
        }
    }

    private List<Column> columns = new ArrayList<>();
    private boolean escapingEnabled = true;

    private JpaSort() {
    }

    /**
     * JpaSort by the given column, in ascending order.
     *
     * @param column the column to JpaSort on, in ascending order.
     * @return a new JpaSort instance which sorts on the given column in ascending order.
     * @see #by(String, Direction)
     * @see #by(String...)
     */
    public static JpaSort by(String column) {
        return new JpaSort().and(column);
    }

    /**
     * JpaSort by the given column, in the given order.
     *
     * @param column the column to JpaSort on, in the given order.
     * @param direction the direction to JpaSort on.
     * @return a new JpaSort instance which sorts on the given column in the given order.
     * @see #by(String)
     * @see #by(String...)
     */
    public static JpaSort by(String column, Direction direction) {
        return new JpaSort().and(column, direction);
    }

    /**
     * JpaSort by the given column, in the given order and in the given null precedence.
     *
     * @param column the column to JpaSort on, in the given order.
     * @param nullPrecedence the null precedence to use.
     * @return a new JpaSort instance which sorts on the given column in the given order and null precedence.
     * @see #by(String)
     * @see #by(String...)
     */
    public static JpaSort by(String column, NullPrecedence nullPrecedence) {
        return by(column, Direction.Ascending, nullPrecedence);
    }

    /**
     * JpaSort by the given column, in the given order and in the given null precedence.
     *
     * @param column the column to JpaSort on, in the given order.
     * @param direction the direction to JpaSort on.
     * @param nullPrecedence the null precedence to use.
     * @return a new JpaSort instance which sorts on the given column in the given order and null precedence.
     * @see #by(String)
     * @see #by(String...)
     */
    public static JpaSort by(String column, Direction direction, NullPrecedence nullPrecedence) {
        return new JpaSort().and(column, direction, nullPrecedence);
    }

    /**
     * JpaSort by the given columns, in ascending order. Equivalent to {@link #ascending(String...)}.
     *
     * @param columns the columns to JpaSort on, in ascending order.
     * @return a new JpaSort instance which sorts on the given columns in ascending order.
     * @see #by(String, Direction)
     * @see #by(String)
     * @see #ascending(String...)
     * @see #descending(String...)
     */
    public static JpaSort by(String... columns) {
        JpaSort JpaSort = new JpaSort();
        for (String column : columns) {
            JpaSort.and(column);
        }
        return JpaSort;
    }

    /**
     * JpaSort by the given columns, in ascending order. Equivalent to {@link #by(String...)}.
     *
     * @param columns the columns to JpaSort on, in ascending order.
     * @return a new JpaSort instance which sorts on the given columns in ascending order.
     * @see #by(String, Direction)
     * @see #by(String)
     * @see #by(String...)
     * @see #descending(String...)
     */
    public static JpaSort ascending(String... columns) {
        return by(columns);
    }

    /**
     * JpaSort by the given columns, in descending order.
     *
     * @param columns the columns to JpaSort on, in descending order.
     * @return a new JpaSort instance which sorts on the given columns in descending order.
     * @see #by(String, Direction)
     * @see #by(String)
     * @see #descending(String...)
     */
    public static JpaSort descending(String... columns) {
        JpaSort JpaSort = new JpaSort();
        for (String column : columns) {
            JpaSort.and(column, Direction.Descending);
        }
        return JpaSort;
    }

    /**
     * Sets the order to descending for all current JpaSort columns.
     *
     * @return this instance, modified.
     * @see #ascending()
     * @see #direction(Direction)
     */
    public JpaSort descending() {
        return direction(Direction.Descending);
    }

    /**
     * Sets the order to ascending for all current JpaSort columns.
     *
     * @return this instance, modified.
     * @see #descending()
     * @see #direction(Direction)
     */
    public JpaSort ascending() {
        return direction(Direction.Ascending);
    }

    /**
     * Sets the order to all current JpaSort columns.
     *
     * @param direction the direction to use for all current JpaSort columns.
     * @return this instance, modified.
     * @see #descending()
     * @see #ascending()
     */
    public JpaSort direction(Direction direction) {
        for (Column column : columns) {
            column.direction = direction;
        }
        return this;
    }

    /**
     * Adds a JpaSort column, in ascending order.
     *
     * @param name the new column to JpaSort on, in ascending order.
     * @return this instance, modified.
     * @see #and(String, Direction)
     */
    public JpaSort and(String name) {
        columns.add(new Column(name));
        return this;
    }

    /**
     * Adds a JpaSort column, in the given order.
     *
     * @param name the new column to JpaSort on, in the given order.
     * @param direction the direction to JpaSort on.
     * @return this instance, modified.
     * @see #and(String)
     */
    public JpaSort and(String name, Direction direction) {
        columns.add(new Column(name, direction));
        return this;
    }

    /**
     * Adds a JpaSort column, in the given null precedence.
     *
     * @param name the new column to JpaSort on, in the given null precedence.
     * @param nullPrecedence the null precedence to use.
     * @return this instance, modified.
     * @see #and(String)
     */
    public JpaSort and(String name, NullPrecedence nullPrecedence) {
        return and(name, Direction.Ascending, nullPrecedence);
    }

    /**
     * Adds a JpaSort column, in the given order and null precedence.
     *
     * @param name the new column to JpaSort on, in the given order and null precedence.
     * @param direction the direction to JpaSort on.
     * @param nullPrecedence the null precedence to use.
     * @return this instance, modified.
     * @see #and(String)
     */
    public JpaSort and(String name, Direction direction, NullPrecedence nullPrecedence) {
        columns.add(new Column(name, direction, nullPrecedence));
        return this;
    }

    /**
     * Disables escaping of column names with a backticks during HQL Order By clause generation
     *
     * @return this instance, modified.
     */
    public JpaSort disableEscaping() {
        escapingEnabled = false;
        return this;
    }


    public List<Column> getColumns() {
        return columns;
    }


    public static JpaSort empty() {
        return by();
    }

    public boolean isEscapingEnabled() {
        return escapingEnabled;
    }
}