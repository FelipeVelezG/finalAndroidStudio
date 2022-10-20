package com.example.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class sales extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();// intancia de firestore
    String idSales; //Variable que contendra las ventas
    String idSeller;
    String venta;
    double comision;
    double contadorComision;
    String  totalcomision;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        EditText dateSales = findViewById(R.id.etdateSales);
        EditText saleValue = findViewById(R.id.etsaleValue);
        Button saveSales = findViewById(R.id.btnsaveSales);
        EditText emailSellerSearch = findViewById(R.id.etemailSellerSearch);

        saveSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSales(dateSales.getText().toString(),saleValue.getText().toString(),emailSellerSearch.getText().toString());
            }

            private void saveSales(String sDateSales, String sSaleValue, String sEmailSellerSearch) {
                db.collection("Seller")
                        .whereEqualTo("emailSeller", emailSellerSearch.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (!task.getResult().isEmpty()) {//Si encontró el documento
//                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                             Map<String, Object> Seller = new HashMap<>();// Tabla cursor

                                            venta = saleValue.getText().toString();
                                            comision = 0.02 * (Double.parseDouble(venta)) ;
                                            contadorComision = contadorComision + comision;
                                            totalcomision = String.valueOf(contadorComision);
                                            Seller.put("totalCommisionSeller",totalcomision );



                                            db.collection("Seller")
                                                .add(Seller)
                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                    @Override
                                                    public void onSuccess(DocumentReference documentReference) {
//                                              Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                                        Toast.makeText(getApplicationContext(),"Vendedor agregado correctamente...",Toast.LENGTH_LONG).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
//                                              Log.w(TAG, "Error adding document", e);
                                                        Toast.makeText(getApplicationContext(),"Error! Vendedor no se guardó...",Toast.LENGTH_LONG).show();
                                                    }
                                                });



//                                            Toast.makeText(getApplicationContext(),"ingresamos la venta ",Toast.LENGTH_LONG).show();
                                            db.collection("Sales")
                                                    .whereEqualTo("emailSales",sEmailSellerSearch )
                                                    .get()
                                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                                            if (task.isSuccessful()) {
//                                                                if (task.getResult().isEmpty()) {//No encontró el documento
                                                                    //Guardar los datos del venta(Seller)
                                                                    Map<String, Object> Sales = new HashMap<>();// Tabla cursor
                                                                    Sales.put("emailSales", sEmailSellerSearch);
                                                                    Sales.put("dateSales", sDateSales);
                                                                    Sales.put("saleValue", sSaleValue);
//



                                                                    db.collection("Sales")
                                                                            .add(Sales)
                                                                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                                @Override
                                                                                public void onSuccess(DocumentReference documentReference) {
//                                              Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                                                                    Toast.makeText(getApplicationContext(),"Venta registrada correctamente",Toast.LENGTH_LONG).show();
                                                                                }
                                                                            })
                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
//                                              Log.w(TAG, "Error adding document", e);
                                                                                    Toast.makeText(getApplicationContext(),"Error! la venta no se registro...",Toast.LENGTH_LONG).show();
                                                                                }
                                                                            });
//                                                                }
//                                                                else
//                                                                {
//                                                                    Toast.makeText(getApplicationContext(),"ID del Vendedor existente",Toast.LENGTH_SHORT).show();
//                                                                }
//                                                            }
                                                        }
                                                    });
                                            }
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),"Vendedor no existe, digite correo registrado",Toast.LENGTH_SHORT).show();
                                    }
                                }
//                            }
                        });



            }
        });



    }
}