public class EncryptedDatabase implements Database {
    private final net.sqlcipher.database.SQLiteDatabase delegate;

    public EncryptedDatabase(net.sqlcipher.database.SQLiteDatabase delegate) {
        this.delegate = delegate;
    }

    @Override
    public void beginTransaction() {
        delegate.beginTransaction();
    }

    @Override
    public DatabaseStatement compileStatement(String sql) {
        return new EncryptedDatabaseStatement(delegate.compileStatement(sql));
    }

    @Override
    public Cursor rawQuery(String sql, String[] selectionArgs) {
        return delegate.rawQuery(sql, selectionArgs);
    }
}