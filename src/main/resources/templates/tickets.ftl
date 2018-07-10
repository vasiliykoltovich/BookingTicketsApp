<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tickets</title>
    <link href="/css/main.css" rel="stylesheet">
</head>
<body>
<h2 class="events-list">Tickets:</h2>


<table>
    <tr>
        <th>Event</th>
        <th>Date</th>
        <th>Seats</th>
        <th>Price</th>
        <th>Email</th>
        <th>Auditorium</th>
    </tr>
    <#list tickets as t>
    <tr>
        <td>${t.event.name}</td>
        <td>${t.dateTime}</td>
        <td>${t.seats}</td>
        <td>${t.price}</td>
        <td>${t.user.email}</td>
        <td>${t.event.auditorium.name}</td>
    </tr>
</#list>
</table>



<script src="/js/main.js"></script>
</body>
</html>