package com.example.varun.bookedu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class home_page extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FirebaseAuth mAuth;
    TextView email,name;
    ImageView profile_picture;
    private RecyclerView mbooklist;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mAuth=FirebaseAuth.getInstance();
        email=findViewById(R.id.email_textView4);
        name=findViewById(R.id.name_textView);
        profile_picture=findViewById(R.id.profile_picture_imageView);
        mDatabase=FirebaseDatabase.getInstance().getReference().child("sbooks");
        mDatabase.keepSynced(true);
        mbooklist=findViewById(R.id.myrecyclerview);
        mbooklist.setHasFixedSize(true);
        mbooklist.setLayoutManager(new LinearLayoutManager(this));
        if(mAuth.getCurrentUser()==null)
        {
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
        //FAB button implementation
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(home_page.this,sellers_page.class);
                startActivity(i);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<book,bookviewholder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<book, bookviewholder>
                (book.class,R.layout.book_card_view,bookviewholder.class,mDatabase) {
            @Override
            protected void populateViewHolder(bookviewholder viewHolder, book model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setAuthor1(model.getAuthor1());
                viewHolder.setImage_url(getApplicationContext(),model.getImage_url());
                viewHolder.setEdition(model.getEdition());
                viewHolder.setPrice(model.getPrice());
            }
        };
        mbooklist.setAdapter(firebaseRecyclerAdapter);
    }

    public static class bookviewholder extends RecyclerView.ViewHolder
    {
        View mview;
        public bookviewholder(View itemView)
        {
            super(itemView);
            mview=itemView;
        }

        public void setTitle(String title)
        {
            TextView book_name=(TextView)mview.findViewById(R.id.book_name_textView);
            book_name.setText(title);
        }

        public void setAuthor1(String author1)
        {
            TextView author1_name=(TextView)mview.findViewById(R.id.author_name_textView);
            author1_name.setText(author1);
        }

        public void setImage_url(Context cxt, String image_url)
        {
            ImageView book_image= (ImageView)mview.findViewById(R.id.book_image_imageView);
            Picasso.with(cxt).load(image_url).into(book_image);
        }
        public void setEdition(String edition)
        {
            TextView edition_number=(TextView)mview.findViewById(R.id.edition_number_textView);
            edition_number.setText(edition);
        }
        public void setPrice(long price)
        {
            TextView price_=(TextView)mview.findViewById(R.id.price_textView);
            price_.setText(String.valueOf(price));
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
        }
        else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_contactus) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent i = new Intent(home_page.this,MainActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
