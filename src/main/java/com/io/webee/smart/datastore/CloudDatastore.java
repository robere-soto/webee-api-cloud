package com.io.webee.smart.datastore;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.KeyFactory;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public class CloudDatastore {

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
		// asynchronously retrieve all devices
		ApiFuture<QuerySnapshot> query = db.collection("Devices").whereEqualTo("MacAddress", mac).get();
		
		QuerySnapshot querySnapshot = null;
		try {
			querySnapshot = query.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
			return "3000";
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
		
	    return "3001";
	}
	
	public String deregisterDevice(String mac) {
		
		// asynchronously delete a document
		ApiFuture<WriteResult> writeResult = db.collection("Devices").document(mac).delete();

		try {
			System.out.println("Update time : " + writeResult.get().getUpdateTime());
		} catch (InterruptedException e) {
			e.printStackTrace();
			return "3001";
		} catch (ExecutionException e) {
			e.printStackTrace();
			return "3001";
		}
		
		return "0";
		
	}
	public String registerDevice(String mac) {
	
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		DocumentReference docRef = db.collection("Devices").document(mac);
		
		Map<String, Object> data = new HashMap<>();
		data.put("MacAddress", mac);
		data.put("Timestamp", timestamp);
		
		//asynchronously write data
		ApiFuture<WriteResult> result = docRef.set(data);
		
		try {
			System.out.println("Update time : " + result.get().getUpdateTime());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "3001";
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "3001";
		}
		
		return "0";
	}
	
}
