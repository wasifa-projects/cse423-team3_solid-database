// Role-specific interfaces
public interface TransactionManager {
    void beginTransaction();
    void endTransaction();
    void setTransactionSuccessful();
    boolean inTransaction();
}

public interface QueryExecutor {
    Cursor rawQuery(String sql, String[] selectionArgs);
    void execSQL(String sql) throws SQLException;
}

public interface StatementCompiler {
    DatabaseStatement compileStatement(String sql);
}

// Unified Segregated Interface
public interface Database extends TransactionManager, QueryExecutor, StatementCompiler, Closeable {
    Object getRawDatabase();
}