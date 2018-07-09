<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tickets</title>
    <link href="/css/main.css" rel="stylesheet">
</head>
<body>
<h2 class="users-list">Booked Tickets:</h2>

<table>
    <tr>
        <th>Id</th>
        <th>Identification Number</th>
        <th>Arrival</th>
        <th>Departure</th>
    </tr>
    <#list tickets as t>
    <tr>
        <td>${t.id}</td>
        <td>${t.identification}</td>
        <td>${t.arrival}</td>
        <td>${t.departure}</td>
    </tr>
</#list>
</table>



<script src="/js/main.js"></script>
</body>
</html>