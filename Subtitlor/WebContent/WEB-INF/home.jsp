<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>Subtitlor</title>
	</head>
	<body>
	
		<h1 style="text-align:center">Subtitlor</h1>
			    
	    <p style="text-align:center">
		    Traduisez vos fichiers de sous-titres avec Subtitlor !
		    <br>Afin de démarrer, importez votre fichier de sous-titres ".srt".
		    <br>Après l'import, le nom de votre fichier s'affiche ci-dessous.
		    <br>Cliquez alors dessus pour démarrer la traduction ;)
	    </p>
		
		<h2>Fichiers .srt envoyés ( ${ numberFiles } )</h2>
		<ul>
			<c:forEach items="${ fileNames }" var="fileName">
				<li><a href="/Subtitlor/edit?fileName=${ fileName }"><c:out value="${ fileName }" /></a></li>
			</c:forEach>
		</ul>
		
		<c:if test="${ !empty fichier }"><p style="color:green"><c:out value="Le fichier ${ fichier } a été uploadé !" /></p></c:if>
	    
	    <form method="post" action="home" enctype="multipart/form-data">
	        <label for="fichier">Fichier à envoyer : </label>
	        <input type="file" name="fichier" id="fichier" accept=".srt" />
	        <input type="submit" value="Envoyer le fichier"/>
	    </form>
	    
	</body>
</html>