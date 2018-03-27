package com.dam.financialgame.services;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dam.financialgame.controllers.Escenario;
import com.dam.financialgame.iservices.almacenJuego;

import java.util.Collections;
import java.util.Vector;


public class AlmacenJuegoImpl extends SQLiteOpenHelper implements almacenJuego {
    // Hacemos uso de las bases de datos
    /* Recuerda, para borrar base de datos en dispositivo android:
        1º: Accedemos a adb shell: adb shell
        2º: Accedemos como super usuario: su
        3º: Borramos el fichero correspondiente a la base de datos que queremos borrar: rm /data/data/com.dam.consultamedicacalendario/databases/citas
     */
    private static final String TAG = Escenario.class.getSimpleName();
    // La base de datos hay que limpiar las tabla jugadores cuando temrina partida
    public AlmacenJuegoImpl(Context context) {
        super(context, "juegoMesa", null, 1);
    }

    // Métodos de SQLiteOpenHelper
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE cartas ("+"_id INTEGER PRIMARY KEY AUTOINCREMENT, "+"nombre TEXT, tipo TEXT, atributo1 INTEGER, atributo2 INTEGER, atributo3 INTEGER, atributo4 INTEGER, atributo5 INTEGER)");
        db.execSQL("CREATE TABLE atributos ("+"_id INTEGER PRIMARY KEY AUTOINCREMENT, "+"descripcion TEXT ,"+"sentido TEXT ,"+"cantidad INTEGER)");
        // En nuestro caso, las variables son: acciones, bienesInmuebles, bonosEstado, planPensiones, efectivo, (-)financiacion
        // El cash de un jugador sería: efectivo + alquileres + intereses
        db.execSQL("CREATE TABLE jugadores ("+"_id INTEGER PRIMARY KEY AUTOINCREMENT, "+"nombre TEXT, puntuacion INTEGER, variable1 INTEGER, variable2 INTEGER, variable3 INTEGER, variable4 INTEGER, variable5 INTEGER, variable6 INTEGER)");
        // Creamos una tabla donde guardaremos un historial de los jugadores para, por ejemplo, representarlo en gráficas
        db.execSQL("CREATE TABLE historialJugadores ("+"_id INTEGER PRIMARY KEY AUTOINCREMENT, "+"nombre TEXT, ronda INTEGER, puntuacion INTEGER)");
        db.execSQL("CREATE TABLE mazo ("+"_id INTEGER PRIMARY KEY AUTOINCREMENT, "+"id_carta INTEGER)");

        // Rellenamos las tablas
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Tipo interes variable', '↥', null)");                     //1
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Tipo interes variable', '↧', null)");                     //2
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Tipo interes variable', '↥↥', null)");                    //3
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Tipo interes variable', '↧↧', null)");                    //4
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Precio mercado inmoviliario', '↥', null)");               //5
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Precio mercado inmoviliario', '↧', null)");               //6
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Cotizacion acciones europeas', '↥', null)");              //7
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Cotizacion acciones europeas', '↧', null)");              //8
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Cotizacion acciones europeas', '↥↥', null)");             //9
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Cotizacion acciones europeas', '↧↧', null)");             //10
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Cotizacion acciones americanas', '↥', null)");            //11
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Cotizacion acciones americanas', '↧', null)");            //12
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Cotizacion acciones americanas', '↥↥', null)");           //13
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Cotizacion acciones americanas', '↧↧', null)");           //14
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Cotizacion acciones emergentes', '↥', null)");            //15
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Cotizacion acciones emergentes', '↧', null)");            //16
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Cotizacion acciones emergentes', '↥↥', null)");           //17
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Cotizacion acciones emergentes', '↧↧', null)");           //18
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Cotizacion acciones emergentes', '↥↥↥', null)");          //19
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Cotizacion acciones emergentes', '↧↧↧', null)");          //20
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Acciones europeas', null, 3000)");                        //21
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Acciones americanas', null, 5000)");                      //22
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Acciones emergentes', null, 7000)");                      //23
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Acciones europeas', null, 7000)");                        //24
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Acciones americanas', null, 3000)");                      //25
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Acciones emergentes', null, 5000)");                      //26
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Acciones europeas', null, 5000)");                        //27
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Acciones americanas', null, 7000)");                      //28
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Acciones emergentes', null, 3000)");                      //29
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Necesidad al terminar la ronda', null, 5000)");           //30
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Necesidad al terminar la ronda', null, 10000)");          //31
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Necesidad al terminar la ronda', null, 15000)");          //32
        db.execSQL("INSERT INTO atributos VALUES ( null, 'Necesidad al terminar la ronda', null, 20000)");          //33

