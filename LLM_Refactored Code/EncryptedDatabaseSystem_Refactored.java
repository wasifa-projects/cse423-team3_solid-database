// Abstract Parameter Mapper
public interface EncryptedParamMapper {
    void mapBindings(net.sqlcipher.database.SQLiteStatement delegate, Object[] args);
}

// Refactored Class using Adapter Pattern & DIP
public class EncryptedDatabaseStatement implements DatabaseStatement {
    private final net.sqlcipher.database.SQLiteStatement delegate;

    public EncryptedDatabaseStatement(net.sqlcipher.database.SQLiteStatement delegate) {
        this.delegate = delegate;
    }

    @Override
    public void execute() {
        delegate.execute();
    }

    @Override
    public void bindString(int index, String value) {
        delegate.bindString(index, value);
    }

    @Override
    public void close() {
        delegate.close();
    }
}