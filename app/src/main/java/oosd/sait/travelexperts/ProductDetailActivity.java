package oosd.sait.travelexperts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import oosd.sait.travelexperts.data.DataSource;
import oosd.sait.travelexperts.data.Product;
import oosd.sait.travelexperts.data.ProductManager;

public class ProductDetailActivity extends AppCompatActivity {
    TextView tvHeader;
    EditText etProductId, etProductName;
    Button btnSave, btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        tvHeader = findViewById(R.id.tvHeader);
        etProductId = findViewById(R.id.etProductId);
        etProductName = findViewById(R.id.etProductName);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        etProductId.setFocusable(false);
        etProductName.setFocusedByDefault(true);

        String mode = getIntent().getStringExtra("mode");

        DataSource<Product, Integer> ds = new ProductManager(this);

        if (mode.equals("create")) {
            tvHeader.setText("New Product");
            btnDelete.setVisibility(View.GONE);

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String productName = etProductName.getText().toString();

                    new Thread(() -> {
                        int result = ds.insert(new Product(0, productName));
                        Log.d("nate", result+"");

                        runOnUiThread(() -> {
                            ProductsActivity.getInstance().loadProducts();
                            Toast.makeText(ProductsActivity.getInstance(), "Product added.", Toast.LENGTH_LONG).show();
                            finish();
                        });

                    }).start();
                }
            });
        }
    }
}