        // Valor cero en columna atributo quiere decir que no hay
        db.execSQL("INSERT INTO cartas VALUES ( null, 'Variaciones mercado financiero', 'Escenario financiero', 1, 6, 10, 14, 17)");    //1
        db.execSQL("INSERT INTO cartas VALUES ( null, 'Variaciones mercado financiero', 'Escenario financiero', 1, 6, 8, 14, 20)");     //2
        db.execSQL("INSERT INTO cartas VALUES ( null, 'Variaciones mercado financiero', 'Escenario financiero', 2, 5, 9, 13, 19)");     //3
        db.execSQL("INSERT INTO cartas VALUES ( null, 'Variaciones mercado financiero', 'Escenario financiero', 2, 5, 7, 13, 18)");     //4
        db.execSQL("INSERT INTO cartas VALUES ( null, 'Variaciones mercado financiero', 'Escenario financiero', 2, 5, 7, 11, 15)");     //5
        db.execSQL("INSERT INTO cartas VALUES ( null, 'Variaciones mercado financiero', 'Escenario financiero', 2, 5, 7, 11, 18)");     //6
        db.execSQL("INSERT INTO cartas VALUES ( null, 'Variaciones mercado financiero', 'Escenario financiero', 1, 6, 8, 12, 15)");     //7
        db.execSQL("INSERT INTO cartas VALUES ( null, 'Variaciones mercado financiero', 'Escenario financiero', 1, 6, 17, 0, 0)");      //8
        db.execSQL("INSERT INTO cartas VALUES ( null, 'Variaciones mercado financiero', 'Escenario financiero', 2, 5, 8, 14, 15)");     //9
        db.execSQL("INSERT INTO cartas VALUES ( null, 'Reparto dividendos', 'Reparto', 21, 22, 23, 0, 0)");                             //10
        db.execSQL("INSERT INTO cartas VALUES ( null, 'Reparto dividendos', 'Reparto', 24, 25, 26, 0, 0)");                             //11
        db.execSQL("INSERT INTO cartas VALUES ( null, 'Reparto dividendos', 'Reparto', 27, 28, 29, 0, 0)");                             //12
        db.execSQL("INSERT INTO cartas VALUES ( null, 'Desempleo temporal', 'Necesidades financieras', 31, 0, 0, 0, 0)");               //13
        db.execSQL("INSERT INTO cartas VALUES ( null, 'Gastos universitarios', 'Necesidades financieras', 31, 0, 0, 0, 0)");            //14
        db.execSQL("INSERT INTO cartas VALUES ( null, 'Adquisicion de vehiculos', 'Necesidades financieras', 33, 0, 0, 0, 0)");         //15
        db.execSQL("INSERT INTO cartas VALUES ( null, 'Reformas en el hogar', 'Necesidades financieras', 32, 0, 0, 0, 0)");             //16
        db.execSQL("INSERT INTO cartas VALUES ( null, 'Viaje familiar', 'Necesidades financieras', 30, 0, 0, 0, 0)");                   //17

