package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.arthenica.mobileffmpeg.Config;
import com.arthenica.mobileffmpeg.FFmpeg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static com.arthenica.mobileffmpeg.FFmpeg.RETURN_CODE_CANCEL;
import static com.arthenica.mobileffmpeg.FFmpeg.RETURN_CODE_SUCCESS;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> imagesArray = new  ArrayList<String>();

    File[] listFile ;

    public final String[] EXTERNAL_PERMS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public final int EXTERNAL_REQUEST = 138;

    String guid = "";
    String nomePortfolio = "";

    File pathToDelete = null;
    File exitFile = null;
    File imgSequences = null;
    File music = null;
    File imageTemp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e(  "INFO"  , "Entrou no executar"  );



        Log.e(  "INFO"  , "Entrou no Renderizar as fotos"  );

        try{

            //JSONObject options = args.getJSONObject(0);

            Log.e(  "INFO"  , "Inicializando o Json"  );

            //String pathImagens = options.getString("pathImagens") ;
            //String idUsuario =  options.getString("idUsuario") ;
            //String nomePortfolio = options.getString("nomePortfolio") ;
            //String serie = options.getString("serie") ;
            //String turma = options.getString("turma") ;
            //String guid = options.getString("guid") ;

            //Começa a renderização

            if (requestForPermission()) {

                // new Thread(new Runnable() {
                //     @Override
                //     public void run() {


                Log.e(  "INFO"  , "Entrando na Thread"  );

                guid = "video-processing-20199393423";

                pathToDelete = new File(android.os.Environment.getExternalStorageDirectory(), "" + guid);

                exitFile = new File(android.os.Environment.getExternalStorageDirectory(), "Android/data/br.com.educacional.pensematematicamobile/files/" + nomePortfolio + ".mp4");

                imageTemp = new File(android.os.Environment.getExternalStorageDirectory(), "" + guid);

                imgSequences = new File(android.os.Environment.getExternalStorageDirectory(), "" + guid + "/temp%03d.jpg");

                music = new File(android.os.Environment.getExternalStorageDirectory(), "Download/music/believer.mp3");

                createBitMap(imageTemp);

                //String anim = "-filter_complex zoompan=z='zoom+0.002':d=25*4:s=854x480";

                //String anim = "-vf zoompan=z='zoom+0.002':d=25*5:s=1280x800 -pix_fmt yuv420p";

                Log.e(  "INFO"  , "Iniciando o executar"  );

                //FFmpeg.execute("-f image2 -r 1/5 -i " + imgSequences + " -i " + music + " -vf scale=854:480,format=yuv420p -c:v mpeg4 " + exitFile);

                //FFmpeg.execute("-f image2 -r 1/5 -i " + imgSequences + " -i " + music + " -c:v mpeg4 " + anim + " -s 854x480 " + exitFile);

                //FFmpeg.execute("-f image2 -r 1/6 -i " + imgSequences + " -i " + music + " -c:v mpeg4 " + anim + " -s 854x480 " + exitFile);

                //Log.e(  "INFO"  , "Finalizou o executar"  );

                //int rc = FFmpeg.getLastReturnCode();

                //String output = FFmpeg.getLastCommandOutput();

                //if (rc == RETURN_CODE_SUCCESS) {
                //  Log.e(Config.TAG, "Command execution completed successfully.");
                //} else if (rc == RETURN_CODE_CANCEL) {
                //   Log.e(Config.TAG, "Command execution cancelled by user.");
                //} else {
                //   Log.e(Config.TAG, String.format("Command execution failed with rc=%d and output=%s.", rc, output));
                //}

                //FFmpeg.cancel();

                //Log.e(  "INFO"  , "Deletando pasta"  );
                //deleTeAllItems(pathToDelete);
                //Log.e(  "INFO"  , "Pasta deletada"  );


                //     }
                // }).start();

            }

        }
        catch( Exception e){
            Log.e(  "INFO"  , "Houve algum erro " + e.getMessage()  );

        }






    }

    public  void createBitMap(File path){

        ArrayList<Bitmap> listBitmap = new ArrayList<Bitmap>();

        //if( path.exists() ) {

        if (requestForPermission()) {
            File[] files = path.listFiles();

            for (int i = 0; i < files.length; i++) {

                Bitmap bitmap = BitmapFactory.decodeFile(files[i].getAbsolutePath());

                Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0 , 0 ,bitmap.getWidth(), bitmap.getHeight()  );

                listBitmap.add(newBitmap);


            }

        }
        //}

        String newPath = path.getAbsolutePath() ;





    }


    public boolean deleTeAllItems(File path){
        if( path.exists() ) {
            File[] files = path.listFiles();
            if (files == null) {
                return true;
            }
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleTeAllItems(files[i]);
                }
                else {
                    files[i].delete();
                }
            }
        }
        return( path.delete() );

    }

    public void getImagesFromExternal(){

        File file = new File(android.os.Environment.getExternalStorageDirectory(),"Download/video-portfolio");


        requestForPermission();


        if(file.isDirectory())
        {
            listFile = file.listFiles();
            for (int i = 0; i < listFile.length; i++)
            {
                imagesArray.add(listFile[i].getAbsolutePath());
            }
        }

    }

    public boolean requestForPermission() {

        boolean isPermissionOn = true;
        final int version = Build.VERSION.SDK_INT;
        if(version >= 23) {
            if(!canAccessExternalSd()) {
                isPermissionOn = false;
                requestPermissions( EXTERNAL_PERMS, EXTERNAL_REQUEST);
            }
        }

        return isPermissionOn;
    }

    public boolean canAccessExternalSd() {
        return (hasPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE));
    }

    private boolean hasPermission(String perm) {
        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, perm));

    }



}
