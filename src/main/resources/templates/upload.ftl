<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Upload</title>
    <link href="/css/main.css" rel="stylesheet">
</head>
<body>
<h2 class="users-list">Upload Events:</h2>

<form name="uploadingForm" enctype="multipart/form-data" action="/loadEvents" method="POST">
    <p>
        <input type="file" name="file" onchange="updateSize();" multiple>
        selected files: <span id="fileNumE">0</span>;
        total size: <span id="fileSizeE">0</span>
    </p>
    <p>
        <input type="submit" value="Upload files">
    </p>
</form>

<h2 class="users-list">Upload Users:</h2>
<form name="uploadingForm" enctype="multipart/form-data" action="/loadUsers" method="POST">
    <p>
        <input type="file" name="file" onchange="updateSize();" multiple>
        selected files: <span id="fileNumU">0</span>;
        total size: <span id="fileSizeU">0</span>
    </p>
    <p>
        <input type="submit" value="Upload files">
    </p>
</form>

<script src="/js/main.js"></script>
</body>
</html>