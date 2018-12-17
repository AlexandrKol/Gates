package com.accesshouse.kostikovo;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DBOpenHelper{
	
static String[] result_columns = new String[] {"_id","name","id", "tel","imei","login","status"
	,"submission_date"," reserve_date","reserveint","reservechar","login2" ,"pass2"};
private static final String DATABASE_NAME = "MyToSEcDB.db";
private static final String DATABASE_LAKE = "lake";
private static final String DATABASE_CREATE_T1 =
		"create table " + DATABASE_LAKE + " ( _id integer primary key autoincrement," +
					"name text,id integer,tel text,imei text,login text, status integer,"+
	"submission_date long, reserve_date long,reserveint integer,reservechar text,login2 text,pass2 text);";
 
static SQLiteDatabase myDatabase;
private static String DATABASE_TABLE=DATABASE_LAKE;
public DBOpenHelper(Context context) {
	super();
	
	
	myDatabase = context.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);

}



@SuppressWarnings("unchecked")
static
ArrayList<String> getReadText(){
	@SuppressWarnings("rawtypes")
	ArrayList read=new ArrayList<String>();
		
	Cursor allRows ;
	
	try{	allRows 	= myDatabase.query(true, DATABASE_LAKE, result_columns,
			null, null, null, null, null, null);
	if(allRows.moveToFirst())do{
		read.add(allRows.getString(1));
		read.add(allRows.getString(2));	
		read.add(allRows.getString(3));	
		read.add(allRows.getString(4));
		read.add(allRows.getString(5));
		read.add(allRows.getString(6));
		read.add(allRows.getString(7));
		read.add(allRows.getString(8));
		read.add(allRows.getString(9));
		read.add(allRows.getString(10));
		read.add(allRows.getString(11));
		read.add(allRows.getString(12)); 
	}while(allRows.moveToNext());
}catch(Exception e){
	
	try{
		myDatabase.execSQL(DATABASE_CREATE_T1); 
	}catch(Exception ee){}
	}

	return read;
}

static long insertAndSend(String name,String tel,int id,int status,String login2) {

	

	ContentValues newValues = new ContentValues();
	newValues.put(result_columns[1], name);
	newValues.put(result_columns[2], id);
	newValues.put(result_columns[3], tel);
	newValues.put(result_columns[4], status);
	newValues.put(result_columns[11], login2 );
	return myDatabase.insert(DATABASE_TABLE, null, newValues);


}
static int update(String[] strings,int id) {
	ContentValues newValues = new ContentValues();
	
	for(int i=0;i<strings.length;i++)
	newValues.put(result_columns[i+1], strings[i]);
	
	return	myDatabase.update(DATABASE_TABLE, newValues,  "_id="+id, null);
}

static int updatePin(String strings) {

	ContentValues newValues = new ContentValues();
	
	newValues.put(result_columns[6], strings);
	
 return myDatabase.update(DATABASE_TABLE, newValues,  "_id="+getid(), null);

}
static int updatelogin(String login,String password) {

	
	String DATABASE_TABLE=DATABASE_LAKE; 
	

	ContentValues newValues = new ContentValues();
	
	newValues.put(result_columns[11], login);
	newValues.put(result_columns[12], password);
	return	myDatabase.update(DATABASE_TABLE, newValues,  "_id="+getid(), null);
}
private static int getid(){
	int out = 0;
Cursor allRows = myDatabase.query(true, DATABASE_LAKE, result_columns,
			null, null, null, null, null, null);
	if(allRows.moveToFirst())//do{
		out= Integer.parseInt(allRows.getString(0));

	return out;
}
static class allArray{

	public static ArrayList getArrayTop(){
		return getReadText();
	}
	
}
 static void removeRows(String[] trash) {
Cursor allRows = myDatabase.query(true, DATABASE_LAKE, result_columns,
		null, null, null, null, null, null);
if(allRows.moveToFirst())do{
	
		if(trash.length>1){
		if( allRows.getString(2).equals(trash[1]) && allRows.getString(1).equals(trash[0]))

		myDatabase.delete(DATABASE_LAKE, "_id=" + allRows.getString(0), null);
		} else if(allRows.getString(1).equals(trash[0]))
			myDatabase.delete(DATABASE_LAKE, "_id=" + allRows.getString(0), null);
	}while(allRows.moveToNext());	
	

}



public long insertAndSend(String dds, String simSerialNumber) {
String DATABASE_TABLE=DATABASE_LAKE; 
	

	ContentValues newValues = new ContentValues();

	newValues.put(result_columns[3], dds);

	newValues.put(result_columns[11], simSerialNumber );
return myDatabase.insert(DATABASE_TABLE, null, newValues);
	
}
}