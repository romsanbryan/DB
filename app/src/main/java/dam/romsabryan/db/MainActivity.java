package dam.romsabryan.db;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Aplicacion de una base de datos de "estudiantes" en dispositivo Android.
 * En esta base de datos podemos realizar las operaciones de creacion escritura, lectura, actualizacion y borrado
 *
 * @author romsanbryan
 * @version 1.0.0  2018-02-01
 * @since API 15
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DataBaseHelper(this); // Definimos la clase pasandole el contexto

        // Definicion de objetos
        editName = (EditText) findViewById(R.id.editText_name); // Student Name
        editSubname = (EditText) findViewById(R.id.editText_subname); // Student Subname
        editMarks = (EditText) findViewById(R.id.editText_marks); // Student Marks
        editID = (EditText) findViewById(R.id.editText_id); // Student ID

        btnAddData = (Button) findViewById(R.id.button_add); // Button add
        btnViewAll = (Button) findViewById(R.id.button_view); // Button view
        btnUpdate = (Button) findViewById(R.id.button_update); // Button update
        btnDel = (Button) findViewById(R.id.button_del); // Button delete

        // Metodos
        addData();
        viewAll();
        updateData();
        del();
    }

    /**
     * Acción de boton para introducir datos. Si lo inserta correctamente manda un Toast con mensaje de informacion
     * si no, manda con mensaje de datos no insertados
     */
    private void addData(){
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean  isInserted = myDB.insertData(editName.getText().toString(),
                        editSubname.getText().toString(), editMarks.getText().toString());
                if (isInserted == true)
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();

            }
        });
    }

    /**
     * Acción de boton para visualizar datos.
     */
    private void viewAll(){
        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDB.getAllData();
                if (res.getCount() == 0){
                    // show message
                    showMessage("Error", "Nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("ID: "+res.getString(0)+"\n");
                    buffer.append("Name: "+res.getString(1)+"\n");
                    buffer.append("Subname: "+res.getString(2)+"\n");
                    buffer.append("Marks: "+res.getString(3)+"\n\n");

                }
                // Show all Data
                showMessage("Data", buffer.toString());
            }
        });
    }

    /**
     * Mensaje de AlertDialog para mostrar datos de la consulta
     * @param title Titulo de la ventana, si no hay datos nos mostrara "error" y si los hay "data"
     * @param message Mensaje de la ventana, muestra los datos de la base de datos si hubiera
     */
    private void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    /**
     * Accion del boton actualizar. Si lo actualiza correctamente manda un Toast con mensaje de informacion
     * si no, manda con mensaje de datos no actualizados
     */
    private void updateData(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate = myDB.updateData(editID.getText().toString(), editName.getText().toString(),
                        editSubname.getText().toString(), editMarks.getText().toString());
                if (isUpdate == true)
                    Toast.makeText(MainActivity.this, "Data Update", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data not Update", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Accion del boton borrar. Si lo borra correctamente manda un Toast con mensaje de informacion
     * si no, manda con mensaje de datos no borra
     */
    private void del(){
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer delRows = myDB.deleteData(editID.getText().toString());
                if (delRows > 0)
                    Toast.makeText(MainActivity.this, "Data Delete", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Data not Delte", Toast.LENGTH_LONG).show();

            }
        });
    }

    // Variables
    private DataBaseHelper myDB; // Objeto de clase
    private EditText editName, editSubname, editMarks, editID; // EditText para recoger valores
        // Botones
    private Button btnAddData; // Add
    private Button btnViewAll; // View
    private Button btnUpdate; // Update
    private Button btnDel; // Delete
}
