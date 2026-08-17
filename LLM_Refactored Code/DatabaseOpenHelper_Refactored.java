// Strategy Pattern for Migration (OCP)
public interface DatabaseMigrationStrategy {
    void onUpgrade(Database db, int oldVersion, int newVersion);
}

// Factory Pattern for Connection Instantiation (DIP)
public interface EncryptedDbFactory {
    Database createEncryptedDb(String password);
}

public abstract class DatabaseOpenHelper extends SQLiteOpenHelper {
    private final DatabaseMigrationStrategy migrationStrategy;

    public DatabaseOpenHelper(Context context, String name, int version, DatabaseMigrationStrategy migrationStrategy) {
        super(context, name, null, version);
        this.migrationStrategy = migrationStrategy;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        migrationStrategy.onUpgrade(new StandardDatabase(db, StandardDatabaseStatement::new), oldVersion, newVersion);
    }
}