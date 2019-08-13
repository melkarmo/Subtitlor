package com.subtitlor.utilities;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.Part;

import com.subtitlor.conf.SubtitlorConfiguration;

public class FileHandler {
 
    private Part part;
    private String nomFichier;
    
    public FileHandler ( Part part ) {
    	this.part = part;
    }

	public String writeFile() {
        nomFichier = getNomFichier(part);
        if (nomFichier != null && !nomFichier.isEmpty()) {
            // Correction d'un bug d'Internet Explorer
            nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1).substring(nomFichier.lastIndexOf('\\') + 1);
            try {
				ecrireFichier(part, nomFichier, SubtitlorConfiguration.CHEMIN_FICHIERS);
			} catch (IOException e) {
			}
        }
        return nomFichier;
    }
    
    private void ecrireFichier( Part part, String nomFichier, String chemin ) throws IOException {
        BufferedInputStream entree = null;
        BufferedOutputStream sortie = null;
        try {
            entree = new BufferedInputStream(part.getInputStream(), SubtitlorConfiguration.TAILLE_TAMPON);
            sortie = new BufferedOutputStream(new FileOutputStream(new File(chemin + nomFichier)), SubtitlorConfiguration.TAILLE_TAMPON);

            byte[] tampon = new byte[SubtitlorConfiguration.TAILLE_TAMPON];
            int longueur;
            while ((longueur = entree.read(tampon)) > 0) {
                sortie.write(tampon, 0, longueur);
            }
        } finally {
            try {
                sortie.close();
            } catch (IOException ignore) {
            }
            try {
                entree.close();
            } catch (IOException ignore) {
            }
        }
    }
    
    private static String getNomFichier( Part part ) {
        for ( String contentDisposition : part.getHeader( "content-disposition" ).split( ";" ) ) {
            if ( contentDisposition.trim().startsWith( "filename" ) ) {
                return contentDisposition.substring( contentDisposition.indexOf( '=' ) + 1 ).trim().replace( "\"", "" );
            }
        }
        return null;
    }
    
    public static ArrayList<String> getFileNames() {
    	ArrayList<String> fileNames = new ArrayList<String>();
    	File folder = new File(SubtitlorConfiguration.CHEMIN_FICHIERS);
    	File[] listOfFiles = folder.listFiles();
    	for (int i = 0; i < listOfFiles.length; i++) {
    	  fileNames.add(listOfFiles[i].getName());
    	}
    	return fileNames;
    }

}
