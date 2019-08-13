package com.subtitlor.conf;

public class SubtitlorConfiguration {
	
	/* Deux répértoires vous seront nécessaires :
	 * 1) un répertoire de stockage temporaire pour l'upload de fichiers .srt, 
	 * veuillez renseigner son chemin dans le WEB-INF>lib>web.xml dans la balise <multipart-config><location>
	 * 2) un répertoire de stockage des fichiers .srt,
	 * veuillez renseigner son chemin ci-dessous dans le champ CHEMIN_FICHIERS
	 */
	
	// Configuration pour l'upload de fichiers
    public static final String CHEMIN_FICHIERS = "/Users/melkarmo/Documents/fichiers/"; // chemin de stokage des fichiers
    public static final int TAILLE_TAMPON = 10240;
    
    // Configuration base de données mysql
    public static final String DATABASE_PORT = "3306"; // port
    public static final String DATABASE_LOGIN = "root"; // login
    public static final String DATABASE_PWD = "root"; // mot de passe
    public static final String DATABASE_NAME = "subtitlor_db"; // nom de la base de donnée
    public static final String DATABASE_TABLE = "traduction"; // nom de la table utilisée
    
    /* Pour importer le fichier 'import_table.sql' depuis MySQL Command Line :
     * 1) create database subtitlor_db; (vous pouvez reprendre la votre, veillez cependant à changer le DATABASE_NAME ci-dessus)
     * 2) use subtitlor_db;
     * 3) source import_table.sql;
     * Une table 'traduction' est alors créée ! Vous pouvez alors démarrer l'appli ;)
     * (démarrez la servlet Home [http://localhost:8080/Subtitlor/home] ^^)
     */
    
    

}
