package com.example.myapplication.database;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class WordRoomDatabase_Impl extends WordRoomDatabase {
  private volatile WordDao _wordDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `word_table` (`startTime` TEXT NOT NULL, `endTime` TEXT NOT NULL, PRIMARY KEY(`startTime`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `sleep_time_table` (`date` TEXT NOT NULL, `duration` INTEGER NOT NULL, PRIMARY KEY(`date`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `item_table` (`_id` INTEGER NOT NULL, `name` TEXT NOT NULL, `icon` TEXT NOT NULL, `value` INTEGER NOT NULL, `level` INTEGER NOT NULL, PRIMARY KEY(`_id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '0b9d6dd17729716818323a5fa3fcca73')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `word_table`");
        _db.execSQL("DROP TABLE IF EXISTS `sleep_time_table`");
        _db.execSQL("DROP TABLE IF EXISTS `item_table`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsWordTable = new HashMap<String, TableInfo.Column>(2);
        _columnsWordTable.put("startTime", new TableInfo.Column("startTime", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWordTable.put("endTime", new TableInfo.Column("endTime", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWordTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWordTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoWordTable = new TableInfo("word_table", _columnsWordTable, _foreignKeysWordTable, _indicesWordTable);
        final TableInfo _existingWordTable = TableInfo.read(_db, "word_table");
        if (! _infoWordTable.equals(_existingWordTable)) {
          return new RoomOpenHelper.ValidationResult(false, "word_table(com.example.myapplication.database.Word).\n"
                  + " Expected:\n" + _infoWordTable + "\n"
                  + " Found:\n" + _existingWordTable);
        }
        final HashMap<String, TableInfo.Column> _columnsSleepTimeTable = new HashMap<String, TableInfo.Column>(2);
        _columnsSleepTimeTable.put("date", new TableInfo.Column("date", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSleepTimeTable.put("duration", new TableInfo.Column("duration", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSleepTimeTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSleepTimeTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSleepTimeTable = new TableInfo("sleep_time_table", _columnsSleepTimeTable, _foreignKeysSleepTimeTable, _indicesSleepTimeTable);
        final TableInfo _existingSleepTimeTable = TableInfo.read(_db, "sleep_time_table");
        if (! _infoSleepTimeTable.equals(_existingSleepTimeTable)) {
          return new RoomOpenHelper.ValidationResult(false, "sleep_time_table(com.example.myapplication.database.Sleep).\n"
                  + " Expected:\n" + _infoSleepTimeTable + "\n"
                  + " Found:\n" + _existingSleepTimeTable);
        }
        final HashMap<String, TableInfo.Column> _columnsItemTable = new HashMap<String, TableInfo.Column>(5);
        _columnsItemTable.put("_id", new TableInfo.Column("_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsItemTable.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsItemTable.put("icon", new TableInfo.Column("icon", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsItemTable.put("value", new TableInfo.Column("value", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsItemTable.put("level", new TableInfo.Column("level", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysItemTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesItemTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoItemTable = new TableInfo("item_table", _columnsItemTable, _foreignKeysItemTable, _indicesItemTable);
        final TableInfo _existingItemTable = TableInfo.read(_db, "item_table");
        if (! _infoItemTable.equals(_existingItemTable)) {
          return new RoomOpenHelper.ValidationResult(false, "item_table(com.example.myapplication.database.Item).\n"
                  + " Expected:\n" + _infoItemTable + "\n"
                  + " Found:\n" + _existingItemTable);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "0b9d6dd17729716818323a5fa3fcca73", "be7416945a539c021e7967fb4fc766c9");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "word_table","sleep_time_table","item_table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `word_table`");
      _db.execSQL("DELETE FROM `sleep_time_table`");
      _db.execSQL("DELETE FROM `item_table`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  WordDao wordDao() {
    if (_wordDao != null) {
      return _wordDao;
    } else {
      synchronized(this) {
        if(_wordDao == null) {
          _wordDao = new WordDao_Impl(this);
        }
        return _wordDao;
      }
    }
  }
}
