package com.ifood.ifood.ultil;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.ifood.ifood.data.Transaction;

import java.util.ArrayList;
import java.util.List;

public class SqliteTransactionController extends SqliteDataController {
    private final String TABLE_NAME = "Transaction";
    private final int COLUMN_ID_INDEX = 0;
    private final int COLUMN_USER_ID_INDEX = 1;
    private final int COLUMN_TRANSACTION_TYPE_INDEX = 2;
    private final int COLUMN_ORDER_TIME_INDEX = 3;
    private final int COLUMN_STATUS_INDEX = 4;
    public SqliteTransactionController(Context con) {
        super(con);
        checkTableExistInDatabase(TABLE_NAME);
    }

    public List<Transaction> getTransactionHistory(int transactionId, int userId) {
        List<Transaction> transactions = new ArrayList<>();
        try {
            openDataBase();
            Cursor cs = database.query(TABLE_NAME, null, "Id = ? AND UserId = ?"
                    , new String[]{transactionId + "", userId + ""}, null, null, null, null);
            while (cs.moveToNext()) {
                Transaction transaction = new Transaction();
                transaction.setId(cs.getInt(COLUMN_ID_INDEX));
                transaction.setUserId(cs.getInt(COLUMN_USER_ID_INDEX));
                transaction.setTypeOfTransaction(cs.getInt(COLUMN_TRANSACTION_TYPE_INDEX));
                transaction.setCreateOn(cs.getString(COLUMN_ORDER_TIME_INDEX));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return transactions;
    }


    public String getTableName(){
        return TABLE_NAME;
    }
}
