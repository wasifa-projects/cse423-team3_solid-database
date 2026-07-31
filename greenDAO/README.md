# greenDAO Database Module - Selected Files Overview

| File Name | Code Size | Short Description |
| :--- | :--- | :--- |
| `DatabaseStatement.java` | **Small** | An abstraction interface representing a compiled SQL statement to execute queries safely. |
| `EncryptedDatabaseStatement.java` | **Small** | A wrapper class delegating database statements to SQLCipher for encrypted database operations. |
| `Database.java` | **Medium** | The main interface defining core database operations and transaction controls across standard and encrypted DBs. |
| `StandardDatabaseStatement.java` | **Medium** | Implementation that wraps Android's native `android.database.sqlite.SQLiteStatement`. |
| `EncryptedDatabase.java` | **Medium** | Implementation of the `Database` interface backed by SQLCipher's encrypted `SQLiteDatabase`. |
| `StandardDatabase.java` | **Large** | Core class wrapping Android's native `SQLiteDatabase` to handle queries, compilation, and transactions. |
| `DatabaseOpenHelper.java` | **Large** | Abstract helper class managing database creation, schema upgrades, and encrypted connection setups. |


For detailed every file descriptions and SOLID principles analysis, please check the main README.md.
