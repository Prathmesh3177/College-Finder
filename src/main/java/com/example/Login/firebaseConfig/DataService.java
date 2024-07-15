package com.example.Login.firebaseConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.SetOptions;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public class DataService {

    private static Firestore db;

    static {
        try {
            initializeFirebase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initializeFirebase() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("");//Path 
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);
        db = FirestoreClient.getFirestore();
    }

    // Student Data Methods
    public void addStudentData(String document, Map<String, Object> data)
            throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("students").document(document);
        ApiFuture<WriteResult> result = docRef.set(data);
        result.get();
    }

    public DocumentSnapshot getStudentData(String document) throws ExecutionException, InterruptedException {
        try {
            DocumentReference docRef = db.collection("students").document(document);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void deleteStudent(String document) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> writeResult = db.collection("students").document(document).delete();
        writeResult.get();
    }

    public List<Map<String, Object>> getStudentsInDescendingOrder(String orderByField)
            throws ExecutionException, InterruptedException {
        List<Map<String, Object>> documentsList = new ArrayList<>();

        CollectionReference collection = db.collection("students");
        Query query = collection.orderBy(orderByField, Query.Direction.DESCENDING);

        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            documentsList.add(document.getData());
        }

        return documentsList;
    }

    public void updateStudentData(String documentId, Map<String, Object> updatedData)
            throws ExecutionException, InterruptedException {
        CollectionReference collection = db.collection("students");
        DocumentReference docRef = collection.document(documentId);
        ApiFuture<WriteResult> future = docRef.set(updatedData, SetOptions.merge());
        future.get();
    }

    public void deleteCollege(String document) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> writeResult = db.collection("colleges").document(document).delete();
        writeResult.get();
    }

    public List<Map<String, Object>> getCollegesInDescendingOrder(String orderByField)
            throws ExecutionException, InterruptedException {
        List<Map<String, Object>> documentsList = new ArrayList<>();

        CollectionReference collection = db.collection("colleges");
        Query query = collection.orderBy(orderByField, Query.Direction.DESCENDING);

        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            documentsList.add(document.getData());
        }

        return documentsList;
    }

    public void updateCollegeData(String documentId, Map<String, Object> updatedData)
            throws ExecutionException, InterruptedException {
        CollectionReference collection = db.collection("colleges");
        DocumentReference docRef = collection.document(documentId);
        ApiFuture<WriteResult> future = docRef.set(updatedData, SetOptions.merge());
        future.get();
    }

    // User Authentication Methods
    public boolean authenticateUser(String username, String password) throws ExecutionException, InterruptedException {
        DocumentSnapshot document = db.collection("users").document(username).get().get();
        if (document.exists()) {
            String storedPassword = document.getString("password");
            return password.equals(storedPassword);
        }
        return false;
    }

    public boolean isAdmin(String username) throws ExecutionException, InterruptedException {
        DocumentSnapshot document = db.collection("users").document(username).get().get();
        if (document.exists()) {
            String role = document.getString("role");
            return "College".equals(role);
        }
        return false;
    }

    // Generic Methods for Firestore Interaction
    public void updateData(String collectionName, String documentId, Map<String, Object> data)
            throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection(collectionName).document(documentId);
        ApiFuture<WriteResult> future = docRef.set(data, SetOptions.merge());
        future.get();
        System.out.println("Document updated successfully: " + documentId);
    }

    public void deleteData(String collectionName, String documentId) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection(collectionName).document(documentId);
        ApiFuture<WriteResult> future = docRef.delete();
        future.get();
        System.out.println("Document deleted successfully: " + documentId);
    }

    public List<Map<String, Object>> getDataInDescendingOrder(String collectionName, String orderByField)
            throws ExecutionException, InterruptedException {
        List<Map<String, Object>> dataList = new ArrayList<>();

        CollectionReference collectionRef = db.collection(collectionName);
        Query query = collectionRef.orderBy(orderByField, Query.Direction.DESCENDING);

        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            Map<String, Object> data = document.getData();
            dataList.add(data != null ? new HashMap<>(data) : new HashMap<>());
        }

        return dataList;
    }

    public void addData(String collectionName, String documentId, Map<String, Object> data)
            throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection(collectionName).document(documentId);
        ApiFuture<WriteResult> result = docRef.set(data);
        result.get();
    }

    public void setData(String collectionName, String documentName, Map<String, Object> data)
            throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection(collectionName).document(documentName);
        ApiFuture<WriteResult> result = docRef.set(data);
        result.get();
    }

    public Map<String, Object> getData(String collectionName, String documentName)
            throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection(collectionName).document(documentName);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        if (document.exists()) {
            return document.getData();
        } else {
            System.out.println("No such document!");
            return null;
        }
    }

    public void addCollegeData(String collegeName, Map<String, Object> collegeData)
            throws ExecutionException, InterruptedException {
        // Add college data to Firestore
        DocumentReference collegeDocRef = db.collection("colleges").document(collegeName);
        collegeDocRef.set(collegeData).get();

        // Add category data to a new collection under the original college document
        @SuppressWarnings("unchecked")
        Map<String, String> cutoffMarks = (Map<String, String>) collegeData.get("cutoff_marks");
        if (cutoffMarks != null) {
            for (Map.Entry<String, String> entry : cutoffMarks.entrySet()) {
                Map<String, Object> categoryData = new HashMap<>();
                categoryData.put("cutoff", entry.getValue());
                collegeDocRef.collection("categories").document(entry.getKey()).set(categoryData).get();
            }
        }
    }

    public Map<String, Object> getCollegeData(String collegeName) throws ExecutionException, InterruptedException {
        // Fetch college data from Firestore
        DocumentReference collegeDocRef = db.collection("colleges").document(collegeName);
        return collegeDocRef.get().get().getData();
    }

    public Map<String, Object> getCategoryData(String collegeName, String category)
            throws ExecutionException, InterruptedException {
        // Fetch category data from Firestore
        DocumentReference categoryDocRef = db.collection("colleges").document(collegeName).collection("categories")
                .document(category);
        return categoryDocRef.get().get().getData();
    }

    public Map<String, Object> getCollegesByCutoffMarks(String cutoff) throws ExecutionException, InterruptedException {
        // Fetch college data by cutoff from Firestore
        Map<String, Object> result = new HashMap<>();
        CollectionReference collegesRef = db.collection("colleges");
        Iterable<DocumentReference> collegeDocs = collegesRef.listDocuments();
        for (DocumentReference collegeDoc : collegeDocs) {
            CollectionReference categoriesRef = collegeDoc.collection("categories");
            Iterable<DocumentReference> categoryDocs = categoriesRef.listDocuments();
            for (DocumentReference categoryDoc : categoryDocs) {
                Map<String, Object> categoryData = categoryDoc.get().get().getData();
                if (categoryData != null && cutoff.equals(categoryData.get("cutoff"))) {
                    result.put(collegeDoc.getId() + "-" + categoryDoc.getId(), categoryData);
                }
            }
        }
        return result;
    }

    public Map<String, Object> getCollegeDataByCutoffAndCategory(double cutoff, String category)
            throws ExecutionException, InterruptedException {
        // Fetch college data by cutoff and category from Firestore
        Map<String, Object> result = new HashMap<>();
        CollectionReference collegesRef = db.collection("colleges");
        Iterable<DocumentReference> collegeDocs = collegesRef.listDocuments();
        for (DocumentReference collegeDoc : collegeDocs) {
            CollectionReference categoriesRef = collegeDoc.collection("categories");
            DocumentReference categoryDoc = categoriesRef.document(category);
            Map<String, Object> categoryData = categoryDoc.get().get().getData();
            if (categoryData != null) {
                String cutoffValue = (String) categoryData.get("cutoff");
                if (cutoffValue != null && Double.parseDouble(cutoffValue) <= cutoff) {
                    result.put(collegeDoc.getId(), categoryData);
                }
            }
        }
        return result;
    }

    public List<Map<String, Object>> getCollegesByInitialLetters(String initialLetters) throws ExecutionException, InterruptedException {
        List<Map<String, Object>> collegesList = new ArrayList<>();

        // Query colleges collection based on initial letters (case insensitive)
        Query query = db.collection("colleges")
                       .orderBy("name")
                       .startAt(initialLetters.toUpperCase()) // Start at initialLetters (uppercase)
                       .endAt(initialLetters.toLowerCase() + '\uf8ff'); // End at initialLetters (lowercase) + end of Unicode

        // Execute query asynchronously
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        // Retrieve query results
        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            Map<String, Object> collegeData = document.getData();
            collegesList.add(collegeData);
        }

        return collegesList;
    }
}