        // El mazo tendrá x cartas
        db.execSQL("INSERT INTO mazo VALUES ( null, 1)");       //x1
        db.execSQL("INSERT INTO mazo VALUES ( null, 2)");       //x1
        for (int i = 0; i < 2; i++)
            db.execSQL("INSERT INTO mazo VALUES ( null, 3)");       //x2
        for (int i = 0; i < 2; i++)
            db.execSQL("INSERT INTO mazo VALUES ( null, 4)");       //x2
        for (int i = 0; i < 9; i++)
            db.execSQL("INSERT INTO mazo VALUES ( null, 5)");       //x9
        for (int i = 0; i < 3; i++)
            db.execSQL("INSERT INTO mazo VALUES ( null, 6)");       //x3
        for (int i = 0; i < 8; i++)
            db.execSQL("INSERT INTO mazo VALUES ( null, 7)");       //x8
        for (int i = 0; i < 4; i++)
            db.execSQL("INSERT INTO mazo VALUES ( null, 8)");       //x4
        for (int i = 0; i < 2; i++)
            db.execSQL("INSERT INTO mazo VALUES ( null, 9)");       //x2
        db.execSQL("INSERT INTO mazo VALUES ( null, 10)");      //x1
        db.execSQL("INSERT INTO mazo VALUES ( null, 11)");      //x1
        db.execSQL("INSERT INTO mazo VALUES ( null, 12)");      //x1
        db.execSQL("INSERT INTO mazo VALUES ( null, 13)");      //x1
        db.execSQL("INSERT INTO mazo VALUES ( null, 14)");      //x1
        db.execSQL("INSERT INTO mazo VALUES ( null, 15)");      //x1
        db.execSQL("INSERT INTO mazo VALUES ( null, 16)");      //x1
        db.execSQL("INSERT INTO mazo VALUES ( null, 17)");      //x1
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int nerVersion) {
        // En caso de una nueva versión habría que actualizar las tablas
    }

    // Guardamos informacion de un jugador, cada jugador tendra varias filas de informacion segun el numero de rondas. Siempre tendran una inicial con valores cero //!!
    public void setInfoJugador(String nombre, int rondaActual, int variable1, int variable2, int variable3, int variable4, int variable5, int variable6) {
        SQLiteDatabase db = getReadableDatabase();
        boolean existe = false;

        // Primero comprobamos si existe ya en la base de datos
        Cursor cursor = db.rawQuery("SELECT nombre FROM jugadores WHERE nombre='"+nombre+"'", null);
        if(cursor.getCount() > 0){
            existe = true;
        }
        cursor.close();

        db = getWritableDatabase();
        if (existe) { // Existe por lo que actualizamos la información de sus variables y puntuacion total
            db.execSQL("UPDATE jugadores SET variable1=" + variable1 + ", variable2=" + variable2 + ", variable3=" + variable3 + ", variable4=" + variable4 + ", variable5=" + variable5 + ", variable6=" + variable6 +" WHERE nombre='" + nombre + "'");
            db.execSQL("UPDATE jugadores SET puntuacion=" + (variable1 + variable2 + variable3 + variable4 + variable5 - variable6) + " WHERE nombre='" + nombre + "'");

            db.execSQL("INSERT INTO historialJugadores VALUES ( null, '"+nombre+"', "+rondaActual+", "+(variable1 + variable2 + variable3 + variable4 + variable5 - variable6)+")");
        }
        else    // No existe por lo que insertamos un nuevo jugador y su puntuacion a cero
            db.execSQL("INSERT INTO jugadores VALUES ( null, '"+nombre+"', 0, 0, 0, 0, 0, 0, 0)");

        db.close();
    }

    // Obtengo un vector de los jugadores y su puntuacion, ordenados descendente
    public Vector<String> getInfoJugadores() {
        Vector<String> result = new Vector<String>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT nombre, puntuacion FROM jugadores ORDER BY puntuacion DESC", null);
        while (cursor.moveToNext()){
            result.add((cursor.getPosition()+1)+". " + cursor.getString(0)+":    "+cursor.getString(1));
        }
        cursor.close();
        db.close();

        return result;
    }

    // Obtengo unicamente un vector de los jugadores almacenados
    public Vector<String> getJugadores() {
        Vector<String> result = new Vector<String>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT nombre FROM jugadores ORDER BY nombre", null);
        while (cursor.moveToNext()){
            result.add(cursor.getString(0));
        }
        cursor.close();
        db.close();

        return result;
    }

    // Obtenemos el historial de puntuaciones totales de un jugador
    public Vector<Vector<Integer>> getHistorialJugador(String nombreJugador) {
        Vector<Vector<Integer>> historialJugador = new Vector<Vector<Integer>>();
        SQLiteDatabase db = getReadableDatabase();
        int fila = 0;

        Cursor cursor = db.rawQuery("SELECT ronda,puntuacion FROM historialJugadores WHERE nombre = '"+nombreJugador+"' ORDER BY ronda", null);
        while (cursor.moveToNext()){
            historialJugador.add(new Vector<Integer>());
            historialJugador.get(fila).add(cursor.getInt(0));
            historialJugador.get(fila).add(cursor.getInt(1));
            fila ++;
        }
        cursor.close();
        db.close();

        return historialJugador;
    }

    // Obtengo un vector con el valor de los atributos de la puntuación final, en orden
    public Vector<Integer> getPuntuacionAtributosJugador (String nombreJugador) {
        Vector<Integer> result = new Vector<Integer>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT puntuacion, variable1, variable2, variable3, variable4, variable5, variable6 FROM jugadores WHERE nombre = '"+nombreJugador+"'", null);
        while (cursor.moveToNext()){
            result.add(cursor.getInt(0));
            result.add(cursor.getInt(1));
            result.add(cursor.getInt(2));
            result.add(cursor.getInt(3));
            result.add(cursor.getInt(4));
            result.add(cursor.getInt(5));
            result.add(cursor.getInt(6));
        }
        cursor.close();
        db.close();

        return result;
    }

    public void vaciarTablaJugadores() {
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("DELETE FROM jugadores");
        db.execSQL("DELETE FROM historialJugadores");
        db.close();
    }

    public Vector<Integer> getIndicesCartas() {
        Vector<Integer> indicesCartasMazo = new Vector<Integer>();
        SQLiteDatabase db = getReadableDatabase();

        // Obtenemos el conjunto de indices de cartas del mazo, para luego trabajar con ellas
        Cursor cursor = db.rawQuery("SELECT id_carta FROM mazo", null);
        while (cursor.moveToNext()) {
            indicesCartasMazo.add(cursor.getInt(0)); // Obtenemos el id de la carta finalmente buscada.
        }
        cursor.close();
        db.close();

        // Barajamos aleatoriamente el orden de los elementos del vector
        Collections.shuffle(indicesCartasMazo);
        // Para depurar
        for (int i = 0; i < indicesCartasMazo.size(); i++) {
            Log.i("getIndicesCartas()", "valor del vector de indices barajado es "+indicesCartasMazo.get(i));
        }
        return indicesCartasMazo;
    }

    public Vector<String> obtenerCartaDelMazo(int indice) {
        // Defino un vector que tendrá la siguiente info de la carta seleccionada: nombre, tipo, atributos
        // Más tarde, en el código cliente, según el tipo de la carta, se pone una imagen u otra
        Vector<String> result = new Vector<String>();
        int idAtributo[] = new int[5];     // Id del atributo de la carta finalmente elegida

        SQLiteDatabase db = getReadableDatabase();

        // Rellenamos el vector result con el nombre de la carta y el tipo
        Cursor cursor = db.rawQuery("Select * FROM cartas WHERE _id="+indice, null);
        while (cursor.moveToNext()) {
            result.add(cursor.getString(1));
            result.add(cursor.getString(2));
            idAtributo[0] = cursor.getInt(3);
            idAtributo[1] = cursor.getInt(4);
            idAtributo[2] = cursor.getInt(5);
            idAtributo[3] = cursor.getInt(6);
            idAtributo[4] = cursor.getInt(7);
            Log.d(TAG, "atributo1: "+idAtributo[0]+" atributo 2: "+idAtributo[1]+" aributo 3: "+idAtributo[2]+" Atributo 4: "+idAtributo[3]+" Atributo 5: "+idAtributo[4]); // Para depurar
        }

        // Obtenemos los atributos de la carta finalmente elegida y rellenamos result
        for (int i = 0; i < 5; i++ ) {
            if (idAtributo[i] != 0){
                cursor = db.rawQuery("SELECT * FROM atributos WHERE _id="+idAtributo[i], null);
                while (cursor.moveToNext()) {
                    if (cursor.getString(2) == null) {
                        result.add(cursor.getString(1) + "  " + cursor.getString(3));
                    } else if (cursor.getString(3) == null) {
                        result.add(cursor.getString(1) + "  " + cursor.getString(2));
                    } else {
                        result.add(cursor.getString(1) + "  " + cursor.getString(2)+ "  " + cursor.getString(3));
                    }
                }
            } else{
                result.add("0");
            }
        }

        return result;
    }

    public String getJugadorGanador() {
        String nombreGanadorPuntuacion = "";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT nombre, Max(puntuacion) FROM jugadores", null);
        while (cursor.moveToNext()){
            // El string enviado obtinene el nombre del jugador ganador y su puntuación
            nombreGanadorPuntuacion = cursor.getString(0)+":  "+Integer.toString(cursor.getInt(1));
        }
        cursor.close();
        db.close();

        return  nombreGanadorPuntuacion;
    }
}
