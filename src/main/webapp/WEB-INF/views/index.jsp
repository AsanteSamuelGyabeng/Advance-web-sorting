<%@ page import="services.RunAlgo" %>
<%@ page import="java.util.*" %>
<html>
<head>
    <title>Sorting Algorithm Results</title>
</head>
<body>

<h2>Sorting Algorithm Results</h2>

<!-- Form to trigger sorting -->
<form method="post">
    <input type="submit" value="Sort Students">
</form>

<%
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String sortingResults = RunAlgo.getSortedResults();
%>

<!-- Display the sorted results -->
<h3>Sorted Results:</h3>
<pre>
<%= sortingResults %>
</pre>

<% } %>

</body>
</html>
