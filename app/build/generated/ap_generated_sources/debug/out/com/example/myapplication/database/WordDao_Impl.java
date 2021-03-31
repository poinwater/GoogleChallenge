package com.example.myapplication.database;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class WordDao_Impl implements WordDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Word> __insertionAdapterOfWord;

  private final EntityInsertionAdapter<Sleep> __insertionAdapterOfSleep;

  private final EntityInsertionAdapter<Item> __insertionAdapterOfItem;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllSleep;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllitem;

  public WordDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWord = new EntityInsertionAdapter<Word>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `word_table` (`startTime`,`endTime`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Word value) {
        if (value.getStartTime() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getStartTime());
        }
        if (value.getEndTime() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getEndTime());
        }
      }
    };
    this.__insertionAdapterOfSleep = new EntityInsertionAdapter<Sleep>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `sleep_time_table` (`date`,`duration`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Sleep value) {
        if (value.getDate() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getDate());
        }
        stmt.bindLong(2, value.getDuration());
      }
    };
    this.__insertionAdapterOfItem = new EntityInsertionAdapter<Item>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `item_table` (`_id`,`name`,`icon`,`value`,`level`) VALUES (?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Item value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getIcon() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getIcon());
        }
        stmt.bindLong(4, value.getValue());
        stmt.bindLong(5, value.get_rare_level());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM word_table";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllSleep = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM sleep_time_table";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllitem = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM item_table";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Word word) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfWord.insert(word);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertDuration(final Sleep sleep) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfSleep.insert(sleep);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertItem(final Item item) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfItem.insert(item);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public void deleteAllSleep() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllSleep.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllSleep.release(_stmt);
    }
  }

  @Override
  public void deleteAllitem() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllitem.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllitem.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Word>> getAlphabetizedWords() {
    final String _sql = "SELECT * FROM word_table ORDER BY startTime ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"word_table"}, false, new Callable<List<Word>>() {
      @Override
      public List<Word> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final List<Word> _result = new ArrayList<Word>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Word _item;
            final String _tmpStartTime;
            _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
            final String _tmpEndTime;
            _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
            _item = new Word(_tmpStartTime,_tmpEndTime);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Sleep>> getduration() {
    final String _sql = "SELECT * FROM sleep_time_table";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"sleep_time_table"}, false, new Callable<List<Sleep>>() {
      @Override
      public List<Sleep> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final List<Sleep> _result = new ArrayList<Sleep>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Sleep _item;
            final String _tmpDate;
            _tmpDate = _cursor.getString(_cursorIndexOfDate);
            final long _tmpDuration;
            _tmpDuration = _cursor.getLong(_cursorIndexOfDuration);
            _item = new Sleep(_tmpDate,_tmpDuration);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Item>> getAllItem() {
    final String _sql = "SELECT * FROM item_table ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"item_table"}, false, new Callable<List<Item>>() {
      @Override
      public List<Item> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "_id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIcon = CursorUtil.getColumnIndexOrThrow(_cursor, "icon");
          final int _cursorIndexOfValue = CursorUtil.getColumnIndexOrThrow(_cursor, "value");
          final int _cursorIndexOfRareLevel = CursorUtil.getColumnIndexOrThrow(_cursor, "level");
          final List<Item> _result = new ArrayList<Item>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Item _item;
            final int _tmp_id;
            _tmp_id = _cursor.getInt(_cursorIndexOfId);
            final String _tmp_name;
            _tmp_name = _cursor.getString(_cursorIndexOfName);
            final String _tmp_icon;
            _tmp_icon = _cursor.getString(_cursorIndexOfIcon);
            final int _tmp_value;
            _tmp_value = _cursor.getInt(_cursorIndexOfValue);
            final int _tmp_rare_level;
            _tmp_rare_level = _cursor.getInt(_cursorIndexOfRareLevel);
            _item = new Item(_tmp_id,_tmp_name,_tmp_icon,_tmp_value,_tmp_rare_level);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }
}
