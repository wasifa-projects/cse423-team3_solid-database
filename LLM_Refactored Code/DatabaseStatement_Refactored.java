// Interface 1: Focused solely on parameter binding
public interface StatementBinder {
    void bindNull(int index);
    void bindLong(int index, long value);
    void bindDouble(int index, double value);
    void bindString(int index, String value);
    void bindBlob(int index, byte[] value);
    void clearBindings();
}

// Interface 2: Focused solely on statement execution
public interface StatementExecutable {
    void execute();
    long insert();
    long simpleQueryForLong();
    String simpleQueryForString();
}

// Unified Abstraction (DIP Applied)
public interface DatabaseStatement extends StatementBinder, StatementExecutable, Closeable {
    Object getRawStatement();
}