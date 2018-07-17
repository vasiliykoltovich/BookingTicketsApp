<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Events</title>
    <link href="/css/main.css" rel="stylesheet">
</head>
<body>
<#include "header.ftl">
<h2 class="events-list">Events:</h2>
<table>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Rate</th>
        <th>Price</th>
        <th>Date</th>
        <th>Auditorium</th>
    </tr>
    <#list events as ev>
    <tr>
        <td>${ev.id}</td>
        <td>${ev.name}</td>
        <td>${ev.rate}</td>
        <td>${ev.basePrice}</td>
        <td>${ev.dateTime}</td>
        <td>${ev.auditorium.name}</td>
    </tr>
</#list>
</table>



<script src="/js/main.js"></script>
</body>
</html>