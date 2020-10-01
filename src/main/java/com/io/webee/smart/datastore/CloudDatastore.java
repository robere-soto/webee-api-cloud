package com.io.webee.smart.datastore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.OrderBy;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public class CloudDatastore {

	private Datastore datastore;
	private KeyFactory keyFactory;
	private Firestore db;
	
	public CloudDatastore(){

		// Use a service account
		InputStream serviceAccount = null;
		serviceAccount = this.getClass().getResourceAsStream("devices-db-ee7276c04b01.json");
		GoogleCredentials credentials = null;
		try {
			credentials = GoogleCredentials.fromStream(serviceAccount);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FirebaseOptions options = new FirebaseOptions.Builder()
		    .setCredentials(credentials)
		    .build();
		FirebaseApp.initializeApp(options);

		db = FirestoreClient.getFirestore();
	}
	
	public String readDevice(String mac) {
		
		HashMap<String, Object> map = null;
		// asynchronously retrieve all users
		ApiFuture<QuerySnapshot> query = db.collection("Devices").whereEqualTo("MacAddress", mac).get();
		
		// ...
		// query.get() blocks on response
		QuerySnapshot querySnapshot = null;
		try {
			querySnapshot = query.get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents) {
		  System.out.println("User: " + document.getData());
		  map = (HashMap<String, Object>) document.getData();
		  
		  for(Entry<String, Object> entry : map.entrySet()) {
			  if(entry.getKey().equals("Timestamp"))
				return (String) entry.getValue();
		  }
	           
		}
		
	    return null;
	}
	
	public void registerDevice(String mac) {
	
	    FullEntity messageEntity = Entity.newBuilder(keyFactory.newKey())
	  	      .set("MacAddress", mac)
	  	      .set("Timestamp", System.currentTimeMillis())
	  	      .build();
	  	    datastore.put(messageEntity);
	}
	
}
