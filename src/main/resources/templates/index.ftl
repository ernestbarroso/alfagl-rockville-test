<html>
<head>
    <title>File Upload</title>
</head>
<body>
<h2>Welcome to the File Upload App</h2>
<form method="POST" name="uploadingForm" action="upload" enctype="multipart/form-data">
    <table>
        <tr>
            <td>Select a file to upload</td>
            <td><input type="file" name="file" /></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit" /></td>
        </tr>
    </table>
    <a href="showdata" >show data</a>
</form>
</body>
</html>