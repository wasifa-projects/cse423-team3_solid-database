public class StandardDatabaseStatement implements DatabaseStatement {
    private final android.database.sqlite.SQLiteStatement delegate;

    public StandardDatabaseStatement(android.database.sqlite.SQLiteStatement delegate) {
        this.delegate = delegate;
    }

    @Override
    public void execute() {
        delegate.execute();
    }

    @Override
    public void bindLong(int index, long value) {
        delegate.bindLong(index, value);
    }

    @Override
    public void close() {
        delegate.close();
    }

    @Override
    public Object getRawStatement() {
        return delegate;
    }
}