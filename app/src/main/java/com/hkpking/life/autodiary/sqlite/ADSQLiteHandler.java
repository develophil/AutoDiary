package com.hkpking.life.autodiary.sqlite;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.util.*;

import com.hkpking.life.autodiary.common.CommonDefines;

public class ADSQLiteHandler implements CommonDefines {
	
	ADSQLiteOpenHelper helper;
	SQLiteDatabase db;

	//초기화
	public ADSQLiteHandler(Context ctx) {
		Log.i(TAG, "ADSQLiteHandler 초기화 >>>>>>>>>>>>>>");
		helper = new ADSQLiteOpenHelper(ctx, "adDatabase.sqlite", null, 2);
	}
	
	//open
	public static ADSQLiteHandler open(Context ctx) {
		return new ADSQLiteHandler(ctx);
	}
	
	//close
	public void close() {
		db.close();
	}
	
	//ad_tb_loc010 위치 정보 저장
	public void insert(Double latitude, Double longitude, String address) {
		Log.i(TAG, "insert("+latitude+", "+longitude+") >>> "+address);
		db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("latitude", latitude);
		values.put("longitude", longitude);
		values.put("address", address);
		db.insert("ad_tb_loc010", null, values);
	}
	
	

	//로그인시 아이디저장 업데이트
	public void updateCheckedID(String Aname, String Bname) {
		Log.i(TAG, "update("+Aname+" "+Bname+") >>>>>>>>>>>>>>");
		db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("Aname", Aname);
		values.put("Bname", Bname);
		db.update("checkData", values, null, null);
	}
	
	//date 정보로 마지막 값 삭제
	public void delete(String date) {
		Log.i(TAG, "delete> "+date+" >>>>>>>>>>>>>>");
		db = helper.getWritableDatabase();
		db.delete("ycData", "date=?", new String[]{date});
	}
	
	//경고메세지 삭제
	public void deleteMSG(String message) {
		Log.i(TAG, "deleteMSG> "+message+" >>>>>>>>>>>>>>");
		db = helper.getWritableDatabase();
		db.delete("msgData", "message=?", new String[]{message});
	}
	
	//경고정보 갯수 검색
	public Cursor selectMessageCount(String name, String msg, String who) {
		Log.i(TAG, msg+"의 갯수 구하기 >>>>>>>>>>>>>>");
		db = helper.getReadableDatabase();
		Cursor c = db.query("ycData", null, "Aname=? and message=? and who=?", new String[]{name, msg, who}, null, null, null);
		return c;
	}

	//경고정보 검색
	public Cursor selectYC(String name, String who) {
		Log.i(TAG, who+"의 select >>>>>>>>>>>>>>");
		db = helper.getReadableDatabase();
		Cursor c = db.query("ycData", null, "Aname=? and who=?", new String[]{name, who}, null, null, null);
		return c;
	}
	
	//검색
	public Cursor select(String table) {
		Log.i(TAG, "select("+table+") >>>>>>>>>>>>>>");
		db = helper.getReadableDatabase();
		Cursor c = db.query(table, null, null, null, null, null, null);
		return c;
	}
	
	//메세지 존재여부 검색
	public Cursor selectMSG(String message) {
		Log.i(TAG, "select("+message+") >>>>>>>>>>>>>>");
		db = helper.getReadableDatabase();
		Cursor c = db.query("msgData", null, "message=?", new String[]{message}, null, null, null);
		return c;
	}
	
	//로그인정보 검색
	public Cursor select(String userid, String passwd) {
		Log.i(TAG, "select("+userid+" "+passwd+") >>>>>>>>>>>>>>");
		db = helper.getReadableDatabase();
		Cursor c = db.query("loginData", new String[]{"Aname","Apasswd"}, "Aname=? and Apasswd=?", new String[]{userid,passwd}, null, null, null);
		return c;
	}

	//차후 직접입력 필요한 데이터베이스 명령들(맘대로 수정)
	public void dbAction() {
		db = helper.getWritableDatabase();
		String sql8 = "create table checkData ( " +
				" _id integer primary key autoincrement , " +
				" Aname text , " +
				" Bname text )";
		db.execSQL(sql8);
	}
	

}//end class
