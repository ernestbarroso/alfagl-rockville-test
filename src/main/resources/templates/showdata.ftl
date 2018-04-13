<html>
<head>
    <title>File Upload</title>
</head>
<body>
<h2>Current date on the database</h2>
<table>
    <#if dataList??>
        <#list dataList as item>
        <tr>
            <td>Value:</td>
            <td>${data.originalFilename}</td>
        </tr>
        <tr>
            <td>Name:</td>
            <td>${data.contentType}</td>
        </tr>
        </#list>
    <#else>
        <p>no records to show</p>
    </#if>
</table>
<a href="/alfagl/" >return ssss</a>

</body>
</html>