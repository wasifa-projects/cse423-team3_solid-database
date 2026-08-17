// Factory Abstraction for OCP
public interface StatementFactory {
    DatabaseStatement createStatement(android.database.sqlite.SQLiteDatabase db, String sql);
}

public class StandardDatabase implements Database {
    private final android.database.sqlite.SQLiteDatabase delegate;
    private final StatementFactory statementFactory;

    public StandardDatabase(android.database.sqlite.SQLiteDatabase delegate, StatementFactory statementFactory) {
        this.delegate = delegate;
        this.statementFactory = statementFactory;
    }

    @Override
    public DatabaseStatement compileStatement(String sql) {
        return statementFactory.createStatement(delegate, sql);
    }

    @Override
    public void beginTransaction() {
        delegate.beginTransaction();
    }
}