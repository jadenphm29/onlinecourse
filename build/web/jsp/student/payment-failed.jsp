
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    <title>Payment Failed</title>
</head>
<body>
<jsp:include page="/header.jsp" />
    <h2 style="color:red;">
        Payment Failed!
    </h2>

    <p>
        Something went wrong. Please try again.
    </p>

    <a href="${pageContext.request.contextPath}/myCourses">
        Back
    </a>
<jsp:include page="/footer.jsp" />
</body>
</html>