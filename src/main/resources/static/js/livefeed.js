<script>
    var evtSource = new EventSource("http://localhost:8080/pets");
    evtSource.onmessage = function (event) {
        var data = JSON.parse(event.data);
        var table = document.getElementById("pets-table");
        var row = table.insertRow(1);
        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        var cell3 = row.insertCell(2);
        var cell4 = row.insertCell(3);

        
        var email = data.email;
        var shelterAddress = '<p>' + data.address1 + '<p>' + data.city + '<p> ' + data.state + '</p>'

        cell1.innerHTML = '<td> <img class="img-thumbnail" src="' + data.thumbnailImage + '" width="150" height="100"/> </td>';
        cell2.innerHTML = '<td>' + data.name + '</td>';
        cell3.innerHTML = '<td>' + email + '</td>';
        cell4.innerHTML = '<td>' + shelterAddress + '</td>';

    }
</script>