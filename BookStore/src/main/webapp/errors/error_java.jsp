<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Error</title>
		<meta http-equiv="Content-Security-Policy" content="default-src 'none'; script-src 'self'; connect-src 'self'; img-src 'self'; style-src 'self'; base-uri 'self'; form-action 'self';">
    </head>
    <body>
        <h1> Java Error</h1>
        <p> Sorry, Java has thrown an exception.</p>
        <p>To continue, click the Back button.</p>
        <h2> Details</h2>
        <p>Type: ${pageContext.exception["class"]}</p>
        <p> Message: ${pageContext.exception.message}</p>
    </body>
</html>

