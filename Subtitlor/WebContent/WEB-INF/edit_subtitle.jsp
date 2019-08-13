<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
		<title>Subtitlor</title>
	</head>
	<body>
	
		<c:if test="${ !empty erreur }"><p style="color:red;"><c:out value="${ erreur }" /></p></c:if>
		
	    <form method="post" action="edit">
	    
	        <input type="submit" style="position:fixed; top: 10px; right: 10px;" value="Enregistrer" />
	        <a href="/Subtitlor/home" type="submit" style="position:fixed; top: 50px; right: 10px;">Retourner à l'accueil</a>
	        <a href="/Subtitlor/export?fileName=${ fileName }" type="submit" style="position:fixed; top: 80px; right: 10px;">Exporter la traduction</a>
	        <span style="position:fixed; top: 100px; right: 10px;"><i>(Veillez à enregistrer avant l'export)</i></span>
	        
	        <h1><b><c:out value="${ fileName }" /></b></h1>
	        
	        <c:if test="${ empty erreur && !empty success }"><p style="color:green;"><c:out value="${ success }" /></p></c:if>
	        
		    <table>
		    
		    	<c:forEach items="${ blocks }" var="block" varStatus="status">
		    	
		    		<c:if test="${ block.idLine == 0 }">
		        		<tr>
			        		<td style="text-align:right; padding-top:15px"><b><c:out value="${ block.timeInterval }" /></b></td>
		        		</tr>
		        	</c:if>
		    	
		        	<tr>
		        		<td style="text-align:right;"><c:out value="${ block.subtitles }" /></td>
		        		<td>
		        			<input type="text" 
		        				name="${ block.id }_${ block.idLine }" 
		        				id="${ block.id }_${ block.idLine }" 
		        				size="50"
		        				value="${ savedBlocks[status.index].subtitles }" />
		        			</td>
		        	</tr>
		        	
		    	</c:forEach>
		    	
		    	<tr>
		    		<td style="text-align:right; padding-top:15px;"><h2><i>Fin</i></h2></td>
		    	</tr>
		    	
		    </table>
		    
	    </form>
	    
	</body>
</html>