# cse423-team3_solid-database

# SOLID & Architecture Analysis - Database Module

## 📌 Project Overview
This repository contains selected source code files from the **Database module** of the [greenDAO](https://github.com/greenrobot/greenDAO) open-source project. These files were selected for software architecture analysis to evaluate code structure, understand component responsibilities, and identify opportunities for applying **SOLID principles**.

---

## 🔗 Original Repository Details
* **Project Name:** greenDAO
* **Original GitHub Link:** https://github.com/greenrobot/greenDAO
* **Selected Module Path:** `DaoCore/src/main/java/org/greenrobot/greendao/database`

---

## 📂 Repository Structure
```
greenDAO/
└── database/
├── DatabaseStatement.java
├── EncryptedDatabaseStatement.java
├── Database.java
├── StandardDatabaseStatement.java
├── EncryptedDatabase.java
├── StandardDatabase.java
└── DatabaseOpenHelper.java
```
---

## 📊 Selected Files Overview Table

| # | File Name | Code Size | Short Role | Primary SOLID Focus |
|---|---|---|---|---|
| 1 | `DatabaseStatement.java` | **Small** | SQL Statement Abstraction | Interface Segregation (ISP) |
| 2 | `EncryptedDatabaseStatement.java` | **Small** | SQLCipher Statement Adapter | Dependency Inversion (DIP) |
| 3 | `Database.java` | **Medium** | Core Database Interface | Single Responsibility (SRP) / ISP |
| 4 | `StandardDatabaseStatement.java` | **Medium** | Native Android SQLite Wrapper | Liskov Substitution (LSP) |
| 5 | `EncryptedDatabase.java` | **Medium** | Encrypted SQLCipher DB Wrapper | Single Responsibility (SRP) |
| 6 | `StandardDatabase.java` | **Large** | Standard SQLite Execution Engine | Open/Closed Principle (OCP) |
| 7 | `DatabaseOpenHelper.java` | **Large** | DB Lifecycle & Migration Manager | Single Responsibility (SRP) / OCP |

---

## 📝 Detailed File Descriptions & SOLID Analysis

### 1. DatabaseStatement.java
* **Description:** A core interface that represents a pre-compiled SQL statement abstraction in greenDAO. It abstracts execution logic away from specific database drivers (like standard Android SQLite or encrypted SQLCipher), allowing safe and flexible query execution with parameter binding.
* **Main Methods:** `execute()`, `simpleQueryForLong()`, `bindString()`, `bindLong()`, `close()`
* **SOLID Principles to Apply:**
  * **Single Responsibility Principle (SRP):** The interface handles both statement execution and raw parameter binding. Parameter binding could be extracted into a dedicated `StatementBinder` interface.
  * **Interface Segregation Principle (ISP):** Clients that only need basic execution are forced to depend on binding methods. Splitting this into `ExecutableStatement` and `BindableStatement` would follow ISP better.

---

### 2. EncryptedDatabaseStatement.java
* **Description:** An adapter class that delegates statement operations to SQLCipher's native `SQLiteStatement`. It enables greenDAO to run encrypted database statements transparently using the same interface as unencrypted statements.
* **Main Methods:** `execute()`, `simpleQueryForLong()`, `bindString()`, `getRawStatement()`
* **SOLID Principles to Apply:**
  * **Single Responsibility Principle (SRP):** Manages both method delegation and native SQLCipher parameter mapping. Mapping logic can be isolated into a separate wrapper adapter.
  * **Dependency Inversion Principle (DIP):** Depends directly on concrete SQLCipher classes (`net.sqlcipher.database.SQLiteStatement`). Depending on a broader encrypted statement interface would reduce tight coupling.

---

### 3. Database.java
* **Description:** The primary interface abstracting low-level database connection operations for greenDAO. It provides a unified API for managing transactions, compiling raw SQL statements, executing direct queries, and handling underlying database connections regardless of encryption.
* **Main Methods:** `beginTransaction()`, `endTransaction()`, `compileStatement()`, `rawQuery()`
* **SOLID Principles to Apply:**
  * **Interface Segregation Principle (ISP):** Contains methods for query execution, transaction control, and raw SQL compilation. Splitting this into `TransactionManager`, `QueryExecutor`, and `StatementCompiler` would allow clients to depend only on what they use.
  * **Dependency Inversion Principle (DIP):** Provides a common abstraction over Android SQLite and SQLCipher, decoupling high-level DAOs from low-level database drivers.

---

### 4. StandardDatabaseStatement.java
* **Description:** The standard, unencrypted implementation of the `DatabaseStatement` interface. It wraps Android’s native `android.database.sqlite.SQLiteStatement` to bridge native Android SQLite operations with greenDAO’s framework.
* **Main Methods:** `execute()`, `simpleQueryForLong()`, `bindLong()`, `getRawStatement()`
* **SOLID Principles to Apply:**
  * **Single Responsibility Principle (SRP):** Handles both delegation to native SQLite and native object extraction. Resource lifetime management could be moved to a helper wrapper class.
  * **Liskov Substitution Principle (LSP):** Honors LSP by providing a full, predictable implementation of `DatabaseStatement` without modifying expected interface behavior when substituted for native database contexts.

---

### 5. EncryptedDatabase.java
* **Description:** Implements the `Database` interface using SQLCipher’s `SQLiteDatabase`. It allows greenDAO to perform transparent end-to-end encryption by wrapping SQLCipher calls while maintaining the same public interface as standard database drivers.
* **Main Methods:** `beginTransaction()`, `compileStatement()`, `rawQuery()`, `getRawDatabase()`
* **SOLID Principles to Apply:**
  * **Single Responsibility Principle (SRP):** Manages transaction controls, statement compilation, and SQLCipher database instance delegation. Extracting transaction management into a dedicated `EncryptedTransactionHandler` would adhere strictly to SRP.
  * **Dependency Inversion Principle (DIP):** Directly references concrete SQLCipher imports. Wrapping SQLCipher initialization behind a factory or provider abstraction would reduce direct library dependency.

---

### 6. StandardDatabase.java
* **Description:** The main unencrypted implementation of the `Database` interface. It wraps Android's native `android.database.sqlite.SQLiteDatabase` to manage query compilation, execution, and transaction states for standard Android apps.
* **Main Methods:** `beginTransaction()`, `compileStatement()`, `rawQuery()`, `getRawDatabase()`
* **SOLID Principles to Apply:**
  * **Single Responsibility Principle (SRP):** Mixes transaction lifecycle management with query execution and statement compilation. Separating transaction handling into a `NativeTransactionManager` would improve maintainability.
  * **Open/Closed Principle (OCP):** Modifying how statements are created requires editing this class directly. Implementing a `StatementFactory` interface would allow new statement compilation behaviors without changing existing code.

---

### 7. DatabaseOpenHelper.java
* **Description:** An abstract helper class extending Android’s `SQLiteOpenHelper`. It manages database creation, schema upgrades, and context-aware connections, supporting both standard unencrypted and SQLCipher-encrypted database instances.
* **Main Methods:** `getWritableDb()`, `getEncryptedWritableDb()`, `onCreate()`, `onUpgrade()`
* **SOLID Principles to Apply:**
  * **Single Responsibility Principle (SRP):** Handles database lifecycle callbacks (`onCreate`/`onUpgrade`), connection creation, and encryption configuration. Migration logic should be delegated to a dedicated `SchemaMigrator` class.
  * **Open/Closed Principle (OCP):** When adding new migration strategies, `onUpgrade` must be modified directly. Using a Strategy Pattern (`MigrationStrategy`) would allow adding migration rules without altering the helper class.
  * **Dependency Inversion Principle (DIP):** Instead of directly instantiating encrypted or standard database wrappers, an abstract `DatabaseFactory` should be injected to handle connection instantiation.
