<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
<title>Upload File Request Page</title>
</head>
<body>

	<form method="POST" action="uploadFile" enctype="multipart/form-data">
		File to upload: <input type="file" name="file"><br /> 
		Name: <input type="text" name="name"><br /> <br /> 
		Highlight Text: <input type="text" name="hname"><br /> <br /> 
		Type of Text: 
		<select name="type">
  			<option value="name">swimmer's Name</option>
 			 <option value="team">team name</option>
 			 </select>
		<input type="submit" value="Upload"> Press here to upload the file (pdf please in order to test pdf parser)!
	</form>
	
</body>
</html>