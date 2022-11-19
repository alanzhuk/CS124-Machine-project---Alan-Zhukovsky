package edu.illinois.cs.cs124.ay2022.mp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.concurrent.CompletableFuture;
import edu.illinois.cs.cs124.ay2022.mp.R;
import edu.illinois.cs.cs124.ay2022.mp.application.FavoritePlacesApplication;
import edu.illinois.cs.cs124.ay2022.mp.models.Place;
import edu.illinois.cs.cs124.ay2022.mp.models.ResultMightThrow;
import edu.illinois.cs.cs124.ay2022.mp.network.Client;

public class AddPlaceActivity extends AppCompatActivity {
  @Override
  public void onCreate(@Nullable final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Intent returnToMain = new Intent(this, MainActivity.class);
    returnToMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    setContentView(R.layout.activity_addplace);
    Button cancelButton = findViewById(R.id.cancel_button);
    Button saveButton = findViewById(R.id.save_button);
    EditText description = findViewById(R.id.description);
    cancelButton.setOnClickListener(v -> {
      startActivity(returnToMain);
    });
    saveButton.setOnClickListener(v -> {
      Place newPlace = new Place(FavoritePlacesApplication.CLIENT_ID, "Zeus",
          Double.parseDouble(getIntent().getStringExtra("latitude")),
          Double.parseDouble(getIntent().getStringExtra("longitude")), description.toString());
      Client client = Client.start();
      CompletableFuture<ResultMightThrow<Boolean>> hld = new CompletableFuture<>();
      client.postFavoritePlace(newPlace, hld::complete);
      startActivity(returnToMain);
    });
  }
}
