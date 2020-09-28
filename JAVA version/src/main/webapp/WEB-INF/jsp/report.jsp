<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<body>
	<div class="container">

		 Generated report!


		<br>
        <button type="button" name="Go back" onclick="history.back()">back</button>
        <br>
		<spring:url value="/home" var="homeUrl" />
		<button class="btn btn-info"  onclick="location.href='${homeUrl}'">Home</button>
		</br>
	</div>
</body>
</html>