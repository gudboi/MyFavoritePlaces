package pt.estig.myfavoriteplaces;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pt.estig.myfavoriteplaces.data.DataBase;
import pt.estig.myfavoriteplaces.data.Place;

/**
 *  This activity is here to list all places added by us,
 *  and for calling the AddPlacesActivity by pressing the add button,
 *  and calling the SingularPlaceActivity by pressing in one item.
 */
 public class PlacesActivity extends AppCompatActivity {


    private static final String MAIN_PREFS = "main_prefs";
    private static final String SORTING_PREF = "sorting";
    private static final String PLACE_ID = "place_id";

    private long user_id;
    private String username;

    private TextView usernameText;

    private Intent intent;

    private LinearLayoutManager linearLayoutManager;
    private List places;
    private RecyclerView placeList;
    private PlaceAdapter placeAdapter;

    private View addPlaceHint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        this.user_id = getIntent().getLongExtra("USER_ID", 0);
        this.username = getIntent().getStringExtra("USERNAME");

        //this.usernameText = findViewById(R.id.textView_welcome);
        //usernameText.setText(username);

        placeList = findViewById(R.id.recyclerView);
        placeAdapter = new PlaceAdapter();
        linearLayoutManager = new LinearLayoutManager(this);

        placeList.setAdapter(placeAdapter);
        placeList.setLayoutManager(linearLayoutManager);

        addPlaceHint = findViewById(R.id.add_place_hint_wrapper);
        FloatingActionButton addPlaceFab = findViewById(R.id.button_addPlace);

       addPlaceFab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // Um click longo no FAB, mostra o que o botão faz ao utilizador
                Toast.makeText(PlacesActivity.this, R.string.create_place_btn_hint, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        List<Place> places = DataBase.getInstance(this).placeDao().getAllPlaces();

        placeAdapter.setData(places, true);
        boolean sortedAz = getSharedPreferences(MAIN_PREFS, Context.MODE_PRIVATE).getBoolean(SORTING_PREF, true);

        placeAdapter.setData(places, sortedAz);
        setAddPlaceHintVisible(places.size() == 0); // Se o número de sítios é 0, mostra a View com a dica para adicionar places
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Fazer inflate do xml do menu

        /*getMenuInflater().inflate(R.menu.main, menu);
        boolean contactListSort = getPlaceListSort(this);
        MenuItem item = menu.findItem(R.id.sort_a_z);
        item.setChecked(contactListSort);*/

        //if(menu instanceof MenuBuilder) ((MenuBuilder) menu).setOptionalIconsVisible(true);
        // Temos de devolver true para o menu aparecer
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //boolean Sorting = getSharedPreferences(MAIN_PREFS,MODE_PRIVATE).getBoolean(SORTING_PREF, true);
        /*boolean placeListSort = getPlaceListSort(this);
        MenuItem item = menu.findItem(R.id.sort_a_z);
        item.setChecked(placeListSort);*/
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Método de callback de interação com os itens do menu


        /*switch (item.getItemId()) { // Vamos buscar o id do item...
            case R.id.delete_all_places:
                showDeleteAllPlacesDialog();
                return true; // Devolvemos true se tratámos desta interação
            case R.id.places_map:
                showPlacesMap();
                return true;
            case R.id.sort_a_z:
                placeAdapter.sort(true);
                item.setChecked(true);
                return true;
            case R.id.sort_z_a:
                placeAdapter.sort(false);
                boolean placeListSort;
                setPlaceListSort(this,false);
                item.setChecked(true);
                return true;
        }*/

        return super.onOptionsItemSelected(item); // caso contrário, deixamos a Activity procurar outro possível "handler"
    }

    /**
     * Define a visibilidade da View que indica que não existem contactos (ver activity_main.xml)
     * @param visible Visível
     */
    private void setAddPlaceHintVisible(boolean visible) {
        // Existem 3 modos de visibilidade possíveis: VISIBLE, INVISIBLE e GONE
        // São constantes (int) definidas na class View
        addPlaceHint.setVisibility(visible ? View.VISIBLE : View.GONE);
    }



    public void btn_logoutClicked(View view){
        intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * @param view
     * carregar alt + enter com o cursor em cima do metodo
     */
    public void btn_addPlaceClicked(View view){
        intent = new Intent(this, AddPlaceActivity.class);
        intent.putExtra("USER_ID", this.user_id);
        startActivity(intent);
    }

    private void startSinglePlaceActivity(Place place) {
        /*long id_place = place.getId_place();
        String place_name = place.getPlace_name();
        byte[] place_photo = place.getPhoto();


        intent = new Intent(this, SinglePlaceActivity.class);
        intent.putExtra("PLACE_ID", id_place);
        intent.putExtra("PLACE_NAME", place_name);
        intent.putExtra("PLACE_PHOTO", place_photo);
        //intent.putExtra("USERNAME", username);
        startActivity(intent);*/

        SinglePlaceActivity.start(this, place.getId_place());
    }

    /*private void showContactsMap() {
        ContactsMapActivity.start(this);
    }*/



    class PlaceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        Place place;

        final TextView name;
        final ImageView photo; // Se quiserem utilizar ImageViews circulares, podem utilizar a biblioteca CircleImageView (https://github.com/hdodenhof/CircleImageView)



        private PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this); // também existe um Listener para clicks longos! ver #onLongClick abaixo
            name = itemView.findViewById(R.id.textView_placeName);
            photo = itemView.findViewById(R.id.imageView_placeImage);
        }

        private void bind(Place place) {
            this.place = place;
            name.setText(place.getPlace_name());
            if(place.getPhoto() != null && place.getPhoto().length != 0) {
                photo.setImageBitmap(bitmapFromBytes(place.getPhoto()));
                photo.setVisibility(View.VISIBLE);
            }
            else {
                photo.setVisibility(View.INVISIBLE);
            }

        }

        private Bitmap bitmapFromBytes(byte[] photoBytes) {
            ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(photoBytes);
            Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream);
            return bitmap;
        }

        @Override
        public void onClick(View view) {
            startSinglePlaceActivity(place); // um click curto, lança a Activity com as mensagens do contacto
        }

        @Override
        public boolean onLongClick(View view) {
            //showDeleteContactDialog(contact); // um click longo, invocamos o método para apagar o contacto
            return true; // devolvemos true se tratámos o evento
        }
    }

    class PlaceAdapter extends RecyclerView.Adapter<PlaceViewHolder> {

        private List<Place> data = new ArrayList<>();

        private void setData(List<Place> data, boolean sort) {
            this.data = data;
            sort(sort);
        }

        private void sort(final boolean asc) {

            Collections.sort(data, new Comparator<Place>() {
                @Override
                public int compare(Place o1, Place o2) {
                    int sort = o1.getPlace_name().compareTo(o2.getPlace_name());
                    if(asc) {
                        return sort;
                    } else {
                        return -sort;
                    }
                }
            });
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.place, viewGroup, false);
            return new PlaceViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PlaceViewHolder placeViewHolder, int i) {
            Place place = data.get(i);
            placeViewHolder.bind(place);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        private void remove(Place place) {
            int index = data.indexOf(place);
            if(index != -1) {
                data.remove(index);
                notifyItemRemoved(index);
            }
        }

        private void removeAll() {
            int count = data.size();
            if(count > 0) {
                data.clear();
                notifyItemRangeRemoved(0, count);
            }
        }
    }

    /*public void createContact(View view) {
        CreateContactActivity.start(this);
    }*/

    /*private void showDeleteAllContactsDialog() {

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.delete_contacts_dialog_title)
                .setMessage(R.string.delete_contacts_dialog_message)
                .setPositiveButton(R.string.delete_contact_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteAllContacts();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null) // Se não precisamos de tratar o evento, podemos passar null
                .setCancelable(false) // cancelable a false evita que o utilizador possa dispensar o dialog pressionando fora da caixa deste
                .create();

        dialog.show(); // não esquecer invocar o método para exibir o diálogo
    }

    private void deleteAllContacts() {
        int deletedCount = ChatDatabase.getInstance(this).contactDao().deleteAll();

        // Ver Plurals em strings.xml (https://developer.android.com/guide/topics/resources/string-resource#Plurals)
        String quantityString = getResources().getQuantityString(R.plurals.deleted_contacts_count_toast, deletedCount, deletedCount);

        Toast.makeText(this, quantityString, Toast.LENGTH_SHORT).show();
        contactAdapter.removeAll();
        setAddContactHintVisible(true);
    }


    private void showDeleteContactDialog(final Contact contact) {

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.delete_contact_dialog_title)
                .setMessage(getString(R.string.delete_contact_dialog_message, contact.getName()))
                .setPositiveButton(R.string.delete_contact_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteContact(contact);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();

        dialog.show();
    }

    private void deleteContact(Contact contact) {
        // Prints demonstram que ao eliminar o contacto da BD também as 'suas' mensagens são eliminadas (ver ForeignKey em Message)
        int msgCount = ChatDatabase.getInstance(this).messageDao().messageCountForContact(contact.getId());
        System.out.println("Before removing contact: " + msgCount + " messages.");
        ChatDatabase.getInstance(this).contactDao().delete(contact);
        msgCount = ChatDatabase.getInstance(this).messageDao().messageCountForContact(contact.getId());
        System.out.println("After removing contact: " + msgCount + " messages.");

        contactAdapter.remove(contact);
        setAddContactHintVisible(contactAdapter.getItemCount() == 0);
    }*/

}
